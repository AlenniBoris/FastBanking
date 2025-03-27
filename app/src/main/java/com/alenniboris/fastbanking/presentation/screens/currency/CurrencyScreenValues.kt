package com.alenniboris.fastbanking.presentation.screens.currency

import com.alenniboris.fastbanking.R
import com.alenniboris.fastbanking.domain.model.currency.CurrencyModelDomain

enum class CurrencyScreenMode {
    Exchange,
    Rate
}

fun CurrencyScreenMode.toUiString(): Int = when (this) {
    CurrencyScreenMode.Exchange -> R.string.exchange_screen_mode_text
    CurrencyScreenMode.Rate -> R.string.currency_rate_screen_mode_text
}


object CurrencyScreenConstants {

    const val BaseExchangeRate: Double = 1.0

    val BaseExchangeCurrency: CurrencyModelDomain = CurrencyModelDomain(
        code = "USD",
        fullName = "United States Dollar"
    )

    const val BaseCurrencyValue: Double = 1.0
}