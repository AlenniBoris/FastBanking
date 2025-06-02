package com.alenniboris.fastbanking.domain.usecase.implementation.transactions

import com.alenniboris.fastbanking.domain.model.CustomResultModelDomain
import com.alenniboris.fastbanking.domain.model.exception.CommonExceptionModelDomain
import com.alenniboris.fastbanking.domain.model.transaction.TransactionModelDomain
import com.alenniboris.fastbanking.domain.repository.IBankProductsRepository
import com.alenniboris.fastbanking.domain.usecase.logic.transactions.IGetAllTransactionsForAccountByIdUseCase
import com.alenniboris.fastbanking.domain.utils.IAppDispatchers
import kotlinx.coroutines.withContext

class GetAllTransactionsForAccountByIdUseCaseImpl(
    private val bankProductsRepository: IBankProductsRepository,
    private val dispatchers: IAppDispatchers
) : IGetAllTransactionsForAccountByIdUseCase {

    override suspend fun invoke(
        accountId: String
    ): CustomResultModelDomain<List<TransactionModelDomain>, CommonExceptionModelDomain> =
        withContext(dispatchers.IO) {
            return@withContext bankProductsRepository.getAllTransactionsForAccountById(accountId = accountId)
        }
}