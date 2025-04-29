package com.alenniboris.fastbanking.presentation.screens.product_appliances_forms.card_form_screen

interface ICardApplianceFormScreenEvent {

    data object NavigateBack : ICardApplianceFormScreenEvent

    data class ShowToastMessage(val messageId: Int) : ICardApplianceFormScreenEvent
}