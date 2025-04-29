package com.alenniboris.fastbanking.domain.usecase.implementation.authorization

import com.alenniboris.fastbanking.domain.repository.IAuthenticationRepository
import com.alenniboris.fastbanking.domain.usecase.logic.authorization.ISignOutUseCase

class SignOutUseCaseImpl(
    private val authRepository: IAuthenticationRepository
) : ISignOutUseCase {

    override fun invoke() {
        authRepository.signOut()
    }

}