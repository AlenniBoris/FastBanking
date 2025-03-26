package com.alenniboris.fastbanking.data.mappers

import com.alenniboris.fastbanking.domain.model.exception.AuthenticationExceptionModelDomain
import com.alenniboris.fastbanking.domain.model.exception.CurrencyExceptionModelDomain
import com.alenniboris.fastbanking.domain.model.exception.MapsExceptionModelDomain
import java.net.ConnectException
import java.net.UnknownHostException

fun Throwable.toAuthenticationException(): AuthenticationExceptionModelDomain =
    when (this) {
        is AuthenticationExceptionModelDomain -> this
        is UnknownHostException, is ConnectException -> AuthenticationExceptionModelDomain.WebException
        else -> AuthenticationExceptionModelDomain.Other
    }

fun Throwable.toMapsException(): MapsExceptionModelDomain =
    when (this) {
        is MapsExceptionModelDomain -> this
        is UnknownHostException, is ConnectException -> MapsExceptionModelDomain.WebException
        else -> MapsExceptionModelDomain.Other
    }

fun Throwable.toCurrencyException(): CurrencyExceptionModelDomain =
    when (this) {
        is CurrencyExceptionModelDomain -> this
        is UnknownHostException, is ConnectException -> CurrencyExceptionModelDomain.WebException
        else -> CurrencyExceptionModelDomain.Other
    }