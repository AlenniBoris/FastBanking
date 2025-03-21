package com.alenniboris.fastbanking.data.mappers

import com.alenniboris.fastbanking.domain.model.exception.AuthenticationExceptionModelDomain
import java.net.ConnectException
import java.net.UnknownHostException

fun Throwable.toAuthenticationException(): AuthenticationExceptionModelDomain =
    when (this) {
        is AuthenticationExceptionModelDomain -> this
        is UnknownHostException, is ConnectException -> AuthenticationExceptionModelDomain.WebException
        else -> AuthenticationExceptionModelDomain.Other
    }