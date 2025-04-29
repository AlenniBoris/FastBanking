package com.alenniboris.fastbanking.presentation.mappers

import com.alenniboris.fastbanking.R
import com.alenniboris.fastbanking.domain.model.card.CardSystem
import com.alenniboris.fastbanking.domain.model.card.CardType

fun CardType.toUiString(): Int = when (this) {
    CardType.Credit -> R.string.card_type_credit_text
    CardType.Dedut -> R.string.card_type_debut_text
    CardType.Undefined -> R.string.undefined_text
}

fun CardType.toUiPicture(): Int = when (this) {
    CardType.Credit, CardType.Dedut, CardType.Undefined -> R.drawable.credit_card_action_icon
}

fun CardSystem.toUiString(): Int = when (this) {
    CardSystem.Visa -> R.string.card_system_visa
    CardSystem.Mastercard -> R.string.card_system_mastercard
    CardSystem.Mir -> R.string.card_system_mir
    CardSystem.Undefined -> R.string.undefined_text
}