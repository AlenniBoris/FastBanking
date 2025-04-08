package com.alenniboris.fastbanking.domain.repository

import com.alenniboris.fastbanking.domain.model.CustomResultModelDomain
import com.alenniboris.fastbanking.domain.model.card.CardModelDomain
import com.alenniboris.fastbanking.domain.model.exception.CommonExceptionModelDomain
import com.alenniboris.fastbanking.domain.model.transaction.TransactionModelDomain
import com.alenniboris.fastbanking.domain.model.user.UserModelDomain

interface IUserRepository {

    suspend fun getAllUserCards(
        user: UserModelDomain
    ): CustomResultModelDomain<List<CardModelDomain>, CommonExceptionModelDomain>

    suspend fun getAllUserTransactions(
        user: UserModelDomain
    ): CustomResultModelDomain<List<TransactionModelDomain>, CommonExceptionModelDomain>
}