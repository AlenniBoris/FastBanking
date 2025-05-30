package com.alenniboris.fastbanking.presentation.model.bank_product

import com.alenniboris.fastbanking.R
import com.alenniboris.fastbanking.domain.model.card.CardModelDomain
import com.alenniboris.fastbanking.domain.model.transaction.TransactionModelDomain
import com.alenniboris.fastbanking.domain.model.transaction.TransactionType
import com.alenniboris.fastbanking.presentation.uikit.functions.CommonFunctions

data class TransactionModelUi(
    val domainModel: TransactionModelDomain,
    val usedCard: CardModelDomain? = null,
) {

    val picture: Int = domainModel.type.toUiPicture()

    val priceText: String = usedCard?.let {
        if (usedCard.id == domainModel.senderId) {
            "-"
        } else {
            "+"
        } + CommonFunctions.formatAmount(domainModel.priceAmount) + " " + domainModel.currency.code
    }
        ?: ("-" + CommonFunctions.formatAmount(domainModel.priceAmount) + " " + domainModel.currency.code)

    val dateText: String =
        CommonFunctions.formatDateToDateText(domainModel.date)

    val dateAndTimeText: String =
        CommonFunctions.formatDateToDateAndTimeText(domainModel.date)

    val numberText: String = usedCard?.let {
        "**** " + usedCard.number.substring(usedCard.number.length - 4)
    } ?: ""
}


fun TransactionType.toUiPicture(): Int = when (this) {
    TransactionType.P2P -> R.drawable.p2p_icon
    TransactionType.Credit -> R.drawable.undefined_transaction_icon
    TransactionType.Undefined -> R.drawable.undefined_transaction_icon
}

fun TransactionModelDomain.toUiModel(): TransactionModelUi =
    TransactionModelUi(
        domainModel = this
    )

fun TransactionModelDomain.toUiModel(usedCard: CardModelDomain) =
    TransactionModelUi(
        domainModel = this,
        usedCard = usedCard
    )