package com.alenniboris.fastbanking.domain.usecase.implementation.appliance

import com.alenniboris.fastbanking.domain.model.CustomResultModelDomain
import com.alenniboris.fastbanking.domain.model.appliances.CardApplianceModelDomain
import com.alenniboris.fastbanking.domain.model.exception.CommonExceptionModelDomain
import com.alenniboris.fastbanking.domain.repository.IUserRepository
import com.alenniboris.fastbanking.domain.usecase.logic.appliance.IGetCardApplianceByIdUseCase
import com.alenniboris.fastbanking.domain.utils.IAppDispatchers
import kotlinx.coroutines.withContext

class GetCardApplianceByIdUseCaseImpl(
    private val userRepository: IUserRepository,
    private val dispatchers: IAppDispatchers
) : IGetCardApplianceByIdUseCase {

    override suspend fun invoke(
        id: String
    ): CustomResultModelDomain<CardApplianceModelDomain?, CommonExceptionModelDomain> =
        withContext(dispatchers.IO) {
            return@withContext userRepository.getCardApplianceById(id = id)
        }
}