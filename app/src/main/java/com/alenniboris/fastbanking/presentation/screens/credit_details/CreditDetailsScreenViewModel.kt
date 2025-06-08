package com.alenniboris.fastbanking.presentation.screens.credit_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alenniboris.fastbanking.domain.model.CustomResultModelDomain
import com.alenniboris.fastbanking.domain.usecase.logic.credits.IChangeCreditNameUseCase
import com.alenniboris.fastbanking.domain.usecase.logic.credits.IGetCreditByIdUseCase
import com.alenniboris.fastbanking.domain.utils.SingleFlowEvent
import com.alenniboris.fastbanking.presentation.mappers.toUiMessageString
import com.alenniboris.fastbanking.presentation.model.bank_product.toModelUi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CreditDetailsScreenViewModel(
    private val creditId: String,
    private val getCreditByIdUseCase: IGetCreditByIdUseCase,
    private val changeCreditNameUseCase: IChangeCreditNameUseCase
) : ViewModel() {

    private val _screenState = MutableStateFlow(CreditDetailsScreenState())
    val state = _screenState.asStateFlow()

    private val _event = SingleFlowEvent<ICreditDetailsScreenEvent>(viewModelScope)
    val event = _event.flow

    init {
        loadCredit()
    }

    private fun loadCredit() {
        viewModelScope.launch {
            _screenState.update { it.copy(isLoading = true) }

            when (val res = getCreditByIdUseCase.invoke(creditId)) {
                is CustomResultModelDomain.Success -> {
                    _screenState.update { it.copy(credit = res.result?.toModelUi()) }
                }

                is CustomResultModelDomain.Error -> {
                    _event.emit(
                        ICreditDetailsScreenEvent.ShowToastMessage(
                            res.exception.toUiMessageString()
                        )
                    )
                }
            }

            _screenState.update { it.copy(isLoading = false) }
        }
    }

    fun proceedIntent(intent: ICreditDetailsScreenIntent) {
        when (intent) {
            is ICreditDetailsScreenIntent.NavigateBack -> navigateBack()
            is ICreditDetailsScreenIntent.ProceedDetailsAction -> proceedDetailsAction(intent.action)
            ICreditDetailsScreenIntent.ChangeCreditNameSettingsVisibility -> changeCreditNameSettingsVisibility()
            ICreditDetailsScreenIntent.UpdateCreditName -> updateCreditName()
            is ICreditDetailsScreenIntent.UpdateCreditNewName -> updateCreditNewName(intent.newName)
        }
    }

    private fun updateCreditNewName(newName: String) {
        _screenState.update {
            it.copy(
                creditNewName = newName
            )
        }
    }

    private fun updateCreditName() {
        _screenState.value.credit?.let { credit ->
            viewModelScope.launch {
                when (
                    val res = changeCreditNameUseCase.invoke(
                        credit = credit.domainModel,
                        newName = _screenState.value.creditNewName
                    )
                ) {
                    is CustomResultModelDomain.Success -> {
                        changeCreditNameSettingsVisibility()
                        loadCredit()
                    }

                    is CustomResultModelDomain.Error -> {
                        _event.emit(
                            ICreditDetailsScreenEvent.ShowToastMessage(
                                res.exception.toUiMessageString()
                            )
                        )
                    }
                }
            }
        }
    }

    private fun changeCreditNameSettingsVisibility() {
        _screenState.update {
            it.copy(
                isCreditNameSettingsVisible = !it.isCreditNameSettingsVisible,
                creditNewName = ""
            )
        }
    }

    private fun navigateBack() {
        _event.emit(ICreditDetailsScreenEvent.NavigateBack)
    }

    private fun proceedDetailsAction(action: CreditDetailsScreenActions) {
        when (action) {
            CreditDetailsScreenActions.History -> navigateToCreditTransactionsHistoryScreen()
            CreditDetailsScreenActions.Details -> navigateToCreditDetailsInformationScreen()
        }
    }

    private fun navigateToCreditDetailsInformationScreen() {
        _screenState.value.credit?.let { credit ->
            _event.emit(
                ICreditDetailsScreenEvent.NavigateToProductInfoScreen(credit)
            )
        }
    }

    private fun navigateToCreditTransactionsHistoryScreen() {
        _screenState.value.credit?.let { credit ->
            _event.emit(
                ICreditDetailsScreenEvent.NavigateToProductHistoryScreen(credit)
            )
        }
    }
}