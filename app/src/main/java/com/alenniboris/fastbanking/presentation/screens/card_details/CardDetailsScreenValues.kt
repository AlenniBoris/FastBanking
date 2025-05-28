package com.alenniboris.fastbanking.presentation.screens.card_details

import com.alenniboris.fastbanking.R

enum class CardDetailsScreenActions {
    History,
    Details
}

fun CardDetailsScreenActions.toUiString(): Int = when (this) {
    CardDetailsScreenActions.History -> R.string.history_action_text
    CardDetailsScreenActions.Details -> R.string.details_action_text
}

fun CardDetailsScreenActions.toUiPicture(): Int = when (this) {
    CardDetailsScreenActions.History -> R.drawable.history_icon
    CardDetailsScreenActions.Details -> R.drawable.info_action_icon
}