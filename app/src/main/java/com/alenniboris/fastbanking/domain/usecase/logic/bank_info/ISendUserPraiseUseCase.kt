package com.alenniboris.fastbanking.domain.usecase.logic.bank_info

import com.alenniboris.fastbanking.domain.model.CustomResultModelDomain
import com.alenniboris.fastbanking.domain.model.exception.CommonExceptionModelDomain

interface ISendUserPraiseUseCase {

    suspend fun invoke(
        praise: String
    ): CustomResultModelDomain<Unit, CommonExceptionModelDomain>
}