package com.alenniboris.fastbanking.data.model.appliances

import com.alenniboris.fastbanking.domain.model.appliances.CardApplianceModelDomain
import com.alenniboris.fastbanking.domain.model.appliances.OfficeModelDomain

data class CardApplianceSavingModelData(
    val currencyCode: String,
    val dateOfAppliance: String,
    val id: String,
    val isNewAccountNecessary: String,
    val isSalaryCard: String,
    val isVirtual: String,
    val selectedOffice: OfficeModelDomain,
    val status: String,
    val system: String,
    val type: String,
    val userId: String,
    val detailedCardApplianceType: String
)

fun CardApplianceModelDomain.toSavingModel(): CardApplianceSavingModelData =
    CardApplianceSavingModelData(
        currencyCode = this.currencyCode,
        dateOfAppliance = this.dateOfAppliance.time.toString(),
        id = this.id,
        isNewAccountNecessary = this.isNewAccountNecessary.toString(),
        isSalaryCard = this.isSalaryCard.toString(),
        isVirtual = this.isVirtual.toString(),
        selectedOffice = this.selectedOffice,
        status = this.status.toString(),
        system = this.system.toString(),
        type = this.type.toString(),
        userId = this.userId,
        detailedCardApplianceType = this.detailedCardApplianceType.toString()
    )