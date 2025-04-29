package com.alenniboris.fastbanking.data.model.appliances

import com.alenniboris.fastbanking.domain.model.appliances.CreditApplianceModelDomain
import com.alenniboris.fastbanking.domain.model.appliances.OfficeModelDomain

data class CreditApplianceSavingModelData(
    val id: String,
    val currencyCode: String,
    val dateOfAppliance: String,
    val status: String,
    val selectedOffice: OfficeModelDomain,
    val userId: String,
    val creditGoal: String,
    val detailedCreditApplianceType: String
)

fun CreditApplianceModelDomain.toSavingModel(): CreditApplianceSavingModelData =
    CreditApplianceSavingModelData(
        id = this.id,
        currencyCode = this.currencyCode,
        dateOfAppliance = this.dateOfAppliance.time.toString(),
        status = this.status.toString(),
        selectedOffice = this.selectedOffice,
        userId = this.userId,
        creditGoal = this.creditGoal,
        detailedCreditApplianceType = this.detailedCreditApplianceType.toString()
    )
