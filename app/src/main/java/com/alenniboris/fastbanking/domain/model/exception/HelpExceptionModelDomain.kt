package com.alenniboris.fastbanking.domain.model.exception

sealed class HelpExceptionModelDomain : Throwable() {

    data object ExceptionException : HelpExceptionModelDomain()

    data object Other : HelpExceptionModelDomain()
}