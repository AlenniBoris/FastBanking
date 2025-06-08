package com.alenniboris.fastbanking.domain.usecase.implementation.cards

import com.alenniboris.fastbanking.domain.model.CustomResultModelDomain
import com.alenniboris.fastbanking.domain.model.card.CardModelDomain
import com.alenniboris.fastbanking.domain.model.exception.CommonExceptionModelDomain
import com.alenniboris.fastbanking.domain.repository.IBankProductsRepository
import com.alenniboris.fastbanking.domain.usecase.logic.cards.IChangeCardNameUseCase
import com.alenniboris.fastbanking.domain.utils.IAppDispatchers
import kotlinx.coroutines.withContext

class ChangeCardNameUseCaseImpl(
    private val bankProductsRepository: IBankProductsRepository,
    private val dispatchers: IAppDispatchers
) : IChangeCardNameUseCase {

    override suspend fun invoke(
        card: CardModelDomain,
        newName: String
    ): CustomResultModelDomain<Unit, CommonExceptionModelDomain> = withContext(dispatchers.IO) {
        return@withContext bankProductsRepository.updateCardValue(card = card.copy(name = newName))
    }
}