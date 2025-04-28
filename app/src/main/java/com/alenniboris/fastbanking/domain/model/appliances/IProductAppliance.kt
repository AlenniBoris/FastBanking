package com.alenniboris.fastbanking.domain.model.appliances

import java.util.Date

interface IProductAppliance {
    val id: String
    val dateOfAppliance: Date
    val status: ApplianceStatus
    val selectedOffice: OfficeModelDomain
    val userId: String
    val currencyCode: String
}

