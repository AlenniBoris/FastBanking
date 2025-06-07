package com.alenniboris.fastbanking.presentation.screens.personal

import com.alenniboris.fastbanking.R


enum class PersonalScreenCategories {
    PROFILE,
    SETTING
}

fun PersonalScreenCategories.toUiString(): Int = when (this) {
    PersonalScreenCategories.PROFILE -> R.string.profile_text
    PersonalScreenCategories.SETTING -> R.string.settings_text
}

enum class ProfileActions {
    PERSONAL_DATA,
    APPLIANCES_FOR_PRODUCTS
}

fun ProfileActions.toUiString(): Int = when (this) {
    ProfileActions.PERSONAL_DATA -> R.string.personal_data_text
    ProfileActions.APPLIANCES_FOR_PRODUCTS -> R.string.user_appliances_for_products_text
}

fun ProfileActions.toUiDescriptionString(): Int = when (this) {
    ProfileActions.PERSONAL_DATA -> R.string.personal_data_description_text
    ProfileActions.APPLIANCES_FOR_PRODUCTS -> R.string.user_appliances_for_products_description_text
}

fun ProfileActions.toUiPicture(): Int = when (this) {
    ProfileActions.PERSONAL_DATA -> R.drawable.personal_icon
    ProfileActions.APPLIANCES_FOR_PRODUCTS -> R.drawable.list_of_appliances_icon
}

enum class SettingsActionsCategory {
    GENERAL,
    SAFETY
}

enum class SettingsActions {
    APPLICATION_THEME,
    APPLICATION_LANGUAGE,
    APPLICATION_BASE_CURRENCY,
    ACCOUNT_DATA
}

fun SettingsActionsCategory.toUiString(): Int = when (this) {
    SettingsActionsCategory.GENERAL -> R.string.general_settiings_category_text
    SettingsActionsCategory.SAFETY -> R.string.safety_settings_category_text
}

fun SettingsActionsCategory.toListOfActions(): List<SettingsActions> = when (this) {
    SettingsActionsCategory.GENERAL -> listOf(
        SettingsActions.APPLICATION_THEME,
        SettingsActions.APPLICATION_LANGUAGE,
        SettingsActions.APPLICATION_BASE_CURRENCY
    )

    SettingsActionsCategory.SAFETY -> listOf(
        SettingsActions.ACCOUNT_DATA
    )
}

fun SettingsActions.toUiString(): Int = when (this) {
    SettingsActions.APPLICATION_THEME -> R.string.application_theme_text
    SettingsActions.APPLICATION_LANGUAGE -> R.string.application_language_text
    SettingsActions.APPLICATION_BASE_CURRENCY -> R.string.application_base_currency_text
    SettingsActions.ACCOUNT_DATA -> R.string.my_account_text
}

fun SettingsActions.toUiPicture(): Int = when (this) {
    SettingsActions.APPLICATION_THEME -> R.drawable.application_theme_icon
    SettingsActions.APPLICATION_LANGUAGE -> R.drawable.application_language_icon
    SettingsActions.APPLICATION_BASE_CURRENCY -> R.drawable.application_base_currency_icon
    SettingsActions.ACCOUNT_DATA -> R.drawable.personal_icon
}

fun SettingsActions.toUiDescriptionString(): Int = when (this) {
    SettingsActions.APPLICATION_THEME -> R.string.application_theme_description_text
    SettingsActions.APPLICATION_LANGUAGE -> R.string.application_language_description_text
    SettingsActions.APPLICATION_BASE_CURRENCY -> R.string.application_base_currency_description_text
    SettingsActions.ACCOUNT_DATA -> R.string.my_account_description_text
}