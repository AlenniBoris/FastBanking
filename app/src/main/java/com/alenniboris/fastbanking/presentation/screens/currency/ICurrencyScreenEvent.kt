package com.alenniboris.fastbanking.presentation.screens.currency

sealed interface ICurrencyScreenEvent {

    data object NavigateBack : ICurrencyScreenEvent

    data class ShowToastMessage(val messageId: Int) : ICurrencyScreenEvent
}