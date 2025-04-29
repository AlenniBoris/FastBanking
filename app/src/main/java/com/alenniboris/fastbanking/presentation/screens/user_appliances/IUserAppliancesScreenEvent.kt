package com.alenniboris.fastbanking.presentation.screens.user_appliances

import com.alenniboris.fastbanking.presentation.model.appliance.ProductApplianceModelUi

sealed interface IUserAppliancesScreenEvent {

    data object NavigateBack : IUserAppliancesScreenEvent

    data class OpenDetailsPage(val appliance: ProductApplianceModelUi) : IUserAppliancesScreenEvent

    data class ShowToastMessage(val messageId: Int) : IUserAppliancesScreenEvent
}