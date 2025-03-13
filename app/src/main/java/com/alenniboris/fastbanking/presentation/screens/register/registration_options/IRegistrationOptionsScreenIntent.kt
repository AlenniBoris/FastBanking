package com.alenniboris.fastbanking.presentation.screens.register.registration_options

sealed interface IRegistrationOptionsScreenIntent {

    data object NavigateToPreviousScreen: IRegistrationOptionsScreenIntent

    data object NavigateToRegistrationAsAppClient: IRegistrationOptionsScreenIntent
}