package com.alenniboris.fastbanking.presentation.screens.card_details

sealed interface ICardDetailsScreenIntent {

    data object NavigateBack : ICardDetailsScreenIntent

    data class ProceedDetailsAction(val action: CardDetailsScreenActions) : ICardDetailsScreenIntent
}