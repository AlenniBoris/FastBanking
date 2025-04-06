package com.alenniboris.fastbanking.domain.usecase.logic.user

import com.alenniboris.fastbanking.domain.model.CustomResultModelDomain
import com.alenniboris.fastbanking.domain.model.exception.AuthenticationExceptionModelDomain

interface IChangePasswordUseCase {

    suspend fun invoke(
        userId: String,
        newPassword: String
    ): CustomResultModelDomain<Unit, AuthenticationExceptionModelDomain>
}