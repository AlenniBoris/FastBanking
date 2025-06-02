package com.alenniboris.fastbanking.presentation.screens.user_app_account_settings

interface IUserAppAccountSettingsScreenEvent {

    data object NavigateBack : IUserAppAccountSettingsScreenEvent

    data object OpenPasswordResetScreen : IUserAppAccountSettingsScreenEvent
}