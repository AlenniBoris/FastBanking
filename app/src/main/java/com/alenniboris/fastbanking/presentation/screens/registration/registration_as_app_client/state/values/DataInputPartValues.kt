package com.alenniboris.fastbanking.presentation.screens.registration.registration_as_app_client.state.values

import com.alenniboris.fastbanking.R

data class DataInputPartState(
    val registrationDocumentType: RegistrationDocumentType = RegistrationDocumentType.Passport,
    val possibleRegistrationDocuments: List<RegistrationDocumentType> =
        RegistrationDocumentType.entries.toList(),
    val identificationNumber: String = ""
)

enum class RegistrationDocumentType {
    Passport
}

fun RegistrationDocumentType.toDocumentTypeString(): Int = when (this) {
    RegistrationDocumentType.Passport -> R.string.document_type_password_text
}

fun RegistrationDocumentType.toDocumentHintString(): Int = when (this) {
    RegistrationDocumentType.Passport -> R.string.document_type_password_hint
}

fun RegistrationDocumentType.toDocumentHintDescriptionString(): Int = when (this) {
    RegistrationDocumentType.Passport -> R.string.document_type_password_hint_description
}