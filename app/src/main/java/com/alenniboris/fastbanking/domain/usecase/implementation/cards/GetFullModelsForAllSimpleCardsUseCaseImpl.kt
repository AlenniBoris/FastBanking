package com.alenniboris.fastbanking.domain.usecase.implementation.cards

import com.alenniboris.fastbanking.domain.model.CustomResultModelDomain
import com.alenniboris.fastbanking.domain.model.card.CardModelDomain
import com.alenniboris.fastbanking.domain.model.card.SimpleCardModelDomain
import com.alenniboris.fastbanking.domain.model.exception.CommonExceptionModelDomain
import com.alenniboris.fastbanking.domain.usecase.logic.cards.IGetCardByIdUseCase
import com.alenniboris.fastbanking.domain.usecase.logic.cards.IGetFullModelsForAllSimpleCardsUseCase
import com.alenniboris.fastbanking.domain.utils.IAppDispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.withContext

class GetFullModelsForAllSimpleCardsUseCaseImpl(
    private val getCardByIdUseCase: IGetCardByIdUseCase,
    private val dispatchers: IAppDispatchers
) : IGetFullModelsForAllSimpleCardsUseCase {

    override suspend fun invoke(
        simpleModels: List<SimpleCardModelDomain>
    ): CustomResultModelDomain<List<CardModelDomain>, CommonExceptionModelDomain> =
        withContext(dispatchers.IO) {
            return@withContext CustomResultModelDomain.Success(
                simpleModels
                    .map { it.id }
                    .map {
                        async {
                            getCardByIdUseCase.invoke(id = it)
                        }
                    }
                    .awaitAll()
                    .mapNotNull { result ->
                        if (result is CustomResultModelDomain.Success) {
                            result.result
                        } else {
                            return@withContext CustomResultModelDomain.Error(result.exception!!)
                        }
                    }
            )
        }
}