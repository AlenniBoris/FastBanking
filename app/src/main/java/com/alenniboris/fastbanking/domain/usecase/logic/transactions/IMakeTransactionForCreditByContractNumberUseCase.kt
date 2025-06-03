package com.alenniboris.fastbanking.domain.usecase.logic.transactions

import com.alenniboris.fastbanking.domain.model.CustomResultModelDomain
import com.alenniboris.fastbanking.domain.model.card.CardModelDomain
import com.alenniboris.fastbanking.domain.model.exception.CommonExceptionModelDomain

interface IMakeTransactionForCreditByContractNumberUseCase {

    suspend fun invoke(
        usedCard: CardModelDomain,
        contractNumber: String,
        amount: Double
    ): CustomResultModelDomain<Unit, CommonExceptionModelDomain>
}