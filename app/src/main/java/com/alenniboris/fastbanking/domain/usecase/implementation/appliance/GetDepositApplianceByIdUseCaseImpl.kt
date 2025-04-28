package com.alenniboris.fastbanking.domain.usecase.implementation.appliance

import com.alenniboris.fastbanking.domain.model.CustomResultModelDomain
import com.alenniboris.fastbanking.domain.model.appliances.DepositApplianceModelDomain
import com.alenniboris.fastbanking.domain.model.exception.CommonExceptionModelDomain
import com.alenniboris.fastbanking.domain.repository.IUserRepository
import com.alenniboris.fastbanking.domain.usecase.logic.appliance.IGetDepositApplianceByIdUseCase
import com.alenniboris.fastbanking.domain.utils.IAppDispatchers
import kotlinx.coroutines.withContext

class GetDepositApplianceByIdUseCaseImpl(
    private val userRepository: IUserRepository,
    private val dispatchers: IAppDispatchers
) : IGetDepositApplianceByIdUseCase {

    override suspend fun invoke(
        id: String
    ): CustomResultModelDomain<DepositApplianceModelDomain?, CommonExceptionModelDomain> =
        withContext(dispatchers.IO) {
            return@withContext userRepository.getDepositApplianceById(id = id)
        }
}