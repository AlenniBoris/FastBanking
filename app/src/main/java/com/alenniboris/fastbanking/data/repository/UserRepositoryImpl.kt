package com.alenniboris.fastbanking.data.repository

import com.alenniboris.fastbanking.domain.model.CustomResultModelDomain
import com.alenniboris.fastbanking.domain.model.card.CardModelDomain
import com.alenniboris.fastbanking.domain.model.exception.CommonExceptionModelDomain
import com.alenniboris.fastbanking.domain.model.transaction.TransactionModelDomain
import com.alenniboris.fastbanking.domain.model.user.UserModelDomain
import com.alenniboris.fastbanking.domain.repository.IUserRepository
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
            TODO("Not yet implemented")
        }

    override suspend fun getAllUserTransactions(
        user: UserModelDomain
    ): CustomResultModelDomain<List<TransactionModelDomain>, CommonExceptionModelDomain> =
        withContext(dispatchers.IO) {
            TODO("Not yet implemented")
        }
}