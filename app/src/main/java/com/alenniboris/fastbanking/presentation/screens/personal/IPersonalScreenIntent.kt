package com.alenniboris.fastbanking.presentation.screens.personal

sealed interface IPersonalScreenIntent {

    data object NavigateBack : IPersonalScreenIntent

    data object SignOut : IPersonalScreenIntent

    data class UpdateCurrentlyViewedCategory(val newValue: PersonalScreenCategories) :
        IPersonalScreenIntent

    data class ProceedAccordingToProfileAction(val action: ProfileActions) :
        IPersonalScreenIntent

    data class ProceedAccordingToSettingsAction(val action: SettingsActions) :
        IPersonalScreenIntent
}