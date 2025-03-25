package com.alenniboris.fastbanking.domain.usecase.implementation.user

import com.alenniboris.fastbanking.domain.repository.IUserRepository
import com.alenniboris.fastbanking.domain.usecase.logic.user.ISendVerificationCodeForRegistrationUseCase

class SendVerificationCodeForRegistrationUseCaseImpl(
    private val userRepository: IUserRepository
) : ISendVerificationCodeForRegistrationUseCase {

    override fun invoke() {
        userRepository.sendVerificationCode()
    }
}