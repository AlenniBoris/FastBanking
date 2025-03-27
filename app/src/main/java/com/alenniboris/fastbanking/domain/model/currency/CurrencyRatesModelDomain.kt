package com.alenniboris.fastbanking.domain.model.currency

import java.util.Date

data class CurrencyRatesModelDomain(
    val lastUpdateDate: Date,
    val nextUpdateDate: Date,
    val baseCode: String,
    val listOfRates: List<RateModelDomain>
)

data class RateModelDomain(
    val code: String,
    val rate: Double
)