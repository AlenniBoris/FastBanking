package com.alenniboris.fastbanking.presentation.screens.additions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alenniboris.fastbanking.domain.utils.SingleFlowEvent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class AdditionsScreenViewModel() : ViewModel() {

    private val _screenState = MutableStateFlow(AdditionsScreenState())
    val state = _screenState.asStateFlow()

    private val _event = SingleFlowEvent<IAdditionsScreenEvent>(viewModelScope)
    val event = _event.flow

    fun proceedIntent(intent: IAdditionsScreenIntent) {
        when (intent) {
            is IAdditionsScreenIntent.ProceedAccordingToAction -> proceedAccordingToAction(
                intent.action
            )
        }
    }

    private fun proceedAccordingToAction(action: AdditionsCategoriesAction) {
        _event.emit(
            when (action) {
                AdditionsCategoriesAction.REGISTRATION_IN_SYSTEM ->
                    IAdditionsScreenEvent.OpenRegistrationOptionsPage

                AdditionsCategoriesAction.PASSWORD_RECOVERY ->
                    IAdditionsScreenEvent.OpenPasswordRecoveryPage

                AdditionsCategoriesAction.CURRENCY_EXCHANGER ->
                    IAdditionsScreenEvent.OpenCurrencyExchangePage

                AdditionsCategoriesAction.BANK_NEWS ->
                    IAdditionsScreenEvent.OpenBankNewsPage

                AdditionsCategoriesAction.APP_VERSION ->
                    IAdditionsScreenEvent.OpenApplicationVersionPage

                AdditionsCategoriesAction.ATMS_AND_OFFICES ->
                    IAdditionsScreenEvent.OpenMapPage

                AdditionsCategoriesAction.PRAISE ->
                    IAdditionsScreenEvent.OpenPraisePage
            }
        )
    }
}