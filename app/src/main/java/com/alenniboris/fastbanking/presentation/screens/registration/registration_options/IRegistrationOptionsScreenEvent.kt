package com.alenniboris.fastbanking.presentation.screens.registration.registration_options

interface IRegistrationOptionsScreenEvent {

    data object NavigateToPreviousScreen: IRegistrationOptionsScreenEvent

    data object NavigateToRegistrationAsAppClient: IRegistrationOptionsScreenEvent
}