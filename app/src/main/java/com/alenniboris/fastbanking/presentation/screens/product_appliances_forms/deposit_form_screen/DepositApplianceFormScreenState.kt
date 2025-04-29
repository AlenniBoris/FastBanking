package com.alenniboris.fastbanking.presentation.screens.product_appliances_forms.deposit_form_screen

import android.icu.util.Calendar
import com.alenniboris.fastbanking.domain.model.appliances.ApplianceStatus
import com.alenniboris.fastbanking.domain.model.appliances.DepositDetailedApplianceType
import com.alenniboris.fastbanking.domain.model.appliances.OfficeModelDomain
import com.alenniboris.fastbanking.domain.model.currency.CurrencyModelDomain
import com.alenniboris.fastbanking.presentation.model.user.UserModelUi
import com.alenniboris.fastbanking.presentation.screens.product_appliances_forms.ProductApplianceFormScreenProcess
import java.util.Date

data class DepositApplianceFormScreenState(
    val applianceType: DepositDetailedApplianceType,
    val currentUser: UserModelUi? = null,
    val isAllCurrenciesLoading: Boolean = false,
    val supportedCurrencies: List<CurrencyModelDomain> = emptyList(),
    val selectedCurrency: CurrencyModelDomain? = supportedCurrencies.firstOrNull(),
    val isBankOfficesLoading: Boolean = false,
    val allBankOffices: List<OfficeModelDomain> = emptyList(),
    val selectedBankOffice: OfficeModelDomain? = allBankOffices.firstOrNull(),
    val minimumContribution: String = "",
    val procentText: String = "",
    val isPeriodsSheetVisible: Boolean = false,
    val periodTime: Int = possibleDepositPeriods.first(),
    val allPossiblePeriods: List<Int> = possibleDepositPeriods,
    val isPolicyAccepted: Boolean = false,
    val currentProcess: ProductApplianceFormScreenProcess = ProductApplianceFormScreenProcess.FILLING_DATA,
    val allProcessParts: List<ProductApplianceFormScreenProcess> = ProductApplianceFormScreenProcess.entries.toList(),
    val isApplianceProceeding: Boolean = false
) {

    val procentValue: Double = if (procentText.isEmpty()) 0.0 else procentText.toDouble()
    val minimumContributionValue: Double =
        if (minimumContribution.isEmpty()) 0.0 else minimumContribution.toDouble()
    val dateOfAppliance: Date = Calendar.getInstance().time
    val applianceStatus: ApplianceStatus = ApplianceStatus.Waiting
}
