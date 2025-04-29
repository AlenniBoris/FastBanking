package com.alenniboris.fastbanking.presentation.uikit.values

import com.alenniboris.fastbanking.R
import com.alenniboris.fastbanking.domain.model.appliances.CardDetailedApplianceType
import com.alenniboris.fastbanking.domain.model.appliances.CreditDetailedApplianceType
import com.alenniboris.fastbanking.domain.model.appliances.DepositDetailedApplianceType

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


val cardDetailedApplianceTypes = listOf(
    CardDetailedApplianceType.ISSUE_PAYMENT_CARD,
    CardDetailedApplianceType.ISSUE_VIRTUAL_CARD,
    CardDetailedApplianceType.REISSUE_PAYMENT_CARD,
    CardDetailedApplianceType.REISSUE_VIRTUAL_CARD
)

val creditDetailedApplianceTypes = listOf(
    CreditDetailedApplianceType.BBANK_CREDIT,
    CreditDetailedApplianceType.SOCIAL_CREDIT
)

val depositDetailedApplianceTypes = listOf(
    DepositDetailedApplianceType.URGENT_DEPOSIT
)