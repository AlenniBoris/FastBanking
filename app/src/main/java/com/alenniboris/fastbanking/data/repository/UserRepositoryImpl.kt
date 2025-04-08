package com.alenniboris.fastbanking.data.repository

import android.util.Log
import com.alenniboris.fastbanking.data.mappers.toCommonException
import com.alenniboris.fastbanking.data.model.CardModelData
import com.alenniboris.fastbanking.data.model.TransactionModelData
import com.alenniboris.fastbanking.data.model.toModelDomain
import com.alenniboris.fastbanking.domain.model.CustomResultModelDomain
import com.alenniboris.fastbanking.domain.model.card.CardModelDomain
import com.alenniboris.fastbanking.domain.model.exception.CommonExceptionModelDomain
import com.alenniboris.fastbanking.domain.model.transaction.TransactionModelDomain
import com.alenniboris.fastbanking.domain.model.user.UserModelDomain
import com.alenniboris.fastbanking.domain.repository.IUserRepository
import com.alenniboris.fastbanking.domain.utils.GsonUtil.fromJson
import com.alenniboris.fastbanking.domain.utils.GsonUtil.toJson
import com.alenniboris.fastbanking.domain.utils.IAppDispatchers
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class UserRepositoryImpl(
    private val database: FirebaseDatabase,
    private val dispatchers: IAppDispatchers
) : IUserRepository {

    override suspend fun getAllUserCards(
        user: UserModelDomain
    ): CustomResultModelDomain<List<CardModelDomain>, CommonExceptionModelDomain> =
        withContext(dispatchers.IO) {
            runCatching {

                val snapshot = database.reference
                    .child(FirebaseDatabaseValues.TABLE_CARDS)
                    .get()
                    .await()

                val cards = snapshot.children
                    .toList()
                    .map { it.value.toJson().fromJson<CardModelData>() }
                    .mapNotNull { it.toModelDomain() }
                    .filter { it.owner.id == user.id }

                return@withContext CustomResultModelDomain.Success(
                    cards
                )
            }.getOrElse { exception ->
                Log.e("!!!", exception.stackTraceToString())
                return@withContext CustomResultModelDomain.Error(
                    exception.toCommonException()
                )
            }
        }

    override suspend fun getAllUserTransactions(
        user: UserModelDomain
    ): CustomResultModelDomain<List<TransactionModelDomain>, CommonExceptionModelDomain> =
        withContext(dispatchers.IO) {
            runCatching {
                val snapshot = database.reference
                    .child(FirebaseDatabaseValues.TABLE_TRANSACTION)
                    .get()
                    .await()

                val transactions = snapshot.children
                    .toList()
                    .map { it.value.toJson().fromJson<TransactionModelData>() }
                    .mapNotNull { it.toModelDomain() }
                    .filter { it.senderId == user.id }

                return@withContext CustomResultModelDomain.Success(
                    transactions
                )
            }.getOrElse { exception ->
                Log.e("!!!", exception.stackTraceToString())
                return@withContext CustomResultModelDomain.Error(
                    exception.toCommonException()
                )
            }
        }
}