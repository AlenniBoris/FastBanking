package com.alenniboris.fastbanking.domain.usecase.implementation.transactions

import com.alenniboris.fastbanking.domain.model.CustomResultModelDomain
import com.alenniboris.fastbanking.domain.model.card.CardModelDomain
import com.alenniboris.fastbanking.domain.model.exception.CommonExceptionModelDomain
import com.alenniboris.fastbanking.domain.repository.IBankProductsRepository
import com.alenniboris.fastbanking.domain.usecase.logic.transactions.IMakeTransactionByEripNumberUseCase
import com.alenniboris.fastbanking.domain.utils.IAppDispatchers
import kotlinx.coroutines.withContext

class MakeTransactionByEripNumberUseCaseImpl(
    private val bankProductsRepository: IBankProductsRepository,
    private val dispatchers: IAppDispatchers
): IMakeTransactionByEripNumberUseCase {

    override suspend fun invoke(
        eripNumber: String,
        usedCard: CardModelDomain,
        amount: Double
    ): CustomResultModelDomain<Unit, CommonExceptionModelDomain> = withContext(dispatchers.IO){
        return@withContext bankProductsRepository.makeTransactionByEripNumber(
            eripNumber = eripNumber,
            usedCard = usedCard,
            amount = amount
        )
    }
}