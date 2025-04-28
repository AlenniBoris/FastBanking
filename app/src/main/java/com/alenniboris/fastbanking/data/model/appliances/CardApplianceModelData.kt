package com.alenniboris.fastbanking.data.model.appliances

import android.util.Log
import com.alenniboris.fastbanking.domain.model.appliances.ApplianceStatus
import com.alenniboris.fastbanking.domain.model.appliances.CardApplianceModelDomain
import com.alenniboris.fastbanking.domain.model.card.CardSystem
import com.alenniboris.fastbanking.domain.model.card.CardType
import java.util.Date

data class CardApplianceModelData(
    val currency: String? = null,
    val dateOfAppliance: String? = null,
    val id: String? = null,
    val isNewAccountNecessary: String? = null,
    val isSalaryCard: String? = null,
    val isVirtual: String? = null,
    val selectedOffice: OfficeModelData? = null,
    val status: String? = null,
    val system: String? = null,
    val type: String? = null,
    val userId: String? = null
)

fun CardApplianceModelData.toModelDomain(): CardApplianceModelDomain? =
    runCatching {

        CardApplianceModelDomain(
            id = this.id!!,
            currencyCode = this.currency!!,
            dateOfAppliance = Date(this.dateOfAppliance?.toLong()!!),
            status = when (this.status!!) {
                "Approved" -> ApplianceStatus.Approved
                "Declined" -> ApplianceStatus.Declined
                "Waiting" -> ApplianceStatus.Waiting
                else -> ApplianceStatus.Undefined
            },
            selectedOffice = this.selectedOffice?.toModelDomain()!!,
            userId = this.userId!!,
            isNewAccountNecessary = this.isNewAccountNecessary.toBoolean(),
            isSalaryCard = this.isSalaryCard.toBoolean(),
            isVirtual = this.isVirtual.toBoolean(),
            system = when (this.system!!) {
                "Visa" -> CardSystem.Visa
                "Mir" -> CardSystem.Mir
                "Mastercard" -> CardSystem.Mastercard
                else -> CardSystem.Undefined
            },
            type = when (this.type!!) {
                "Debut" -> CardType.Dedut
                "Credit" -> CardType.Credit
                else -> CardType.Undefined
            }
        )
    }.getOrElse { exception ->
        Log.e("!!!", exception.stackTraceToString())
        null
    }