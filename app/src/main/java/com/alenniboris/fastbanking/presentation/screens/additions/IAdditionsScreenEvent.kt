package com.alenniboris.fastbanking.presentation.screens.additions

sealed interface IAdditionsScreenEvent {

    data class ShowToastMessage(val messageId: Int) : IAdditionsScreenEvent

    data object OpenRegistrationOptionsPage : IAdditionsScreenEvent

    data object OpenPasswordRecoveryPage : IAdditionsScreenEvent

    data object OpenCurrencyExchangePage : IAdditionsScreenEvent

    data object OpenBankNewsPage : IAdditionsScreenEvent

    data object OpenApplicationInformationPage : IAdditionsScreenEvent

    data object OpenMapPage : IAdditionsScreenEvent

    data object OpenPraisePage : IAdditionsScreenEvent
}