package com.alenniboris.fastbanking.domain.usecase.logic.accounts

import com.alenniboris.fastbanking.domain.model.CustomResultModelDomain
import com.alenniboris.fastbanking.domain.model.currency.CurrencyModelDomain
import com.alenniboris.fastbanking.domain.model.exception.CommonExceptionModelDomain

interface IGetAllUserAccountsCurrencyAmountUseCase {

    suspend fun invoke(
        baseCurrency: CurrencyModelDomain
    ): CustomResultModelDomain<Double, CommonExceptionModelDomain>
}