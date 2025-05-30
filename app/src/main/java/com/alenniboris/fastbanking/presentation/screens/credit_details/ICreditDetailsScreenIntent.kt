package com.alenniboris.fastbanking.presentation.screens.credit_details

sealed interface ICreditDetailsScreenIntent {

    data object NavigateBack : ICreditDetailsScreenIntent

    data class ProceedDetailsAction(val action: CreditDetailsScreenActions) :
        ICreditDetailsScreenIntent
}