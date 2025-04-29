package com.alenniboris.fastbanking.presentation.screens.product_appliance_choosing

import com.alenniboris.fastbanking.domain.model.appliances.CardDetailedApplianceType
import com.alenniboris.fastbanking.domain.model.appliances.CreditDetailedApplianceType
import com.alenniboris.fastbanking.domain.model.appliances.DepositDetailedApplianceType

sealed interface IProductApplianceChoosingScreenIntent {

    data class ProceedCardOption(val option: CardDetailedApplianceType) :
        IProductApplianceChoosingScreenIntent

    data class ProceedCreditOption(val option: CreditDetailedApplianceType) :
        IProductApplianceChoosingScreenIntent

    data class ProceedDepositOption(val option: DepositDetailedApplianceType) :
        IProductApplianceChoosingScreenIntent

    data object NavigateBack: IProductApplianceChoosingScreenIntent
}