package com.alenniboris.fastbanking.domain.repository

import com.alenniboris.fastbanking.domain.model.CustomResultModelDomain
import com.alenniboris.fastbanking.domain.model.country.CountryInfoModelDomain
import com.alenniboris.fastbanking.domain.model.exception.CurrencyExceptionModelDomain

interface ICountriesRepository {

    suspend fun GetCountryFlagByCurrencyCode(
        currencyCode: String
    ): CustomResultModelDomain<CountryInfoModelDomain, CurrencyExceptionModelDomain>
}