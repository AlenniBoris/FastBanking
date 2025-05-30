package com.alenniboris.fastbanking.domain.repository

import com.alenniboris.fastbanking.domain.model.CustomResultModelDomain
import com.alenniboris.fastbanking.domain.model.account.AccountModelDomain
import com.alenniboris.fastbanking.domain.model.appliances.CardApplianceModelDomain
import com.alenniboris.fastbanking.domain.model.appliances.CreditApplianceModelDomain
import com.alenniboris.fastbanking.domain.model.appliances.DepositApplianceModelDomain
import com.alenniboris.fastbanking.domain.model.card.CardModelDomain
import com.alenniboris.fastbanking.domain.model.credit.CreditModelDomain
import com.alenniboris.fastbanking.domain.model.exception.CommonExceptionModelDomain
import com.alenniboris.fastbanking.domain.model.transaction.TransactionModelDomain
import com.alenniboris.fastbanking.domain.model.user.UserModelDomain

interface IBankProductsRepository {

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

    suspend fun getAllUserAppliancesForCards(
        user: UserModelDomain
    ): CustomResultModelDomain<List<CardApplianceModelDomain>, CommonExceptionModelDomain>

    suspend fun getAllUserAppliancesForCredits(
        user: UserModelDomain
    ): CustomResultModelDomain<List<CreditApplianceModelDomain>, CommonExceptionModelDomain>

    suspend fun getAllUserAppliancesForDeposits(
        user: UserModelDomain
    ): CustomResultModelDomain<List<DepositApplianceModelDomain>, CommonExceptionModelDomain>

    suspend fun getCardApplianceById(
        id: String
    ): CustomResultModelDomain<CardApplianceModelDomain?, CommonExceptionModelDomain>

    suspend fun getCreditApplianceById(
        id: String
    ): CustomResultModelDomain<CreditApplianceModelDomain?, CommonExceptionModelDomain>

    suspend fun getDepositApplianceById(
        id: String
    ): CustomResultModelDomain<DepositApplianceModelDomain?, CommonExceptionModelDomain>

    suspend fun sendApplianceForCard(
        appliance: CardApplianceModelDomain
    ): CustomResultModelDomain<Unit, CommonExceptionModelDomain>

    suspend fun sendApplianceForCredit(
        appliance: CreditApplianceModelDomain
    ): CustomResultModelDomain<Unit, CommonExceptionModelDomain>

    suspend fun sendApplianceForDeposit(
        appliance: DepositApplianceModelDomain
    ): CustomResultModelDomain<Unit, CommonExceptionModelDomain>

    suspend fun getAllUserTransactions(
        user: UserModelDomain
    ): CustomResultModelDomain<List<TransactionModelDomain>, CommonExceptionModelDomain>

    suspend fun getAllTransactionsForCreditById(
        creditId: String
    ): CustomResultModelDomain<List<TransactionModelDomain>, CommonExceptionModelDomain>
}