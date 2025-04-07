package com.alenniboris.fastbanking.domain.usecase.implementation.user

import com.alenniboris.fastbanking.domain.repository.IUserRepository
import com.alenniboris.fastbanking.domain.usecase.logic.user.ISendVerificationCodeUseCase

class SendVerificationCodeUseCaseImpl(
    private val userRepository: IUserRepository
) : ISendVerificationCodeUseCase {

    override fun invoke() {
        userRepository.sendVerificationCode()
    }
}