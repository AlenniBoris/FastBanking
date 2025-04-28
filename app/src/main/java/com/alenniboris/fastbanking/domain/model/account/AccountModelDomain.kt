package com.alenniboris.fastbanking.domain.model.account

import com.alenniboris.fastbanking.domain.model.OwnerModelDomain
import com.alenniboris.fastbanking.domain.model.card.SimpleCardModelDomain

data class AccountModelDomain(
    val id: String,
    val amount: Double,
    val amountInReserveCurrency: Double,
    val currency: String,
    val reserveCurrency: String,
    val attachedCards: Map<String, SimpleCardModelDomain>,
    val owner: OwnerModelDomain
)
