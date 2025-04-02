package com.alenniboris.fastbanking.presentation.screens.additions

import com.alenniboris.fastbanking.R


enum class AdditionsScreenCategory {
    REGISTRATION_AND_RECOVERY,
    COURSES,
    INFORMATION,
    PLACES_OF_BANK
}

enum class AdditionsCategoriesAction {
    REGISTRATION_IN_SYSTEM,
    PASSWORD_RECOVERY,
    CURRENCY_EXCHANGER,
    BANK_NEWS,
    APP_VERSION,
    ATMS_AND_OFFICES,
    PRAISE
}

fun AdditionsScreenCategory.toUiString(): Int = when (this) {
    AdditionsScreenCategory.REGISTRATION_AND_RECOVERY -> R.string.recovery_and_info_header_text
    AdditionsScreenCategory.COURSES -> R.string.courses_header_text
    AdditionsScreenCategory.INFORMATION -> R.string.information_header_text
    AdditionsScreenCategory.PLACES_OF_BANK -> R.string.places_of_bank_header_text
}


fun AdditionsCategoriesAction.toUiString(): Int = when (this) {
    AdditionsCategoriesAction.REGISTRATION_IN_SYSTEM -> R.string.registration_text
    AdditionsCategoriesAction.PASSWORD_RECOVERY -> R.string.password_recovery_text
    AdditionsCategoriesAction.CURRENCY_EXCHANGER -> R.string.currency_exchanger_text
    AdditionsCategoriesAction.BANK_NEWS -> R.string.bank_news_text
    AdditionsCategoriesAction.APP_VERSION -> R.string.app_version_text
    AdditionsCategoriesAction.ATMS_AND_OFFICES -> R.string.atms_and_offices_text
    AdditionsCategoriesAction.PRAISE -> R.string.praise_application_text
}

fun AdditionsCategoriesAction.toUiIcon(): Int = when (this) {
    AdditionsCategoriesAction.REGISTRATION_IN_SYSTEM -> R.drawable.registration_action_icon
    AdditionsCategoriesAction.PASSWORD_RECOVERY -> R.drawable.password_closed_icon
    AdditionsCategoriesAction.CURRENCY_EXCHANGER -> R.drawable.exchange_icon
    AdditionsCategoriesAction.BANK_NEWS -> R.drawable.news_action_icon
    AdditionsCategoriesAction.APP_VERSION -> R.drawable.info_action_icon
    AdditionsCategoriesAction.ATMS_AND_OFFICES -> R.drawable.atm_and_offices_action_icon
    AdditionsCategoriesAction.PRAISE -> R.drawable.praise_action_icon
}


fun AdditionsScreenCategory.toListOfActions(isUserAuthorized: Boolean): List<AdditionsCategoriesAction> =
    when (this) {
        AdditionsScreenCategory.REGISTRATION_AND_RECOVERY -> listOf(
            AdditionsCategoriesAction.REGISTRATION_IN_SYSTEM,
            AdditionsCategoriesAction.PASSWORD_RECOVERY
        )

        AdditionsScreenCategory.COURSES -> {
            listOf(
                AdditionsCategoriesAction.CURRENCY_EXCHANGER
            )
        }

        AdditionsScreenCategory.INFORMATION -> {
            if (isUserAuthorized) {
                listOf(
                    AdditionsCategoriesAction.BANK_NEWS,
                    AdditionsCategoriesAction.APP_VERSION,
                    AdditionsCategoriesAction.PRAISE
                )
            } else {
                listOf(
                    AdditionsCategoriesAction.BANK_NEWS,
                    AdditionsCategoriesAction.APP_VERSION
                )
            }
        }

        AdditionsScreenCategory.PLACES_OF_BANK -> listOf(
            AdditionsCategoriesAction.ATMS_AND_OFFICES
        )
    }

val AuthorizedAdditionsCategories: List<AdditionsScreenCategory> = listOf(
    AdditionsScreenCategory.PLACES_OF_BANK,
    AdditionsScreenCategory.COURSES,
    AdditionsScreenCategory.INFORMATION,
)

val NotAuthorizedAdditionsCategories: List<AdditionsScreenCategory> = listOf(
    AdditionsScreenCategory.REGISTRATION_AND_RECOVERY,
    AdditionsScreenCategory.INFORMATION
)