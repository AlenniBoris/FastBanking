package com.alenniboris.fastbanking.data.repository

import com.alenniboris.fastbanking.domain.model.CustomResultModelDomain
import com.alenniboris.fastbanking.domain.model.currency.CurrencyInfoModelDomain
import com.alenniboris.fastbanking.domain.model.exception.CurrencyExceptionModelDomain
import com.alenniboris.fastbanking.domain.repository.ICurrencyRepository

class CurrencyRepositoryImpl() : ICurrencyRepository {

    override suspend fun getAllCurrencies()
            : CustomResultModelDomain<List<CurrencyInfoModelDomain>, CurrencyExceptionModelDomain> {
        TODO("Not yet implemented")
    }

    override suspend fun getExchangeRateForCurrencies(
        first: CurrencyInfoModelDomain,
        second: CurrencyInfoModelDomain
    ): CustomResultModelDomain<Double, CurrencyExceptionModelDomain> {
        TODO("Not yet implemented")
    }
}