package com.alenniboris.fastbanking.domain.model.card

import com.alenniboris.fastbanking.domain.model.OwnerModelDomain
import java.util.Date

data class CardModelDomain(
    val id: String,
    val amount: Double,
    val owner: OwnerModelDomain,
    val expireDate: Date,
    val number: String,
    val cvv: String,
    val type: CardType
)
