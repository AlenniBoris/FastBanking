package com.alenniboris.fastbanking.presentation.uikit.utils

import android.content.Context
import com.alenniboris.fastbanking.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

enum class AppBaseCurrency {
    DOLLAR,
    EURO,
    RUBLE,
    YANE
}

fun AppBaseCurrency.toUiString(): Int =
    when (this) {
        AppBaseCurrency.DOLLAR -> R.string.dollar_currency_text
        AppBaseCurrency.EURO -> R.string.euro_currency_text
        AppBaseCurrency.RUBLE -> R.string.ruble_currency_text
        AppBaseCurrency.YANE -> R.string.yane_currency_text
    }

sealed class BaseCurrencyMode(
    val baseCurrency: AppBaseCurrency,
    val currencyCode: String,
    val currencyFullName: String,
    val baseAmount: Double
) {

    class Dollar() : BaseCurrencyMode(
        baseCurrency = AppBaseCurrency.DOLLAR,
        currencyCode = "usd",
        currencyFullName = "united states dollar",
        baseAmount = 1.0
    )

    class Euro : BaseCurrencyMode(
        baseCurrency = AppBaseCurrency.EURO,
        currencyCode = "eur",
        currencyFullName = "Euro",
        baseAmount = 1.0
    )

    class Ruble : BaseCurrencyMode(
        baseCurrency = AppBaseCurrency.RUBLE,
        currencyCode = "rub",
        currencyFullName = "Russian ruble",
        baseAmount = 100.0
    )

    class Yane : BaseCurrencyMode(
        baseCurrency = AppBaseCurrency.YANE,
        currencyCode = "cny",
        currencyFullName = "Chinese yane",
        baseAmount = 100.0
    )
}

private val _baseCurrencyMode = MutableStateFlow<BaseCurrencyMode>(BaseCurrencyMode.Dollar())
val baseCurrencyMode = _baseCurrencyMode.asStateFlow()

private val PREFERENCIES_NAME = "LAST_BASE_CURRENCY"
private val BASE_CURRENCY_STRING_NAME = "CURRENCY"

private fun AppBaseCurrency.update() {
    _baseCurrencyMode.update {
        when (this) {
            AppBaseCurrency.DOLLAR -> BaseCurrencyMode.Dollar()
            AppBaseCurrency.EURO -> BaseCurrencyMode.Euro()
            AppBaseCurrency.RUBLE -> BaseCurrencyMode.Ruble()
            AppBaseCurrency.YANE -> BaseCurrencyMode.Yane()
        }
    }
}

fun Context.setBaseCurrency(
    selectedCurrency: AppBaseCurrency,
) {
    applicationContext.getSharedPreferences(PREFERENCIES_NAME, Context.MODE_PRIVATE)
        .edit()
        .putString(BASE_CURRENCY_STRING_NAME, selectedCurrency.name)
        .apply()

    selectedCurrency.update()
}

fun Context.getLastBaseCurrencyAndApply(): AppBaseCurrency {
    val currency = applicationContext.getSharedPreferences(PREFERENCIES_NAME, Context.MODE_PRIVATE)
        .getString(BASE_CURRENCY_STRING_NAME, null)?.let { lastCurrency ->
            AppBaseCurrency.entries.find { it.name == lastCurrency }
        } ?: AppBaseCurrency.DOLLAR
    currency.update()
    return _baseCurrencyMode.value.baseCurrency
}