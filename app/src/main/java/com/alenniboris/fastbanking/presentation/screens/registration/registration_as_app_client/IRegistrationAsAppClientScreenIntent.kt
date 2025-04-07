package com.alenniboris.fastbanking.presentation.screens.registration.registration_as_app_client

import com.alenniboris.fastbanking.presentation.uikit.values.RegistrationDocumentType

sealed interface IRegistrationAsAppClientScreenIntent {

    data object DoForwardAction : IRegistrationAsAppClientScreenIntent

    data object DoBackwardAction : IRegistrationAsAppClientScreenIntent

    data class UpdateRegistrationDocumentType(val type: RegistrationDocumentType) :
        IRegistrationAsAppClientScreenIntent

    data class UpdateIdentificationNumber(val number: String) :
        IRegistrationAsAppClientScreenIntent

    data object UpdateOptionsBottomSheetVisibility : IRegistrationAsAppClientScreenIntent

    data class UpdateDataInputField(
        val documentType: RegistrationDocumentType,
        val newValue: String
    ) : IRegistrationAsAppClientScreenIntent

    data class UpdatePhoneNumber(val newValue: String) : IRegistrationAsAppClientScreenIntent

    data class UpdatePhoneCode(val newValue: String) : IRegistrationAsAppClientScreenIntent

    data class UpdatePassword(val newValue: String) : IRegistrationAsAppClientScreenIntent

    data class UpdatePasswordCheck(val newValue: String) : IRegistrationAsAppClientScreenIntent

    data object UpdatePasswordVisibility : IRegistrationAsAppClientScreenIntent

    data object UpdatePasswordCheckVisibility : IRegistrationAsAppClientScreenIntent
}