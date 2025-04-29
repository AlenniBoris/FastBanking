package com.alenniboris.fastbanking.presentation.screens.product_appliances_forms.credit_form_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alenniboris.fastbanking.R
import com.alenniboris.fastbanking.domain.model.CustomResultModelDomain
import com.alenniboris.fastbanking.domain.model.appliances.CreditApplianceModelDomain
import com.alenniboris.fastbanking.domain.model.appliances.CreditDetailedApplianceType
import com.alenniboris.fastbanking.domain.model.appliances.OfficeModelDomain
import com.alenniboris.fastbanking.domain.model.currency.CurrencyModelDomain
import com.alenniboris.fastbanking.domain.model.map.MapElementType
import com.alenniboris.fastbanking.domain.usecase.logic.appliance.ISendApplianceForCreditUseCase
import com.alenniboris.fastbanking.domain.usecase.logic.authorization.IGetCurrentUserUseCase
import com.alenniboris.fastbanking.domain.usecase.logic.currency.IGetAllCurrenciesInfoUseCase
import com.alenniboris.fastbanking.domain.usecase.logic.map.IGetBankLocationsUseCase
import com.alenniboris.fastbanking.domain.utils.SingleFlowEvent
import com.alenniboris.fastbanking.presentation.mappers.toUiMessageString
import com.alenniboris.fastbanking.presentation.model.user.toModelUi
import com.alenniboris.fastbanking.presentation.screens.product_appliances_forms.ProductApplianceFormScreenProcess
import com.alenniboris.fastbanking.presentation.uikit.utils.baseCurrencyFlow
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CreditApplianceFormScreenViewModel(
    private val detailedApplianceType: CreditDetailedApplianceType,
    private val getCurrentUserUseCase: IGetCurrentUserUseCase,
    private val getAllCurrenciesUseCase: IGetAllCurrenciesInfoUseCase,
    private val getBankLocationsUseCase: IGetBankLocationsUseCase,
    private val sendApplianceForCreditUseCase: ISendApplianceForCreditUseCase
) : ViewModel() {

    private val _screenState = MutableStateFlow(
        CreditApplianceFormScreenState(
            applianceType = detailedApplianceType
        )
    )
    val state = _screenState.asStateFlow()

    private val _event = SingleFlowEvent<ICreditApplianceFormScreenEvent>(viewModelScope)
    val event = _event.flow

    private var _currenciesJob: Job? = null
    private var _bankLocationsJob: Job? = null

    init {
        viewModelScope.launch {
            getCurrentUserUseCase.userFlow
                .buffer(onBufferOverflow = BufferOverflow.DROP_OLDEST)
                .distinctUntilChanged()
                .collect { user ->
                    _screenState.update { it.copy(currentUser = user?.toModelUi()) }
                }
        }
    }

    init {
        loadSupportedCurrencies()
        loadBankLocations()
    }

    private fun loadSupportedCurrencies() {
        _currenciesJob?.cancel()
        _currenciesJob = viewModelScope.launch {
            _screenState.update { it.copy(isAllCurrenciesLoading = true) }

            when (
                val res = getAllCurrenciesUseCase.invoke()
            ) {
                is CustomResultModelDomain.Success -> {
                    _screenState.update {
                        it.copy(
                            supportedCurrencies = res.result
                        )
                    }
                }

                is CustomResultModelDomain.Error -> {
                    _event.emit(
                        ICreditApplianceFormScreenEvent.ShowToastMessage(
                            res.exception.toUiMessageString()
                        )
                    )
                }
            }

            _screenState.update { it.copy(isAllCurrenciesLoading = false) }
        }
    }

    private fun loadBankLocations() {
        _bankLocationsJob?.cancel()
        _bankLocationsJob = viewModelScope.launch {
            _screenState.update { it.copy(isBankOfficesLoading = true) }

            when (
                val res = getBankLocationsUseCase.invoke()
            ) {
                is CustomResultModelDomain.Success -> {
                    val mappedResult = res.result
                        .filter { it.type == MapElementType.OFFICE }
                        .map {
                            OfficeModelDomain(
                                address = it.address,
                                workingTime = it.workingTime
                            )
                        }
                    _screenState.update {
                        it.copy(allBankOffices = mappedResult)
                    }
                }

                is CustomResultModelDomain.Error -> {
                    _event.emit(
                        ICreditApplianceFormScreenEvent.ShowToastMessage(
                            res.exception.toUiMessageString()
                        )
                    )
                }
            }

            _screenState.update { it.copy(isBankOfficesLoading = false) }
        }
    }

    fun proceedIntent(intent: ICreditApplianceFormScreenIntent) {
        when (intent) {
            is ICreditApplianceFormScreenIntent.ProceedBackwardAction ->
                proceedBackwardAction()

            is ICreditApplianceFormScreenIntent.ProceedForwardAction ->
                proceedForwardAction()

            is ICreditApplianceFormScreenIntent.UpdateSelectedOffice ->
                updateSelectedOffice(intent.office)

            is ICreditApplianceFormScreenIntent.UpdateIsPolicyAccepted ->
                updateIsPolicyAccepted(intent.newValue)

            is ICreditApplianceFormScreenIntent.UpdateSelectedCurrency ->
                updateSelectedCurrency(intent.currency)

            is ICreditApplianceFormScreenIntent.UpdateCreditGoal ->
                updateCreditGoal(intent.newValue)
        }
    }

    private fun updateCreditGoal(newValue: String) {
        _screenState.update { it.copy(creditGoal = newValue) }
    }

    private fun updateSelectedCurrency(currency: CurrencyModelDomain) {
        _screenState.update {
            it.copy(
                selectedCurrency = currency
            )
        }
    }

    private fun updateSelectedOffice(office: OfficeModelDomain) {
        _screenState.update { it.copy(selectedBankOffice = office) }
    }

    private fun updateIsPolicyAccepted(newValue: Boolean) {
        _screenState.update {
            it.copy(isPolicyAccepted = newValue)
        }
    }

    private fun proceedBackwardAction() {
        val currentState = _screenState.value
        val currentProcessPoint = currentState.currentProcess
        val currentProcessIndex = currentState.allProcessParts.indexOf(currentProcessPoint)

        when (currentProcessPoint) {
            ProductApplianceFormScreenProcess.FILLING_DATA -> {
                navigateBack()
            }

            ProductApplianceFormScreenProcess.SELECTING_OFFICE -> {
                _screenState.update {
                    it.copy(
                        currentProcess = currentState.allProcessParts[currentProcessIndex - 1],
                        selectedBankOffice = null
                    )
                }
            }

            ProductApplianceFormScreenProcess.SUCCEEDED -> {
                _screenState.update {
                    it.copy(
                        currentProcess = currentState.allProcessParts[currentProcessIndex - 1],
                    )
                }
            }
        }
    }

    private fun proceedForwardAction() {
        if (!checkIfForwardActionCanBeDone()) return

        val currentState = _screenState.value
        val currentProcessPoint = currentState.currentProcess
        val currentProcessIndex = currentState.allProcessParts.indexOf(currentProcessPoint)

        when (currentProcessPoint) {
            ProductApplianceFormScreenProcess.FILLING_DATA -> {
                _screenState.update {
                    it.copy(
                        currentProcess = currentState.allProcessParts[currentProcessIndex + 1]
                    )
                }
            }

            ProductApplianceFormScreenProcess.SELECTING_OFFICE -> {
                sendAppliance()
            }

            ProductApplianceFormScreenProcess.SUCCEEDED -> {
                navigateBack()
            }
        }
    }

    private fun sendAppliance() {
        viewModelScope.launch {
            _screenState.update { it.copy(isApplianceProceeding = true) }

            val currentState = _screenState.value
            val appliance = CreditApplianceModelDomain(
                id = "",
                currencyCode = currentState.selectedCurrency?.code
                    ?: baseCurrencyFlow.value.currencyCode,
                dateOfAppliance = currentState.dateOfAppliance,
                status = currentState.applianceStatus,
                selectedOffice = currentState.selectedBankOffice ?: OfficeModelDomain(
                    address = "",
                    workingTime = ""
                ),
                userId = currentState.currentUser?.domainModel?.id ?: "",
                detailedCreditApplianceType = currentState.applianceType,
                creditGoal = currentState.creditGoal
            )
            val currentProcessPoint = currentState.currentProcess
            val currentProcessIndex = currentState.allProcessParts.indexOf(currentProcessPoint)

            when (
                val res = sendApplianceForCreditUseCase.invoke(
                    appliance = appliance
                )
            ) {
                is CustomResultModelDomain.Success -> {
                    _screenState.update {
                        it.copy(
                            currentProcess = currentState.allProcessParts[currentProcessIndex + 1]
                        )
                    }
                }

                is CustomResultModelDomain.Error -> {
                    _event.emit(
                        ICreditApplianceFormScreenEvent.ShowToastMessage(
                            res.exception.toUiMessageString()
                        )
                    )
                }
            }

            _screenState.update { it.copy(isApplianceProceeding = false) }
        }
    }

    private fun navigateBack() {
        _event.emit(
            ICreditApplianceFormScreenEvent.NavigateBack
        )
    }

    private fun checkIfForwardActionCanBeDone(): Boolean {
        val currentState = _screenState.value
        val currentProcessPoint = currentState.currentProcess
        when (currentProcessPoint) {
            ProductApplianceFormScreenProcess.FILLING_DATA -> {
                if (
                    currentState.selectedCurrency == null ||
                    !currentState.isPolicyAccepted
                ) {
                    _event.emit(
                        ICreditApplianceFormScreenEvent.ShowToastMessage(
                            R.string.exception_not_all_data_filled
                        )
                    )

                    return false
                }
            }

            ProductApplianceFormScreenProcess.SELECTING_OFFICE -> {
                if (currentState.selectedBankOffice == null) {
                    _event.emit(
                        ICreditApplianceFormScreenEvent.ShowToastMessage(
                            R.string.exception_not_all_data_filled
                        )
                    )
                    return false
                }
            }

            ProductApplianceFormScreenProcess.SUCCEEDED -> {}
        }

        return true
    }
}