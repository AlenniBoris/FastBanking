package com.alenniboris.fastbanking.domain.usecase.logic

import com.alenniboris.fastbanking.domain.model.AuthenticationExceptionModelDomain
import com.alenniboris.fastbanking.domain.model.CustomResultModelDomain

interface ILoginUserIntoBankingUseCase {

    suspend fun invoke(
        login: String,
        password: String
    ): CustomResultModelDomain<Unit, AuthenticationExceptionModelDomain>

}