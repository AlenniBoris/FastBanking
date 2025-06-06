package com.alenniboris.fastbanking.presentation.model.bank_product

import com.alenniboris.fastbanking.R
import com.alenniboris.fastbanking.domain.model.card.CardModelDomain
import com.alenniboris.fastbanking.domain.model.card.CardSystem
import com.alenniboris.fastbanking.domain.model.card.CardType
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

    val dateOfExpireText: String =
        CommonFunctions.formatDateToDateText(domainModel.expireDate)

    val smallDateOfExpireText: String =
        CommonFunctions.formatDateToMonthYearText(domainModel.expireDate)
}

fun CardModelDomain.toModelUi(): CardModelUi = CardModelUi(
    domainModel = this
)

fun CardType.toUiString(): Int = when (this) {
    CardType.Credit -> R.string.card_type_credit_text
    CardType.Dedut -> R.string.card_type_debut_text
    CardType.Undefined -> R.string.undefined_text
}

fun CardType.toUiPicture(): Int = when (this) {
    CardType.Credit, CardType.Dedut, CardType.Undefined -> R.drawable.credit_card_action_icon
}

fun CardSystem.toUiPicture(): Int = when (this) {
    CardSystem.Visa -> R.drawable.visa_card
    CardSystem.Mastercard -> R.drawable.mastercard_card
    CardSystem.Mir -> R.drawable.mir_card
    CardSystem.Undefined -> R.drawable.undefined_card
}

fun CardSystem.toUiString(): Int = when (this) {
    CardSystem.Visa -> R.string.card_system_visa
    CardSystem.Mastercard -> R.string.card_system_mastercard
    CardSystem.Mir -> R.string.card_system_mir
    CardSystem.Undefined -> R.string.undefined_text
}