package com.alenniboris.fastbanking.presentation.screens.theme_settings

import com.alenniboris.fastbanking.presentation.uikit.utils.AppTheme

sealed interface IThemeSettingsScreenIntent {

    data class UpdateAppTheme(val newValue: AppTheme) : IThemeSettingsScreenIntent

    data object NavigateBack : IThemeSettingsScreenIntent
}