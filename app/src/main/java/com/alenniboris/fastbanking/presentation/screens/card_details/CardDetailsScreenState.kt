package com.alenniboris.fastbanking.presentation.screens.card_details

import com.alenniboris.fastbanking.presentation.model.bank_product.CardModelUi

data class CardDetailsScreenState(
    val card: CardModelUi? = null,
    val isLoading: Boolean = false,
    val allActions: List<CardDetailsScreenActions> = CardDetailsScreenActions.entries.toList(),
    val isCardNameSettingsVisible: Boolean = false,
    val cardNewName: String = ""
)
