package com.alenniboris.fastbanking.domain.model.appliances

import java.util.Date

data class DepositApplianceModelDomain(
    override val id: String,
    override val dateOfAppliance: Date,
    override val status: ApplianceStatus,
    override val selectedOffice: OfficeModelDomain,
    override val userId: String,
    override val currencyCode: String,
    val detailedDepositApplianceType: DepositDetailedApplianceType,
    val minimumContribution: Double,
    val period: Int,
    val procent: Double
) : IProductAppliance