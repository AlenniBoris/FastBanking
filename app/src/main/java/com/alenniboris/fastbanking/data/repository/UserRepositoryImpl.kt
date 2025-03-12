package com.alenniboris.fastbanking.data.repository

import com.alenniboris.fastbanking.domain.model.AuthenticationExceptionModelDomain
import com.alenniboris.fastbanking.domain.model.CustomResultModelDomain
import com.alenniboris.fastbanking.domain.model.UserModelDomain
import com.alenniboris.fastbanking.domain.repository.IUserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class UserRepositoryImpl : IUserRepository {

    private val userFlow = MutableStateFlow<UserModelDomain?>(null)
    override val user: StateFlow<UserModelDomain?> = userFlow.asStateFlow()

    override suspend fun loginUserIntoBanking(
        email: String,
        password: String
    ): CustomResultModelDomain<Unit, AuthenticationExceptionModelDomain> {
        return CustomResultModelDomain.Success(Unit)
    }

    override suspend fun registerUserIntoBanking(
        email: String,
        password: String,
        passwordCheck: String
    ): CustomResultModelDomain<Unit, AuthenticationExceptionModelDomain> {
        return CustomResultModelDomain.Success(Unit)
    }
}