package com.alenniboris.fastbanking.presentation.model.bank_product

import com.alenniboris.fastbanking.R
import com.alenniboris.fastbanking.domain.model.transaction.TransactionModelDomain
import com.alenniboris.fastbanking.domain.model.transaction.TransactionType
import com.alenniboris.fastbanking.presentation.uikit.functions.CommonFunctions

data class TransactionModelUi(
    val domainModel: TransactionModelDomain
) {

    val picture: Int = domainModel.type.toUiPicture()

    val priceText: String =
        CommonFunctions.formatAmount(domainModel.priceAmount) + " " + domainModel.currency.code

    val dateText: String =
        CommonFunctions.formatDateToDateText(domainModel.date)

    val dateAndTimeText: String =
        CommonFunctions.formatDateToDateAndTimeText(domainModel.date)

    val numberText: String =
        "**** " + domainModel.cardNumber.substring(domainModel.cardNumber.length - 4)
}


fun TransactionType.toUiPicture(): Int = when (this) {
    TransactionType.P2P -> R.drawable.p2p_icon
    TransactionType.Undefined -> R.drawable.undefined_transaction_icon
}

fun TransactionModelDomain.toUiModel(): TransactionModelUi =
    TransactionModelUi(
        domainModel = this
    )