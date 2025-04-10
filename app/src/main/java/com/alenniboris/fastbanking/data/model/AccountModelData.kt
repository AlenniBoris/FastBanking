package com.alenniboris.fastbanking.data.model

import android.util.Log
import com.alenniboris.fastbanking.domain.model.account.AccountModelDomain
import com.alenniboris.fastbanking.domain.utils.GsonUtil.fromJson
import com.alenniboris.fastbanking.domain.utils.GsonUtil.toJson

data class AccountModelData(
    val amount: String? = null,
    val attachedCards: String? = null,
    val id: String? = null,
    val owner: OwnerModelData? = null,
    val currency: String? = null,
    val reserveCurrency: String? = null,
    val amountInReserveCurrency: String? = null,
)

fun AccountModelData.toModelDomain(): AccountModelDomain? = runCatching {

    AccountModelDomain(
        amount = this.amount?.toDouble()!!,
        amountInReserveCurrency = this.amountInReserveCurrency?.toDouble()!!,
        attachedCards = this.attachedCards?.split(",")!!,
        id = this.id!!,
        owner = this.owner?.toModelDomain()!!,
        currency = this.currency!!,
        reserveCurrency = this.reserveCurrency!!
    )
}.getOrElse { exception ->
    Log.e("!!!", "AccountModelData.toModelDomain, ${exception.stackTraceToString()}")
    null
}