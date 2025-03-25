package com.alenniboris.fastbanking.domain.usecase.logic.currency

import com.alenniboris.fastbanking.domain.model.CustomResultModelDomain
import com.alenniboris.fastbanking.domain.model.country.CountryInfoModelDomain
import com.alenniboris.fastbanking.domain.model.exception.CurrencyExceptionModelDomain

interface IGetCountryFlagByCurrencyCodeUseCase {

    suspend fun invoke(
        currencyCode: String
    ): CustomResultModelDomain<CountryInfoModelDomain, CurrencyExceptionModelDomain>
}