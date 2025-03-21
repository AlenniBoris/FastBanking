package com.alenniboris.fastbanking.domain.model.exception

sealed class AuthenticationExceptionModelDomain : Throwable() {
    data object NoSuchUserException : AuthenticationExceptionModelDomain()

    data object OnlineBankingNotAllowed : AuthenticationExceptionModelDomain()

    data object UserAlreadyExistsException : AuthenticationExceptionModelDomain()

    data object WrongEnteringFieldException : AuthenticationExceptionModelDomain()

    data object WebException : AuthenticationExceptionModelDomain()

    data object ErrorGettingData : AuthenticationExceptionModelDomain()

    data object Other : AuthenticationExceptionModelDomain()

    data object NotAllInputDataWasEntered : AuthenticationExceptionModelDomain()

    data object IdentificationNumberIsInWrongFormat : AuthenticationExceptionModelDomain()

    data object PhoneNumberIsInWrongFormat : AuthenticationExceptionModelDomain()

    data object OneOfPasswordsIsEmpty : AuthenticationExceptionModelDomain()

    data object PasswordsAreNotTheSame : AuthenticationExceptionModelDomain()

    data object WeakPasswordException : AuthenticationExceptionModelDomain()

    data object VerificationWithCodeFailed : AuthenticationExceptionModelDomain()
}