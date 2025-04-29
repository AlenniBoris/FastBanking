package com.alenniboris.fastbanking.domain.usecase.logic.authorization

import com.alenniboris.fastbanking.domain.model.CustomResultModelDomain
import com.alenniboris.fastbanking.domain.model.exception.AuthenticationExceptionModelDomain
import com.alenniboris.fastbanking.domain.model.user.UserModelDomain

interface IGetUserByIdUseCase {

    suspend fun invoke(
        id: String
    ): CustomResultModelDomain<UserModelDomain, AuthenticationExceptionModelDomain>
}