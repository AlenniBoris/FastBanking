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
    val name: String? = null
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
        name = this.name!!
    )
}.getOrElse { exception ->
    Log.e("!!!", "CreditModelData.toModelDomain, ${exception.stackTraceToString()}")
    null
}