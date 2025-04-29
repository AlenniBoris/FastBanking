package com.alenniboris.fastbanking.domain.usecase.implementation.appliance

import com.alenniboris.fastbanking.domain.model.CustomResultModelDomain
import com.alenniboris.fastbanking.domain.model.appliances.DepositApplianceModelDomain
import com.alenniboris.fastbanking.domain.model.exception.CommonExceptionModelDomain
import com.alenniboris.fastbanking.domain.repository.IBankProductsRepository
import com.alenniboris.fastbanking.domain.usecase.logic.appliance.IGetDepositApplianceByIdUseCase
import com.alenniboris.fastbanking.domain.utils.IAppDispatchers
import kotlinx.coroutines.withContext

class GetDepositApplianceByIdUseCaseImpl(
    private val bankRepository: IBankProductsRepository,
    private val dispatchers: IAppDispatchers
) : IGetDepositApplianceByIdUseCase {

    override suspend fun invoke(
        id: String
    ): CustomResultModelDomain<DepositApplianceModelDomain?, CommonExceptionModelDomain> =
        withContext(dispatchers.IO) {
            return@withContext bankRepository.getDepositApplianceById(id = id)
        }
}