package com.alenniboris.fastbanking.presentation.screens.product_appliance_details

sealed interface IProductApplianceDetailsScreenEvent {

    data object NavigateBack : IProductApplianceDetailsScreenEvent

    data class ShowMessage(val messageId: Int) : IProductApplianceDetailsScreenEvent
}