package com.alenniboris.fastbanking.domain.model.user

data class UserModelDomain(
    val id: String,
    val password: String,
    val name: String,
    val surname: String,
    val email: String,
    val age: Int,
    val gender: UserGender,
    val country: String,
    val accountId: String,
    val hasOnlineBanking: Boolean,
    val phoneNumber: String,
    val job: String
)
