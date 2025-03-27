package com.alenniboris.fastbanking.domain.repository

import com.alenniboris.fastbanking.domain.model.CustomResultModelDomain
import com.alenniboris.fastbanking.domain.model.currency.CurrencyModelDomain
import com.alenniboris.fastbanking.domain.model.currency.CurrencyRatesModelDomain
import com.alenniboris.fastbanking.domain.model.exception.CurrencyExceptionModelDomain

interface ICurrencyRepository {

    suspend fun getAllCurrencies(): CustomResultModelDomain<List<CurrencyModelDomain>, CurrencyExceptionModelDomain>

    suspend fun getExchangeRateForCurrencies(
        fromCurrency: CurrencyModelDomain?,
        toCurrency: CurrencyModelDomain?
    ): CustomResultModelDomain<Double, CurrencyExceptionModelDomain>

    suspend fun getLatestConversionRates(
        currency: CurrencyModelDomain
    ): CustomResultModelDomain<CurrencyRatesModelDomain, CurrencyExceptionModelDomain>
}