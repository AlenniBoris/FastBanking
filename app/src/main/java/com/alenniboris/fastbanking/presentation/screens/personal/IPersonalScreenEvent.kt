package com.alenniboris.fastbanking.presentation.screens.personal

sealed interface IPersonalScreenEvent {

    data object NavigateBack : IPersonalScreenEvent

    data class ShowToastMessage(val messageId: Int) : IPersonalScreenEvent

    data object OpenThemeSettingsScreen : IPersonalScreenEvent

    data object OpenLanguageSettingsScreen : IPersonalScreenEvent

    data object OpenBaseCurrencySettingsScreen : IPersonalScreenEvent

    data object OpenAccountDataScreen : IPersonalScreenEvent

    data object OpenPersonalDetailsScreen : IPersonalScreenEvent

    data object OpenUserProductsAppliancesScreen : IPersonalScreenEvent
}