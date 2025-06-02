package com.alenniboris.fastbanking.presentation.screens.account_details

import com.alenniboris.fastbanking.presentation.model.bank_product.AccountModelUi

sealed interface IAccountDetailsScreenEvent {

    data class NavigateToProductHistoryScreen(val account: AccountModelUi) : IAccountDetailsScreenEvent

    data class NavigateToProductInfoScreen(val account: AccountModelUi) : IAccountDetailsScreenEvent

    data class ShowToastMessage(val messageId: Int) : IAccountDetailsScreenEvent

    data object NavigateBack : IAccountDetailsScreenEvent

    data class OpenCardDetailsScreen(val cardId: String) : IAccountDetailsScreenEvent
}