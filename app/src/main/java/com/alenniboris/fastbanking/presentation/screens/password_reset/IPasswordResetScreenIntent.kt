package com.alenniboris.fastbanking.presentation.screens.password_reset

import com.alenniboris.fastbanking.presentation.uikit.values.RegistrationDocumentType

interface IPasswordResetScreenIntent {

    data object DoForwardAction : IPasswordResetScreenIntent

    data object DoBackwardAction : IPasswordResetScreenIntent

    data class UpdateDocumentValue(
        val documentType: RegistrationDocumentType,
        val newValue: String
    ) : IPasswordResetScreenIntent

    data class UpdateCodeValue(val newValue: String) : IPasswordResetScreenIntent

    data class UpdateEnteredPassword(val newValue: String) : IPasswordResetScreenIntent

    data class UpdatePasswordCheck(val newValue: String) : IPasswordResetScreenIntent

    data object UpdateOptionsBottomSheetVisibility : IPasswordResetScreenIntent

    data class UpdateRegistrationDocumentType(val newValue: RegistrationDocumentType) :
        IPasswordResetScreenIntent

    data object UpdatePasswordVisibility : IPasswordResetScreenIntent

    data object UpdatePasswordCheckVisibility : IPasswordResetScreenIntent
}