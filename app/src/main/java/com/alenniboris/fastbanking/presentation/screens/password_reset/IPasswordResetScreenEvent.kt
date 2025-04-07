package com.alenniboris.fastbanking.presentation.screens.password_reset

interface IPasswordResetScreenEvent {

    data class ShowToastMessage(val messageId: Int) : IPasswordResetScreenEvent

    data object NavigateBack : IPasswordResetScreenEvent

    data object NavigateToLoginPage : IPasswordResetScreenEvent
}