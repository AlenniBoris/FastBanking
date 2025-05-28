package com.alenniboris.fastbanking.presentation.screens.product_information

interface IProductInformationScreenEvent {

    data object NavigateBack : IProductInformationScreenEvent

    data class CopyTextToClipboard(val text: String) : IProductInformationScreenEvent
}