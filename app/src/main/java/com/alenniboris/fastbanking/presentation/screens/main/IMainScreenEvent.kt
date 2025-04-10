package com.alenniboris.fastbanking.presentation.screens.main

sealed interface IMainScreenEvent {

    data class ShowToastMessage(val messageId: Int) : IMainScreenEvent

    data object OpenHelpScreen : IMainScreenEvent

    data object OpenPersonalDetailsScreen : IMainScreenEvent

    data class OpenRecommendedNewsDetails(val newsId: String) : IMainScreenEvent

    data object OpenCreditCardAppliance: IMainScreenEvent

    data object OpenCreditAppliance: IMainScreenEvent

    data object OpenDepositAppliance: IMainScreenEvent
}