package com.alenniboris.fastbanking.presentation.mappers

import com.alenniboris.fastbanking.R
import com.alenniboris.fastbanking.domain.model.AuthenticationExceptionModelDomain

fun AuthenticationExceptionModelDomain.toUiMessageString(): Int =
    when (this) {
        AuthenticationExceptionModelDomain.ErrorGettingData -> R.string.exception_getting_data
        AuthenticationExceptionModelDomain.NoSuchUserException -> R.string.exception_no_such_user
        AuthenticationExceptionModelDomain.Other -> R.string.exception_unknown
        AuthenticationExceptionModelDomain.UserAlreadyExistsException -> R.string.exception_user_existed
        AuthenticationExceptionModelDomain.WebException -> R.string.exception_internet_error
        AuthenticationExceptionModelDomain.WrongEnteringFieldException -> R.string.exception_entering_fields_wrong
        AuthenticationExceptionModelDomain.OnlineBankingNotAllowed -> R.string.exception_user_underage_for_banking
        AuthenticationExceptionModelDomain.NotAllInputDataWasEntered -> R.string.exception_input_data_not_fully_entered
        AuthenticationExceptionModelDomain.IdentificationNumberIsInWrongFormat -> R.string.exception_identification_number_format
        AuthenticationExceptionModelDomain.PhoneNumberIsInWrongFormat -> R.string.exception_phone_number_format
        AuthenticationExceptionModelDomain.OneOfPasswordsIsEmpty -> R.string.exception_one_password_field_is_empty
        AuthenticationExceptionModelDomain.PasswordsAreNotTheSame -> R.string.exception_password_check
        AuthenticationExceptionModelDomain.WeakPasswordException -> R.string.exception_weak_password
        AuthenticationExceptionModelDomain.VerificationWithCodeFailed -> R.string.exception_verification_code
    }