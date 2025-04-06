package com.alenniboris.fastbanking.presentation.screens.registration.registration_options

sealed interface IRegistrationOptionsScreenIntent {

    data object NavigateToPreviousScreen: IRegistrationOptionsScreenIntent

    data object NavigateToRegistrationAsAppClient: IRegistrationOptionsScreenIntent
}