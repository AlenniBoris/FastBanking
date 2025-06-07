package com.alenniboris.fastbanking.presentation.screens.base_currency_settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alenniboris.fastbanking.domain.utils.SingleFlowEvent
import com.alenniboris.fastbanking.presentation.uikit.utils.AppBaseCurrency
import com.alenniboris.fastbanking.presentation.uikit.utils.baseCurrencyMode
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class BaseCurrencySettingsScreenViewModel : ViewModel() {

    private val _screenState = MutableStateFlow(BaseCurrencySettingsScreenState())
    val state = _screenState.asStateFlow()

    private val _event = SingleFlowEvent<IBaseCurrencySettingsScreenEvent>(viewModelScope)
    val event = _event.flow

    init {
        viewModelScope.launch {
            baseCurrencyMode
                .buffer(onBufferOverflow = BufferOverflow.DROP_OLDEST)
                .distinctUntilChanged()
                .collect { baseCurrencyMode ->
                    _screenState.update { it.copy(currentBaseCurrency = baseCurrencyMode.baseCurrency) }
                }
        }
    }

    fun proceedIntent(intent: IBaseCurrencySettingsScreenIntent) {
        when (intent) {
            is IBaseCurrencySettingsScreenIntent.NavigateBack -> navigateBack()
            is IBaseCurrencySettingsScreenIntent.UpdateAppBaseCurrency -> updateAppBaseCurrency(
                intent.newValue
            )
        }
    }

    private fun navigateBack() {
        _event.emit(IBaseCurrencySettingsScreenEvent.NavigateBack)
    }

    private fun updateAppBaseCurrency(newValue: AppBaseCurrency) {
        _event.emit(IBaseCurrencySettingsScreenEvent.UpdateAppBaseCurrency(newValue))
    }
}