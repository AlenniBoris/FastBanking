package com.alenniboris.fastbanking.domain.model.exception

sealed class CurrencyExceptionModelDomain {

    data object Other : CurrencyExceptionModelDomain()
}