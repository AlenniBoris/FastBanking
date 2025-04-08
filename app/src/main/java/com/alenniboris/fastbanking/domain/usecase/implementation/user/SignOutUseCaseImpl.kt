package com.alenniboris.fastbanking.domain.usecase.implementation.user

import com.alenniboris.fastbanking.domain.repository.IAuthenticationRepository
import com.alenniboris.fastbanking.domain.usecase.logic.user.ISignOutUseCase

class SignOutUseCaseImpl(
    private val authRepository: IAuthenticationRepository
) : ISignOutUseCase {

    override fun invoke() {
        authRepository.signOut()
    }

}