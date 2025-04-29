package com.alenniboris.fastbanking.data.repository

import android.util.Log
import com.alenniboris.fastbanking.domain.model.CustomResultModelDomain
import com.alenniboris.fastbanking.domain.utils.GsonUtil.toJson
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

object DatabaseFunctions {

    suspend fun <DataModel, DomainModel, ExceptionModel> requestListOfElements(
        dispatcher: CoroutineDispatcher,
        database: FirebaseDatabase,
        table: String,
        jsonMapping: (String) -> DataModel,
        modelsMapping: (DataModel) -> DomainModel?,
        filterPredicate: (DomainModel) -> Boolean,
        exceptionMapping: (Throwable) -> ExceptionModel
    ): CustomResultModelDomain<List<DomainModel>, ExceptionModel> = withContext(dispatcher) {
        runCatching {
            val snapshot = database.reference
                .child(table)
                .get()
                .await()

            val elements = snapshot.children
                .toList()
                .map { jsonMapping(it.value.toJson()) }
                .mapNotNull { modelsMapping(it) }
                .filter { filterPredicate(it) }

            return@withContext CustomResultModelDomain.Success(
                elements
            )
        }.getOrElse { exception ->
            Log.e("!!!", exception.stackTraceToString())
            return@withContext CustomResultModelDomain.Error(
                exceptionMapping(exception)
            )
        }
    }

    suspend inline fun <reified DataModel, DomainModel, ExceptionModel> requestElementById(
        id: String,
        dispatcher: CoroutineDispatcher,
        database: FirebaseDatabase,
        table: String,
        crossinline resultMapping: (DataModel?) -> DomainModel?,
        crossinline resultCheck: (DataModel?) -> Unit,
        crossinline exceptionMapping: (Throwable) -> ExceptionModel
    ): CustomResultModelDomain<DomainModel?, ExceptionModel> = withContext(dispatcher) {
        runCatching {

            val snapshot = database.reference
                .child(table)
                .child(id)
                .get()
                .await()

            val result = snapshot.getValue(DataModel::class.java)

            resultCheck(result)

            return@withContext CustomResultModelDomain.Success(
                resultMapping(result)
            )
        }.getOrElse { exception ->
            Log.e("!!!", exception.stackTraceToString())
            return@withContext CustomResultModelDomain.Error(
                exceptionMapping(exception)
            )
        }
    }

    suspend inline fun <reified SavingModel, ExceptionModel> sendApplianceForProduct(
        dispatcher: CoroutineDispatcher,
        database: FirebaseDatabase,
        table: String,
        crossinline exceptionMapping: (Throwable) -> ExceptionModel,
        crossinline onGeneratingError: () -> ExceptionModel,
        crossinline editingAppliance: (newId: String) -> SavingModel,
    ): CustomResultModelDomain<Unit, ExceptionModel> = withContext(dispatcher) {
        runCatching {

            val tableRef = database.getReference(table)
            val newTableRef = tableRef.push()

            val newApplianceId =
                newTableRef.key ?: return@withContext CustomResultModelDomain.Error(
                    onGeneratingError()
                )

            val editedAppliance = editingAppliance(newApplianceId)

            newTableRef.setValue(editedAppliance)

            return@withContext CustomResultModelDomain.Success(Unit)
        }.getOrElse { exception ->
            Log.e("!!!", exception.stackTraceToString())
            return@withContext CustomResultModelDomain.Error(
                exceptionMapping(exception)
            )
        }
    }
}