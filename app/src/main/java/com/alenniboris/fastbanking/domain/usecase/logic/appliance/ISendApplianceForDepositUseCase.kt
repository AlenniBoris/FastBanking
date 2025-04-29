package com.alenniboris.fastbanking.domain.usecase.logic.appliance

import com.alenniboris.fastbanking.domain.model.CustomResultModelDomain
import com.alenniboris.fastbanking.domain.model.appliances.DepositApplianceModelDomain
import com.alenniboris.fastbanking.domain.model.exception.CommonExceptionModelDomain

interface ISendApplianceForDepositUseCase {

    suspend fun invoke(
        appliance: DepositApplianceModelDomain
    ): CustomResultModelDomain<Unit, CommonExceptionModelDomain>
}