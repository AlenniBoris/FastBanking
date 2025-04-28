package com.alenniboris.fastbanking.data.repository

import com.alenniboris.fastbanking.data.mappers.toMapsException
import com.alenniboris.fastbanking.data.model.map.MapsElementModelData
import com.alenniboris.fastbanking.data.model.map.toModelDomain
import com.alenniboris.fastbanking.domain.model.CustomResultModelDomain
import com.alenniboris.fastbanking.domain.model.exception.MapsExceptionModelDomain
import com.alenniboris.fastbanking.domain.model.map.MapsElementModelDomain
import com.alenniboris.fastbanking.domain.repository.IMapsRepository
import com.alenniboris.fastbanking.domain.utils.GsonUtil.fromJson
import com.alenniboris.fastbanking.domain.utils.IAppDispatchers
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.withContext

class MapsRepositoryImpl(
    private val database: FirebaseDatabase,
    private val dispatchers: IAppDispatchers
) : IMapsRepository {

    override suspend fun getLocations(): CustomResultModelDomain<List<MapsElementModelDomain>, MapsExceptionModelDomain> =
        withContext(dispatchers.IO) {
            return@withContext DatabaseFunctions.requestListOfElements(
                dispatcher = dispatchers.IO,
                database = database,
                table = FirebaseDatabaseValues.TABLE_MAPS_DATA,
                jsonMapping = { json -> json.fromJson<MapsElementModelData>() },
                modelsMapping = { dataModel -> dataModel.toModelDomain() },
                filterPredicate = { domainModel -> true },
                exceptionMapping = { exception ->
                    exception.toMapsException()
                }
            )
        }
}