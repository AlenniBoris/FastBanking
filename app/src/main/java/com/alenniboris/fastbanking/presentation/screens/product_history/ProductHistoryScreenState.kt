package com.alenniboris.fastbanking.presentation.screens.product_history

import com.alenniboris.fastbanking.presentation.model.bank_product.TransactionModelUi

data class ProductHistoryScreenState(
    val transactions: List<TransactionModelUi> = emptyList(),
    val selected: TransactionModelUi? = null,
    val isLoading: Boolean = false
)
