package com.alenniboris.fastbanking.domain.usecase.implementation.appliance

import com.alenniboris.fastbanking.domain.model.CustomResultModelDomain
import com.alenniboris.fastbanking.domain.model.appliances.CreditApplianceModelDomain
import com.alenniboris.fastbanking.domain.model.exception.CommonExceptionModelDomain
import com.alenniboris.fastbanking.domain.repository.IBankProductsRepository
import com.alenniboris.fastbanking.domain.usecase.logic.appliance.IGetCreditApplianceByIdUseCase
import com.alenniboris.fastbanking.domain.utils.IAppDispatchers
import kotlinx.coroutines.withContext

class GetCreditApplianceByIdUseCaseImpl(
    private val bankRepository: IBankProductsRepository,
    private val dispatchers: IAppDispatchers
) : IGetCreditApplianceByIdUseCase {

    override suspend fun invoke(
        id: String
    ): CustomResultModelDomain<CreditApplianceModelDomain?, CommonExceptionModelDomain> =
        withContext(dispatchers.IO) {
            return@withContext bankRepository.getCreditApplianceById(id = id)
        }
}