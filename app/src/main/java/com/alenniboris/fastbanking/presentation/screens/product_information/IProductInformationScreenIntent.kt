package com.alenniboris.fastbanking.presentation.screens.product_information

interface IProductInformationScreenIntent {

    data object NavigateBack : IProductInformationScreenIntent

    data class CopyTextToClipboard(val text: String) : IProductInformationScreenIntent
}