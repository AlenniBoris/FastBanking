package com.alenniboris.fastbanking.data.mappers

import com.alenniboris.fastbanking.domain.model.AuthenticationExceptionModelDomain
import com.google.firebase.FirebaseException
import com.google.firebase.database.DatabaseException
import java.net.ConnectException
import java.net.UnknownHostException

fun Throwable.toAuthenticationException(): AuthenticationExceptionModelDomain =
    when (this) {
        is AuthenticationExceptionModelDomain -> this
        is UnknownHostException, is ConnectException -> AuthenticationExceptionModelDomain.WebException
        else -> AuthenticationExceptionModelDomain.Other
    }