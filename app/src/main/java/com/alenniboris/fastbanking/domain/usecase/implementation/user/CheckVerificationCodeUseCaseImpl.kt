package com.alenniboris.fastbanking.domain.usecase.implementation.user

import com.alenniboris.fastbanking.domain.model.exception.AuthenticationExceptionModelDomain
import com.alenniboris.fastbanking.domain.model.CustomResultModelDomain
import com.alenniboris.fastbanking.domain.repository.IAuthenticationRepository
import com.alenniboris.fastbanking.domain.usecase.logic.user.ICheckVerificationCodeUseCase

class CheckVerificationCodeUseCaseImpl(
    private val authRepository: IAuthenticationRepository
) : ICheckVerificationCodeUseCase {

    override fun invoke(
        code: String
    ): CustomResultModelDomain<Unit, AuthenticationExceptionModelDomain> {
        return authRepository.checkVerificationCodeForRegistration(code = code)
    }
}