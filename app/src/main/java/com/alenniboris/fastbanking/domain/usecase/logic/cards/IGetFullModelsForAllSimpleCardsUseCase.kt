package com.alenniboris.fastbanking.domain.usecase.logic.cards

import com.alenniboris.fastbanking.domain.model.CustomResultModelDomain
import com.alenniboris.fastbanking.domain.model.card.CardModelDomain
import com.alenniboris.fastbanking.domain.model.card.SimpleCardModelDomain
import com.alenniboris.fastbanking.domain.model.exception.CommonExceptionModelDomain

interface IGetFullModelsForAllSimpleCardsUseCase {

    suspend fun invoke(
        simpleModels: List<SimpleCardModelDomain>
    ): CustomResultModelDomain<List<CardModelDomain>, CommonExceptionModelDomain>
}