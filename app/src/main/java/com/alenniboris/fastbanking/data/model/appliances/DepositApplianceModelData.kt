package com.alenniboris.fastbanking.data.model.appliances

import android.util.Log
import com.alenniboris.fastbanking.domain.model.appliances.ApplianceStatus
import com.alenniboris.fastbanking.domain.model.appliances.DepositApplianceModelDomain
import java.util.Date

data class DepositApplianceModelData(
    val id: String? = null,
    val currency: String? = null,
    val dateOfAppliance: String? = null,
    val minimumContribution: String? = null,
    val period: String? = null,
    val procent: String? = null,
    val selectedOffice: OfficeModelData? = null,
    val userId: String? = null,
    val status: String? = null
)

fun DepositApplianceModelData.toModelDomain(): DepositApplianceModelDomain? =
    runCatching {

        DepositApplianceModelDomain(
            id = this.id!!,
            currencyCode = this.currency!!,
            dateOfAppliance = Date(this.dateOfAppliance?.toLong()!!),
            minimumContribution = this.minimumContribution?.toDouble()!!,
            period = this.period?.toInt()!!,
            procent = this.procent?.toDouble()!!,
            selectedOffice = this.selectedOffice?.toModelDomain()!!,
            userId = this.userId!!,
            status = when (this.status!!) {
                "Approved" -> ApplianceStatus.Approved
                "Declined" -> ApplianceStatus.Declined
                "Waiting" -> ApplianceStatus.Waiting
                else -> ApplianceStatus.Undefined
            }
        )
    }.getOrElse { exception ->
        Log.e("!!!", exception.stackTraceToString())
        null
    }