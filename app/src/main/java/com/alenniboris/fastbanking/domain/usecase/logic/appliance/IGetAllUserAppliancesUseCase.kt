package com.alenniboris.fastbanking.domain.usecase.logic.appliance

import com.alenniboris.fastbanking.domain.model.CustomResultModelDomain
import com.alenniboris.fastbanking.domain.model.appliances.IProductAppliance
import com.alenniboris.fastbanking.domain.model.exception.CommonExceptionModelDomain

interface IGetAllUserAppliancesUseCase {

    suspend fun invoke(): CustomResultModelDomain<List<IProductAppliance>, CommonExceptionModelDomain>
}