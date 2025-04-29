package com.alenniboris.fastbanking.data.model.appliances

import android.util.Log
import com.alenniboris.fastbanking.domain.model.appliances.ApplianceStatus
import com.alenniboris.fastbanking.domain.model.appliances.CardApplianceModelDomain
import com.alenniboris.fastbanking.domain.model.appliances.CardDetailedApplianceType
import com.alenniboris.fastbanking.domain.model.card.CardSystem
import com.alenniboris.fastbanking.domain.model.card.CardType
import java.util.Date

data class CardApplianceModelData(
    val currencyCode: String? = null,
    val dateOfAppliance: String? = null,
    val id: String? = null,
    val newAccountNecessary: String? = null,
    val salaryCard: String? = null,
    val isVirtual: String? = null,
    val selectedOffice: OfficeModelData? = null,
    val status: String? = null,
    val system: String? = null,
    val type: String? = null,
    val userId: String? = null,
    val detailedCardApplianceType: String? = null
)

fun CardApplianceModelData.toModelDomain(): CardApplianceModelDomain? =
    runCatching {

        CardApplianceModelDomain(
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
            isNewAccountNecessary = this.newAccountNecessary.toBoolean(),
            isSalaryCard = this.salaryCard.toBoolean(),
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
            },
            detailedCardApplianceType = when (this.detailedCardApplianceType!!) {
                "ISSUE_PAYMENT_CARD" -> CardDetailedApplianceType.ISSUE_PAYMENT_CARD
                "ISSUE_VIRTUAL_CARD" -> CardDetailedApplianceType.ISSUE_VIRTUAL_CARD
                "REISSUE_PAYMENT_CARD" -> CardDetailedApplianceType.REISSUE_PAYMENT_CARD
                "REISSUE_VIRTUAL_CARD" -> CardDetailedApplianceType.REISSUE_VIRTUAL_CARD
                else -> CardDetailedApplianceType.UNDEFINED
            }
        )
    }.getOrElse { exception ->
        Log.e("!!!", exception.stackTraceToString())
        null
    }