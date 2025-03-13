package com.alenniboris.fastbanking.domain.usecase.implementation

import com.alenniboris.fastbanking.domain.repository.IUserRepository
import com.alenniboris.fastbanking.domain.usecase.logic.ISendVerificationCodeForRegistrationUseCase

class SendVerificationCodeForRegistrationUseCaseImpl(
    private val userRepository: IUserRepository
) : ISendVerificationCodeForRegistrationUseCase {

    override fun invoke() {
        userRepository.sendVerificationCode()
    }
}