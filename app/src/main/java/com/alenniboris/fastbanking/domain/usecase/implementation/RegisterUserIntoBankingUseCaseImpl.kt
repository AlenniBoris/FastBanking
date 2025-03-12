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
        email: String,
        password: String,
        passwordCheck: String
    ): CustomResultModelDomain<Unit, AuthenticationExceptionModelDomain> =
        withContext(dispatchers.IO) {
            return@withContext userRepository.registerUserIntoBanking(
                email = email,
                password = password,
                passwordCheck = passwordCheck
            )
        }

}