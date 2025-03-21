package com.alenniboris.fastbanking.domain.model.exception

sealed class MapsExceptionModelDomain() : Throwable() {

    data object ErrorGettingData : MapsExceptionModelDomain()
}
