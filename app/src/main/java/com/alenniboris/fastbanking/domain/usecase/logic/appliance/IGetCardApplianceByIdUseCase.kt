package com.alenniboris.fastbanking.domain.usecase.logic.appliance

import com.alenniboris.fastbanking.domain.model.CustomResultModelDomain
import com.alenniboris.fastbanking.domain.model.appliances.CardApplianceModelDomain
import com.alenniboris.fastbanking.domain.model.exception.CommonExceptionModelDomain

interface IGetCardApplianceByIdUseCase {

    suspend fun invoke(
        id: String
    ): CustomResultModelDomain<CardApplianceModelDomain?, CommonExceptionModelDomain>
}