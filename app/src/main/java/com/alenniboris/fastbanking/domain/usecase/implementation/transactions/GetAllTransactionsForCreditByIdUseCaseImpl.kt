package com.alenniboris.fastbanking.domain.usecase.implementation.transactions

import com.alenniboris.fastbanking.domain.model.CustomResultModelDomain
import com.alenniboris.fastbanking.domain.model.exception.CommonExceptionModelDomain
import com.alenniboris.fastbanking.domain.model.transaction.TransactionModelDomain
import com.alenniboris.fastbanking.domain.repository.IBankProductsRepository
import com.alenniboris.fastbanking.domain.usecase.logic.transactions.IGetAllTransactionsForCreditByIdUseCase
import com.alenniboris.fastbanking.domain.utils.IAppDispatchers
import kotlinx.coroutines.withContext

class GetAllTransactionsForCreditByIdUseCaseImpl(
    private val bankProductsRepository: IBankProductsRepository,
    private val dispatchers: IAppDispatchers
) : IGetAllTransactionsForCreditByIdUseCase {

    override suspend fun invoke(
        creditId: String
    ): CustomResultModelDomain<List<TransactionModelDomain>, CommonExceptionModelDomain> =
        withContext(dispatchers.IO) {
            return@withContext bankProductsRepository.getAllTransactionsForCreditById(creditId = creditId)
        }
}