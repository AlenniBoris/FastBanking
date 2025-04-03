package com.alenniboris.fastbanking.data.repository

import android.util.Log
import com.alenniboris.fastbanking.data.mappers.toCommonInfoException
import com.alenniboris.fastbanking.data.model.ApplicationInfoModelData
import com.alenniboris.fastbanking.data.model.BankNewsModelData
import com.alenniboris.fastbanking.data.model.toModelDomain
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
            runCatching {

                val snapshot = database.reference
                    .child(FirebaseDatabaseValues.TABLE_BANK_NEWS)
                    .get()
                    .await()

                val elements = snapshot.children
                    .toList()
                    .map { it.value.toJson().fromJson<BankNewsModelData>() }
                    .mapNotNull { it.toModelDomain() }

                return@withContext CustomResultModelDomain.Success(elements)
            }.getOrElse { exception ->
                Log.e("!!!", "getBankNews ${exception.stackTraceToString()}")
                return@withContext CustomResultModelDomain.Error(exception.toCommonInfoException())
            }
        }
}