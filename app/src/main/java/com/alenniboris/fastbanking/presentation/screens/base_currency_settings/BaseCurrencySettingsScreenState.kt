package com.alenniboris.fastbanking.presentation.screens.base_currency_settings

import com.alenniboris.fastbanking.presentation.uikit.utils.AppBaseCurrency

data class BaseCurrencySettingsScreenState(
    val allBaseCurrencies: List<AppBaseCurrency> = AppBaseCurrency.entries.toList(),
    val currentBaseCurrency: AppBaseCurrency = AppBaseCurrency.DOLLAR
)
