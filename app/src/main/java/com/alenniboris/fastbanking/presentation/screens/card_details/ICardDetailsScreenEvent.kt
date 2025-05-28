package com.alenniboris.fastbanking.presentation.screens.card_details

import com.alenniboris.fastbanking.presentation.model.bank_product.CardModelUi

sealed interface ICardDetailsScreenEvent {

    data class NavigateToProductHistoryScreen(val card: CardModelUi) : ICardDetailsScreenEvent

    data class NavigateToProductInfoScreen(val card: CardModelUi) : ICardDetailsScreenEvent

    data class ShowToastMessage(val messageId: Int) : ICardDetailsScreenEvent

    data object NavigateBack : ICardDetailsScreenEvent
}