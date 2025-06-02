package com.alenniboris.fastbanking.presentation.screens.account_details

import com.alenniboris.fastbanking.R

enum class AccountDetailsScreenActions {
    History,
    Details,
    AttachedCards
}

fun AccountDetailsScreenActions.toUiString(): Int = when (this) {
    AccountDetailsScreenActions.History -> R.string.history_action_text
    AccountDetailsScreenActions.Details -> R.string.details_action_text
    AccountDetailsScreenActions.AttachedCards -> R.string.attached_cards_text
}

fun AccountDetailsScreenActions.toUiPicture(): Int = when (this) {
    AccountDetailsScreenActions.History -> R.drawable.history_icon
    AccountDetailsScreenActions.Details -> R.drawable.info_action_icon
    AccountDetailsScreenActions.AttachedCards -> R.drawable.credit_card_action_icon
}