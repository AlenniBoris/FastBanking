package com.alenniboris.fastbanking.domain.repository

import com.alenniboris.fastbanking.domain.model.CustomResultModelDomain
import com.alenniboris.fastbanking.domain.model.account.AccountModelDomain
import com.alenniboris.fastbanking.domain.model.card.CardModelDomain
import com.alenniboris.fastbanking.domain.model.credit.CreditModelDomain
import com.alenniboris.fastbanking.domain.model.exception.CommonExceptionModelDomain
import com.alenniboris.fastbanking.domain.model.transaction.TransactionModelDomain
import com.alenniboris.fastbanking.domain.model.user.UserModelDomain

interface IUserRepository {

    suspend fun getAllUserCards(
        user: UserModelDomain
    ): CustomResultModelDomain<List<CardModelDomain>, CommonExceptionModelDomain>

    suspend fun getAllUserTransactionsByCard(
        user: UserModelDomain,
        cardId: String
    ): CustomResultModelDomain<List<TransactionModelDomain>, CommonExceptionModelDomain>

    suspend fun getUserCardById(
        id: String
    ): CustomResultModelDomain<CardModelDomain?, CommonExceptionModelDomain>

    suspend fun getAllUserAccounts(
        user: UserModelDomain
    ): CustomResultModelDomain<List<AccountModelDomain>, CommonExceptionModelDomain>

    suspend fun getUserAccountById(
        id: String
    ): CustomResultModelDomain<AccountModelDomain?, CommonExceptionModelDomain>

    suspend fun getAllUserCredits(
        user: UserModelDomain
    ): CustomResultModelDomain<List<CreditModelDomain>, CommonExceptionModelDomain>

    suspend fun getUserCreditById(
        id: String
    ): CustomResultModelDomain<CreditModelDomain?, CommonExceptionModelDomain>
}