package com.alenniboris.fastbanking.presentation.screens.application_information

sealed interface IApplicationInformationScreenEvent {

    data class ShowToastMessage(val messageId: Int) : IApplicationInformationScreenEvent

    data object NavigateBack : IApplicationInformationScreenEvent
}