package com.alenniboris.fastbanking.presentation.screens.language_settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alenniboris.fastbanking.domain.utils.SingleFlowEvent
import com.alenniboris.fastbanking.presentation.uikit.utils.AppLanguage
import com.alenniboris.fastbanking.presentation.uikit.utils.currentLanguageMode
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LanguageSettingsScreenViewModel : ViewModel() {

    private val _screenState = MutableStateFlow(LanguageSettingsScreenState())
    val state = _screenState.asStateFlow()

    private val _event = SingleFlowEvent<ILanguageSettingsScreenEvent>(viewModelScope)
    val event = _event.flow

    init {
        viewModelScope.launch {
            currentLanguageMode
                .buffer(onBufferOverflow = BufferOverflow.DROP_OLDEST)
                .distinctUntilChanged()
                .collect { languageMode ->
                    _screenState.update { it.copy(currentLanguage = languageMode.language) }
                }
        }
    }

    fun proceedIntent(intent: ILanguageSettingsScreenIntent) {
        when (intent) {
            is ILanguageSettingsScreenIntent.NavigateBack -> navigateBack()
            is ILanguageSettingsScreenIntent.UpdateAppLanguage -> updateAppTheme(intent.newValue)
        }
    }

    private fun navigateBack() {
        _event.emit(ILanguageSettingsScreenEvent.NavigateBack)
    }

    private fun updateAppTheme(newValue: AppLanguage) {
        _event.emit(ILanguageSettingsScreenEvent.UpdateAppLanguage(newValue))
    }
}