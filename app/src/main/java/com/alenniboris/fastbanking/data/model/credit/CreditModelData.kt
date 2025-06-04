package com.alenniboris.fastbanking.data.model.credit

import android.util.Log
import com.alenniboris.fastbanking.domain.model.credit.CreditModelDomain
import java.util.Date

data class CreditModelData(
    val id: String? = null,
    val initialAmount: String? = null,
    val currency: String? = null,
    val reserveCurrency: String? = null,
    val percentage: String? = null,
    val lastPayment: String? = null,
    val startDate: String? = null,
    val goalDescription: String? = null,
    val ownerId: String? = null,
    val amountInReserveCurrency: String? = null,
    val name: String? = null,
    val bankIdCode: String? = null,
    val contractNumber: String? = null
)

fun CreditModelData.toModelDomain(): CreditModelDomain? = runCatching {

    CreditModelDomain(
        id = this.id!!,
        amountInReserveCurrency = this.amountInReserveCurrency?.toDouble()!!,
        initialAmount = this.initialAmount?.toDouble()!!,
        currency = this.currency!!,
        reserveCurrency = this.reserveCurrency!!,
        percentage = this.percentage?.toDouble()!!,
        lastPayment = Date(this.lastPayment?.toLong()!!),
        startDate = Date(this.startDate?.toLong()!!),
        goalDescription = this.goalDescription!!,
        ownerId = this.ownerId!!,
        name = this.name!!,
        bankIdCode = this.bankIdCode!!,
        contractNumber = this.contractNumber!!
    )
}.getOrElse { exception ->
    Log.e("!!!", "CreditModelData.toModelDomain, ${exception.stackTraceToString()}")
    null
}

fun CreditModelDomain.toModelData(): CreditModelData = CreditModelData(
    id = this.id,
    amountInReserveCurrency = this.amountInReserveCurrency.toString(),
    initialAmount = this.initialAmount.toString(),
    currency = this.currency,
    reserveCurrency = this.reserveCurrency,
    percentage = this.percentage.toString(),
    lastPayment = this.lastPayment.time.toString(),
    startDate = this.startDate.time.toString(),
    goalDescription = this.goalDescription,
    ownerId = this.ownerId,
    name = this.name,
    bankIdCode = this.bankIdCode,
    contractNumber = this.contractNumber
)