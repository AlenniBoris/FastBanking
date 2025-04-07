package com.alenniboris.fastbanking.presentation.screens.login

sealed interface ILoginScreenIntent {

    data object UpdatePasswordVisibility : ILoginScreenIntent

    data class UpdateLogin(val newLogin: String) : ILoginScreenIntent

    data class UpdatePassword(val newPassword: String) : ILoginScreenIntent

    data object LoginIntoBanking : ILoginScreenIntent

    data object StartRegistration : ILoginScreenIntent

    data object OpenResetPasswordPage : ILoginScreenIntent
}