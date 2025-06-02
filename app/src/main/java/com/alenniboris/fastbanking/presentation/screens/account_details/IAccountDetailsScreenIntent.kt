package com.alenniboris.fastbanking.presentation.screens.account_details

sealed interface IAccountDetailsScreenIntent {

    data object NavigateBack : IAccountDetailsScreenIntent

    data class ProceedDetailsAction(val action: AccountDetailsScreenActions) :
        IAccountDetailsScreenIntent

    data object UpdateAttachedCardsSheetVisibility : IAccountDetailsScreenIntent

    data class OpenCardDetailsScreen(val cardId: String) : IAccountDetailsScreenIntent
}