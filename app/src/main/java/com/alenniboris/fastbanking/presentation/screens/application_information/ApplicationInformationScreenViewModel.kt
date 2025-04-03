package com.alenniboris.fastbanking.presentation.screens.application_information

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alenniboris.fastbanking.domain.model.CustomResultModelDomain
import com.alenniboris.fastbanking.domain.usecase.logic.bank_info.IGetApplicationInfoUseCase
import com.alenniboris.fastbanking.domain.utils.SingleFlowEvent
import com.alenniboris.fastbanking.presentation.mappers.toUiMessageString
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ApplicationInformationScreenViewModel(
    private val getApplicationInfoUseCase: IGetApplicationInfoUseCase
) : ViewModel() {

    private val _screenState = MutableStateFlow(ApplicationInformationScreenState())
    val state = _screenState.asStateFlow()

    private val _event = SingleFlowEvent<IApplicationInformationScreenEvent>(viewModelScope)
    val event = _event.flow

    init {
        loadAppData()
    }

    private fun loadAppData() {
        viewModelScope.launch {
            _screenState.update { it.copy(isLoading = true) }
            when (
                val res = getApplicationInfoUseCase.invoke()
            ) {
                is CustomResultModelDomain.Success -> {
                    _screenState.update { it.copy(appInfo = res.result) }
                }

                is CustomResultModelDomain.Error -> {
                    _event.emit(
                        IApplicationInformationScreenEvent.ShowToastMessage(
                            res.exception.toUiMessageString()
                        )
                    )
                }
            }
            _screenState.update { it.copy(isLoading = false) }
        }
    }

    fun proceedIntent(intent: IApplicationInformationScreenIntent) {
        when (intent) {
            is IApplicationInformationScreenIntent.NavigateBack -> navigateBack()
        }
    }

    private fun navigateBack() {
        _event.emit(
            IApplicationInformationScreenEvent.NavigateBack
        )
    }
}