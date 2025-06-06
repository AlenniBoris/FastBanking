package com.alenniboris.fastbanking.presentation.screens.payment_type_selection

import com.alenniboris.fastbanking.R

enum class PaymentType {
    OnMyCard,
    ByCardNumber,
    ByEripNumber,
    ForCreditByContractNumber
}

fun PaymentType.toUiPicture(): Int = when (this) {
    PaymentType.OnMyCard -> R.drawable.repeat_process_icon
    PaymentType.ByCardNumber -> R.drawable.credit_card_action_icon
    PaymentType.ByEripNumber -> R.drawable.credit_card_action_icon
    PaymentType.ForCreditByContractNumber -> R.drawable.payment_for_credit_icon
}

fun PaymentType.toUiString(): Int = when (this) {
    PaymentType.OnMyCard -> R.string.payment_on_my_card_text
    PaymentType.ByCardNumber -> R.string.payment_by_card_number_text
    PaymentType.ByEripNumber -> R.string.payment_by_erip_number_text
    PaymentType.ForCreditByContractNumber -> R.string.payment_for_credit_by_contract_number_text
}

fun PaymentType.toUiPlaceholderText(): Int = when (this) {
    PaymentType.OnMyCard -> R.string.enter_card_number_text
    PaymentType.ByCardNumber -> R.string.enter_card_number_text
    PaymentType.ByEripNumber -> R.string.enter_erip_number_text
    PaymentType.ForCreditByContractNumber -> R.string.enter_contract_number_text
}