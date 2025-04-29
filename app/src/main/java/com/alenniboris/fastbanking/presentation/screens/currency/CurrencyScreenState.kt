package com.alenniboris.fastbanking.presentation.screens.currency

import com.alenniboris.fastbanking.domain.model.currency.CurrencyModelDomain
import com.alenniboris.fastbanking.presentation.model.currency.CurrencyRatesModelUi

data class CurrencyScreenState(
    val screenMode: CurrencyScreenMode = CurrencyScreenMode.Exchange,
    val listOfScreenModes: List<CurrencyScreenMode> = CurrencyScreenMode.entries.toList(),
    val fromCurrency: CurrencyModelDomain? = null,
    val toCurrency: CurrencyModelDomain? = null,
    val exchangeRate: Double = CurrencyScreenConstants.BaseExchangeRate,
    val isExchangeRateLoading: Boolean = false,
    val fromValueText: String = "1",
    val toValueText: String = "",
    val listOfCurrencies: List<CurrencyModelDomain> = emptyList(),
    val isFromCurrencyMenuVisible: Boolean = false,
    val isToCurrencyMenuVisible: Boolean = false,
    val isAllExchangeRatesForBaseCurrencyLoading: Boolean = false,
    val baseExchangeCurrency: CurrencyModelDomain = CurrencyScreenConstants.BaseExchangeCurrency,
    val currencyExchangeRatesForBaseCurrency: CurrencyRatesModelUi = CurrencyRatesModelUi()
)