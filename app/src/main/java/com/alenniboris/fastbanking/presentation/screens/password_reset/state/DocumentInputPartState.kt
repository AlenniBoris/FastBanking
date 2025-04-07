package com.alenniboris.fastbanking.presentation.screens.password_reset.state

import com.alenniboris.fastbanking.presentation.uikit.values.RegistrationDocumentType

data class DocumentInputPartState(
    val selectedDocument: RegistrationDocumentType = RegistrationDocumentType.Passport,
    val allPossibleDocumentTypes: List<RegistrationDocumentType> =
        RegistrationDocumentType.entries.toList(),
    val identificationNumber: String = ""
)