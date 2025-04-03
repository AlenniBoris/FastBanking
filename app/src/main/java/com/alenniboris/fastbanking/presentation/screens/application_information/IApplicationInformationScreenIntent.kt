package com.alenniboris.fastbanking.presentation.screens.application_information

sealed interface IApplicationInformationScreenIntent {

    data object NavigateBack : IApplicationInformationScreenIntent
}