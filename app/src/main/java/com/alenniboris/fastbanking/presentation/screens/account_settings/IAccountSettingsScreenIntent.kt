package com.alenniboris.fastbanking.presentation.screens.account_settings

sealed interface IAccountSettingsScreenIntent {

    data object NavigateBack : IAccountSettingsScreenIntent

    data object OpenPasswordResetScreen : IAccountSettingsScreenIntent
}
