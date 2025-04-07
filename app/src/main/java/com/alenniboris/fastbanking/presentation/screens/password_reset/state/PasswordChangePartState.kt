package com.alenniboris.fastbanking.presentation.screens.password_reset.state

data class PasswordChangePartState(
    val enteredPassword: String = "",
    val passwordCheck: String = "",
    val isPasswordVisible: Boolean = false,
    val isPasswordCheckVisible: Boolean = false
)
