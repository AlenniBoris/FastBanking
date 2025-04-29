package com.alenniboris.fastbanking.domain.model.appliances

import java.util.Date

data class CreditApplianceModelDomain(
    override val id: String,
    override val dateOfAppliance: Date,
    override val status: ApplianceStatus,
    override val selectedOffice: OfficeModelDomain,
    override val userId: String,
    override val currencyCode: String,
    val detailedCreditApplianceType: CreditDetailedApplianceType,
    val creditGoal: String
) : IProductAppliance