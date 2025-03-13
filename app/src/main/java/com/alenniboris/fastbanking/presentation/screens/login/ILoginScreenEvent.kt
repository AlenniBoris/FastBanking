package com.alenniboris.fastbanking.presentation.screens.login

sealed interface ILoginScreenEvent {

    data class ShowToastMessage(val messageId: Int) : ILoginScreenEvent

    data object NavigateToRegistrationOptionsScreen : ILoginScreenEvent

}