package com.alenniboris.fastbanking.presentation.screens.registration.registration_as_app_client.state.values

import com.alenniboris.fastbanking.presentation.uikit.values.RegistrationDocumentType

data class DataInputPartState(
    val registrationDocumentType: RegistrationDocumentType = RegistrationDocumentType.Passport,
    val possibleRegistrationDocuments: List<RegistrationDocumentType> =
        RegistrationDocumentType.entries.toList(),
    val identificationNumber: String = ""
)