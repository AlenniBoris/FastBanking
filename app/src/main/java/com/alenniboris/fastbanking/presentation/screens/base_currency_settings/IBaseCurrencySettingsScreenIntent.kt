package com.alenniboris.fastbanking.presentation.screens.base_currency_settings

import com.alenniboris.fastbanking.presentation.uikit.utils.AppBaseCurrency

sealed interface IBaseCurrencySettingsScreenIntent {

    data class UpdateAppBaseCurrency(val newValue: AppBaseCurrency) :
        IBaseCurrencySettingsScreenIntent

    data object NavigateBack : IBaseCurrencySettingsScreenIntent
}