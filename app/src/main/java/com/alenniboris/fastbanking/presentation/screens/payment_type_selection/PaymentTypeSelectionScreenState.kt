package com.alenniboris.fastbanking.presentation.screens.payment_type_selection

data class PaymentTypeSelectionScreenState(
    val paymentTypes: List<PaymentType> = PaymentType.entries.toList()
)
