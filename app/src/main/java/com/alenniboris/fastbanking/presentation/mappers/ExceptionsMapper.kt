package com.alenniboris.fastbanking.presentation.mappers

import com.alenniboris.fastbanking.R
import com.alenniboris.fastbanking.domain.model.AuthenticationExceptionModelDomain

fun AuthenticationExceptionModelDomain.toUiMessageString(): Int =
    when (this) {
        AuthenticationExceptionModelDomain.ErrorGettingData -> R.string.exception_getting_data
        AuthenticationExceptionModelDomain.NoSuchUserException -> R.string.exception_no_such_user
        AuthenticationExceptionModelDomain.Other -> R.string.exception_unknown
        AuthenticationExceptionModelDomain.PasswordsCheckException -> R.string.exception_password_check
        AuthenticationExceptionModelDomain.UserAlreadyExistsException -> R.string.exception_user_existed
        AuthenticationExceptionModelDomain.WeakPasswordException -> R.string.exception_weak_password
        AuthenticationExceptionModelDomain.WebException -> R.string.exception_internet_error
        AuthenticationExceptionModelDomain.WrongEnteringFieldException -> R.string.exception_entering_fields_wrong
        AuthenticationExceptionModelDomain.OnlineBankingNotAllowed -> R.string.exception_user_underage_for_banking
    }