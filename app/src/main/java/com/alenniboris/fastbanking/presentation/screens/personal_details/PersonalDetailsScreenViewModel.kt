package com.alenniboris.fastbanking.presentation.screens.personal_details

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

class PersonalDetailsScreenViewModel(
    private val getCurrentUserUseCase: IGetCurrentUserUseCase
) : ViewModel() {

    private val _screenState = MutableStateFlow(PersonalDetailsScreenState())
    val state = _screenState.asStateFlow()

    private val _event = SingleFlowEvent<IPersonalDetailsScreenEvent>(viewModelScope)
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

    fun proceedIntent(intent: IPersonalDetailsScreenIntent) {
        when (intent) {
            is IPersonalDetailsScreenIntent.NavigateBack -> navigateBack()
        }
    }

    private fun navigateBack() {
        _event.emit(
            IPersonalDetailsScreenEvent.NavigateBack
        )
    }
}