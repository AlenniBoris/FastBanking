package com.alenniboris.fastbanking.domain.usecase.logic.currency

import com.alenniboris.fastbanking.domain.model.CustomResultModelDomain
import com.alenniboris.fastbanking.domain.model.currency.CurrencyInfoModelDomain
import com.alenniboris.fastbanking.domain.model.exception.CurrencyExceptionModelDomain

interface IGetCurrenciesExchangeRateUseCase {

    suspend fun invoke(
        first: CurrencyInfoModelDomain,
        second: CurrencyInfoModelDomain
    ): CustomResultModelDomain<Double, CurrencyExceptionModelDomain>
}