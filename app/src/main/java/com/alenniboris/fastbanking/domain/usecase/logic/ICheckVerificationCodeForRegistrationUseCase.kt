package com.alenniboris.fastbanking.domain.usecase.logic

import com.alenniboris.fastbanking.domain.model.exception.AuthenticationExceptionModelDomain
import com.alenniboris.fastbanking.domain.model.CustomResultModelDomain

interface ICheckVerificationCodeForRegistrationUseCase {

    fun invoke(
        code: String
    ): CustomResultModelDomain<Unit, AuthenticationExceptionModelDomain>
}