package com.alenniboris.fastbanking.presentation.screens.payment_process

import com.alenniboris.fastbanking.presentation.model.bank_product.CardModelUi
import com.alenniboris.fastbanking.presentation.screens.payment_type_selection.PaymentType

data class PaymentProcessScreenState(
    val paymentType: PaymentType,

    val isAllUserCardsLoading: Boolean = false,
    val allUserCards: List<CardModelUi> = emptyList(),
    val selectedUsedCard: CardModelUi? = null,
    val selectedReceiverCard: CardModelUi? = null,
    val isCardsSheetForUsedCardVisible: Boolean = false,
    val isCardsSheetForReceiverCardVisible: Boolean = false,

    val enteredAmount: String = "",
    val enteredReceiverData: String = "",

    val isPaymentProceeding: Boolean = false,
    val isPaymentSucceeded: Boolean = false
)