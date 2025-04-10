package com.alenniboris.fastbanking.presentation.screens.main

import com.alenniboris.fastbanking.R

enum class ActionsWithProducts {

    GET_OWN_CARD,
    SEND_APPLY_FOR_CREDIT,
    OPEN_ONLINE_DEPOSIT
}

fun ActionsWithProducts.toUiPicture(): Int = when(this){
    ActionsWithProducts.GET_OWN_CARD -> R.drawable.credit_card_action_icon
    ActionsWithProducts.SEND_APPLY_FOR_CREDIT -> R.drawable.credit_action_icon
    ActionsWithProducts.OPEN_ONLINE_DEPOSIT -> R.drawable.deposit_action_icon
}

fun ActionsWithProducts.toUiString(): Int = when(this){
    ActionsWithProducts.GET_OWN_CARD -> R.string.apply_for_card_text
    ActionsWithProducts.SEND_APPLY_FOR_CREDIT -> R.string.apply_for_credit_text
    ActionsWithProducts.OPEN_ONLINE_DEPOSIT -> R.string.apply_for_deposit_text
}