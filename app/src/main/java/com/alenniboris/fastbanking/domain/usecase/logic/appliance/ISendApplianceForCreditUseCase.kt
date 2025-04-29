package com.alenniboris.fastbanking.domain.usecase.logic.appliance

import com.alenniboris.fastbanking.domain.model.CustomResultModelDomain
import com.alenniboris.fastbanking.domain.model.appliances.CreditApplianceModelDomain
import com.alenniboris.fastbanking.domain.model.exception.CommonExceptionModelDomain

interface ISendApplianceForCreditUseCase {

    suspend fun invoke(
        appliance: CreditApplianceModelDomain
    ): CustomResultModelDomain<Unit, CommonExceptionModelDomain>
}