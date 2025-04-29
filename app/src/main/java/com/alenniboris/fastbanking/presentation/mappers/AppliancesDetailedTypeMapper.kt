package com.alenniboris.fastbanking.presentation.mappers

import com.alenniboris.fastbanking.R
import com.alenniboris.fastbanking.domain.model.appliances.CardDetailedApplianceType
import com.alenniboris.fastbanking.domain.model.appliances.CreditDetailedApplianceType
import com.alenniboris.fastbanking.domain.model.appliances.DepositDetailedApplianceType

fun CardDetailedApplianceType.toUiString(): Int = when (this) {
    CardDetailedApplianceType.ISSUE_PAYMENT_CARD -> R.string.issue_payment_card_text
    CardDetailedApplianceType.ISSUE_VIRTUAL_CARD -> R.string.issue_virtual_card_text
    CardDetailedApplianceType.REISSUE_PAYMENT_CARD -> R.string.reissue_payment_card_text
    CardDetailedApplianceType.REISSUE_VIRTUAL_CARD -> R.string.reissue_virtual_card_text
    CardDetailedApplianceType.UNDEFINED -> R.string.undefined_text
}

fun CreditDetailedApplianceType.toUiString(): Int = when (this) {
    CreditDetailedApplianceType.BBANK_CREDIT -> R.string.bbank_credit_text
    CreditDetailedApplianceType.SOCIAL_CREDIT -> R.string.social_credit_text
    CreditDetailedApplianceType.UNDEFINED -> R.string.undefined_text
}

fun DepositDetailedApplianceType.toUiString(): Int = when (this) {
    DepositDetailedApplianceType.URGENT_DEPOSIT -> R.string.urgent_deposit_text
    DepositDetailedApplianceType.UNDEFINED -> R.string.undefined_text
}
