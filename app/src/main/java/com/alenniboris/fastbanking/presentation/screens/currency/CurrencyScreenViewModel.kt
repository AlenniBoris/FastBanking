package com.alenniboris.fastbanking.presentation.screens.currency

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alenniboris.fastbanking.domain.model.CustomResultModelDomain
import com.alenniboris.fastbanking.domain.model.currency.CurrencyModelDomain
import com.alenniboris.fastbanking.domain.usecase.logic.currency.IGetAllCurrenciesInfoUseCase
import com.alenniboris.fastbanking.domain.usecase.logic.currency.IGetAllExchangeRatesForCurrencyUseCase
import com.alenniboris.fastbanking.domain.usecase.logic.currency.IGetCurrenciesExchangeRateUseCase
import com.alenniboris.fastbanking.domain.utils.SingleFlowEvent
import com.alenniboris.fastbanking.presentation.mappers.toUiMessageString
import com.alenniboris.fastbanking.presentation.model.toModelUi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CurrencyScreenViewModel(
    private val getCurrenciesExchangeRateUseCase: IGetCurrenciesExchangeRateUseCase,
    private val getAllCurrenciesInfoUseCase: IGetAllCurrenciesInfoUseCase,
    private val getAllExchangeRatesForCurrencyUseCase: IGetAllExchangeRatesForCurrencyUseCase
) : ViewModel() {

    private val _screenState = MutableStateFlow(CurrencyScreenState())
    val state = _screenState.asStateFlow()

    private val _event = SingleFlowEvent<ICurrencyScreenEvent>(viewModelScope)
    val event = _event.flow

    init {
        loadAllCurrencies()
        loadAllExchangeRatesForBaseCurrency()
    }

    private fun loadAllExchangeRatesForBaseCurrency() {
        viewModelScope.launch {
            _screenState.update { it.copy(isAllExchangeRatesForBaseCurrencyLoading = true) }
            when (
                val res = getAllExchangeRatesForCurrencyUseCase.invoke(
                    currency = _screenState.value.baseExchangeCurrency
                )
            ) {
                is CustomResultModelDomain.Success -> {
                    _screenState.update {
                        it.copy(
                            currencyExchangeRatesForBaseCurrency = res.result.toModelUi()
                        )
                    }
                }

                is CustomResultModelDomain.Error -> {
                    _event.emit(
                        ICurrencyScreenEvent.ShowToastMessage(
                            res.exception.toUiMessageString()
                        )
                    )
                }
            }
            _screenState.update { it.copy(isAllExchangeRatesForBaseCurrencyLoading = false) }
        }
    }

    private fun loadAllCurrencies() {
        viewModelScope.launch {
            when (
                val result = getAllCurrenciesInfoUseCase.invoke()
            ) {
                is CustomResultModelDomain.Success -> {
                    _screenState.update { it.copy(listOfCurrencies = result.result) }
                }

                is CustomResultModelDomain.Error -> {
                    _event.emit(
                        ICurrencyScreenEvent.ShowToastMessage(
                            result.exception.toUiMessageString()
                        )
                    )
                }
            }
        }
    }

    fun proceedIntent(intent: ICurrencyScreenIntent) {
        when (intent) {
            is ICurrencyScreenIntent.NavigateBack -> navigateBack()
            is ICurrencyScreenIntent.UpdateCurrentScreenMode ->
                updateCurrentScreenMode(intent.newValue)

            is ICurrencyScreenIntent.UpdateFromValue ->
                updateFromValue(intent.newValue)

            is ICurrencyScreenIntent.ChangeCurrenciesPlaces ->
                changeCurrenciesPlaces()

            is ICurrencyScreenIntent.UpdateFromCurrency ->
                updateFromCurrency(intent.newValue)

            is ICurrencyScreenIntent.UpdateToCurrency ->
                updateToCurrency(intent.newValue)

            is ICurrencyScreenIntent.UpdateToCurrencyMenuVisibility ->
                updateToCurrencyMenuVisibility()

            is ICurrencyScreenIntent.UpdateFromCurrencyMenuVisibility ->
                updateFromCurrencyMenuVisibility()
        }
    }

    private fun updateFromCurrencyMenuVisibility() {
        _screenState.update { it.copy(isFromCurrencyMenuVisible = !it.isFromCurrencyMenuVisible) }
    }

    private fun updateToCurrencyMenuVisibility() {
        _screenState.update { it.copy(isToCurrencyMenuVisible = !it.isToCurrencyMenuVisible) }
    }

    private fun updateFromCurrency(newValue: CurrencyModelDomain) {
        _screenState.update { it.copy(fromCurrency = newValue) }
        loadExchangeRate()
    }

    private fun updateToCurrency(newValue: CurrencyModelDomain) {
        _screenState.update { it.copy(toCurrency = newValue) }
        loadExchangeRate()
    }

    private fun changeCurrenciesPlaces() {
        _screenState.update { state ->
            state.copy(
                fromCurrency = state.toCurrency,
                toCurrency = state.fromCurrency
            )
        }
        loadExchangeRate()
    }

    private fun loadExchangeRate() {
        viewModelScope.launch {
            _screenState.update { it.copy(isExchangeRateLoading = true) }

            when (
                val result = getCurrenciesExchangeRateUseCase.invoke(
                    fromCurrency = _screenState.value.fromCurrency,
                    toCurrency = _screenState.value.toCurrency
                )
            ) {
                is CustomResultModelDomain.Success -> {
                    _screenState.update {
                        it.copy(
                            exchangeRate = result.result
                        )
                    }
                    updateToValue()
                }

                is CustomResultModelDomain.Error -> {
                    _event.emit(
                        ICurrencyScreenEvent.ShowToastMessage(
                            result.exception.toUiMessageString()
                        )
                    )
                }
            }

            _screenState.update { it.copy(isExchangeRateLoading = false) }
        }
    }

    private fun updateToValue() {
        _screenState.update {
            it.copy(
                toValueText = (it.fromValueText.toDouble() * it.exchangeRate).toString()
            )
        }
    }

    private fun updateFromValue(newValue: String) {
        _screenState.update {
            it.copy(
                fromValueText = newValue,
                toValueText = (newValue.toDouble() * it.exchangeRate).toString()
            )
        }
    }

    private fun updateCurrentScreenMode(newValue: CurrencyScreenMode) {
        _screenState.update { it.copy(screenMode = newValue) }
    }

    private fun navigateBack() {
        _event.emit(
            ICurrencyScreenEvent.NavigateBack
        )
    }
}