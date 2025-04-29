package com.alenniboris.fastbanking.presentation.model.bank_product

import com.alenniboris.fastbanking.domain.model.credit.CreditModelDomain
import com.alenniboris.fastbanking.presentation.uikit.functions.CommonFunctions
import java.util.Calendar

data class CreditModelUi(
    val domainModel: CreditModelDomain
) : IBankProductModelUi {

    val creditBodyText: String =
        getCreditBody(domainModel.initialAmount) + " " + domainModel.currency

    val reserveCreditBodyText: String =
        getCreditBody(domainModel.amountInReserveCurrency) + " " + domainModel.reserveCurrency

    private fun getCreditBody(amount: Double): String {
        val currentDate = Calendar.getInstance().time
        val timeDiff = currentDate.time - domainModel.lastPayment.time
        val daysElapsed = timeDiff / (1000 * 60 * 60 * 24)

        val dailyRate = domainModel.percentage / (100 * 365)

        val totalAmount = amount * (1 + dailyRate * daysElapsed)

        return CommonFunctions.formatAmount(totalAmount)
    }
}

fun CreditModelDomain.toModelUi(): CreditModelUi =
    CreditModelUi(
        domainModel = this
    )