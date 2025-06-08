package com.alenniboris.fastbanking.presentation.screens.credit_details

import com.alenniboris.fastbanking.presentation.model.bank_product.CreditModelUi

data class CreditDetailsScreenState(
    val credit: CreditModelUi? = null,
    val isLoading: Boolean = false,
    val allActions: List<CreditDetailsScreenActions> = CreditDetailsScreenActions.entries.toList(),
    val isCreditNameSettingsVisible: Boolean = false,
    val creditNewName: String = ""
)
