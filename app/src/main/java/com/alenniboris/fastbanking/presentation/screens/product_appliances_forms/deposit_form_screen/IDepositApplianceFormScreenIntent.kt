package com.alenniboris.fastbanking.presentation.screens.product_appliances_forms.deposit_form_screen

import com.alenniboris.fastbanking.domain.model.appliances.OfficeModelDomain
import com.alenniboris.fastbanking.domain.model.currency.CurrencyModelDomain

interface IDepositApplianceFormScreenIntent {

    data object ProceedBackwardAction : IDepositApplianceFormScreenIntent

    data object ProceedForwardAction : IDepositApplianceFormScreenIntent

    data class UpdateSelectedCurrency(val currency: CurrencyModelDomain) :
        IDepositApplianceFormScreenIntent

    data class UpdateSelectedOffice(val office: OfficeModelDomain) :
        IDepositApplianceFormScreenIntent

    data class UpdateMinimumContribution(val newValue: String) : IDepositApplianceFormScreenIntent

    data class UpdateDepositProcent(val newValue: String) : IDepositApplianceFormScreenIntent

    data class UpdateSelectedPeriod(val newValue: Int) : IDepositApplianceFormScreenIntent

    data class UpdateIsPolicyAccepted(val newValue: Boolean) : IDepositApplianceFormScreenIntent

    data object UpdatePeriodSheetVisibility : IDepositApplianceFormScreenIntent
}