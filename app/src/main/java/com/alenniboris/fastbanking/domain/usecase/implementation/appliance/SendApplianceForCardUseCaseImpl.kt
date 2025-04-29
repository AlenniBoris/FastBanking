package com.alenniboris.fastbanking.domain.usecase.implementation.appliance

import com.alenniboris.fastbanking.domain.model.CustomResultModelDomain
import com.alenniboris.fastbanking.domain.model.appliances.CardApplianceModelDomain
import com.alenniboris.fastbanking.domain.model.exception.CommonExceptionModelDomain
import com.alenniboris.fastbanking.domain.repository.IBankProductsRepository
import com.alenniboris.fastbanking.domain.usecase.logic.appliance.ISendApplianceForCardUseCase
import com.alenniboris.fastbanking.domain.utils.IAppDispatchers
import kotlinx.coroutines.withContext

class SendApplianceForCardUseCaseImpl(
    private val bankRepository: IBankProductsRepository,
    private val dispatchers: IAppDispatchers
) : ISendApplianceForCardUseCase {

    override suspend fun invoke(
        appliance: CardApplianceModelDomain
    ): CustomResultModelDomain<Unit, CommonExceptionModelDomain> = withContext(dispatchers.IO) {
        return@withContext bankRepository.sendApplianceForCard(appliance = appliance)
    }
}