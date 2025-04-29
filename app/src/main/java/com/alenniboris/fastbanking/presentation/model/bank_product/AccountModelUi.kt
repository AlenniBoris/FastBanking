package com.alenniboris.fastbanking.presentation.model.bank_product

import com.alenniboris.fastbanking.domain.model.account.AccountModelDomain
import com.alenniboris.fastbanking.presentation.uikit.functions.CommonFunctions

data class AccountModelUi(
    val domainModel: AccountModelDomain
) : IBankProductModelUi {

    val accountIdText: String = domainModel.id

    val mainCurrencyText =
        CommonFunctions.formatAmount(domainModel.amount) + " " + domainModel.currency

    val reserveCurrencyText =
        CommonFunctions.formatAmount(domainModel.amountInReserveCurrency) + " " + domainModel.reserveCurrency
}

fun AccountModelDomain.toModelUi(): AccountModelUi =
    AccountModelUi(
        domainModel = this
    )