package com.alenniboris.fastbanking.presentation.screens.activity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alenniboris.fastbanking.domain.usecase.logic.IGetCurrentUserUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainActivityViewModel(
    private val getCurrentUserUseCase: IGetCurrentUserUseCase
) : ViewModel() {

    private val _isUserAuthenticatedFlow = MutableStateFlow(
        getCurrentUserUseCase.userFlow.value != null
    )
    val isUserAuthenticatedFlow = _isUserAuthenticatedFlow.asStateFlow()

    init {
        viewModelScope.launch {
            getCurrentUserUseCase.userFlow.collect { user ->
                _isUserAuthenticatedFlow.update { user != null }
            }
        }
    }

}