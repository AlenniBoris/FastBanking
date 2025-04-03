package com.alenniboris.fastbanking.domain.usecase.implementation.bank_info

import com.alenniboris.fastbanking.domain.model.CustomResultModelDomain
import com.alenniboris.fastbanking.domain.model.bank_info.ApplicationInfoModelDomain
import com.alenniboris.fastbanking.domain.model.exception.CommonInfoExceptionModelDomain
import com.alenniboris.fastbanking.domain.repository.IBankInfoRepository
import com.alenniboris.fastbanking.domain.usecase.logic.bank_info.IGetApplicationInfoUseCase
import com.alenniboris.fastbanking.domain.utils.IAppDispatchers
import kotlinx.coroutines.withContext

class GetApplicationInfoUseCaseImpl(
    private val bankInfoRepository: IBankInfoRepository,
    private val dispatchers: IAppDispatchers
) : IGetApplicationInfoUseCase {

    override suspend fun invoke():
            CustomResultModelDomain<ApplicationInfoModelDomain, CommonInfoExceptionModelDomain> =
        withContext(dispatchers.IO) {
            return@withContext bankInfoRepository.getApplicationInformation()
        }
}