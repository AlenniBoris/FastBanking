package com.alenniboris.fastbanking.presentation.screens.account_details

import com.alenniboris.fastbanking.presentation.model.bank_product.AccountModelUi
import com.alenniboris.fastbanking.presentation.model.bank_product.CardModelUi

data class AccountDetailsScreenState(
    val account: AccountModelUi? = null,
    val isAccountLoading: Boolean = false,
    val attachedCards: List<CardModelUi> = emptyList(),
    val isAttachedCardsLoading: Boolean = false,
    val isAttachedCardsSheetVisible: Boolean = false,
    val allActions: List<AccountDetailsScreenActions> = AccountDetailsScreenActions.entries.toList()
)
