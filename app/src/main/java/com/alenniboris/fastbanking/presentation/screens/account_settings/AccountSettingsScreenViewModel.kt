package com.alenniboris.fastbanking.presentation.screens.account_settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alenniboris.fastbanking.domain.usecase.logic.authorization.IGetCurrentUserUseCase
import com.alenniboris.fastbanking.domain.utils.SingleFlowEvent
import com.alenniboris.fastbanking.presentation.model.user.toModelUi
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AccountSettingsScreenViewModel(
    private val getCurrentUserUseCase: IGetCurrentUserUseCase
) : ViewModel() {

    private val _screenState = MutableStateFlow(AccountSettingsScreenState())
    val state = _screenState.asStateFlow()

    private val _event = SingleFlowEvent<IAccountSettingsScreenEvent>(viewModelScope)
    val event = _event.flow

    init {
        viewModelScope.launch {
            getCurrentUserUseCase.userFlow
                .buffer(onBufferOverflow = BufferOverflow.DROP_OLDEST)
                .distinctUntilChanged()
                .collect { user ->
                    _screenState.update { it.copy(user = user?.toModelUi()) }
                }
        }
    }

    fun proceedIntent(intent: IAccountSettingsScreenIntent) {
        when (intent) {
            is IAccountSettingsScreenIntent.NavigateBack -> navigateBack()
            is IAccountSettingsScreenIntent.OpenPasswordResetScreen -> openPasswordResetScreen()
        }
    }

    private fun navigateBack() {
        _event.emit(
            IAccountSettingsScreenEvent.NavigateBack
        )
    }

    private fun openPasswordResetScreen() {
        _event.emit(
            IAccountSettingsScreenEvent.OpenPasswordResetScreen
        )
    }
}