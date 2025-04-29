package com.alenniboris.fastbanking.presentation.screens.product_appliances_forms.deposit_form_screen

interface IDepositApplianceFormScreenEvent {

    data object NavigateBack : IDepositApplianceFormScreenEvent

    data class ShowToastMessage(val messageId: Int) : IDepositApplianceFormScreenEvent
}