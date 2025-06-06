package com.alenniboris.fastbanking.presentation.screens.payment_process

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alenniboris.fastbanking.R
import com.alenniboris.fastbanking.domain.model.CustomResultModelDomain
import com.alenniboris.fastbanking.domain.usecase.logic.cards.IGetAllUserCardsUseCase
import com.alenniboris.fastbanking.domain.usecase.logic.transactions.IMakeTransactionByCardNumberUseCase
import com.alenniboris.fastbanking.domain.usecase.logic.transactions.IMakeTransactionByEripNumberUseCase
import com.alenniboris.fastbanking.domain.usecase.logic.transactions.IMakeTransactionForCreditByContractNumberUseCase
import com.alenniboris.fastbanking.domain.utils.SingleFlowEvent
import com.alenniboris.fastbanking.presentation.mappers.toUiMessageString
import com.alenniboris.fastbanking.presentation.model.bank_product.CardModelUi
import com.alenniboris.fastbanking.presentation.model.bank_product.toModelUi
import com.alenniboris.fastbanking.presentation.screens.payment_type_selection.PaymentType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PaymentProcessScreenViewModel(
    private val paymentType: PaymentType,
    private val makeTransactionByCardNumberUseCase: IMakeTransactionByCardNumberUseCase,
    private val makeTransactionByEripNumberUseCase: IMakeTransactionByEripNumberUseCase,
    private val makeTransactionForCreditByContractNumberUseCase: IMakeTransactionForCreditByContractNumberUseCase,
    private val getAllUserCardsUseCase: IGetAllUserCardsUseCase
) : ViewModel() {

    private val _screenState = MutableStateFlow(
        PaymentProcessScreenState(paymentType = paymentType)
    )
    val state = _screenState.asStateFlow()

    private val _event = SingleFlowEvent<IPaymentProcessScreenEvent>(viewModelScope)
    val event = _event.flow

    init {
        loadUserCards()
    }

    private fun loadUserCards() {
        viewModelScope.launch {
            _screenState.update { it.copy(isAllUserCardsLoading = true) }

            when (
                val res = getAllUserCardsUseCase.invoke()
            ) {
                is CustomResultModelDomain.Success -> {

                    val allCards = res.result.map { it.toModelUi() }

                    _screenState.update {
                        it.copy(
                            allUserCards = allCards,
                            selectedUsedCard = allCards.firstOrNull(),
                            selectedReceiverCard = allCards.firstOrNull()
                        )
                    }
                }

                is CustomResultModelDomain.Error -> {
                    _event.emit(
                        IPaymentProcessScreenEvent.ShowToastMessage(
                            res.exception.toUiMessageString()
                        )
                    )
                }
            }

            _screenState.update { it.copy(isAllUserCardsLoading = false) }
        }
    }

    fun proceedIntent(intent: IPaymentProcessScreenIntent) {
        when (intent) {
            is IPaymentProcessScreenIntent.NavigateBack -> navigateBack()
            is IPaymentProcessScreenIntent.ProceedTransaction -> proceedTransaction()
            is IPaymentProcessScreenIntent.UpdateCardsSheetVisibilityForUsedCard -> updateCardsSheetVisibilityForUsedCard()
            is IPaymentProcessScreenIntent.UpdateCardsSheetVisibilityForReceiverCard -> updateCardsSheetVisibilityForReceiverCard()
            is IPaymentProcessScreenIntent.UpdateEnteredAmount -> updateEnteredAmount(intent.amount)
            is IPaymentProcessScreenIntent.UpdateSelectedReceiverCard ->
                updateSelectedReceiverCard(intent.card)

            is IPaymentProcessScreenIntent.UpdateSelectedUsedCard -> updateSelectedUsedCard(intent.card)
            is IPaymentProcessScreenIntent.UpdateEnteredReceiverData ->
                updateEnteredReceiverData(intent.data)
        }
    }

    private fun updateEnteredReceiverData(data: String) {
        _screenState.update { it.copy(enteredReceiverData = data) }
    }

    private fun updateSelectedUsedCard(card: CardModelUi) {
        _screenState.update { it.copy(selectedUsedCard = card) }
    }

    private fun updateSelectedReceiverCard(card: CardModelUi) {
        _screenState.update { it.copy(selectedReceiverCard = card) }
    }

    private fun updateEnteredAmount(amount: String) {
        _screenState.update { it.copy(enteredAmount = amount) }
    }

    private fun updateCardsSheetVisibilityForUsedCard() {
        _screenState.update { it.copy(isCardsSheetForUsedCardVisible = !it.isCardsSheetForUsedCardVisible) }
    }

    private fun updateCardsSheetVisibilityForReceiverCard() {
        _screenState.update { it.copy(isCardsSheetForReceiverCardVisible = !it.isCardsSheetForReceiverCardVisible) }
    }

    private fun navigateBack() {
        _event.emit(
            IPaymentProcessScreenEvent.NavigateBack
        )
    }

    private fun proceedTransaction() {

        if (_screenState.value.paymentType != PaymentType.OnMyCard && _screenState.value.enteredReceiverData.isEmpty()){
            _event.emit(
                IPaymentProcessScreenEvent.ShowToastMessage(
                    R.string.transaction_data_error
                )
            )
        }

        if (_screenState.value.enteredAmount.toDouble() == 0.0) {
            _event.emit(
                IPaymentProcessScreenEvent.ShowToastMessage(
                    R.string.transaction_data_error
                )
            )
        }
        when (
            _screenState.value.paymentType
        ) {
            PaymentType.OnMyCard -> {
                makeTransactionToMyOwnCard()
            }

            PaymentType.ByCardNumber -> {
                makeTransactionToOtherCardByNumber()
            }

            PaymentType.ByEripNumber -> {
                makeTransactionByEripNumber()
            }

            PaymentType.ForCreditByContractNumber -> {
                makePayForCreditByContractNumber()
            }
        }
    }

    private fun makePayForCreditByContractNumber() {
        viewModelScope.launch {
            _screenState.update { it.copy(isPaymentProceeding = true) }

            when (
                val res = makeTransactionForCreditByContractNumberUseCase.invoke(
                    usedCard = _screenState.value.selectedUsedCard?.domainModel!!,
                    contractNumber = _screenState.value.enteredReceiverData,
                    amount = _screenState.value.enteredAmount.toDouble()
                )
            ) {
                is CustomResultModelDomain.Success -> {
                    _screenState.update { it.copy(isPaymentSucceeded = true) }
                }

                is CustomResultModelDomain.Error -> {
                    _event.emit(
                        IPaymentProcessScreenEvent.ShowToastMessage(
                            res.exception.toUiMessageString()
                        )
                    )
                }
            }

            _screenState.update { it.copy(isPaymentProceeding = false) }
        }
    }

    private fun makeTransactionByEripNumber() {
        viewModelScope.launch {
            _screenState.update { it.copy(isPaymentProceeding = true) }

            when (
                val res = makeTransactionByEripNumberUseCase.invoke(
                    usedCard = _screenState.value.selectedUsedCard?.domainModel!!,
                    eripNumber = _screenState.value.enteredReceiverData,
                    amount = _screenState.value.enteredAmount.toDouble()
                )
            ) {
                is CustomResultModelDomain.Success -> {
                    _screenState.update { it.copy(isPaymentSucceeded = true) }
                }

                is CustomResultModelDomain.Error -> {
                    _event.emit(
                        IPaymentProcessScreenEvent.ShowToastMessage(
                            res.exception.toUiMessageString()
                        )
                    )
                }
            }

            _screenState.update { it.copy(isPaymentProceeding = false) }
        }
    }

    private fun makeTransactionToOtherCardByNumber() {
        viewModelScope.launch {
            _screenState.update { it.copy(isPaymentProceeding = true) }

            when (
                val res = makeTransactionByCardNumberUseCase.invoke(
                    usedCard = _screenState.value.selectedUsedCard?.domainModel!!,
                    cardNumber = _screenState.value.enteredReceiverData,
                    amount = _screenState.value.enteredAmount.toDouble()
                )
            ) {
                is CustomResultModelDomain.Success -> {
                    _screenState.update { it.copy(isPaymentSucceeded = true) }
                }

                is CustomResultModelDomain.Error -> {
                    _event.emit(
                        IPaymentProcessScreenEvent.ShowToastMessage(
                            res.exception.toUiMessageString()
                        )
                    )
                }
            }

            _screenState.update { it.copy(isPaymentProceeding = false) }
        }
    }

    private fun makeTransactionToMyOwnCard() {
        viewModelScope.launch {
            _screenState.update { it.copy(isPaymentProceeding = true) }

            when (
                val res = makeTransactionByCardNumberUseCase.invoke(
                    usedCard = _screenState.value.selectedUsedCard?.domainModel!!,
                    cardNumber = _screenState.value.selectedReceiverCard?.domainModel?.number!!,
                    amount = _screenState.value.enteredAmount.toDouble()
                )
            ) {
                is CustomResultModelDomain.Success -> {
                    _screenState.update { it.copy(isPaymentSucceeded = true) }
                }

                is CustomResultModelDomain.Error -> {
                    _event.emit(
                        IPaymentProcessScreenEvent.ShowToastMessage(
                            res.exception.toUiMessageString()
                        )
                    )
                }
            }

            _screenState.update { it.copy(isPaymentProceeding = false) }
        }
    }
}