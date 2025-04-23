package com.alenniboris.fastbanking.presentation.screens.account_settings

interface IAccountSettingsScreenEvent {

    data object NavigateBack : IAccountSettingsScreenEvent

    data object OpenPasswordResetScreen : IAccountSettingsScreenEvent
}