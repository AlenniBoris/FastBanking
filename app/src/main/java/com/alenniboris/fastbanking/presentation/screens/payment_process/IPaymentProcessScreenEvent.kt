package com.alenniboris.fastbanking.presentation.screens.payment_process

interface IPaymentProcessScreenEvent {

    data object NavigateBack : IPaymentProcessScreenEvent

    data class ShowToastMessage(val messageId: Int) : IPaymentProcessScreenEvent
}