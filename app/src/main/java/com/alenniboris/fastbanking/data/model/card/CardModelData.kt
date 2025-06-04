package com.alenniboris.fastbanking.data.model.card

import android.util.Log
import com.alenniboris.fastbanking.data.model.OwnerModelData
import com.alenniboris.fastbanking.data.model.toModelData
import com.alenniboris.fastbanking.data.model.toModelDomain
import com.alenniboris.fastbanking.domain.model.card.CardModelDomain
import com.alenniboris.fastbanking.domain.model.card.CardSystem
import com.alenniboris.fastbanking.domain.model.card.CardType
import java.util.Date

data class CardModelData(
    val id: String? = null,
    val currency: String? = null,
    val amount: String? = null,
    val owner: OwnerModelData? = null,
    val expireDate: String? = null,
    val number: String? = null,
    val cvv: String? = null,
    val type: String? = null,
    val system: String? = null,
    val reserveCurrency: String? = null,
    val amountInReserveCurrency: String? = null,
    val name: String? = null,
    val erip: String? = null,
    val iban: String? = null,
    val bankIdCode: String? = null,
)

fun CardModelData.toModelDomain(): CardModelDomain? = runCatching {
    CardModelDomain(
        id = this.id!!,
        amountInReserveCurrency = this.amountInReserveCurrency?.toDouble()!!,
        currencyCode = this.currency!!,
        reserveCurrencyCode = this.reserveCurrency!!,
        amount = this.amount?.toDouble()!!,
        owner = this.owner?.toModelDomain()!!,
        expireDate = Date(this.expireDate?.toLong()!!),
        number = this.number!!,
        cvv = this.cvv!!,
        type = when (this.type!!) {
            "Credit" -> CardType.Credit
            "Debut" -> CardType.Dedut
            else -> CardType.Undefined
        },
        system = when (this.system!!) {
            "Visa" -> CardSystem.Visa
            "Mastercard" -> CardSystem.Mastercard
            "Mir" -> CardSystem.Mir
            else -> CardSystem.Undefined
        },
        name = this.name!!,
        erip = this.erip!!,
        iban = this.iban!!,
        bankIdCode = this.bankIdCode!!
    )
}.getOrElse { exception ->
    Log.e("!!!", "CardModelData.toModelDomain ${exception.stackTraceToString()}")
    null
}

fun CardModelDomain.toModelData(): CardModelData =
    CardModelData(
        id = this.id,
        amountInReserveCurrency = this.amountInReserveCurrency.toString(),
        currency = this.currencyCode,
        reserveCurrency = this.reserveCurrencyCode,
        amount = this.amount.toString(),
        owner = this.owner.toModelData(),
        expireDate = this.expireDate.time.toString(),
        number = this.number,
        cvv = this.cvv,
        type = this.type.toString(),
        system = this.system.toString(),
        name = this.name,
        erip = this.erip,
        iban = this.iban,
        bankIdCode = this.bankIdCode
    )