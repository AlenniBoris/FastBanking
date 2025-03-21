package com.alenniboris.fastbanking.domain.usecase.implementation

import com.alenniboris.fastbanking.domain.model.user.UserModelDomain
import com.alenniboris.fastbanking.domain.repository.IUserRepository
import com.alenniboris.fastbanking.domain.usecase.logic.IGetCurrentUserUseCase
import com.alenniboris.fastbanking.domain.utils.IAppDispatchers
import kotlinx.coroutines.flow.StateFlow

class GetCurrentUserUseCaseImpl(
    private val userRepository: IUserRepository,
    private val dispatchers: IAppDispatchers
) : IGetCurrentUserUseCase {

    override val userFlow: StateFlow<UserModelDomain?> = userRepository.user

}