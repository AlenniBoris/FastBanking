package com.alenniboris.fastbanking.data.source.remote.api.exchange_rate_api

object ExchangeRateApiValues {

    const val BASE_URL = "https://v6.exchangerate-api.com/v6/"

    const val REQUEST_CODES = "/codes"

    const val REQUEST_PAIR = "/pair/{from}/{to}"

    const val REQUEST_LATEST_COURSE = "/latest/{currency}"

    const val PARAMETER_FROM = "from"

    const val PARAMETER_TO = "to"

    const val PARAMETER_CURRENCY = "currency"
}