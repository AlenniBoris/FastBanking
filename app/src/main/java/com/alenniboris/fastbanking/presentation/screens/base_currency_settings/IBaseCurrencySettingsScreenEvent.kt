package com.alenniboris.fastbanking.presentation.screens.base_currency_settings

import com.alenniboris.fastbanking.presentation.uikit.utils.AppBaseCurrency

sealed interface IBaseCurrencySettingsScreenEvent {

    data object NavigateBack : IBaseCurrencySettingsScreenEvent

    data class UpdateAppBaseCurrency(val newValue: AppBaseCurrency) :
        IBaseCurrencySettingsScreenEvent
}