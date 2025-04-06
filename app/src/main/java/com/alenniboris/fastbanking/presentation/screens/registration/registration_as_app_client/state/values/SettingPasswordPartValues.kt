package com.alenniboris.fastbanking.presentation.screens.registration.registration_as_app_client.state.values

data class SettingPasswordPartState(
    val password: String = "",
    val isPasswordVisible: Boolean = false,
    val passwordCheck: String = "",
    val isPasswordCheckVisible: Boolean = false
)