package com.alenniboris.fastbanking.presentation.screens.main

import com.alenniboris.fastbanking.R

enum class BankProduct {
    CARD,
    CREDIT,
    DEPOSITS_AND_ACCOUNTS
}

fun BankProduct.toUiText(): Int = when (this) {
    BankProduct.CARD -> R.string.cards_products_text
    BankProduct.CREDIT -> R.string.credits_products_text
    BankProduct.DEPOSITS_AND_ACCOUNTS -> R.string.deposits_and_accounts_products_text
}
