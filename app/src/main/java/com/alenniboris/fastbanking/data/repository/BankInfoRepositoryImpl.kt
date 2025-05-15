package com.alenniboris.fastbanking.data.repository

import android.util.Log
import com.alenniboris.fastbanking.data.mappers.toCommonInfoException
import com.alenniboris.fastbanking.data.model.bank_info.ApplicationInfoModelData
import com.alenniboris.fastbanking.data.model.bank_info.BankNewsModelData
import com.alenniboris.fastbanking.data.model.bank_info.toModelDomain
import com.alenniboris.fastbanking.domain.model.CustomResultModelDomain
import com.alenniboris.fastbanking.domain.model.bank_info.ApplicationInfoModelDomain
import com.alenniboris.fastbanking.domain.model.bank_info.BankNewsModelDomain
import com.alenniboris.fastbanking.domain.model.exception.CommonInfoExceptionModelDomain
import com.alenniboris.fastbanking.domain.repository.IBankInfoRepository
import com.alenniboris.fastbanking.domain.utils.GsonUtil.fromJson
import com.alenniboris.fastbanking.domain.utils.GsonUtil.toJson
import com.alenniboris.fastbanking.domain.utils.IAppDispatchers
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class BankInfoRepositoryImpl(
    private val database: FirebaseDatabase,
    private val dispatchers: IAppDispatchers
) : IBankInfoRepository {

    override suspend fun getApplicationInformation():
            CustomResultModelDomain<ApplicationInfoModelDomain, CommonInfoExceptionModelDomain> =
        withContext(dispatchers.IO) {
            runCatching {

                val snapshot = database.reference
                    .child(FirebaseDatabaseValues.APPLICATION_DATA)
                    .get()
                    .await()

                val data = snapshot.value.toJson().fromJson<ApplicationInfoModelData>()

                if (data.hasSomeValueMissing) {
                    return@withContext CustomResultModelDomain.Error(
                        CommonInfoExceptionModelDomain.ServerError
                    )
                }

                val result =
                    data.toModelDomain() ?: return@withContext CustomResultModelDomain.Error(
                        CommonInfoExceptionModelDomain.ErrorGettingData
                    )

                return@withContext CustomResultModelDomain.Success(result)
            }.getOrElse { exception ->
                Log.e("!!!", "getApplicationInformation ${exception.stackTraceToString()}")
                return@withContext CustomResultModelDomain.Error(exception.toCommonInfoException())
            }
        }

    override suspend fun getBankNews():
            CustomResultModelDomain<List<BankNewsModelDomain>, CommonInfoExceptionModelDomain> =
        withContext(dispatchers.IO) {
            return@withContext DatabaseFunctions.requestListOfElements(
                dispatcher = dispatchers.IO,
                database = database,
                table = FirebaseDatabaseValues.TABLE_BANK_NEWS,
                jsonMapping = { json -> json.fromJson<BankNewsModelData>() },
                modelsMapping = { dataModel -> dataModel.toModelDomain() },
                filterPredicate = { domainModel -> true },
                exceptionMapping = { exception ->
                    exception.toCommonInfoException()
                }
            )
        }

    override suspend fun getBankRecommendedNews():
            CustomResultModelDomain<List<BankNewsModelDomain>, CommonInfoExceptionModelDomain> =
        withContext(dispatchers.IO) {
            return@withContext DatabaseFunctions.requestListOfElements(
                dispatcher = dispatchers.IO,
                database = database,
                table = FirebaseDatabaseValues.TABLE_BANK_NEWS,
                jsonMapping = { json -> json.fromJson<BankNewsModelData>() },
                modelsMapping = { dataModel -> dataModel.toModelDomain() },
                filterPredicate = { domainModel -> domainModel.isRecommended },
                exceptionMapping = { exception ->
                    exception.toCommonInfoException()
                }
            )
        }

    override suspend fun getBankNewsById(
        id: String
    ): CustomResultModelDomain<BankNewsModelDomain?, CommonInfoExceptionModelDomain> =
        withContext(dispatchers.IO) {
            return@withContext DatabaseFunctions.requestElementById<BankNewsModelData, BankNewsModelDomain, CommonInfoExceptionModelDomain>(
                id = id,
                dispatcher = dispatchers.IO,
                database = database,
                table = FirebaseDatabaseValues.TABLE_BANK_NEWS,
                resultCheck = { dataModel ->
                    if (dataModel == null) {
                        throw CommonInfoExceptionModelDomain.ErrorGettingData
                    }
                },
                resultMapping = { dataModel ->
                    dataModel?.toModelDomain()
                },
                exceptionMapping = { exception ->
                    exception.toCommonInfoException()
                }
            )
        }
}