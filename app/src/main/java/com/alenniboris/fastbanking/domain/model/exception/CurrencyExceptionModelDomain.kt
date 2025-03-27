package com.alenniboris.fastbanking.domain.model.exception

sealed class CurrencyExceptionModelDomain {

    data object SomeCurrencyNotChosen : CurrencyExceptionModelDomain()

    data object WebException : CurrencyExceptionModelDomain()

    data object ServerError : CurrencyExceptionModelDomain()

    data object ErrorMapping : CurrencyExceptionModelDomain()

    data object Other : CurrencyExceptionModelDomain()
}