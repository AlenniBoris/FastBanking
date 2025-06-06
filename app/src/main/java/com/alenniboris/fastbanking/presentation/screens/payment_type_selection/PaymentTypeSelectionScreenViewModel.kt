package com.alenniboris.fastbanking.presentation.screens.payment_type_selection

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alenniboris.fastbanking.domain.utils.SingleFlowEvent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class PaymentTypeSelectionScreenViewModel : ViewModel() {

    private val _screenState = MutableStateFlow(PaymentTypeSelectionScreenState())
    val state = _screenState.asStateFlow()

    private val _event = SingleFlowEvent<IPaymentTypeSelectionScreenEvent>(viewModelScope)
    val event = _event.flow

    fun proceedIntent(intent: IPaymentTypeSelectionScreenIntent) {
        when (intent) {
            is IPaymentTypeSelectionScreenIntent.OpenPaymentProcessScreen ->
                openPaymentProcessScreen(selectedType = intent.paymentType)
        }
    }

    private fun openPaymentProcessScreen(selectedType: PaymentType) {
        _event.emit(
            when (selectedType) {
                PaymentType.OnMyCard ->
                    IPaymentTypeSelectionScreenEvent.OpenPaymentToMyOwnCard

                PaymentType.ByCardNumber ->
                    IPaymentTypeSelectionScreenEvent.OpenPaymentWithCreditCardNumber

                PaymentType.ByEripNumber ->
                    IPaymentTypeSelectionScreenEvent.OpenPaymentWithEripNumber

                PaymentType.ForCreditByContractNumber ->
                    IPaymentTypeSelectionScreenEvent.OpenPaymentForCreditByContractNumber
            }
        )
    }
}