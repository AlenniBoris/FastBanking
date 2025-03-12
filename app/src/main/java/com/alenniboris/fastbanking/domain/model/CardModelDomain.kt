package com.alenniboris.fastbanking.domain.model

import java.util.Date

data class CardModelDomain(
    val id: String,
    val amount: Double,
    val owner: OwnerModelDomain,
    val expireDate: Date,
    val number: Long,
    val cvv: Int,
    val type: CardType
)
