package com.alenniboris.fastbanking.presentation.screens.help

import com.alenniboris.fastbanking.presentation.uikit.utils.PermissionType

sealed interface IHelpScreenIntent {

    data object ProceedNavigateBack : IHelpScreenIntent

    data object UpdatePhoneNumbersSheetVisibility : IHelpScreenIntent

    data object UpdateMessengersSheetVisibility : IHelpScreenIntent

    data object UpdateBankInfoSheetVisibility : IHelpScreenIntent

    data class MakePhoneCall(val phoneNumber: BankPhoneNumber) : IHelpScreenIntent

    data class UpdateRequestedPermissionAndShowDialog(val newValue: PermissionType) :
        IHelpScreenIntent

    data object HidePermissionDialog : IHelpScreenIntent

    data object OpenSettingsAndHidePermissionDialog : IHelpScreenIntent

    data class OpenMessenger(val messenger: BankSupportedMessenger) : IHelpScreenIntent

    data class CopyToClipboard(val text: String) : IHelpScreenIntent
}