package com.alenniboris.fastbanking.presentation.model

import com.alenniboris.fastbanking.R
import com.alenniboris.fastbanking.domain.model.card.CardModelDomain
import com.alenniboris.fastbanking.domain.model.card.CardSystem
import com.alenniboris.fastbanking.presentation.uikit.functions.CommonFunctions

data class CardModelUi(
    val domainModel: CardModelDomain
) : IBankProductModelUi {

    val amountText: String =
        CommonFunctions.formatAmount(domainModel.amount) + " " + domainModel.currency

    val reserveAmountText: String =
        CommonFunctions.formatAmount(domainModel.amountInReserveCurrency) + " " + domainModel.reserveCurrency

    val picture: Int = domainModel.system.toUiPicture()

    val numberText: String = "**** " + domainModel.number.substring(domainModel.number.length - 4)
}

fun CardSystem.toUiPicture(): Int = when (this) {
    CardSystem.VISA -> R.drawable.visa_card
    CardSystem.MASTERCARD -> R.drawable.mastercard_card
    CardSystem.MIR -> R.drawable.mir_card
    CardSystem.UNDEFINED -> R.drawable.undefined_card
}

fun CardModelDomain.toModelUi(): CardModelUi = CardModelUi(
    domainModel = this
)