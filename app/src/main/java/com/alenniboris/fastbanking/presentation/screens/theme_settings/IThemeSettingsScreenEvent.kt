package com.alenniboris.fastbanking.presentation.screens.theme_settings

import com.alenniboris.fastbanking.presentation.uikit.utils.AppTheme

sealed interface IThemeSettingsScreenEvent {

    data class UpdateAppTheme(val newValue: AppTheme) : IThemeSettingsScreenEvent

    data object NavigateBack : IThemeSettingsScreenEvent
}