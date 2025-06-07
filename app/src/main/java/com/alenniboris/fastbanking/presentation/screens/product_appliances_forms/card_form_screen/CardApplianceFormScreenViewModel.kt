package com.alenniboris.fastbanking.presentation.screens.product_appliances_forms.card_form_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alenniboris.fastbanking.R
import com.alenniboris.fastbanking.domain.model.CustomResultModelDomain
import com.alenniboris.fastbanking.domain.model.appliances.CardApplianceModelDomain
import com.alenniboris.fastbanking.domain.model.appliances.CardDetailedApplianceType
import com.alenniboris.fastbanking.domain.model.appliances.OfficeModelDomain
import com.alenniboris.fastbanking.domain.model.card.CardSystem
import com.alenniboris.fastbanking.domain.model.card.CardType
import com.alenniboris.fastbanking.domain.model.currency.CurrencyModelDomain
import com.alenniboris.fastbanking.domain.model.map.MapElementType
import com.alenniboris.fastbanking.domain.usecase.logic.appliance.ISendApplianceForCardUseCase
import com.alenniboris.fastbanking.domain.usecase.logic.authorization.IGetCurrentUserUseCase
import com.alenniboris.fastbanking.domain.usecase.logic.currency.IGetAllCurrenciesInfoUseCase
import com.alenniboris.fastbanking.domain.usecase.logic.map.IGetBankLocationsUseCase
import com.alenniboris.fastbanking.domain.utils.SingleFlowEvent
import com.alenniboris.fastbanking.presentation.mappers.toUiMessageString
import com.alenniboris.fastbanking.presentation.model.user.toModelUi
import com.alenniboris.fastbanking.presentation.screens.product_appliances_forms.ProductApplianceFormScreenProcess
import com.alenniboris.fastbanking.presentation.uikit.utils.baseCurrencyMode
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CardApplianceFormScreenViewModel(
    private val detailedApplianceType: CardDetailedApplianceType,
    private val sendApplianceForCardUseCase: ISendApplianceForCardUseCase,
    private val getAllCurrenciesUseCase: IGetAllCurrenciesInfoUseCase,
    private val getBankLocationsUseCase: IGetBankLocationsUseCase,
    private val getCurrentUser: IGetCurrentUserUseCase
) : ViewModel() {

    private val _screenState = MutableStateFlow(
        CardApplianceFormScreenState(
            applianceType = detailedApplianceType
        )
    )
    val state = _screenState.asStateFlow()

    private val _event = SingleFlowEvent<ICardApplianceFormScreenEvent>(viewModelScope)
    val event = _event.flow

    private var _currenciesJob: Job? = null
    private var _bankLocationsJob: Job? = null

    init {
        viewModelScope.launch {
            getCurrentUser.userFlow
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
                        ICardApplianceFormScreenEvent.ShowToastMessage(
                            res.exception.toUiMessageString()
                        )
                    )
                }
            }

            _screenState.update { it.copy(isBankOfficesLoading = false) }
        }
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
                        ICardApplianceFormScreenEvent.ShowToastMessage(
                            res.exception.toUiMessageString()
                        )
                    )
                }
            }

            _screenState.update { it.copy(isAllCurrenciesLoading = false) }
        }
    }

    fun proceedIntent(intent: ICardApplianceFormScreenIntent) {
        when (intent) {
            is ICardApplianceFormScreenIntent.ProceedBackwardAction -> proceedBackwardAction()

            is ICardApplianceFormScreenIntent.ProceedForwardAction -> proceedForwardAction()

            is ICardApplianceFormScreenIntent.UpdateSelectedCurrency ->
                updateSelectedCurrency(intent.currency)

            is ICardApplianceFormScreenIntent.ProceedCardIssuingTypeAction ->
                proceedCardIssuingTypeAction(intent.issueType)

            is ICardApplianceFormScreenIntent.UpdateIsSalaryCard ->
                updateIsSalaryCard(intent.newValue)

            is ICardApplianceFormScreenIntent.UpdateCardTypeSheetVisibility ->
                updateCardTypeSheetVisibility()

            is ICardApplianceFormScreenIntent.UpdateSelectedCardType ->
                updateSelectedCardType(intent.newValue)

            is ICardApplianceFormScreenIntent.UpdateCardSystemSheetVisibility ->
                updateCardSystemSheetVisibility()

            is ICardApplianceFormScreenIntent.UpdateSelectedCardSystem ->
                updateSelectedCardSystem(intent.newValue)

            is ICardApplianceFormScreenIntent.UpdateIsPolicyAccepted ->
                updateIsPolicyAccepted(intent.newValue)

            is ICardApplianceFormScreenIntent.UpdateSelectedOffice ->
                updateSelectedOffice(intent.office)
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
                        ICardApplianceFormScreenEvent.ShowToastMessage(
                            R.string.exception_not_all_data_filled
                        )
                    )

                    return false
                }
            }

            ProductApplianceFormScreenProcess.SELECTING_OFFICE -> {
                if (currentState.selectedBankOffice == null) {
                    _event.emit(
                        ICardApplianceFormScreenEvent.ShowToastMessage(
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

    private fun updateSelectedCardSystem(newValue: CardSystem) {
        _screenState.update { it.copy(selectedCardSystem = newValue) }
    }

    private fun updateCardSystemSheetVisibility() {
        _screenState.update { it.copy(isCardSystemSheetVisible = !it.isCardSystemSheetVisible) }
    }

    private fun updateSelectedCardType(newValue: CardType) {
        _screenState.update { it.copy(selectedCardType = newValue) }
        updateCardTypeSheetVisibility()
    }

    private fun updateCardTypeSheetVisibility() {
        _screenState.update { it.copy(isCardTypeSheetVisible = !it.isCardTypeSheetVisible) }
    }

    private fun updateIsSalaryCard(newValue: Boolean) {
        _screenState.update { it.copy(isSalaryCard = newValue) }
    }

    private fun proceedCardIssuingTypeAction(issueType: CardIssuingType) {
        _screenState.update {
            it.copy(
                isNewAccountNeeded = when (issueType) {
                    CardIssuingType.WITH_NEW_ACCOUNT -> true
                    CardIssuingType.TO_EXISTING_ACCOUNT -> false
                }
            )
        }
    }

    private fun updateSelectedCurrency(currency: CurrencyModelDomain) {
        _screenState.update {
            it.copy(
                selectedCurrency = currency
            )
        }
    }

    private fun navigateBack() {
        _event.emit(ICardApplianceFormScreenEvent.NavigateBack)
    }

    private fun sendAppliance() {
        viewModelScope.launch {
            _screenState.update { it.copy(isApplianceProceeding = true) }

            val currentState = _screenState.value
            val appliance = CardApplianceModelDomain(
                id = "",
                currencyCode = currentState.selectedCurrency?.code
                    ?: baseCurrencyMode.value.currencyCode,
                dateOfAppliance = currentState.dateOfAppliance,
                status = currentState.applianceStatus,
                selectedOffice = currentState.selectedBankOffice ?: OfficeModelDomain(
                    address = "",
                    workingTime = ""
                ),
                userId = currentState.currentUser?.domainModel?.id ?: "",
                detailedCardApplianceType = currentState.applianceType,
                isNewAccountNecessary = currentState.isNewAccountNeeded,
                isSalaryCard = currentState.isSalaryCard,
                isVirtual = currentState.isVirtualCard,
                system = currentState.selectedCardSystem,
                type = currentState.selectedCardType
            )
            val currentProcessPoint = currentState.currentProcess
            val currentProcessIndex = currentState.allProcessParts.indexOf(currentProcessPoint)

            when (
                val res = sendApplianceForCardUseCase.invoke(
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
                        ICardApplianceFormScreenEvent.ShowToastMessage(
                            res.exception.toUiMessageString()
                        )
                    )
                }
            }

            _screenState.update { it.copy(isApplianceProceeding = false) }
        }
    }
}