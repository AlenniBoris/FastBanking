package com.alenniboris.fastbanking.domain.usecase.implementation.appliance

import com.alenniboris.fastbanking.domain.model.CustomResultModelDomain
import com.alenniboris.fastbanking.domain.model.appliances.CreditApplianceModelDomain
import com.alenniboris.fastbanking.domain.model.exception.CommonExceptionModelDomain
import com.alenniboris.fastbanking.domain.repository.IUserRepository
import com.alenniboris.fastbanking.domain.usecase.logic.appliance.IGetCreditApplianceByIdUseCase
import com.alenniboris.fastbanking.domain.utils.IAppDispatchers
import kotlinx.coroutines.withContext

class GetCreditApplianceByIdUseCaseImpl(
    private val userRepository: IUserRepository,
    private val dispatchers: IAppDispatchers
) : IGetCreditApplianceByIdUseCase {

    override suspend fun invoke(
        id: String
    ): CustomResultModelDomain<CreditApplianceModelDomain, CommonExceptionModelDomain> =
        withContext(dispatchers.IO) {
            return@withContext userRepository.getCreditApplianceById(id = id)
        }
}