package com.alenniboris.fastbanking.presentation.screens.payment_type_selection

sealed interface IPaymentTypeSelectionScreenEvent {

    data object OpenPaymentToMyOwnCard: IPaymentTypeSelectionScreenEvent

    data object OpenPaymentWithCreditCardNumber: IPaymentTypeSelectionScreenEvent

    data object OpenPaymentWithEripNumber: IPaymentTypeSelectionScreenEvent

    data object OpenPaymentForCreditByContractNumber: IPaymentTypeSelectionScreenEvent
}