package com.alenniboris.fastbanking.domain.model.exception

sealed class CommonInfoExceptionModelDomain : Throwable() {

    data object WebException : CommonInfoExceptionModelDomain()

    data object ErrorGettingData : CommonInfoExceptionModelDomain()

    data object Other : CommonInfoExceptionModelDomain()
}