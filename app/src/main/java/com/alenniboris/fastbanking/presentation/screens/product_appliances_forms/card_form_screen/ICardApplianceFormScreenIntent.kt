package com.alenniboris.fastbanking.presentation.screens.product_appliances_forms.card_form_screen

import com.alenniboris.fastbanking.domain.model.appliances.OfficeModelDomain
import com.alenniboris.fastbanking.domain.model.card.CardSystem
import com.alenniboris.fastbanking.domain.model.card.CardType
import com.alenniboris.fastbanking.domain.model.currency.CurrencyModelDomain

sealed interface ICardApplianceFormScreenIntent {

    data object ProceedBackwardAction : ICardApplianceFormScreenIntent

    data class UpdateSelectedCurrency(val currency: CurrencyModelDomain) :
        ICardApplianceFormScreenIntent

    data class ProceedCardIssuingTypeAction(val issueType: CardIssuingType) :
        ICardApplianceFormScreenIntent

    data class UpdateIsSalaryCard(val newValue: Boolean) : ICardApplianceFormScreenIntent

    data object UpdateCardTypeSheetVisibility : ICardApplianceFormScreenIntent

    data class UpdateSelectedCardType(val newValue: CardType) : ICardApplianceFormScreenIntent

    data object UpdateCardSystemSheetVisibility : ICardApplianceFormScreenIntent

    data class UpdateSelectedCardSystem(val newValue: CardSystem) : ICardApplianceFormScreenIntent

    data object ProceedForwardAction : ICardApplianceFormScreenIntent

    data class UpdateIsPolicyAccepted(val newValue: Boolean) : ICardApplianceFormScreenIntent

    data class UpdateSelectedOffice(val office: OfficeModelDomain) : ICardApplianceFormScreenIntent
}