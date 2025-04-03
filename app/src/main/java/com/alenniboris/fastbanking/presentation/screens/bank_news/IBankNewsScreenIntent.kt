package com.alenniboris.fastbanking.presentation.screens.bank_news

sealed interface IBankNewsScreenIntent {

    data object NavigateBack : IBankNewsScreenIntent

    data class OpenNewsDetailsPage(val newsId: String) : IBankNewsScreenIntent
}