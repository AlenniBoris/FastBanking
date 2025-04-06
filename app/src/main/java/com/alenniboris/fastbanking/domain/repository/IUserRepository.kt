package com.alenniboris.fastbanking.domain.repository

import com.alenniboris.fastbanking.domain.model.CustomResultModelDomain
import com.alenniboris.fastbanking.domain.model.exception.AuthenticationExceptionModelDomain
import com.alenniboris.fastbanking.domain.model.user.UserModelDomain
import kotlinx.coroutines.flow.StateFlow

interface IUserRepository {
    val user: StateFlow<UserModelDomain?>

    suspend fun loginUserIntoBanking(
        login: String,
        password: String
    ): CustomResultModelDomain<Unit, AuthenticationExceptionModelDomain>

    suspend fun registerUserIntoBanking(
        login: String,
        password: String
    ): CustomResultModelDomain<Unit, AuthenticationExceptionModelDomain>

    fun signOut()

    fun sendVerificationCode()

    fun checkVerificationCodeForRegistration(
        code: String
    ): CustomResultModelDomain<Unit, AuthenticationExceptionModelDomain>

    suspend fun getUserById(
        id: String
    ): CustomResultModelDomain<UserModelDomain, AuthenticationExceptionModelDomain>

    suspend fun changeUserPassword(
        userId: String,
        newPassword: String
    ): CustomResultModelDomain<Unit, AuthenticationExceptionModelDomain>
}