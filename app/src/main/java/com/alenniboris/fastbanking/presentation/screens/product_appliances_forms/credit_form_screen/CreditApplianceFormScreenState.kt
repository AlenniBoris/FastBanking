package com.alenniboris.fastbanking.presentation.screens.product_appliances_forms.credit_form_screen

import android.icu.util.Calendar
import com.alenniboris.fastbanking.domain.model.appliances.ApplianceStatus
import com.alenniboris.fastbanking.domain.model.appliances.CreditDetailedApplianceType
import com.alenniboris.fastbanking.domain.model.appliances.OfficeModelDomain
import com.alenniboris.fastbanking.domain.model.currency.CurrencyModelDomain
import com.alenniboris.fastbanking.presentation.model.user.UserModelUi
import com.alenniboris.fastbanking.presentation.screens.product_appliances_forms.ProductApplianceFormScreenProcess
import java.util.Date

data class CreditApplianceFormScreenState(
    val applianceType: CreditDetailedApplianceType,
    val currentUser: UserModelUi? = null,
    val isBankOfficesLoading: Boolean = false,
    val allBankOffices: List<OfficeModelDomain> = emptyList(),
    val selectedBankOffice: OfficeModelDomain? = allBankOffices.firstOrNull(),
    val isAllCurrenciesLoading: Boolean = false,
    val supportedCurrencies: List<CurrencyModelDomain> = emptyList(),
    val selectedCurrency: CurrencyModelDomain? = supportedCurrencies.firstOrNull(),
    val creditGoal: String = "",
    val isPolicyAccepted: Boolean = false,
    val currentProcess: ProductApplianceFormScreenProcess = ProductApplianceFormScreenProcess.FILLING_DATA,
    val allProcessParts: List<ProductApplianceFormScreenProcess> = ProductApplianceFormScreenProcess.entries.toList(),
    val isApplianceProceeding: Boolean = false
) {

    val dateOfAppliance: Date = Calendar.getInstance().time
    val applianceStatus: ApplianceStatus = ApplianceStatus.Waiting
}
