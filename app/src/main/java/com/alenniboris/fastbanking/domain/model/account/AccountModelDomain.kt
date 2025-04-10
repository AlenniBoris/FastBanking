package com.alenniboris.fastbanking.domain.model.account

import com.alenniboris.fastbanking.domain.model.OwnerModelDomain

data class AccountModelDomain(
    val id: String,
    val amount: Double,
    val amountInReserveCurrency: Double,
    val currency: String,
    val reserveCurrency: String,
    val attachedCards: List<String>,
    val owner: OwnerModelDomain
)
