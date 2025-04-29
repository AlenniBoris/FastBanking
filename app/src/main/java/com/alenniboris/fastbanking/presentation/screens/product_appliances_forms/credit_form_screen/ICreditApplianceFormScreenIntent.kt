package com.alenniboris.fastbanking.presentation.screens.product_appliances_forms.credit_form_screen

import com.alenniboris.fastbanking.domain.model.appliances.OfficeModelDomain
import com.alenniboris.fastbanking.domain.model.currency.CurrencyModelDomain

interface ICreditApplianceFormScreenIntent {

    data object ProceedBackwardAction : ICreditApplianceFormScreenIntent

    data object ProceedForwardAction : ICreditApplianceFormScreenIntent

    data class UpdateSelectedCurrency(val currency: CurrencyModelDomain) :
        ICreditApplianceFormScreenIntent

    data class UpdateSelectedOffice(val office: OfficeModelDomain) :
        ICreditApplianceFormScreenIntent

    data class UpdateIsPolicyAccepted(val newValue: Boolean) : ICreditApplianceFormScreenIntent

    data class UpdateCreditGoal(val newValue: String) : ICreditApplianceFormScreenIntent
}
