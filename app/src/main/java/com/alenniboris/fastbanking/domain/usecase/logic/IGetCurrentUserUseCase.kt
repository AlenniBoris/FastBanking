package com.alenniboris.fastbanking.domain.usecase.logic

import com.alenniboris.fastbanking.domain.model.UserModelDomain
import kotlinx.coroutines.flow.StateFlow

interface IGetCurrentUserUseCase {

    val userFlow: StateFlow<UserModelDomain?>

}