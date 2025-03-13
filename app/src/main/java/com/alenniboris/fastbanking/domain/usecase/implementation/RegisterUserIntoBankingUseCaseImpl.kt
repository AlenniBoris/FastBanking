package com.alenniboris.fastbanking.domain.usecase.implementation

import com.alenniboris.fastbanking.domain.model.AuthenticationExceptionModelDomain
import com.alenniboris.fastbanking.domain.model.CustomResultModelDomain
import com.alenniboris.fastbanking.domain.repository.IUserRepository
import com.alenniboris.fastbanking.domain.usecase.logic.IRegisterUserIntoBankingUseCase
import com.alenniboris.fastbanking.domain.utils.IAppDispatchers
import kotlinx.coroutines.withContext

class RegisterUserIntoBankingUseCaseImpl(
    private val userRepository: IUserRepository,
    private val dispatchers: IAppDispatchers
) : IRegisterUserIntoBankingUseCase {

    override suspend fun invoke(
        login: String,
        password: String,
        passwordCheck: String
    ): CustomResultModelDomain<Unit, AuthenticationExceptionModelDomain> =
        withContext(dispatchers.IO) {
            when (
                val res = userRepository.registerUserIntoBanking(
                    login = login,
                    password = password,
                    passwordCheck = passwordCheck
                )
            ) {
                is CustomResultModelDomain.Success -> userRepository.loginUserIntoBanking(
                    login = login,
                    password = password
                )

                is CustomResultModelDomain.Error -> CustomResultModelDomain.Error(res.exception)
            }
        }

}