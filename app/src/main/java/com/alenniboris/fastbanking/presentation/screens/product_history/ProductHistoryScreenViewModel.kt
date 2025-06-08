package com.alenniboris.fastbanking.presentation.screens.product_history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alenniboris.fastbanking.domain.model.CustomResultModelDomain
import com.alenniboris.fastbanking.domain.usecase.logic.transactions.IGetAllTransactionsForCreditByIdUseCase
import com.alenniboris.fastbanking.domain.usecase.logic.transactions.IGetAllUserTransactionsByCardUseCase
import com.alenniboris.fastbanking.domain.utils.GsonUtil.fromJson
import com.alenniboris.fastbanking.domain.utils.SingleFlowEvent
import com.alenniboris.fastbanking.presentation.mappers.toUiMessageString
import com.alenniboris.fastbanking.presentation.model.bank_product.AccountModelUi
import com.alenniboris.fastbanking.presentation.model.bank_product.CardModelUi
import com.alenniboris.fastbanking.presentation.model.bank_product.CreditModelUi
import com.alenniboris.fastbanking.presentation.model.bank_product.TransactionModelUi
import com.alenniboris.fastbanking.presentation.model.bank_product.toUiModel
import com.alenniboris.fastbanking.presentation.uikit.values.BankProduct
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProductHistoryScreenViewModel(
    private val productType: BankProduct,
    private val product: String,
    private val getAllUserTransactionsByCardUseCase: IGetAllUserTransactionsByCardUseCase,
    private val getAllTransactionsForCreditByIdUseCase: IGetAllTransactionsForCreditByIdUseCase
) : ViewModel() {

    private val _screenState = MutableStateFlow(ProductHistoryScreenState())
    val state = _screenState.asStateFlow()

    private val _event = SingleFlowEvent<IProductHistoryScreenEvent>(viewModelScope)
    val event = _event.flow

    init {
        (product.fromJson<AccountModelUi>())
        when (productType) {
            BankProduct.CARD -> {
                loadCardHistory(card = product.fromJson<CardModelUi>())
            }

            BankProduct.CREDIT -> {
                loadCreditHistory(credit = product.fromJson<CreditModelUi>())
            }

            BankProduct.ACCOUNTS_AND_DEPOSITS -> {
                loadAccountHistory(account = product.fromJson<AccountModelUi>())
            }
        }
    }

    private fun loadAccountHistory(account: AccountModelUi) {
        viewModelScope.launch {
            _screenState.update { it.copy(isLoading = true) }
            when (
                val res =
                    getAllTransactionsForCreditByIdUseCase.invoke(creditId = account.domainModel.id)
            ) {
                is CustomResultModelDomain.Success -> {
                    _screenState.update {
                        it.copy(
                            transactions = res.result.map { it.toUiModel() }
                        )
                    }
                }

                is CustomResultModelDomain.Error -> {
                    _event.emit(
                        IProductHistoryScreenEvent.ShowToastMessage(
                            res.exception.toUiMessageString()
                        )
                    )
                }
            }
            _screenState.update { it.copy(isLoading = false) }
        }
    }

    private fun loadCreditHistory(credit: CreditModelUi) {
        viewModelScope.launch {
            _screenState.update { it.copy(isLoading = true) }
            when (
                val res =
                    getAllTransactionsForCreditByIdUseCase.invoke(creditId = credit.domainModel.id)
            ) {
                is CustomResultModelDomain.Success -> {
                    _screenState.update {
                        it.copy(
                            transactions = res.result.map { it.toUiModel() }
                        )
                    }
                }

                is CustomResultModelDomain.Error -> {
                    _event.emit(
                        IProductHistoryScreenEvent.ShowToastMessage(
                            res.exception.toUiMessageString()
                        )
                    )
                }
            }
            _screenState.update { it.copy(isLoading = false) }
        }
    }

    fun proceedIntent(intent: IProductHistoryScreenIntent) {
        when (intent) {
            is IProductHistoryScreenIntent.UpdateSelectedTransaction ->
                updateSelectedTransaction(
                    intent.selected
                )

            is IProductHistoryScreenIntent.NavigateBack -> navigateBack()
        }
    }

    private fun navigateBack() {
        _event.emit(IProductHistoryScreenEvent.NavigateBack)
    }

    private fun updateSelectedTransaction(selected: TransactionModelUi?) {
        _screenState.update { it.copy(selected = selected) }
    }

    private fun loadCardHistory(card: CardModelUi) {
        viewModelScope.launch {
            _screenState.update { it.copy(isLoading = true) }
            when (
                val res = getAllUserTransactionsByCardUseCase.invoke(card.domainModel)
            ) {
                is CustomResultModelDomain.Success -> {
                    _screenState.update {
                        it.copy(
                            transactions = res.result.map { it.toUiModel(usedCard = card.domainModel) }
                        )
                    }
                }

                is CustomResultModelDomain.Error -> {
                    _event.emit(
                        IProductHistoryScreenEvent.ShowToastMessage(
                            res.exception.toUiMessageString()
                        )
                    )
                }
            }
            _screenState.update { it.copy(isLoading = false) }
        }
    }
}