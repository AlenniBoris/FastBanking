package com.alenniboris.fastbanking.data.mappers

import com.alenniboris.fastbanking.domain.model.exception.AuthenticationExceptionModelDomain
import com.alenniboris.fastbanking.domain.model.exception.CommonExceptionModelDomain
import com.alenniboris.fastbanking.domain.model.exception.CommonInfoExceptionModelDomain
import com.alenniboris.fastbanking.domain.model.exception.CurrencyExceptionModelDomain
import com.alenniboris.fastbanking.domain.model.exception.HelpExceptionModelDomain
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

fun Throwable.toHelpException(): HelpExceptionModelDomain =
    when (this) {
        is HelpExceptionModelDomain -> this
        is AccessDeniedException, is SecurityException -> HelpExceptionModelDomain.PermissionException
        else -> HelpExceptionModelDomain.Other
    }

fun Throwable.toCommonInfoException(): CommonInfoExceptionModelDomain =
    when (this) {
        is CommonInfoExceptionModelDomain -> this
        is UnknownHostException, is ConnectException -> CommonInfoExceptionModelDomain.WebException
        else -> CommonInfoExceptionModelDomain.Other
    }

fun Throwable.toCommonException(): CommonExceptionModelDomain =
    when (this) {
        is CommonExceptionModelDomain -> this
        is UnknownHostException, is ConnectException -> CommonExceptionModelDomain.WebException
        else -> CommonExceptionModelDomain.Other
    }