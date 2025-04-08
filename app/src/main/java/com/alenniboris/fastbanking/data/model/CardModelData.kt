package com.alenniboris.fastbanking.data.model

import android.util.Log
import com.alenniboris.fastbanking.domain.model.card.CardModelDomain
import com.alenniboris.fastbanking.domain.model.card.CardType
import java.util.Date

data class CardModelData(
    val id: String? = null,
    val amount: String? = null,
    val owner: OwnerModelData? = null,
    val expireDate: String? = null,
    val number: String? = null,
    val cvv: String? = null,
    val type: String? = null
)

fun CardModelData.toModelDomain(): CardModelDomain? = runCatching {
    CardModelDomain(
        id = this.id!!,
        amount = this.amount?.toDouble()!!,
        owner = this.owner?.toModelDomain()!!,
        expireDate = Date(this.expireDate?.toLong()!!),
        number = this.number!!,
        cvv = this.cvv!!,
        type = when (this.type!!) {
            "Credit" -> CardType.CREDIT
            else -> CardType.DEBUT
        }
    )
}.getOrElse { exception ->
    Log.e("!!!", "CardModelData.toModelDomain ${exception.stackTraceToString()}")
    null
}