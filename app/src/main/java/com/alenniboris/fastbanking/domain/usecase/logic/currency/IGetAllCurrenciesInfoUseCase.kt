package com.alenniboris.fastbanking.domain.usecase.logic.currency

import com.alenniboris.fastbanking.domain.model.CustomResultModelDomain
import com.alenniboris.fastbanking.domain.model.currency.CurrencyInfoModelDomain
import com.alenniboris.fastbanking.domain.model.exception.CurrencyExceptionModelDomain

interface IGetAllCurrenciesInfoUseCase {

    suspend fun invoke(): CustomResultModelDomain<List<CurrencyInfoModelDomain>, CurrencyExceptionModelDomain>
}