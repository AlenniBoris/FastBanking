package com.alenniboris.fastbanking.presentation.screens.payment_process

import com.alenniboris.fastbanking.presentation.model.bank_product.CardModelUi

sealed interface IPaymentProcessScreenIntent {

    data class UpdateEnteredAmount(val amount: String) : IPaymentProcessScreenIntent

    data object NavigateBack : IPaymentProcessScreenIntent

    data object UpdateCardsSheetVisibilityForUsedCard : IPaymentProcessScreenIntent

    data object UpdateCardsSheetVisibilityForReceiverCard : IPaymentProcessScreenIntent

    data class UpdateSelectedUsedCard(val card: CardModelUi) : IPaymentProcessScreenIntent

    data class UpdateSelectedReceiverCard(val card: CardModelUi) : IPaymentProcessScreenIntent

    data object ProceedTransaction : IPaymentProcessScreenIntent

    data class UpdateEnteredReceiverData(val data: String) : IPaymentProcessScreenIntent
}