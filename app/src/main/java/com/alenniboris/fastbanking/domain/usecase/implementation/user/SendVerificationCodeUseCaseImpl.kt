package com.alenniboris.fastbanking.domain.usecase.implementation.user

import com.alenniboris.fastbanking.domain.repository.IAuthenticationRepository
import com.alenniboris.fastbanking.domain.usecase.logic.user.ISendVerificationCodeUseCase

class SendVerificationCodeUseCaseImpl(
    private val authRepository: IAuthenticationRepository
) : ISendVerificationCodeUseCase {

    override fun invoke() {
        authRepository.sendVerificationCode()
    }
}