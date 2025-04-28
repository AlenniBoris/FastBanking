package com.alenniboris.fastbanking.domain.usecase.logic.appliance

import com.alenniboris.fastbanking.domain.model.CustomResultModelDomain
import com.alenniboris.fastbanking.domain.model.appliances.CreditApplianceModelDomain
import com.alenniboris.fastbanking.domain.model.exception.CommonExceptionModelDomain

interface IGetCreditApplianceByIdUseCase {

    suspend fun invoke(
        id: String
    ): CustomResultModelDomain<CreditApplianceModelDomain, CommonExceptionModelDomain>
}