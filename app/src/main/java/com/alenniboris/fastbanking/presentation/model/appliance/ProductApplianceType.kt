package com.alenniboris.fastbanking.presentation.model.appliance

import com.alenniboris.fastbanking.domain.model.appliances.CardApplianceModelDomain
import com.alenniboris.fastbanking.domain.model.appliances.CreditApplianceModelDomain
import com.alenniboris.fastbanking.domain.model.appliances.DepositApplianceModelDomain

enum class ProductApplianceType {
    Card,
    Credit,
    Deposit,
    Undefined
}

fun ProductApplianceModelUi.toProductApplianceType(): ProductApplianceType =
    when (this.domainModel) {
        is CardApplianceModelDomain -> ProductApplianceType.Card
        is CreditApplianceModelDomain -> ProductApplianceType.Credit
        is DepositApplianceModelDomain -> ProductApplianceType.Deposit
        else -> ProductApplianceType.Undefined
    }