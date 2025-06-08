package com.alenniboris.fastbanking.presentation.screens.account_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alenniboris.fastbanking.domain.model.CustomResultModelDomain
import com.alenniboris.fastbanking.domain.usecase.logic.accounts.IChangeAccountNameUseCase
import com.alenniboris.fastbanking.domain.usecase.logic.accounts.IGetAccountByIdUseCase
import com.alenniboris.fastbanking.domain.usecase.logic.cards.IGetFullModelsForAllSimpleCardsUseCase
import com.alenniboris.fastbanking.domain.utils.SingleFlowEvent
import com.alenniboris.fastbanking.presentation.mappers.toUiMessageString
import com.alenniboris.fastbanking.presentation.model.bank_product.AccountModelUi
import com.alenniboris.fastbanking.presentation.model.bank_product.toModelUi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AccountDetailsScreenViewModel(
    private val accountId: String,
    private val getAccountByIdUseCase: IGetAccountByIdUseCase,
    private val changeAccountNameUseCase: IChangeAccountNameUseCase,
    private val getFullModelsForAllSimpleCardsUseCase: IGetFullModelsForAllSimpleCardsUseCase
) : ViewModel() {

    private val _screenState = MutableStateFlow(AccountDetailsScreenState())
    val state = _screenState.asStateFlow()

    private val _event = SingleFlowEvent<IAccountDetailsScreenEvent>(viewModelScope)
    val event = _event.flow

    init {
        loadAccountById()
    }

    private fun loadAccountById() {
        viewModelScope.launch {
            _screenState.update { it.copy(isAccountLoading = true) }

            when (
                val res = getAccountByIdUseCase.invoke(id = accountId)
            ) {
                is CustomResultModelDomain.Success -> {
                    _screenState.update { it.copy(account = res.result?.toModelUi()) }
                    loadAttachedCardsForAccount(account = res.result?.toModelUi())
                }

                is CustomResultModelDomain.Error -> {
                    _event.emit(
                        IAccountDetailsScreenEvent.ShowToastMessage(
                            res.exception.toUiMessageString()
                        )
                    )
                }
            }

            _screenState.update { it.copy(isAccountLoading = false) }
        }
    }

    private fun loadAttachedCardsForAccount(account: AccountModelUi?) {
        account?.let {
            viewModelScope.launch {
                when (
                    val res =
                        getFullModelsForAllSimpleCardsUseCase.invoke(
                            simpleModels = account.domainModel.attachedCards.values.toList()
                        )
                ) {
                    is CustomResultModelDomain.Success -> {
                        _screenState.update { it.copy(attachedCards = res.result.map { it.toModelUi() }) }
                    }

                    is CustomResultModelDomain.Error -> {
                        _event.emit(
                            IAccountDetailsScreenEvent.ShowToastMessage(
                                res.exception.toUiMessageString()
                            )
                        )
                    }
                }
            }
        }
    }

    fun proceedIntent(intent: IAccountDetailsScreenIntent) {
        when (intent) {
            is IAccountDetailsScreenIntent.NavigateBack -> navigateBack()
            is IAccountDetailsScreenIntent.ProceedDetailsAction -> proceedDetailsAction(intent.action)
            is IAccountDetailsScreenIntent.UpdateAttachedCardsSheetVisibility -> updateAccountAttachedCardsSheetVisibility()
            is IAccountDetailsScreenIntent.OpenCardDetailsScreen -> openCardDetailsScreen(intent.cardId)
            is IAccountDetailsScreenIntent.ChangeAccountNameSettingsVisibility -> changeAccountNameSettingsVisibility()
            is IAccountDetailsScreenIntent.UpdateAccountName -> updateAccountName()
            is IAccountDetailsScreenIntent.UpdateAccountNewName -> updateAccountNewName(intent.newName)
        }
    }

    private fun updateAccountName() {
        _screenState.value.account?.let { account ->
            viewModelScope.launch {
                when (
                    val res = changeAccountNameUseCase.invoke(
                        account = account.domainModel,
                        newName = _screenState.value.accountNewName
                    )
                ) {
                    is CustomResultModelDomain.Success -> {
                        changeAccountNameSettingsVisibility()
                        loadAccountById()
                    }

                    is CustomResultModelDomain.Error -> {
                        _event.emit(
                            IAccountDetailsScreenEvent.ShowToastMessage(
                                res.exception.toUiMessageString()
                            )
                        )
                    }
                }
            }
        }
    }

    private fun updateAccountNewName(newName: String) {
        _screenState.update {
            it.copy(accountNewName = newName)
        }
    }

    private fun changeAccountNameSettingsVisibility() {
        _screenState.update {
            it.copy(
                isAccountNameSettingsVisible = !it.isAccountNameSettingsVisible,
                accountNewName = ""
            )
        }
    }

    private fun openCardDetailsScreen(cardId: String) {
        _event.emit(
            IAccountDetailsScreenEvent.OpenCardDetailsScreen(
                cardId = cardId
            )
        )
    }

    private fun proceedDetailsAction(action: AccountDetailsScreenActions) {
        when (action) {
            AccountDetailsScreenActions.History -> openAccountHistoryScreen()
            AccountDetailsScreenActions.Details -> openAccountInformationScreen()
            AccountDetailsScreenActions.AttachedCards -> updateAccountAttachedCardsSheetVisibility()
        }
    }

    private fun updateAccountAttachedCardsSheetVisibility() {
        _screenState.update { it.copy(isAttachedCardsSheetVisible = !it.isAttachedCardsSheetVisible) }
    }

    private fun openAccountHistoryScreen() {
        _screenState.value.account?.let { account ->
            _event.emit(
                IAccountDetailsScreenEvent.NavigateToProductHistoryScreen(account = account)
            )
        }
    }

    private fun openAccountInformationScreen() {
        _screenState.value.account?.let { account ->
            _event.emit(
                IAccountDetailsScreenEvent.NavigateToProductInfoScreen(account = account)
            )
        }
    }

    private fun navigateBack() {
        _event.emit(IAccountDetailsScreenEvent.NavigateBack)
    }
}