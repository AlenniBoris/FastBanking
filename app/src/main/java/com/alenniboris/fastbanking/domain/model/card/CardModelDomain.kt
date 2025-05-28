package com.alenniboris.fastbanking.domain.model.card

import com.alenniboris.fastbanking.domain.model.OwnerModelDomain
import java.util.Date

data class CardModelDomain(
    val id: String,
    val currencyCode: String,
    val reserveCurrencyCode: String,
    val amountInReserveCurrency: Double,
    val amount: Double,
    val owner: OwnerModelDomain,
    val expireDate: Date,
    val number: String,
    val cvv: String,
    val type: CardType,
    val system: CardSystem,
    val name: String,
    val erip: String,
    val iban: String,
    val bankIdCode: String,
)
