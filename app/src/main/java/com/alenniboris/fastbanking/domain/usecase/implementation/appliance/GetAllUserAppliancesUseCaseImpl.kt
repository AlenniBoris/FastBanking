package com.alenniboris.fastbanking.domain.usecase.implementation.appliance

import com.alenniboris.fastbanking.domain.model.CustomResultModelDomain
import com.alenniboris.fastbanking.domain.model.appliances.IProductAppliance
import com.alenniboris.fastbanking.domain.model.exception.CommonExceptionModelDomain
import com.alenniboris.fastbanking.domain.repository.IUserRepository
import com.alenniboris.fastbanking.domain.usecase.logic.appliance.IGetAllUserAppliancesUseCase
import com.alenniboris.fastbanking.domain.usecase.logic.user.IGetCurrentUserUseCase
import com.alenniboris.fastbanking.domain.utils.IAppDispatchers
import kotlinx.coroutines.withContext

class GetAllUserAppliancesUseCaseImpl(
    private val getCurrentUserUseCase: IGetCurrentUserUseCase,
    private val userRepository: IUserRepository,
    private val dispatchers: IAppDispatchers
) : IGetAllUserAppliancesUseCase {

    override suspend fun invoke()
            : CustomResultModelDomain<List<IProductAppliance>, CommonExceptionModelDomain> =
        withContext(dispatchers.IO) {
            return@withContext getCurrentUserUseCase.userFlow.value?.let { user ->
                userRepository.getAllUserAppliances(user = user)
            } ?: CustomResultModelDomain.Error(
                CommonExceptionModelDomain.Other
            )
        }
}