package com.alenniboris.fastbanking.presentation.uikit.utils

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

enum class BaseCurrency {
    DOLLAR,
    EURO,
    RUBLE
}

sealed class BaseCurrencyMode(
    val baseCurrency: BaseCurrency,
    val currencyCode: String,
    val currencyFullName: String,
    val baseAmount: Double
) {

    class Dollar() : BaseCurrencyMode(
        baseCurrency = BaseCurrency.DOLLAR,
        currencyCode = "usd",
        currencyFullName = "united states dollar",
        baseAmount = 1.0
    )

    class Euro : BaseCurrencyMode(
        baseCurrency = BaseCurrency.EURO,
        currencyCode = "eur",
        currencyFullName = "Euro",
        baseAmount = 1.0
    )

    class Ruble : BaseCurrencyMode(
        baseCurrency = BaseCurrency.RUBLE,
        currencyCode = "rub",
        currencyFullName = "Russian ruble",
        baseAmount = 100.0
    )
}

private val _baseCurrencyFlow = MutableStateFlow<BaseCurrencyMode>(BaseCurrencyMode.Dollar())
val baseCurrencyFlow = _baseCurrencyFlow.asStateFlow()