package com.alenniboris.fastbanking.domain.model.exception

sealed class HelpExceptionModelDomain : Throwable() {

    data object PermissionException : HelpExceptionModelDomain()

    data object Other : HelpExceptionModelDomain()
}