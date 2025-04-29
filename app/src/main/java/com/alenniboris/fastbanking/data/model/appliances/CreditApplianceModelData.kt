package com.alenniboris.fastbanking.data.model.appliances

import android.util.Log
import com.alenniboris.fastbanking.domain.model.appliances.ApplianceStatus
import com.alenniboris.fastbanking.domain.model.appliances.CreditApplianceModelDomain
import com.alenniboris.fastbanking.domain.model.appliances.CreditDetailedApplianceType
import java.util.Date

data class CreditApplianceModelData(
    val id: String? = null,
    val currencyCode: String? = null,
    val dateOfAppliance: String? = null,
    val status: String? = null,
    val selectedOffice: OfficeModelData? = null,
    val userId: String? = null,
    val creditGoal: String? = null,
    val detailedCreditApplianceType: String? = null
)

fun CreditApplianceModelData.toModelDomain(): CreditApplianceModelDomain? =
    runCatching {

        CreditApplianceModelDomain(
            id = this.id!!,
            currencyCode = this.currencyCode!!,
            dateOfAppliance = Date(this.dateOfAppliance?.toLong()!!),
            status = when (this.status!!) {
                "Approved" -> ApplianceStatus.Approved
                "Declined" -> ApplianceStatus.Declined
                "Waiting" -> ApplianceStatus.Waiting
                else -> ApplianceStatus.Undefined
            },
            selectedOffice = this.selectedOffice?.toModelDomain()!!,
            userId = this.userId!!,
            creditGoal = this.creditGoal!!,
            detailedCreditApplianceType = when (this.detailedCreditApplianceType!!) {
                "BBANK_CREDIT" -> CreditDetailedApplianceType.BBANK_CREDIT
                "SOCIAL_CREDIT" -> CreditDetailedApplianceType.SOCIAL_CREDIT
                else -> CreditDetailedApplianceType.UNDEFINED
            }
        )
    }.getOrElse { exception ->
        Log.e("!!!", exception.stackTraceToString())
        null
    }