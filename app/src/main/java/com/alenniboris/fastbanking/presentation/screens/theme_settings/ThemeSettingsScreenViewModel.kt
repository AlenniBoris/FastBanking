package com.alenniboris.fastbanking.presentation.screens.theme_settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alenniboris.fastbanking.domain.utils.SingleFlowEvent
import com.alenniboris.fastbanking.presentation.uikit.utils.AppTheme
import com.alenniboris.fastbanking.presentation.uikit.utils.currentThemeMode
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ThemeSettingsScreenViewModel : ViewModel() {

    private val _screenState = MutableStateFlow(ThemeSettingsScreenState())
    val state = _screenState.asStateFlow()

    private val _event = SingleFlowEvent<IThemeSettingsScreenEvent>(viewModelScope)
    val event = _event.flow

    init {
        viewModelScope.launch {
            currentThemeMode
                .buffer(onBufferOverflow = BufferOverflow.DROP_OLDEST)
                .distinctUntilChanged()
                .collect { themeMode ->
                    _screenState.update { it.copy(currentTheme = themeMode.theme) }
                }
        }
    }

    fun proceedIntent(intent: IThemeSettingsScreenIntent) {
        when (intent) {
            is IThemeSettingsScreenIntent.NavigateBack -> navigateBack()
            is IThemeSettingsScreenIntent.UpdateAppTheme -> updateAppTheme(intent.newValue)
        }
    }

    private fun navigateBack() {
        _event.emit(IThemeSettingsScreenEvent.NavigateBack)
    }

    private fun updateAppTheme(newValue: AppTheme) {
        _event.emit(IThemeSettingsScreenEvent.UpdateAppTheme(newValue))
    }
}