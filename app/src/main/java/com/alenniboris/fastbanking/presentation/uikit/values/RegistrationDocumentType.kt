package com.alenniboris.fastbanking.presentation.uikit.values

import com.alenniboris.fastbanking.R

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