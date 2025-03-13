package com.alenniboris.fastbanking.presentation.screens.register.registration_as_app_client

sealed interface IRegistrationAsAppClientScreenEvent {

    data class ShowToastMessage(val messageId: Int) : IRegistrationAsAppClientScreenEvent

    data object NavigateToPreviousScreen : IRegistrationAsAppClientScreenEvent
}