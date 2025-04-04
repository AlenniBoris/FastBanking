package com.alenniboris.fastbanking.presentation.screens.news_details

sealed interface INewsDetailsScreenEvent {

    data class ShowToastMessage(val messageId: Int) : INewsDetailsScreenEvent

    data object NavigateBack : INewsDetailsScreenEvent
}