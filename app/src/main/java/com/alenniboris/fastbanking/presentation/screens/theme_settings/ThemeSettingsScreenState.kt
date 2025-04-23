package com.alenniboris.fastbanking.presentation.screens.theme_settings

import com.alenniboris.fastbanking.presentation.uikit.utils.AppTheme

data class ThemeSettingsScreenState(
    val allThemes: List<AppTheme> = AppTheme.entries.toList(),
    val currentTheme: AppTheme = AppTheme.LIGHT
)
