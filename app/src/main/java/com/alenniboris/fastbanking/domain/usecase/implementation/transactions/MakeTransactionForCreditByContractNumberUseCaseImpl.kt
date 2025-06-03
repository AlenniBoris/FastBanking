package com.alenniboris.fastbanking.domain.usecase.implementation.transactions

import com.alenniboris.fastbanking.domain.model.CustomResultModelDomain
import com.alenniboris.fastbanking.domain.model.card.CardModelDomain
import com.alenniboris.fastbanking.domain.model.exception.CommonExceptionModelDomain
import com.alenniboris.fastbanking.domain.repository.IBankProductsRepository
import com.alenniboris.fastbanking.domain.usecase.logic.transactions.IMakeTransactionForCreditByContractNumberUseCase
import com.alenniboris.fastbanking.domain.utils.IAppDispatchers
import kotlinx.coroutines.withContext

class MakeTransactionForCreditByContractNumberUseCaseImpl(
    private val bankProductsRepository: IBankProductsRepository,
    private val dispatchers: IAppDispatchers
) : IMakeTransactionForCreditByContractNumberUseCase {

    override suspend fun invoke(
        usedCard: CardModelDomain,
        contractNumber: String,
        amount: Double
    ): CustomResultModelDomain<Unit, CommonExceptionModelDomain> = withContext(dispatchers.IO) {
        return@withContext bankProductsRepository.makeTransactionForCreditByContractNumber(
            usedCard = usedCard,
            contractNumber = contractNumber,
            amount = amount
        )
    }
}