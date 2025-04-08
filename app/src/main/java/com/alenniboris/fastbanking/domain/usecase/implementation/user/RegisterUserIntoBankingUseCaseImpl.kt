package com.alenniboris.fastbanking.domain.usecase.implementation.user

import com.alenniboris.fastbanking.domain.model.exception.AuthenticationExceptionModelDomain
import com.alenniboris.fastbanking.domain.model.CustomResultModelDomain
import com.alenniboris.fastbanking.domain.repository.IAuthenticationRepository
import com.alenniboris.fastbanking.domain.usecase.logic.user.IRegisterUserIntoBankingUseCase
import com.alenniboris.fastbanking.domain.utils.IAppDispatchers
import kotlinx.coroutines.withContext

class RegisterUserIntoBankingUseCaseImpl(
    private val authRepository: IAuthenticationRepository,
    private val dispatchers: IAppDispatchers
) : IRegisterUserIntoBankingUseCase {

    override suspend fun invoke(
        login: String,
        password: String
    ): CustomResultModelDomain<Unit, AuthenticationExceptionModelDomain> =
        withContext(dispatchers.IO) {
            when (
                val res = authRepository.registerUserIntoBanking(
                    login = login,
                    password = password
                )
            ) {
                is CustomResultModelDomain.Success -> authRepository.loginUserIntoBanking(
                    login = login,
                    password = password
                )

                is CustomResultModelDomain.Error -> CustomResultModelDomain.Error(res.exception)
            }
        }

}