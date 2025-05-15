package com.alenniboris.fastbanking.presentation.screens.transactions_history

sealed interface ITransactionsHistoryScreenEvent {

    data class ShowToastMessage(val messageId: Int) : ITransactionsHistoryScreenEvent
}