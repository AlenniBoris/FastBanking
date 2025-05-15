package com.alenniboris.fastbanking.data.model.account

import android.util.Log
import com.alenniboris.fastbanking.data.model.OwnerModelData
import com.alenniboris.fastbanking.data.model.card.SimpleCardModelData
import com.alenniboris.fastbanking.data.model.card.toModelDomain
import com.alenniboris.fastbanking.data.model.toModelDomain
import com.alenniboris.fastbanking.domain.model.account.AccountModelDomain
import com.alenniboris.fastbanking.domain.model.card.SimpleCardModelDomain

data class AccountModelData(
    val amount: String? = null,
    val attachedCards: Map<String?, SimpleCardModelData?>? = null,
    val id: String? = null,
    val owner: OwnerModelData? = null,
    val currency: String? = null,
    val reserveCurrency: String? = null,
    val amountInReserveCurrency: String? = null,
    val name: String? = null
)

fun AccountModelData.toModelDomain(): AccountModelDomain? = runCatching {

    val mapped = this.attachedCards?.entries?.toList()
        ?.map { entry -> Pair(entry.key!!, entry.value?.toModelDomain()!!) }!!

    val newMap = mutableMapOf<String, SimpleCardModelDomain>()
    mapped.forEach { pair -> newMap[pair.first] = pair.second }

    AccountModelDomain(
        amount = this.amount?.toDouble()!!,
        amountInReserveCurrency = this.amountInReserveCurrency?.toDouble()!!,
        attachedCards = newMap,
        id = this.id!!,
        owner = this.owner?.toModelDomain()!!,
        currency = this.currency!!,
        reserveCurrency = this.reserveCurrency!!,
        name = this.name!!
    )
}.getOrElse { exception ->
    Log.e("!!!", "AccountModelData.toModelDomain, ${exception.stackTraceToString()}")
    null
}