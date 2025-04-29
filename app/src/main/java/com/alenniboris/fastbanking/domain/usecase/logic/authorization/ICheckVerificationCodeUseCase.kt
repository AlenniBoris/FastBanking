package com.alenniboris.fastbanking.domain.usecase.logic.authorization

import com.alenniboris.fastbanking.domain.model.exception.AuthenticationExceptionModelDomain
import com.alenniboris.fastbanking.domain.model.CustomResultModelDomain

interface ICheckVerificationCodeUseCase {

    fun invoke(
        code: String
    ): CustomResultModelDomain<Unit, AuthenticationExceptionModelDomain>
}