package com.alenniboris.fastbanking.presentation.screens.product_appliance_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alenniboris.fastbanking.R
import com.alenniboris.fastbanking.domain.model.CustomResultModelDomain
import com.alenniboris.fastbanking.domain.usecase.logic.appliance.IGetCardApplianceByIdUseCase
import com.alenniboris.fastbanking.domain.usecase.logic.appliance.IGetCreditApplianceByIdUseCase
import com.alenniboris.fastbanking.domain.usecase.logic.appliance.IGetDepositApplianceByIdUseCase
import com.alenniboris.fastbanking.domain.utils.SingleFlowEvent
import com.alenniboris.fastbanking.presentation.mappers.toUiMessageString
import com.alenniboris.fastbanking.presentation.model.appliance.ProductApplianceType
import com.alenniboris.fastbanking.presentation.model.appliance.toModelUi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProductApplianceDetailsScreenViewModel(
    private val applianceId: String,
    private val applianceType: ProductApplianceType,
    private val getCardApplianceByIdUseCase: IGetCardApplianceByIdUseCase,
    private val getCreditApplianceByIdUseCase: IGetCreditApplianceByIdUseCase,
    private val getDepositApplianceByIdUseCase: IGetDepositApplianceByIdUseCase
) : ViewModel() {

    private val _screenState = MutableStateFlow(ProductApplianceDetailsScreenState())
    val state = _screenState.asStateFlow()

    private val _event = SingleFlowEvent<IProductApplianceDetailsScreenEvent>(viewModelScope)
    val event = _event.flow

    init {
        when (applianceType) {
            ProductApplianceType.Card -> loadCardAppliance()
            ProductApplianceType.Credit -> loadCreditAppliance()
            ProductApplianceType.Deposit -> loadDepositAppliance()
            ProductApplianceType.Undefined -> {
                _event.emit(
                    IProductApplianceDetailsScreenEvent.ShowMessage(
                        R.string.some_error_happened_text
                    )
                )
            }
        }
    }

    private fun loadCardAppliance() {
        viewModelScope.launch {
            _screenState.update { it.copy(isLoading = true) }

            when (
                val res = getCardApplianceByIdUseCase.invoke(id = applianceId)
            ) {
                is CustomResultModelDomain.Success -> {
                    _screenState.update {
                        it.copy(
                            appliance = res.result?.toModelUi()
                        )
                    }
                }

                is CustomResultModelDomain.Error -> {
                    _event.emit(
                        IProductApplianceDetailsScreenEvent.ShowMessage(
                            res.exception.toUiMessageString()
                        )
                    )
                }
            }

            _screenState.update { it.copy(isLoading = false) }
        }
    }

    private fun loadCreditAppliance() {
        viewModelScope.launch {
            _screenState.update { it.copy(isLoading = true) }

            when (
                val res = getCreditApplianceByIdUseCase.invoke(id = applianceId)
            ) {
                is CustomResultModelDomain.Success -> {
                    _screenState.update {
                        it.copy(
                            appliance = res.result?.toModelUi()
                        )
                    }
                }

                is CustomResultModelDomain.Error -> {
                    _event.emit(
                        IProductApplianceDetailsScreenEvent.ShowMessage(
                            res.exception.toUiMessageString()
                        )
                    )
                }
            }

            _screenState.update { it.copy(isLoading = false) }
        }
    }

    private fun loadDepositAppliance() {
        viewModelScope.launch {
            _screenState.update { it.copy(isLoading = true) }

            when (
                val res = getDepositApplianceByIdUseCase.invoke(id = applianceId)
            ) {
                is CustomResultModelDomain.Success -> {
                    _screenState.update {
                        it.copy(
                            appliance = res.result?.toModelUi()
                        )
                    }
                }

                is CustomResultModelDomain.Error -> {
                    _event.emit(
                        IProductApplianceDetailsScreenEvent.ShowMessage(
                            res.exception.toUiMessageString()
                        )
                    )
                }
            }

            _screenState.update { it.copy(isLoading = false) }
        }
    }

    fun proceedIntent(intent: IProductApplianceDetailsScreenEventIntent) {
        when (intent) {
            is IProductApplianceDetailsScreenEventIntent.NavigateBack -> navigateBack()
        }
    }

    private fun navigateBack() {
        _event.emit(IProductApplianceDetailsScreenEvent.NavigateBack)
    }
}