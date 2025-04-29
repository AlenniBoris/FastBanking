package com.alenniboris.fastbanking.domain.usecase.implementation.authorization

import com.alenniboris.fastbanking.domain.repository.IAuthenticationRepository
import com.alenniboris.fastbanking.domain.usecase.logic.authorization.ISendVerificationCodeUseCase

class SendVerificationCodeUseCaseImpl(
    private val authRepository: IAuthenticationRepository
) : ISendVerificationCodeUseCase {

    override fun invoke() {
        authRepository.sendVerificationCode()
    }
}