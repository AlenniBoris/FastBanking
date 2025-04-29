package com.alenniboris.fastbanking.data.model.appliances

import com.alenniboris.fastbanking.domain.model.appliances.DepositApplianceModelDomain
import com.alenniboris.fastbanking.domain.model.appliances.OfficeModelDomain

data class DepositApplianceSavingModelData(
    val id: String,
    val currencyCode: String,
    val dateOfAppliance: String,
    val minimumContribution: String,
    val period: String,
    val procent: String,
    val selectedOffice: OfficeModelDomain,
    val userId: String,
    val status: String,
    val detailedDepositApplianceType: String
)

fun DepositApplianceModelDomain.toSavingModel(): DepositApplianceSavingModelData =
    DepositApplianceSavingModelData(
        id = this.id,
        currencyCode = this.currencyCode,
        dateOfAppliance = this.dateOfAppliance.time.toString(),
        minimumContribution = this.minimumContribution.toString(),
        period = this.period.toString(),
        procent = this.procent.toString(),
        selectedOffice = this.selectedOffice,
        userId = this.userId,
        status = this.status.toString(),
        detailedDepositApplianceType = this.detailedDepositApplianceType.toString()
    )