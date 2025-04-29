package com.alenniboris.fastbanking.presentation.screens.product_appliances_forms.credit_form_screen

interface ICreditApplianceFormScreenEvent {

    data object NavigateBack : ICreditApplianceFormScreenEvent

    data class ShowToastMessage(val messageId: Int) : ICreditApplianceFormScreenEvent
}