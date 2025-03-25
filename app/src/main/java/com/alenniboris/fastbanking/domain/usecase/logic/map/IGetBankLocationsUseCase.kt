package com.alenniboris.fastbanking.domain.usecase.logic.map

import com.alenniboris.fastbanking.domain.model.CustomResultModelDomain
import com.alenniboris.fastbanking.domain.model.exception.MapsExceptionModelDomain
import com.alenniboris.fastbanking.domain.model.map.MapsElementModelDomain

interface IGetBankLocationsUseCase {

    suspend fun invoke(): CustomResultModelDomain<List<MapsElementModelDomain>, MapsExceptionModelDomain>
}
