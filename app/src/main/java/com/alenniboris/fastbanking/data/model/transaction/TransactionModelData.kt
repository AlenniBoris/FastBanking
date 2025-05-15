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
    val type: String?,
    val usedCardId: String?,
    val cardNumber: String?
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
        type = when (this.type!!) {
            "p2p" -> TransactionType.P2P
            else -> TransactionType.Undefined
        },
        usedCardId = this.usedCardId!!,
        cardNumber = this.cardNumber!!
    )
}.getOrElse { exception ->
    Log.e("!!!", "TransactionModelData.toModelDomain ${exception.stackTraceToString()}")
    null
}