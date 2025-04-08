package com.alenniboris.fastbanking.domain.usecase.implementation.cards

import com.alenniboris.fastbanking.domain.model.CustomResultModelDomain
import com.alenniboris.fastbanking.domain.model.card.CardModelDomain
import com.alenniboris.fastbanking.domain.model.exception.CommonExceptionModelDomain
import com.alenniboris.fastbanking.domain.repository.IUserRepository
import com.alenniboris.fastbanking.domain.usecase.logic.cards.IGetAllUserCardsUseCase
import com.alenniboris.fastbanking.domain.usecase.logic.user.IGetCurrentUserUseCase
import com.alenniboris.fastbanking.domain.utils.IAppDispatchers
import kotlinx.coroutines.withContext

class GetAllUserCardsUseCaseImpl(
    private val userRepository: IUserRepository,
    private val getCurrentUserUseCase: IGetCurrentUserUseCase,
    private val dispatchers: IAppDispatchers
) : IGetAllUserCardsUseCase {

    override suspend fun invoke():
            CustomResultModelDomain<List<CardModelDomain>, CommonExceptionModelDomain> =
        withContext(dispatchers.IO) {
            return@withContext getCurrentUserUseCase.userFlow.value?.let { user ->
                userRepository.getAllUserCards(
                    user = user
                )
            } ?: CustomResultModelDomain.Error(
                CommonExceptionModelDomain.Other
            )
        }
}