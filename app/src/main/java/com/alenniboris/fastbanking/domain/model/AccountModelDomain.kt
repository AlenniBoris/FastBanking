package com.alenniboris.fastbanking.domain.model

data class AccountModelDomain(
    val id: String,
    val amount: Double,
    val attachedCards: List<String>,
    val owner: OwnerModelDomain
)
