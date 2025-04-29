package com.alenniboris.fastbanking.presentation.screens.activity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alenniboris.fastbanking.domain.usecase.logic.authorization.IGetCurrentUserUseCase
import com.alenniboris.fastbanking.domain.usecase.logic.authorization.ISignOutUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainActivityViewModel(
    private val getCurrentUserUseCase: IGetCurrentUserUseCase,
    private val signOutUseCase: ISignOutUseCase
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

    fun signOut() {
        signOutUseCase.invoke()
    }
}