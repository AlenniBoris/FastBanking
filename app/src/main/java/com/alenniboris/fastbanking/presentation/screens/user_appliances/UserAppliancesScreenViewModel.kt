package com.alenniboris.fastbanking.presentation.screens.user_appliances

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alenniboris.fastbanking.domain.model.CustomResultModelDomain
import com.alenniboris.fastbanking.domain.usecase.logic.appliance.IGetAllUserAppliancesUseCase
import com.alenniboris.fastbanking.domain.utils.SingleFlowEvent
import com.alenniboris.fastbanking.presentation.mappers.toUiMessageString
import com.alenniboris.fastbanking.presentation.model.appliance.ProductApplianceModelUi
import com.alenniboris.fastbanking.presentation.model.appliance.toModelUi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class UserAppliancesScreenViewModel(
    private val getAllUserAppliancesUseCase: IGetAllUserAppliancesUseCase
) : ViewModel() {

    private val _screenState = MutableStateFlow(UserAppliancesScreenState())
    val state = _screenState.asStateFlow()

    private val _event = SingleFlowEvent<IUserAppliancesScreenEvent>(viewModelScope)
    val event = _event.flow

    init {
        loadUserAppliances()
    }

    private fun loadUserAppliances() {
        viewModelScope.launch {
            _screenState.update { it.copy(isLoading = true) }

            when (
                val res = getAllUserAppliancesUseCase.invoke()
            ) {
                is CustomResultModelDomain.Success -> {
                    _screenState.update {
                        it.copy(
                            appliances = res.result.map { it.toModelUi() }
                        )
                    }
                }

                is CustomResultModelDomain.Error -> {
                    _event.emit(
                        IUserAppliancesScreenEvent.ShowToastMessage(
                            res.exception.toUiMessageString()
                        )
                    )
                }
            }

            _screenState.update { it.copy(isLoading = false) }
        }
    }

    fun proceedIntent(intent: IUserAppliancesScreenIntent) {
        when (intent) {
            is IUserAppliancesScreenIntent.NavigateBack -> navigateBack()
            is IUserAppliancesScreenIntent.OpenDetailsPage -> openDetailsPage(intent.appliance)
        }
    }

    private fun navigateBack() {
        _event.emit(
            IUserAppliancesScreenEvent.NavigateBack
        )
    }

    private fun openDetailsPage(appliance: ProductApplianceModelUi) {
        _event.emit(
            IUserAppliancesScreenEvent.OpenDetailsPage(
                appliance
            )
        )
    }
}