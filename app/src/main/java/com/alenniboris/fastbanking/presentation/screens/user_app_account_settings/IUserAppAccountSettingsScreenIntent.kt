package com.alenniboris.fastbanking.presentation.screens.user_app_account_settings

sealed interface IUserAppAccountSettingsScreenIntent {

    data object NavigateBack : IUserAppAccountSettingsScreenIntent

    data object OpenPasswordResetScreen : IUserAppAccountSettingsScreenIntent
}
