package com.alenniboris.fastbanking.presentation.screens.product_appliance_choosing

import com.alenniboris.fastbanking.domain.model.appliances.CardDetailedApplianceType
import com.alenniboris.fastbanking.domain.model.appliances.CreditDetailedApplianceType
import com.alenniboris.fastbanking.domain.model.appliances.DepositDetailedApplianceType

interface IProductApplianceChoosingScreenEvent {

    data class OpenCardApplianceScreenForm(val option: CardDetailedApplianceType) :
        IProductApplianceChoosingScreenEvent

    data class OpenCreditApplianceScreenForm(val option: CreditDetailedApplianceType) :
        IProductApplianceChoosingScreenEvent

    data class OpenDepositApplianceScreenForm(val option: DepositDetailedApplianceType) :
        IProductApplianceChoosingScreenEvent

    data class ShowToastMessage(val messageId: Int) : IProductApplianceChoosingScreenEvent

    data object NavigateBack : IProductApplianceChoosingScreenEvent
}