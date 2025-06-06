package com.alenniboris.fastbanking.presentation.screens.payment_type_selection

sealed interface IPaymentTypeSelectionScreenIntent {

    data class OpenPaymentProcessScreen(val paymentType: PaymentType) :
        IPaymentTypeSelectionScreenIntent
}