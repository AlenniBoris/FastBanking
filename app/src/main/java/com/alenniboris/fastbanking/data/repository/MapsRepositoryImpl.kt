package com.alenniboris.fastbanking.data.repository

import com.alenniboris.fastbanking.domain.model.CustomResultModelDomain
import com.alenniboris.fastbanking.domain.model.exception.MapsExceptionModelDomain
import com.alenniboris.fastbanking.domain.model.map.MapsElementModelDomain
import com.alenniboris.fastbanking.domain.repository.IMapsRepository

class MapsRepositoryImpl(): IMapsRepository {
    override suspend fun getLocations(): CustomResultModelDomain<List<MapsElementModelDomain>, MapsExceptionModelDomain> {
        TODO("Not yet implemented")
    }
}