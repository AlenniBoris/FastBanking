package com.alenniboris.fastbanking.domain.usecase.implementation.appliance

import com.alenniboris.fastbanking.domain.model.CustomResultModelDomain
import com.alenniboris.fastbanking.domain.model.appliances.DepositApplianceModelDomain
import com.alenniboris.fastbanking.domain.model.exception.CommonExceptionModelDomain
import com.alenniboris.fastbanking.domain.repository.IBankProductsRepository
import com.alenniboris.fastbanking.domain.usecase.logic.appliance.ISendApplianceForDepositUseCase
import com.alenniboris.fastbanking.domain.utils.IAppDispatchers
import kotlinx.coroutines.withContext

class SendApplianceForDepositUseCaseImpl(
    private val bankRepository: IBankProductsRepository,
    private val dispatchers: IAppDispatchers
) : ISendApplianceForDepositUseCase {

    override suspend fun invoke(
        appliance: DepositApplianceModelDomain
    ): CustomResultModelDomain<Unit, CommonExceptionModelDomain> = withContext(dispatchers.IO) {
        return@withContext bankRepository.sendApplianceForDeposit(appliance = appliance)
    }
}