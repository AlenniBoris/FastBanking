package com.alenniboris.fastbanking.data.repository

import android.util.Log
import com.alenniboris.fastbanking.data.mappers.toMapsException
import com.alenniboris.fastbanking.data.model.MapsElementModelData
import com.alenniboris.fastbanking.data.model.toModelDomain
import com.alenniboris.fastbanking.domain.model.CustomResultModelDomain
import com.alenniboris.fastbanking.domain.model.exception.MapsExceptionModelDomain
import com.alenniboris.fastbanking.domain.model.map.MapsElementModelDomain
import com.alenniboris.fastbanking.domain.repository.IMapsRepository
import com.alenniboris.fastbanking.domain.utils.GsonUtil.fromJson
import com.alenniboris.fastbanking.domain.utils.GsonUtil.toJson
import com.alenniboris.fastbanking.domain.utils.IAppDispatchers
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class MapsRepositoryImpl(
    private val database: FirebaseDatabase,
    private val dispatchers: IAppDispatchers
) : IMapsRepository {

    override suspend fun getLocations(): CustomResultModelDomain<List<MapsElementModelDomain>, MapsExceptionModelDomain> =
        withContext(dispatchers.IO) {
            runCatching {

                val snapshot = database.reference
                    .child(FirebaseDatabaseValues.TABLE_MAPS_DATA)
                    .get()
                    .await()


                val elements = snapshot.children
                    .toList()
                    .map {
                        it.value.toJson().fromJson<MapsElementModelData>()
                    }
                    .mapNotNull { it.toModelDomain() }


                CustomResultModelDomain.Success<List<MapsElementModelDomain>, MapsExceptionModelDomain>(
                    elements
                )
            }.getOrElse { exception ->
                Log.e("!!!", exception.stackTraceToString())
                CustomResultModelDomain.Error(
                    exception.toMapsException()
                )
            }
        }
}