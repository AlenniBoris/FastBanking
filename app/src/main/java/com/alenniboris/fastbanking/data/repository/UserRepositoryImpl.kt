package com.alenniboris.fastbanking.data.repository

import com.alenniboris.fastbanking.data.mappers.toCommonException
import com.alenniboris.fastbanking.data.model.AccountModelData
import com.alenniboris.fastbanking.data.model.CardModelData
import com.alenniboris.fastbanking.data.model.CreditModelData
import com.alenniboris.fastbanking.data.model.TransactionModelData
import com.alenniboris.fastbanking.data.model.toModelDomain
import com.alenniboris.fastbanking.domain.model.CustomResultModelDomain
import com.alenniboris.fastbanking.domain.model.account.AccountModelDomain
import com.alenniboris.fastbanking.domain.model.card.CardModelDomain
import com.alenniboris.fastbanking.domain.model.credit.CreditModelDomain
import com.alenniboris.fastbanking.domain.model.exception.CommonExceptionModelDomain
import com.alenniboris.fastbanking.domain.model.transaction.TransactionModelDomain
import com.alenniboris.fastbanking.domain.model.user.UserModelDomain
import com.alenniboris.fastbanking.domain.repository.IUserRepository
import com.alenniboris.fastbanking.domain.utils.GsonUtil.fromJson
import com.alenniboris.fastbanking.domain.utils.IAppDispatchers
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.withContext

class UserRepositoryImpl(
    private val database: FirebaseDatabase,
    private val dispatchers: IAppDispatchers
) : IUserRepository {

    override suspend fun getAllUserCards(
        user: UserModelDomain
    ): CustomResultModelDomain<List<CardModelDomain>, CommonExceptionModelDomain> =
        withContext(dispatchers.IO) {
            return@withContext DatabaseFunctions.requestListOfElements(
                dispatcher = dispatchers.IO,
                database = database,
                table = FirebaseDatabaseValues.TABLE_CARDS,
                jsonMapping = { json -> json.fromJson<CardModelData>() },
                modelsMapping = { dataModel -> dataModel.toModelDomain() },
                filterPredicate = { domainModel -> domainModel.owner.id == user.id },
                exceptionMapping = { exception ->
                    exception.toCommonException()
                }
            )
        }

    override suspend fun getAllUserTransactionsByCard(
        user: UserModelDomain,
        cardId: String
    ): CustomResultModelDomain<List<TransactionModelDomain>, CommonExceptionModelDomain> =
        withContext(dispatchers.IO) {
            return@withContext DatabaseFunctions.requestListOfElements(
                dispatcher = dispatchers.IO,
                database = database,
                table = FirebaseDatabaseValues.TABLE_TRANSACTION,
                jsonMapping = { json -> json.fromJson<TransactionModelData>() },
                modelsMapping = { dataModel -> dataModel.toModelDomain() },
                filterPredicate = { domainModel ->
                    domainModel.usedCardId == cardId
                },
                exceptionMapping = { exception ->
                    exception.toCommonException()
                }
            )
        }

    override suspend fun getAllUserAccounts(
        user: UserModelDomain
    ): CustomResultModelDomain<List<AccountModelDomain>, CommonExceptionModelDomain> =
        withContext(dispatchers.IO) {
            return@withContext DatabaseFunctions.requestListOfElements(
                dispatcher = dispatchers.IO,
                database = database,
                table = FirebaseDatabaseValues.TABLE_ACCOUNTS,
                jsonMapping = { json -> json.fromJson<AccountModelData>() },
                modelsMapping = { dataModel -> dataModel.toModelDomain() },
                filterPredicate = { domainModel -> domainModel.owner.id == user.id },
                exceptionMapping = { exception ->
                    exception.toCommonException()
                }
            )
        }

    override suspend fun getAllUserCredits(
        user: UserModelDomain
    ): CustomResultModelDomain<List<CreditModelDomain>, CommonExceptionModelDomain> =
        withContext(dispatchers.IO) {
            return@withContext DatabaseFunctions.requestListOfElements(
                dispatcher = dispatchers.IO,
                database = database,
                table = FirebaseDatabaseValues.TABLE_CREDITS,
                jsonMapping = { json -> json.fromJson<CreditModelData>() },
                modelsMapping = { dataModel -> dataModel.toModelDomain() },
                filterPredicate = { domainModel -> domainModel.ownerId == user.id },
                exceptionMapping = { exception ->
                    exception.toCommonException()
                }
            )
        }

    override suspend fun getUserCardById(
        id: String
    ): CustomResultModelDomain<CardModelDomain?, CommonExceptionModelDomain> =
        withContext(dispatchers.IO) {
            return@withContext DatabaseFunctions.requestElementById<CardModelData, CardModelDomain, CommonExceptionModelDomain>(
                id = id,
                dispatcher = dispatchers.IO,
                database = database,
                table = FirebaseDatabaseValues.TABLE_CARDS,
                resultCheck = { dataModel ->
                    if (dataModel == null) {
                        throw CommonExceptionModelDomain.ErrorGettingData
                    }
                },
                resultMapping = { dataModel ->
                    dataModel?.toModelDomain()
                },
                exceptionMapping = { exception ->
                    exception.toCommonException()
                }
            )
        }

    override suspend fun getUserAccountById(
        id: String
    ): CustomResultModelDomain<AccountModelDomain?, CommonExceptionModelDomain> =
        withContext(dispatchers.IO) {
            return@withContext DatabaseFunctions.requestElementById<AccountModelData, AccountModelDomain, CommonExceptionModelDomain>(
                id = id,
                dispatcher = dispatchers.IO,
                database = database,
                table = FirebaseDatabaseValues.TABLE_ACCOUNTS,
                resultCheck = { dataModel ->
                    if (dataModel == null) {
                        throw CommonExceptionModelDomain.ErrorGettingData
                    }
                },
                resultMapping = { dataModel ->
                    dataModel?.toModelDomain()
                },
                exceptionMapping = { exception ->
                    exception.toCommonException()
                }
            )
        }


    override suspend fun getUserCreditById(
        id: String
    ): CustomResultModelDomain<CreditModelDomain?, CommonExceptionModelDomain> =
        withContext(dispatchers.IO) {
            return@withContext DatabaseFunctions.requestElementById<CreditModelData, CreditModelDomain, CommonExceptionModelDomain>(
                id = id,
                dispatcher = dispatchers.IO,
                database = database,
                table = FirebaseDatabaseValues.TABLE_CREDITS,
                resultCheck = { dataModel ->
                    if (dataModel == null) {
                        throw CommonExceptionModelDomain.ErrorGettingData
                    }
                },
                resultMapping = { dataModel ->
                    dataModel?.toModelDomain()
                },
                exceptionMapping = { exception ->
                    exception.toCommonException()
                }
            )
        }
}