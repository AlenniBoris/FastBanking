package com.alenniboris.fastbanking.presentation.screens.user_appliances

import com.alenniboris.fastbanking.presentation.model.appliance.ProductApplianceModelUi

data class UserAppliancesScreenState(
    val appliances: List<ProductApplianceModelUi> = emptyList(),
    val isLoading: Boolean = false
)
