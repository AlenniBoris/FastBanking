package com.alenniboris.fastbanking.presentation.screens.help

sealed interface IHelpScreenEvent {

    data class ShowToastMessage(val messageId: Int) : IHelpScreenEvent

    data object NavigateBack : IHelpScreenEvent

    data class CopyToClipboard(val text: String) : IHelpScreenEvent
}