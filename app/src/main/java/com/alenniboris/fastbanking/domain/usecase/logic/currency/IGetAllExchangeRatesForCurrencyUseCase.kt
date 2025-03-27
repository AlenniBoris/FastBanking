package com.alenniboris.fastbanking.domain.usecase.logic.currency

import com.alenniboris.fastbanking.domain.model.CustomResultModelDomain
import com.alenniboris.fastbanking.domain.model.currency.CurrencyModelDomain
import com.alenniboris.fastbanking.domain.model.currency.CurrencyRatesModelDomain
import com.alenniboris.fastbanking.domain.model.exception.CurrencyExceptionModelDomain

interface IGetAllExchangeRatesForCurrencyUseCase {

    suspend fun invoke(
        currency: CurrencyModelDomain
    ): CustomResultModelDomain<CurrencyRatesModelDomain, CurrencyExceptionModelDomain>
}