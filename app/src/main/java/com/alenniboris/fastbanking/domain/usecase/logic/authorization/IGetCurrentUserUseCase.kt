package com.alenniboris.fastbanking.domain.usecase.logic.authorization

import com.alenniboris.fastbanking.domain.model.user.UserModelDomain
import kotlinx.coroutines.flow.StateFlow

interface IGetCurrentUserUseCase {

    val userFlow: StateFlow<UserModelDomain?>

}