package com.alenniboris.fastbanking.domain.model.transaction

import com.alenniboris.fastbanking.domain.model.currency.CurrencyModelDomain
import java.util.Date

data class TransactionModelDomain(
    val currency: CurrencyModelDomain,
    val date: Date,
    val details: String,
    val id: String,
    val priceAmount: Double,
    val senderId: String,
    val receiverId: String,
    val type: TransactionType,
)