package com.alenniboris.fastbanking.domain.usecase.implementation.user

import com.alenniboris.fastbanking.domain.model.exception.AuthenticationExceptionModelDomain
import com.alenniboris.fastbanking.domain.model.CustomResultModelDomain
import com.alenniboris.fastbanking.domain.repository.IUserRepository
import com.alenniboris.fastbanking.domain.usecase.logic.user.ICheckVerificationCodeUseCase

class CheckVerificationCodeUseCaseImpl(
    private val userRepository: IUserRepository
) : ICheckVerificationCodeUseCase {

    override fun invoke(
        code: String
    ): CustomResultModelDomain<Unit, AuthenticationExceptionModelDomain> {
        return userRepository.checkVerificationCodeForRegistration(code = code)
    }
}