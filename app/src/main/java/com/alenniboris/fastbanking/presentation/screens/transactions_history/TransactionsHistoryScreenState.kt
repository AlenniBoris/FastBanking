package com.alenniboris.fastbanking.presentation.screens.transactions_history

import com.alenniboris.fastbanking.presentation.model.bank_product.TransactionModelUi

data class TransactionsHistoryScreenState(
    val transactions: List<TransactionModelUi> = emptyList(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val selectedTransaction: TransactionModelUi? = null
)
