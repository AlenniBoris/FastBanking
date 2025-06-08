package com.alenniboris.fastbanking.presentation.screens.main

import com.alenniboris.fastbanking.presentation.model.bank_product.IBankProductModelUi
import com.alenniboris.fastbanking.presentation.model.bank_product.TransactionModelUi
import com.alenniboris.fastbanking.presentation.uikit.values.BankProduct

sealed interface IMainScreenIntent {

    data object LoadUserProducts : IMainScreenIntent

    data object UpdateRecommendedNewsVisibility : IMainScreenIntent

    data object OpenHelpScreen : IMainScreenIntent

    data object OpenPersonalDetailsScreen : IMainScreenIntent

    data class OpenRecommendedNewsDetails(val newsId: String) : IMainScreenIntent

    data object UpdateActionsWithProductsSheetVisibility : IMainScreenIntent

    data class UpdateCurrentlyViewedProductsType(val productType: BankProduct) : IMainScreenIntent

    data object UpdateUserBankProductsSheetVisibility : IMainScreenIntent

    data class ProceedProductAction(val action: ActionsWithProducts) : IMainScreenIntent

    data class UpdateCurrentViewedUserProduct(val product: IBankProductModelUi) : IMainScreenIntent

    data class UpdateSelectedTransaction(val transaction: TransactionModelUi?) : IMainScreenIntent

    data class OpenProductDetailsScreen(val product: IBankProductModelUi) : IMainScreenIntent
}