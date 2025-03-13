package com.alenniboris.fastbanking.presentation.screens.register.registration_options

interface IRegistrationOptionsScreenEvent {

    data object NavigateToPreviousScreen: IRegistrationOptionsScreenEvent

    data object NavigateToRegistrationAsAppClient: IRegistrationOptionsScreenEvent
}