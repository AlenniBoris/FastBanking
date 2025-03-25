package com.alenniboris.fastbanking.domain.usecase.implementation.map

import com.alenniboris.fastbanking.domain.model.CustomResultModelDomain
import com.alenniboris.fastbanking.domain.model.exception.MapsExceptionModelDomain
import com.alenniboris.fastbanking.domain.model.map.MapsElementModelDomain
import com.alenniboris.fastbanking.domain.repository.IMapsRepository
import com.alenniboris.fastbanking.domain.usecase.logic.map.IGetBankLocationsUseCase
import com.alenniboris.fastbanking.domain.utils.IAppDispatchers
import kotlinx.coroutines.withContext

class GetBankLocationsUseCaseImpl(
    private val mapsRepository: IMapsRepository,
    private val dispatchers: IAppDispatchers
) : IGetBankLocationsUseCase {

    override suspend fun invoke()
            : CustomResultModelDomain<List<MapsElementModelDomain>, MapsExceptionModelDomain> =
        withContext(dispatchers.IO) {
            return@withContext mapsRepository.getLocations()
        }
}