package com.alenniboris.fastbanking.presentation.screens.transactions_history

import com.alenniboris.fastbanking.presentation.model.bank_product.TransactionModelUi

sealed interface ITransactionsHistoryScreenIntent {

    data object ReloadData : ITransactionsHistoryScreenIntent

    data class UpdateSelectedTransaction(val transaction: TransactionModelUi?) :
        ITransactionsHistoryScreenIntent
}