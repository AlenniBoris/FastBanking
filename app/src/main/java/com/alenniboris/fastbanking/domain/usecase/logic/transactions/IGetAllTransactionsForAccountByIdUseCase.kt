package com.alenniboris.fastbanking.domain.usecase.logic.transactions

import com.alenniboris.fastbanking.domain.model.CustomResultModelDomain
import com.alenniboris.fastbanking.domain.model.exception.CommonExceptionModelDomain
import com.alenniboris.fastbanking.domain.model.transaction.TransactionModelDomain

interface IGetAllTransactionsForAccountByIdUseCase {

    suspend fun invoke(
        accountId: String
    ): CustomResultModelDomain<List<TransactionModelDomain>, CommonExceptionModelDomain>
}