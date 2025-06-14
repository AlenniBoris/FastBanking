package com.alenniboris.fastbanking.presentation.screens.main

import com.alenniboris.fastbanking.presentation.uikit.values.BankProduct

sealed interface IMainScreenEvent {

    data class ShowToastMessage(val messageId: Int) : IMainScreenEvent

    data object OpenHelpScreen : IMainScreenEvent

    data object OpenPersonalDetailsScreen : IMainScreenEvent

    data class OpenRecommendedNewsDetails(val newsId: String) : IMainScreenEvent

    data class OpenProductApplianceChoosingScreen(val productType: BankProduct) : IMainScreenEvent

    data class OpenAccountDetailsScreen(val accountId: String) : IMainScreenEvent

    data class OpenCardDetailsScreen(val cardId: String) : IMainScreenEvent

    data class OpenCreditDetailsScreen(val creditId: String) : IMainScreenEvent
}