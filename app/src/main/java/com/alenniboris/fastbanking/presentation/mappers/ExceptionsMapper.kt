package com.alenniboris.fastbanking.presentation.mappers

import com.alenniboris.fastbanking.domain.model.AuthenticationExceptionModelDomain

fun AuthenticationExceptionModelDomain.toUiMessageString() =
    when(this){
        AuthenticationExceptionModelDomain.ErrorGettingData -> "Error getting data from server"
        AuthenticationExceptionModelDomain.NoSuchUserException -> "There is no such user, register"
        AuthenticationExceptionModelDomain.NotEmailTypeException -> "Email is not of necessary type"
        AuthenticationExceptionModelDomain.Other -> "Unknown exception"
        AuthenticationExceptionModelDomain.PasswordsCheckException -> "Error to check passwords"
        AuthenticationExceptionModelDomain.UserAlreadyExistsException -> "User already  exists, login"
        AuthenticationExceptionModelDomain.WeakPasswordException -> "Password is too weak"
        AuthenticationExceptionModelDomain.WebException -> "Error with internet connection"
        AuthenticationExceptionModelDomain.WrongEnteringFieldException -> "User exists, but check your entered fields"
        AuthenticationExceptionModelDomain.OnlineBankingNotAllowed -> "Online banking is allowed only from 18"
    }