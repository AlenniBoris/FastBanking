package com.alenniboris.fastbanking.presentation.screens.product_history

import com.alenniboris.fastbanking.presentation.model.bank_product.TransactionModelUi

sealed interface IProductHistoryScreenIntent {

    data class UpdateSelectedTransaction(val selected: TransactionModelUi?) :
        IProductHistoryScreenIntent

    data object NavigateBack : IProductHistoryScreenIntent
}