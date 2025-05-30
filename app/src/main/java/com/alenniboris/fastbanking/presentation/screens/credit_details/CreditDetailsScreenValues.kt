package com.alenniboris.fastbanking.presentation.screens.credit_details

import com.alenniboris.fastbanking.R

enum class CreditDetailsScreenActions {
    History,
    Details
}

fun CreditDetailsScreenActions.toUiString(): Int = when (this) {
    CreditDetailsScreenActions.History -> R.string.history_action_text
    CreditDetailsScreenActions.Details -> R.string.details_action_text
}

fun CreditDetailsScreenActions.toUiPicture(): Int = when (this) {
    CreditDetailsScreenActions.History -> R.drawable.history_icon
    CreditDetailsScreenActions.Details -> R.drawable.info_action_icon
}