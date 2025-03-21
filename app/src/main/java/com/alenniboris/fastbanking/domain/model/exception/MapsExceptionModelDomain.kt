package com.alenniboris.fastbanking.domain.model.exception

sealed class MapsExceptionModelDomain() : Throwable() {

    data object WebException : MapsExceptionModelDomain()

    data object ErrorGettingData : MapsExceptionModelDomain()

    data object Other: MapsExceptionModelDomain()
}
