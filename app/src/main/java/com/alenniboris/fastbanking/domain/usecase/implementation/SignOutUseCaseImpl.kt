package com.alenniboris.fastbanking.domain.usecase.implementation

import com.alenniboris.fastbanking.domain.repository.IUserRepository
import com.alenniboris.fastbanking.domain.usecase.logic.ISignOutUseCase

class SignOutUseCaseImpl(
    private val userRepository: IUserRepository
) : ISignOutUseCase {

    override fun invoke() {
        userRepository.signOut()
    }

}