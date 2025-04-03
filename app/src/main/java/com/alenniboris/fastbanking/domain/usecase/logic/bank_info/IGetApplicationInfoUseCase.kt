package com.alenniboris.fastbanking.domain.usecase.logic.bank_info

import com.alenniboris.fastbanking.domain.model.CustomResultModelDomain
import com.alenniboris.fastbanking.domain.model.bank_info.ApplicationInfoModelDomain
import com.alenniboris.fastbanking.domain.model.exception.CommonInfoExceptionModelDomain

interface IGetApplicationInfoUseCase {

    suspend fun invoke(): CustomResultModelDomain<ApplicationInfoModelDomain, CommonInfoExceptionModelDomain>
}