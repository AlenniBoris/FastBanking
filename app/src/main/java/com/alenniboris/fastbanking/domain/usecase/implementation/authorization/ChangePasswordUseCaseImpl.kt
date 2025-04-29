package com.alenniboris.fastbanking.domain.usecase.implementation.authorization

import com.alenniboris.fastbanking.domain.model.CustomResultModelDomain
import com.alenniboris.fastbanking.domain.model.exception.AuthenticationExceptionModelDomain
import com.alenniboris.fastbanking.domain.repository.IAuthenticationRepository
import com.alenniboris.fastbanking.domain.usecase.logic.authorization.IChangePasswordUseCase
import com.alenniboris.fastbanking.domain.utils.IAppDispatchers
import kotlinx.coroutines.withContext

class ChangePasswordUseCaseImpl(
    private val authRepository: IAuthenticationRepository,
    private val dispatchers: IAppDispatchers
) : IChangePasswordUseCase {

    override suspend fun invoke(
        userId: String,
        newPassword: String
    ): CustomResultModelDomain<Unit, AuthenticationExceptionModelDomain> =
        withContext(dispatchers.IO) {
            return@withContext authRepository.changeUserPassword(
                userId = userId,
                newPassword = newPassword
            )
        }
}