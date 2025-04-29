package com.alenniboris.fastbanking.domain.usecase.implementation.authorization

import com.alenniboris.fastbanking.domain.model.user.UserModelDomain
import com.alenniboris.fastbanking.domain.repository.IAuthenticationRepository
import com.alenniboris.fastbanking.domain.usecase.logic.authorization.IGetCurrentUserUseCase
import com.alenniboris.fastbanking.domain.utils.IAppDispatchers
import kotlinx.coroutines.flow.StateFlow

class GetCurrentUserUseCaseImpl(
    private val authRepository: IAuthenticationRepository,
    private val dispatchers: IAppDispatchers
) : IGetCurrentUserUseCase {

    override val userFlow: StateFlow<UserModelDomain?> = authRepository.user

}