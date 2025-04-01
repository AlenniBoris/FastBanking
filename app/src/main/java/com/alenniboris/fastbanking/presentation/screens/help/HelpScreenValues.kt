package com.alenniboris.fastbanking.presentation.screens.help

import com.alenniboris.fastbanking.R

object HelpScreenValues {

    val BANK_NUMBERS = listOf(
        BankPhoneNumber(
            cellOperator = "A1",
            phoneNumber = "+375291111111"
        ),
        BankPhoneNumber(
            cellOperator = "MTC",
            phoneNumber = "+375291111111"
        ),
        BankPhoneNumber(
            cellOperator = "A2",
            phoneNumber = "+375291111111"
        )
    )
}

data class BankPhoneNumber(
    val cellOperator: String,
    val phoneNumber: String
)

enum class BankSupportedMessenger {
    VIBER,
    TELEGRAM
}

fun BankSupportedMessenger.toUiPicture(): Int = when (this) {
    BankSupportedMessenger.VIBER -> R.drawable.viber_icon
    BankSupportedMessenger.TELEGRAM -> R.drawable.telegram_icon
}

fun BankSupportedMessenger.toUiString(): Int = when (this) {
    BankSupportedMessenger.VIBER -> R.string.viber_messenger_name
    BankSupportedMessenger.TELEGRAM -> R.string.telegram_messenger_name
}

fun BankSupportedMessenger.toUriString(): String = when (this) {
    BankSupportedMessenger.VIBER -> "viber://chat?number=+375291111111"
    BankSupportedMessenger.TELEGRAM -> "https://t.me/+375291111111"
}

enum class BankInfoCategory {
    OFFICIAL_NAME,
    OFFICIAL_ADDRESS,
    SWIFT_CODE,
    UNP_NUMBER,
    OKPO_NUMBER
}

fun BankInfoCategory.toUiName(): Int = when (this) {
    BankInfoCategory.OFFICIAL_NAME -> R.string.bank_official_name
    BankInfoCategory.OFFICIAL_ADDRESS -> R.string.bank_official_address_name
    BankInfoCategory.SWIFT_CODE -> R.string.bank_swift_code_name
    BankInfoCategory.UNP_NUMBER -> R.string.bank_unp_number_name
    BankInfoCategory.OKPO_NUMBER -> R.string.bank_okpo_number_name
}

fun BankInfoCategory.toUiValue(): Int = when (this) {
    BankInfoCategory.OFFICIAL_NAME -> R.string.bank_official_value
    BankInfoCategory.OFFICIAL_ADDRESS -> R.string.bank_official_address_value
    BankInfoCategory.SWIFT_CODE -> R.string.bank_swift_code_value
    BankInfoCategory.UNP_NUMBER -> R.string.bank_unp_number_value
    BankInfoCategory.OKPO_NUMBER -> R.string.bank_okpo_number_value
}