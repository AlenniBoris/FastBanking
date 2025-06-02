package com.alenniboris.fastbanking.presentation.screens.user_app_account_settings

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

class UserAppAccountSettingsScreenViewModel(
    private val getCurrentUserUseCase: IGetCurrentUserUseCase
) : ViewModel() {

    private val _screenState = MutableStateFlow(UserAppAccountSettingsScreenState())
    val state = _screenState.asStateFlow()

    private val _event = SingleFlowEvent<IUserAppAccountSettingsScreenEvent>(viewModelScope)
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

    fun proceedIntent(intent: IUserAppAccountSettingsScreenIntent) {
        when (intent) {
            is IUserAppAccountSettingsScreenIntent.NavigateBack -> navigateBack()
            is IUserAppAccountSettingsScreenIntent.OpenPasswordResetScreen -> openPasswordResetScreen()
        }
    }

    private fun navigateBack() {
        _event.emit(
            IUserAppAccountSettingsScreenEvent.NavigateBack
        )
    }

    private fun openPasswordResetScreen() {
        _event.emit(
            IUserAppAccountSettingsScreenEvent.OpenPasswordResetScreen
        )
    }
}