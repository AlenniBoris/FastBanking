package com.alenniboris.fastbanking.domain.usecase.logic.appliance

import com.alenniboris.fastbanking.domain.model.CustomResultModelDomain
import com.alenniboris.fastbanking.domain.model.appliances.DepositApplianceModelDomain
import com.alenniboris.fastbanking.domain.model.exception.CommonExceptionModelDomain

interface IGetDepositApplianceByIdUseCase {

    suspend fun invoke(
        id: String
    ): CustomResultModelDomain<DepositApplianceModelDomain, CommonExceptionModelDomain>
}