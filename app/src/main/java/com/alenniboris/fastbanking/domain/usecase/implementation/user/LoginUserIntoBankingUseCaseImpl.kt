package com.alenniboris.fastbanking.domain.usecase.implementation.user

import com.alenniboris.fastbanking.domain.model.exception.AuthenticationExceptionModelDomain
import com.alenniboris.fastbanking.domain.model.CustomResultModelDomain
import com.alenniboris.fastbanking.domain.repository.IAuthenticationRepository
import com.alenniboris.fastbanking.domain.usecase.logic.user.ILoginUserIntoBankingUseCase
import com.alenniboris.fastbanking.domain.utils.IAppDispatchers
import kotlinx.coroutines.withContext

class LoginUserIntoBankingUseCaseImpl(
    private val authRepository: IAuthenticationRepository,
    private val dispatchers: IAppDispatchers
) : ILoginUserIntoBankingUseCase {

    override suspend fun invoke(
        login: String,
        password: String
    ): CustomResultModelDomain<Unit, AuthenticationExceptionModelDomain> =
        withContext(dispatchers.IO) {
            return@withContext authRepository.loginUserIntoBanking(
                login = login,
                password = password
            )
        }

}