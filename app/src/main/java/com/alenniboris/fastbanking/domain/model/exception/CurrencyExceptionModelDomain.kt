package com.alenniboris.fastbanking.domain.model.exception

sealed class CurrencyExceptionModelDomain {

    data object WebException : CurrencyExceptionModelDomain()

    data object ServerError : CurrencyExceptionModelDomain()

    data object Other : CurrencyExceptionModelDomain()
}