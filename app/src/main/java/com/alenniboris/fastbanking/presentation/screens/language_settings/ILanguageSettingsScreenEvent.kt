package com.alenniboris.fastbanking.presentation.screens.language_settings

import com.alenniboris.fastbanking.presentation.uikit.utils.AppLanguage

interface ILanguageSettingsScreenEvent {

    data object NavigateBack : ILanguageSettingsScreenEvent
    data class UpdateAppLanguage(val newValue: AppLanguage) : ILanguageSettingsScreenEvent
}