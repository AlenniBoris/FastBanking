package com.alenniboris.fastbanking.domain.repository

import com.alenniboris.fastbanking.domain.model.CustomResultModelDomain
import com.alenniboris.fastbanking.domain.model.currency.CurrencyInfoModelDomain
import com.alenniboris.fastbanking.domain.model.exception.CurrencyExceptionModelDomain

interface ICurrencyRepository {

    suspend fun getAllCurrencies(): CustomResultModelDomain<List<CurrencyInfoModelDomain>, CurrencyExceptionModelDomain>

    suspend fun getExchangeRateForCurrencies(
        fromCurrency: CurrencyInfoModelDomain,
        toCurrency: CurrencyInfoModelDomain
    ): CustomResultModelDomain<Double, CurrencyExceptionModelDomain>
}