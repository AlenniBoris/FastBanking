package com.alenniboris.fastbanking.presentation.screens.product_appliances_forms.deposit_form_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alenniboris.fastbanking.R
import com.alenniboris.fastbanking.domain.model.CustomResultModelDomain
import com.alenniboris.fastbanking.domain.model.appliances.DepositApplianceModelDomain
import com.alenniboris.fastbanking.domain.model.appliances.DepositDetailedApplianceType
import com.alenniboris.fastbanking.domain.model.appliances.OfficeModelDomain
import com.alenniboris.fastbanking.domain.model.currency.CurrencyModelDomain
import com.alenniboris.fastbanking.domain.model.map.MapElementType
import com.alenniboris.fastbanking.domain.usecase.logic.appliance.ISendApplianceForDepositUseCase
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

class DepositApplianceFormScreenViewModel(
    private val detailedApplianceType: DepositDetailedApplianceType,
    private val getCurrentUserUseCase: IGetCurrentUserUseCase,
    private val getAllCurrenciesUseCase: IGetAllCurrenciesInfoUseCase,
    private val getBankLocationsUseCase: IGetBankLocationsUseCase,
    private val sendApplianceForDepositUseCase: ISendApplianceForDepositUseCase
) : ViewModel() {

    private val _screenState = MutableStateFlow(
        DepositApplianceFormScreenState(
            applianceType = detailedApplianceType
        )
    )
    val state = _screenState.asStateFlow()

    private val _event = SingleFlowEvent<IDepositApplianceFormScreenEvent>(viewModelScope)
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
                        IDepositApplianceFormScreenEvent.ShowToastMessage(
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
                        IDepositApplianceFormScreenEvent.ShowToastMessage(
                            res.exception.toUiMessageString()
                        )
                    )
                }
            }

            _screenState.update { it.copy(isBankOfficesLoading = false) }
        }
    }

    fun proceedIntent(intent: IDepositApplianceFormScreenIntent) {
        when (intent) {
            is IDepositApplianceFormScreenIntent.ProceedBackwardAction ->
                proceedBackwardAction()

            is IDepositApplianceFormScreenIntent.ProceedForwardAction ->
                proceedForwardAction()

            is IDepositApplianceFormScreenIntent.UpdateSelectedCurrency ->
                updateSelectedCurrency(intent.currency)

            is IDepositApplianceFormScreenIntent.UpdateSelectedOffice ->
                updateSelectedOffice(intent.office)

            is IDepositApplianceFormScreenIntent.UpdateMinimumContribution ->
                updateMinimumContribution(intent.newValue)

            is IDepositApplianceFormScreenIntent.UpdateDepositProcent ->
                updateDepositProcent(intent.newValue)

            is IDepositApplianceFormScreenIntent.UpdateSelectedPeriod ->
                updateSelectedPeriod(intent.newValue)

            is IDepositApplianceFormScreenIntent.UpdateIsPolicyAccepted ->
                updateIsPolicyAccepted(intent.newValue)

            is IDepositApplianceFormScreenIntent.UpdatePeriodSheetVisibility ->
                updatePeriodSheetVisibility()
        }
    }

    private fun updatePeriodSheetVisibility() {
        _screenState.update { it.copy(isPeriodsSheetVisible = !it.isPeriodsSheetVisible) }
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
            val appliance = DepositApplianceModelDomain(
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
                detailedDepositApplianceType = currentState.applianceType,
                minimumContribution = currentState.minimumContributionValue,
                period = currentState.periodTime,
                procent = currentState.procentValue
            )
            val currentProcessPoint = currentState.currentProcess
            val currentProcessIndex = currentState.allProcessParts.indexOf(currentProcessPoint)

            when (
                val res = sendApplianceForDepositUseCase.invoke(
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
                        IDepositApplianceFormScreenEvent.ShowToastMessage(
                            res.exception.toUiMessageString()
                        )
                    )
                }
            }

            _screenState.update { it.copy(isApplianceProceeding = false) }
        }
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
                        IDepositApplianceFormScreenEvent.ShowToastMessage(
                            R.string.exception_not_all_data_filled
                        )
                    )

                    return false
                }
            }

            ProductApplianceFormScreenProcess.SELECTING_OFFICE -> {
                if (currentState.selectedBankOffice == null) {
                    _event.emit(
                        IDepositApplianceFormScreenEvent.ShowToastMessage(
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

    private fun navigateBack() {
        _event.emit(
            IDepositApplianceFormScreenEvent.NavigateBack
        )
    }

    private fun updateMinimumContribution(newValue: String) {
        _screenState.update { it.copy(minimumContribution = newValue) }
    }

    private fun updateDepositProcent(newValue: String) {
        _screenState.update { it.copy(procentText = newValue) }
    }

    private fun updateSelectedPeriod(newValue: Int) {
        _screenState.update { it.copy(periodTime = newValue) }
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
}