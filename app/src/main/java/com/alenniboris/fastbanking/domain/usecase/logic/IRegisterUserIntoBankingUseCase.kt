package com.alenniboris.fastbanking.domain.usecase.logic

import com.alenniboris.fastbanking.domain.model.AuthenticationExceptionModelDomain
import com.alenniboris.fastbanking.domain.model.CustomResultModelDomain

interface IRegisterUserIntoBankingUseCase {

    suspend fun invoke(
        email: String,
        password: String,
        passwordCheck: String
    ): CustomResultModelDomain<Unit, AuthenticationExceptionModelDomain>

}