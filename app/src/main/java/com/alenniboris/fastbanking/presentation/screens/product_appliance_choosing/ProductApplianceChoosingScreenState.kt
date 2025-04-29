package com.alenniboris.fastbanking.presentation.screens.product_appliance_choosing

import com.alenniboris.fastbanking.domain.model.appliances.CardDetailedApplianceType
import com.alenniboris.fastbanking.domain.model.appliances.CreditDetailedApplianceType
import com.alenniboris.fastbanking.domain.model.appliances.DepositDetailedApplianceType
import com.alenniboris.fastbanking.presentation.uikit.values.BankProduct
import com.alenniboris.fastbanking.presentation.uikit.values.cardDetailedApplianceTypes
import com.alenniboris.fastbanking.presentation.uikit.values.creditDetailedApplianceTypes
import com.alenniboris.fastbanking.presentation.uikit.values.depositDetailedApplianceTypes

data class ProductApplianceChoosingScreenState(
    val currentProduct: BankProduct? = null,
    val cardOptions: List<CardDetailedApplianceType> = cardDetailedApplianceTypes,
    val creditOptions: List<CreditDetailedApplianceType> = creditDetailedApplianceTypes,
    val depositOptions: List<DepositDetailedApplianceType> = depositDetailedApplianceTypes
)
