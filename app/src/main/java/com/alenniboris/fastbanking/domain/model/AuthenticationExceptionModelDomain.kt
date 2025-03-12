package com.alenniboris.fastbanking.domain.model

sealed class AuthenticationExceptionModelDomain : Throwable() {
    data object NoSuchUserException : AuthenticationExceptionModelDomain()

    data object WrongEnteringFieldException : AuthenticationExceptionModelDomain()

    data object NotEmailTypeException : AuthenticationExceptionModelDomain()

    data object PasswordsCheckException : AuthenticationExceptionModelDomain()

    data object WeakPasswordException : AuthenticationExceptionModelDomain()

    data object WebException : AuthenticationExceptionModelDomain()

    data object Other : AuthenticationExceptionModelDomain()
}