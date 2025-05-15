package com.alenniboris.fastbanking.domain.model.credit

import java.util.Date

data class CreditModelDomain(
    val id: String,
    val initialAmount: Double,
    val currency: String,
    val amountInReserveCurrency: Double,
    val reserveCurrency: String,
    val percentage: Double,
    val lastPayment: Date,
    val startDate: Date,
    val goalDescription: String,
    val ownerId: String,
    val name: String
)
