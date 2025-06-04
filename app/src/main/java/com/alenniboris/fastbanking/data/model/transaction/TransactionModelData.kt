package com.alenniboris.fastbanking.data.model.transaction

import android.util.Log
import com.alenniboris.fastbanking.domain.model.currency.CurrencyModelDomain
import com.alenniboris.fastbanking.domain.model.transaction.TransactionModelDomain
import com.alenniboris.fastbanking.domain.model.transaction.TransactionType
import java.util.Date

data class TransactionModelData(
    val currency: String?,
    val date: String?,
    val details: String?,
    val id: String?,
    val priceAmount: String?,
    val senderId: String?,
    val receiverId: String?,
    val type: String?
)

fun TransactionModelData.toModelDomain(): TransactionModelDomain? = runCatching {
    TransactionModelDomain(
        currency = CurrencyModelDomain(
            code = this.currency!!,
            fullName = this.currency!!
        ),
        date = Date(this.date?.toLong()!!),
        details = this.details!!,
        id = this.id!!,
        priceAmount = this.priceAmount?.toDouble()!!,
        senderId = this.senderId!!,
        receiverId = this.receiverId!!,
        type = when (this.type!!) {
            "P2P" -> TransactionType.P2P
            "Credit" -> TransactionType.Credit
            else -> TransactionType.Undefined
        }
    )
}.getOrElse { exception ->
    Log.e("!!!", "TransactionModelData.toModelDomain ${exception.stackTraceToString()}")
    null
}

fun TransactionModelDomain.toModelData(): TransactionModelData =
    TransactionModelData(
        currency = this.currency.code,
        date = this.date.time.toString(),
        details = this.details,
        id = this.id,
        priceAmount = this.priceAmount.toString(),
        senderId = this.senderId,
        receiverId = this.receiverId,
        type = this.type.toString()
    )