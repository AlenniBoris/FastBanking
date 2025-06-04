package com.alenniboris.fastbanking.domain.model.exception

sealed class CommonExceptionModelDomain : Throwable() {

    data object WebException : CommonExceptionModelDomain()

    data object ServerError : CommonExceptionModelDomain()

    data object ErrorGettingData : CommonExceptionModelDomain()

    data object NoEnoughMoneyAmount : CommonExceptionModelDomain()

    data object NotOurBankCredit : CommonExceptionModelDomain()

    data object Other : CommonExceptionModelDomain()
}