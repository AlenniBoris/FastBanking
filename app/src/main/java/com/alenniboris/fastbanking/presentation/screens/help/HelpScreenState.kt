package com.alenniboris.fastbanking.presentation.screens.help

import com.alenniboris.fastbanking.presentation.uikit.utils.PermissionType

data class HelpScreenState(
    val isPhoneNumbersSheetVisible: Boolean = false,
    val bankPhoneNumbers: List<BankPhoneNumber> = HelpScreenValues.BANK_NUMBERS,
    val bankMessengers: List<BankSupportedMessenger> = BankSupportedMessenger.entries.toList(),
    val bankInfoCategories: List<BankInfoCategory> = BankInfoCategory.entries.toList(),
    val isMessengersSheetVisible: Boolean = false,
    val isBankInfoSheetVisible: Boolean = false,
    val requestedPermission: PermissionType? = null,
    val isPermissionDialogVisible: Boolean = false
)