package com.alenniboris.fastbanking.presentation.screens.product_history

sealed interface IProductHistoryScreenEvent {

    data object NavigateBack : IProductHistoryScreenEvent

    data class ShowToastMessage(val messageIg: Int) : IProductHistoryScreenEvent
}