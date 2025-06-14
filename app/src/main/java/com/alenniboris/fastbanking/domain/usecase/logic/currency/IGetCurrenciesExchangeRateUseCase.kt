package com.alenniboris.fastbanking.domain.usecase.logic.currency

import com.alenniboris.fastbanking.domain.model.CustomResultModelDomain
import com.alenniboris.fastbanking.domain.model.currency.CurrencyModelDomain
import com.alenniboris.fastbanking.domain.model.exception.CurrencyExceptionModelDomain

interface IGetCurrenciesExchangeRateUseCase {

    suspend fun invoke(
        fromCurrency: CurrencyModelDomain?,
        toCurrency: CurrencyModelDomain?
    ): CustomResultModelDomain<Double, CurrencyExceptionModelDomain>
}