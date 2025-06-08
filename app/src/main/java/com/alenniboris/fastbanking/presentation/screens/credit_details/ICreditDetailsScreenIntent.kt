package com.alenniboris.fastbanking.presentation.screens.credit_details

import com.alenniboris.fastbanking.presentation.screens.card_details.ICardDetailsScreenIntent

sealed interface ICreditDetailsScreenIntent {

    data object NavigateBack : ICreditDetailsScreenIntent

    data class ProceedDetailsAction(val action: CreditDetailsScreenActions) :
        ICreditDetailsScreenIntent

    data object ChangeCreditNameSettingsVisibility : ICreditDetailsScreenIntent

    data object UpdateCreditName : ICreditDetailsScreenIntent

    data class UpdateCreditNewName(val newName: String) : ICreditDetailsScreenIntent
}