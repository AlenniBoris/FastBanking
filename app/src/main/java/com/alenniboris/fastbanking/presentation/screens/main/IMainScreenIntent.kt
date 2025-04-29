package com.alenniboris.fastbanking.presentation.screens.main

import com.alenniboris.fastbanking.presentation.uikit.values.BankProduct

sealed interface IMainScreenIntent {

    data object UpdateRecommendedNewsVisibility : IMainScreenIntent

    data object OpenHelpScreen : IMainScreenIntent

    data object OpenPersonalDetailsScreen : IMainScreenIntent

    data class OpenRecommendedNewsDetails(val newsId: String) : IMainScreenIntent

    data object UpdateActionsWithProductsSheetVisibility : IMainScreenIntent

    data class UpdateCurrentlyViewedProductsType(val productType: BankProduct) : IMainScreenIntent

    data object UpdateUserBankProductsSheetVisibility : IMainScreenIntent

    data class ProceedProductAction(val action: ActionsWithProducts) : IMainScreenIntent
}