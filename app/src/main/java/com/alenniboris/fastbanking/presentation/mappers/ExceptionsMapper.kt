package com.alenniboris.fastbanking.presentation.mappers

import com.alenniboris.fastbanking.R
import com.alenniboris.fastbanking.domain.model.exception.AuthenticationExceptionModelDomain
import com.alenniboris.fastbanking.domain.model.exception.CommonExceptionModelDomain
import com.alenniboris.fastbanking.domain.model.exception.CommonInfoExceptionModelDomain
import com.alenniboris.fastbanking.domain.model.exception.CurrencyExceptionModelDomain
import com.alenniboris.fastbanking.domain.model.exception.HelpExceptionModelDomain
import com.alenniboris.fastbanking.domain.model.exception.MapsExceptionModelDomain

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

fun MapsExceptionModelDomain.toUiMessageString(): Int = when (this) {
    MapsExceptionModelDomain.ErrorGettingData -> R.string.exception_getting_data
    MapsExceptionModelDomain.Other -> R.string.exception_unknown
    MapsExceptionModelDomain.WebException -> R.string.exception_internet_error
}

fun CurrencyExceptionModelDomain.toUiMessageString(): Int = when (this) {
    CurrencyExceptionModelDomain.Other -> R.string.exception_unknown
    CurrencyExceptionModelDomain.ServerError -> R.string.exception_getting_data
    CurrencyExceptionModelDomain.SomeCurrencyNotChosen -> R.string.exception_some_currency_not_chosen
    CurrencyExceptionModelDomain.WebException -> R.string.exception_internet_error
    CurrencyExceptionModelDomain.ErrorMapping -> R.string.exception_error_mapping
}

fun HelpExceptionModelDomain.toUiMessageString(): Int = when (this) {
    HelpExceptionModelDomain.Other -> R.string.exception_unknown
    HelpExceptionModelDomain.PermissionException -> R.string.exception_permission_not_granted
}

fun CommonInfoExceptionModelDomain.toUiMessageString(): Int = when (this) {
    CommonInfoExceptionModelDomain.ErrorGettingData -> R.string.exception_getting_data
    CommonInfoExceptionModelDomain.Other -> R.string.exception_unknown
    CommonInfoExceptionModelDomain.ServerError -> R.string.exception_getting_data
    CommonInfoExceptionModelDomain.WebException -> R.string.exception_internet_error
    CommonInfoExceptionModelDomain.NoSuchDataException -> R.string.exception_no_such_data
}

fun CommonExceptionModelDomain.toUiMessageString(): Int = when (this) {
    CommonExceptionModelDomain.ErrorGettingData -> R.string.exception_getting_data
    CommonExceptionModelDomain.Other -> R.string.exception_unknown
    CommonExceptionModelDomain.ServerError -> R.string.exception_getting_data
    CommonExceptionModelDomain.WebException -> R.string.exception_internet_error
}