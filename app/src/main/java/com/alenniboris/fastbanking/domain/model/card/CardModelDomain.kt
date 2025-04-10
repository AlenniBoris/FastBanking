package com.alenniboris.fastbanking.domain.model.card

import com.alenniboris.fastbanking.domain.model.OwnerModelDomain
import java.util.Date

data class CardModelDomain(
    val id: String,
    val currency: String,
    val reserveCurrency: String,
    val amountInReserveCurrency: Double,
    val amount: Double,
    val owner: OwnerModelDomain,
    val expireDate: Date,
    val number: String,
    val cvv: String,
    val type: CardType,
    val system: CardSystem
)
