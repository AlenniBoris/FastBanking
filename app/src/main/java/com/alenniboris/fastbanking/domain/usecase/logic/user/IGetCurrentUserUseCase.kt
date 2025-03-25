package com.alenniboris.fastbanking.domain.usecase.logic.user

import com.alenniboris.fastbanking.domain.model.user.UserModelDomain
import kotlinx.coroutines.flow.StateFlow

interface IGetCurrentUserUseCase {

    val userFlow: StateFlow<UserModelDomain?>

}