package com.alenniboris.fastbanking.presentation.screens.product_appliance_details

import com.alenniboris.fastbanking.presentation.model.appliance.ProductApplianceModelUi

data class ProductApplianceDetailsScreenState(
    val appliance: ProductApplianceModelUi? = null,
    val isLoading: Boolean = false
)
