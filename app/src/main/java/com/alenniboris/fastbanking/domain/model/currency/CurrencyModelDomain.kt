package com.alenniboris.fastbanking.domain.model.currency

import com.alenniboris.fastbanking.domain.model.country.CountryInfoModelDomain

data class CurrencyModelDomain(
    val currencyInfo: CurrencyInfoModelDomain,
    val countryInfo: CountryInfoModelDomain
)