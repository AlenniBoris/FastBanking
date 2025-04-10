package com.alenniboris.fastbanking.domain.usecase.logic.bank_info

import com.alenniboris.fastbanking.domain.model.CustomResultModelDomain
import com.alenniboris.fastbanking.domain.model.bank_info.RecommendedNewsModelDomain
import com.alenniboris.fastbanking.domain.model.exception.CommonInfoExceptionModelDomain

interface IGetBankRecommendedNewsUseCase {

    suspend fun invoke(): CustomResultModelDomain<List<RecommendedNewsModelDomain>, CommonInfoExceptionModelDomain>
}