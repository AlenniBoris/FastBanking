package com.alenniboris.fastbanking.presentation.screens.map

sealed interface IAtmMapScreenEvent {

    data class ShowToastMessage(val messageId: Int) : IAtmMapScreenEvent

    data object NavigateBack : IAtmMapScreenEvent
}