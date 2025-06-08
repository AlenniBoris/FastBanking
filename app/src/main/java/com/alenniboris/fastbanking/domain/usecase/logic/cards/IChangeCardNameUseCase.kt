package com.alenniboris.fastbanking.domain.usecase.logic.cards

import com.alenniboris.fastbanking.domain.model.CustomResultModelDomain
import com.alenniboris.fastbanking.domain.model.card.CardModelDomain
import com.alenniboris.fastbanking.domain.model.exception.CommonExceptionModelDomain

interface IChangeCardNameUseCase {

    suspend fun invoke(
        card: CardModelDomain,
        newName: String
    ): CustomResultModelDomain<Unit, CommonExceptionModelDomain>
}