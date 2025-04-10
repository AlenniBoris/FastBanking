package com.alenniboris.fastbanking.domain.usecase.implementation.transactions

import com.alenniboris.fastbanking.domain.model.CustomResultModelDomain
import com.alenniboris.fastbanking.domain.model.card.CardModelDomain
import com.alenniboris.fastbanking.domain.model.exception.CommonExceptionModelDomain
import com.alenniboris.fastbanking.domain.model.transaction.TransactionModelDomain
import com.alenniboris.fastbanking.domain.repository.IUserRepository
import com.alenniboris.fastbanking.domain.usecase.logic.transactions.IGetAllUserTransactionsByCardUseCase
import com.alenniboris.fastbanking.domain.usecase.logic.user.IGetCurrentUserUseCase
import com.alenniboris.fastbanking.domain.utils.IAppDispatchers
import kotlinx.coroutines.withContext

class GetAllUserTransactionsByCardUseCaseImpl(
    private val userRepository: IUserRepository,
    private val getCurrentUserUseCase: IGetCurrentUserUseCase,
    private val dispatchers: IAppDispatchers
) : IGetAllUserTransactionsByCardUseCase {

    override suspend fun invoke(
        card: CardModelDomain
    ): CustomResultModelDomain<List<TransactionModelDomain>, CommonExceptionModelDomain> =
        withContext(dispatchers.IO) {
            return@withContext getCurrentUserUseCase.userFlow.value?.let { user ->
                userRepository.getAllUserTransactionsByCard(
                    user = user,
                    cardId = card.id
                )
            } ?: CustomResultModelDomain.Error(
                CommonExceptionModelDomain.Other
            )
        }
}