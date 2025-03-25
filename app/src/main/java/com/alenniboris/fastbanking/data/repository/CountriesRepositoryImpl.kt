package com.alenniboris.fastbanking.data.repository

import com.alenniboris.fastbanking.domain.model.CustomResultModelDomain
import com.alenniboris.fastbanking.domain.model.country.CountryInfoModelDomain
import com.alenniboris.fastbanking.domain.model.exception.CurrencyExceptionModelDomain
import com.alenniboris.fastbanking.domain.repository.ICountriesRepository

class CountriesRepositoryImpl() : ICountriesRepository {

    override suspend fun GetCountryFlagByCurrencyCode(
        currencyCode: String
    ): CustomResultModelDomain<CountryInfoModelDomain, CurrencyExceptionModelDomain> {
        TODO("Not yet implemented")
    }
}