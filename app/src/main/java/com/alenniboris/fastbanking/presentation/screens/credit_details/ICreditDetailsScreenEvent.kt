package com.alenniboris.fastbanking.presentation.screens.credit_details

import com.alenniboris.fastbanking.presentation.model.bank_product.CreditModelUi

sealed interface ICreditDetailsScreenEvent {

    data class NavigateToProductHistoryScreen(val credit: CreditModelUi) : ICreditDetailsScreenEvent

    data class NavigateToProductInfoScreen(val credit: CreditModelUi) : ICreditDetailsScreenEvent

    data class ShowToastMessage(val messageId: Int) : ICreditDetailsScreenEvent

    data object NavigateBack : ICreditDetailsScreenEvent
}