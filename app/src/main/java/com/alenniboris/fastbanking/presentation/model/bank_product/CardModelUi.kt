package com.alenniboris.fastbanking.presentation.model.bank_product

import com.alenniboris.fastbanking.R
import com.alenniboris.fastbanking.domain.model.card.CardModelDomain
import com.alenniboris.fastbanking.domain.model.card.CardSystem
import com.alenniboris.fastbanking.presentation.uikit.functions.CommonFunctions

data class CardModelUi(
    val domainModel: CardModelDomain
) : IBankProductModelUi {

    val amountText: String =
        CommonFunctions.formatAmount(domainModel.amount) + " " + domainModel.currencyCode

    val reserveAmountText: String =
        CommonFunctions.formatAmount(domainModel.amountInReserveCurrency) + " " + domainModel.reserveCurrencyCode

    val picture: Int = domainModel.system.toUiPicture()

    val numberText: String = "**** " + domainModel.number.substring(domainModel.number.length - 4)

    override val name: String
        get() = domainModel.name
}

fun CardSystem.toUiPicture(): Int = when (this) {
    CardSystem.Visa -> R.drawable.visa_card
    CardSystem.Mastercard -> R.drawable.mastercard_card
    CardSystem.Mir -> R.drawable.mir_card
    CardSystem.Undefined -> R.drawable.undefined_card
}

fun CardModelDomain.toModelUi(): CardModelUi = CardModelUi(
    domainModel = this
)