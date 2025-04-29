package com.alenniboris.fastbanking.presentation.screens.user_appliances

import com.alenniboris.fastbanking.presentation.model.appliance.ProductApplianceModelUi

sealed interface IUserAppliancesScreenIntent {

    data object NavigateBack : IUserAppliancesScreenIntent

    data class OpenDetailsPage(val appliance: ProductApplianceModelUi) : IUserAppliancesScreenIntent
}