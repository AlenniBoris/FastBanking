package com.alenniboris.fastbanking.domain.model.user

enum class UserGender {
    Male,
    Female,
    Undefined
}

fun String.toUserGender(): UserGender = when (this) {
    "Male" -> UserGender.Male
    "Female" -> UserGender.Female
    else -> UserGender.Undefined
}