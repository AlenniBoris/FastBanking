package com.alenniboris.fastbanking.domain.usecase.logic.credits

import com.alenniboris.fastbanking.domain.model.CustomResultModelDomain
import com.alenniboris.fastbanking.domain.model.credit.CreditModelDomain
import com.alenniboris.fastbanking.domain.model.exception.CommonExceptionModelDomain

interface IGetCreditByIdUseCase {

    suspend fun invoke(
        id: String
    ): CustomResultModelDomain<CreditModelDomain?, CommonExceptionModelDomain>
}