package com.alenniboris.fastbanking.presentation.screens.bank_news

sealed interface IBankNewsScreenEvent {

    data object NavigateBack : IBankNewsScreenEvent

    data class OpenNewsDetailsScreen(val newsId: String) : IBankNewsScreenEvent

    data class ShowToastMessage(val messageId: Int) : IBankNewsScreenEvent
}