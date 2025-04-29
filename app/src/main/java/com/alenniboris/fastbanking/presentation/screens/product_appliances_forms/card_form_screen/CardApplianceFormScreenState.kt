package com.alenniboris.fastbanking.presentation.screens.product_appliances_forms.card_form_screen

import android.icu.util.Calendar
import com.alenniboris.fastbanking.domain.model.appliances.ApplianceStatus
import com.alenniboris.fastbanking.domain.model.appliances.CardDetailedApplianceType
import com.alenniboris.fastbanking.domain.model.appliances.OfficeModelDomain
import com.alenniboris.fastbanking.domain.model.card.CardSystem
import com.alenniboris.fastbanking.domain.model.card.CardType
import com.alenniboris.fastbanking.domain.model.currency.CurrencyModelDomain
import com.alenniboris.fastbanking.presentation.model.user.UserModelUi
import com.alenniboris.fastbanking.presentation.screens.product_appliances_forms.ProductApplianceFormScreenProcess
import java.util.Date

data class CardApplianceFormScreenState(
    val applianceType: CardDetailedApplianceType,
    val isAllCurrenciesLoading: Boolean = false,
    val supportedCurrencies: List<CurrencyModelDomain> = emptyList(),
    val selectedCurrency: CurrencyModelDomain? = supportedCurrencies.firstOrNull(),
    val isNewAccountNeeded: Boolean = false,
    val cardIssuingTypes: List<CardIssuingType> = CardIssuingType.entries.toList(),
    val isSalaryCard: Boolean = false,
    val selectedCardType: CardType = CardType.Dedut,
    val allCardTypes: List<CardType> = listOf(CardType.Dedut, CardType.Credit),
    val isCardTypeSheetVisible: Boolean = false,
    val selectedCardSystem: CardSystem = CardSystem.Visa,
    val allCardSystems: List<CardSystem> = listOfCardSystems,
    val isCardSystemSheetVisible: Boolean = false,
    val isBankOfficesLoading: Boolean = false,
    val allBankOffices: List<OfficeModelDomain> = emptyList(),
    val selectedBankOffice: OfficeModelDomain? = allBankOffices.firstOrNull(),
    val currentUser: UserModelUi? = null,
    val isApplianceProceeding: Boolean = false,
    val isPolicyAccepted: Boolean = false,
    val currentProcess: ProductApplianceFormScreenProcess = ProductApplianceFormScreenProcess.FILLING_DATA,
    val allProcessParts: List<ProductApplianceFormScreenProcess> = ProductApplianceFormScreenProcess.entries.toList()
) {

    val dateOfAppliance: Date = Calendar.getInstance().time
    val applianceStatus: ApplianceStatus = ApplianceStatus.Waiting
    val isVirtualCard: Boolean = applianceType?.let {
        it == CardDetailedApplianceType.REISSUE_VIRTUAL_CARD || it == CardDetailedApplianceType.ISSUE_VIRTUAL_CARD
    } ?: false
    val currentCardIssueType: CardIssuingType =
        if (isNewAccountNeeded) CardIssuingType.WITH_NEW_ACCOUNT else CardIssuingType.TO_EXISTING_ACCOUNT
}
