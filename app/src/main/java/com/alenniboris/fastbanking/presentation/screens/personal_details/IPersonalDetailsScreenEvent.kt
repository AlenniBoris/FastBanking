package com.alenniboris.fastbanking.presentation.screens.personal_details

sealed interface IPersonalDetailsScreenEvent {

    data object NavigateBack : IPersonalDetailsScreenEvent
}