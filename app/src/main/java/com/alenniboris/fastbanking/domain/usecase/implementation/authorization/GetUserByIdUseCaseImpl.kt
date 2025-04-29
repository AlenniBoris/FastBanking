package com.alenniboris.fastbanking.domain.usecase.implementation.authorization

import com.alenniboris.fastbanking.domain.model.CustomResultModelDomain
import com.alenniboris.fastbanking.domain.model.exception.AuthenticationExceptionModelDomain
import com.alenniboris.fastbanking.domain.model.user.UserModelDomain
import com.alenniboris.fastbanking.domain.repository.IAuthenticationRepository
import com.alenniboris.fastbanking.domain.usecase.logic.authorization.IGetUserByIdUseCase
import com.alenniboris.fastbanking.domain.utils.IAppDispatchers
import kotlinx.coroutines.withContext

class GetUserByIdUseCaseImpl(
    private val authRepository: IAuthenticationRepository,
    private val dispatchers: IAppDispatchers
) : IGetUserByIdUseCase {

    override suspend fun invoke(
        id: String
    ): CustomResultModelDomain<UserModelDomain, AuthenticationExceptionModelDomain> =
        withContext(dispatchers.IO) {
            return@withContext authRepository.getUserById(id)
        }
}