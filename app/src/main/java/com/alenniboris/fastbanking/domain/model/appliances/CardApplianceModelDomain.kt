package com.alenniboris.fastbanking.domain.model.appliances

import com.alenniboris.fastbanking.domain.model.card.CardSystem
import com.alenniboris.fastbanking.domain.model.card.CardType
import java.util.Date

data class CardApplianceModelDomain(
    override val id: String,
    override val currencyCode: String,
    override val dateOfAppliance: Date,
    override val status: ApplianceStatus,
    override val selectedOffice: OfficeModelDomain,
    override val userId: String,
    val isNewAccountNecessary: Boolean,
    val isSalaryCard: Boolean,
    val isVirtual: Boolean,
    val system: CardSystem,
    val type: CardType
) : IProductAppliance
