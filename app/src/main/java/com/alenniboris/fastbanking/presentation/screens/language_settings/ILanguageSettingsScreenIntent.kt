package com.alenniboris.fastbanking.presentation.screens.language_settings

import com.alenniboris.fastbanking.presentation.uikit.utils.AppLanguage

sealed interface ILanguageSettingsScreenIntent {

    data class UpdateAppLanguage(val newValue: AppLanguage) : ILanguageSettingsScreenIntent

    data object NavigateBack : ILanguageSettingsScreenIntent
}