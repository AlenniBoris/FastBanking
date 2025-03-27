package com.alenniboris.fastbanking.presentation.screens.currency

import com.alenniboris.fastbanking.domain.model.currency.CurrencyModelDomain

sealed interface ICurrencyScreenIntent {

    data object NavigateBack : ICurrencyScreenIntent

    data class UpdateCurrentScreenMode(val newValue: CurrencyScreenMode) : ICurrencyScreenIntent

    data class UpdateFromValue(val newValue: String) : ICurrencyScreenIntent

    data object ChangeCurrenciesPlaces : ICurrencyScreenIntent

    data object UpdateFromCurrencyMenuVisibility : ICurrencyScreenIntent

    data object UpdateToCurrencyMenuVisibility : ICurrencyScreenIntent

    data class UpdateFromCurrency(val newValue: CurrencyModelDomain) : ICurrencyScreenIntent

    data class UpdateToCurrency(val newValue: CurrencyModelDomain) : ICurrencyScreenIntent
}