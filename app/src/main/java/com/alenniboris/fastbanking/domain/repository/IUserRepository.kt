package com.alenniboris.fastbanking.domain.repository

import com.alenniboris.fastbanking.domain.model.AuthenticationExceptionModelDomain
import com.alenniboris.fastbanking.domain.model.CustomResultModelDomain
import com.alenniboris.fastbanking.domain.model.UserModelDomain
import kotlinx.coroutines.flow.StateFlow

interface IUserRepository {
    val user: StateFlow<UserModelDomain?>

    suspend fun loginUserIntoBanking(
        email: String,
        password: String
    ): CustomResultModelDomain<Unit, AuthenticationExceptionModelDomain>

    suspend fun registerUserIntoBanking(
        email: String,
        password: String,
        passwordCheck: String
    ): CustomResultModelDomain<Unit, AuthenticationExceptionModelDomain>
}