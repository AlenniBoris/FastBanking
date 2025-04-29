package com.alenniboris.fastbanking.presentation.screens.product_appliances_forms.card_form_screen

import com.alenniboris.fastbanking.R
import com.alenniboris.fastbanking.domain.model.card.CardSystem

enum class CardIssuingType {
    WITH_NEW_ACCOUNT,
    TO_EXISTING_ACCOUNT
}

fun CardIssuingType.toUiString(): Int = when (this) {
    CardIssuingType.WITH_NEW_ACCOUNT -> R.string.issue_card_with_new_account
    CardIssuingType.TO_EXISTING_ACCOUNT -> R.string.issue_card_to_existing_account
}

val listOfCardSystems = listOf(
    CardSystem.Visa,
    CardSystem.Mastercard,
    CardSystem.Mir
)