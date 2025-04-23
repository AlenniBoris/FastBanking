package com.alenniboris.fastbanking.presentation.screens.language_settings

import com.alenniboris.fastbanking.presentation.uikit.utils.AppLanguage

data class LanguageSettingsScreenState(
    val allLanguages: List<AppLanguage> = AppLanguage.entries.toList(),
    val currentLanguage: AppLanguage = AppLanguage.English
)
