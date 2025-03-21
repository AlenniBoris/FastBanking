package com.alenniboris.fastbanking.domain.repository

import com.alenniboris.fastbanking.domain.model.CustomResultModelDomain
import com.alenniboris.fastbanking.domain.model.exception.MapsExceptionModelDomain
import com.alenniboris.fastbanking.domain.model.map.MapsElementModelDomain

interface IMapsRepository {

    suspend fun getLocations(): CustomResultModelDomain<List<MapsElementModelDomain>, MapsExceptionModelDomain>
}