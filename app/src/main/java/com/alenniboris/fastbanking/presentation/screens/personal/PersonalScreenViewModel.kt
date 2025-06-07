package com.alenniboris.fastbanking.presentation.screens.personal

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alenniboris.fastbanking.domain.usecase.logic.authorization.IGetCurrentUserUseCase
import com.alenniboris.fastbanking.domain.usecase.logic.authorization.ISignOutUseCase
import com.alenniboris.fastbanking.domain.utils.SingleFlowEvent
import com.alenniboris.fastbanking.presentation.model.user.toModelUi
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PersonalScreenViewModel(
    private val getCurrentUserUseCase: IGetCurrentUserUseCase,
    private val signOutUseCase: ISignOutUseCase
) : ViewModel() {

    private val _screenState = MutableStateFlow(PersonalScreenState())
    val state = _screenState.asStateFlow()

    private val _event = SingleFlowEvent<IPersonalScreenEvent>(viewModelScope)
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

    fun proceedIntent(intent: IPersonalScreenIntent) {
        when (intent) {
            is IPersonalScreenIntent.NavigateBack -> navigateBack()
            is IPersonalScreenIntent.SignOut -> signOut()
            is IPersonalScreenIntent.UpdateCurrentlyViewedCategory ->
                updateCurrentlyViewedCategory(intent.newValue)

            is IPersonalScreenIntent.ProceedAccordingToProfileAction ->
                proceedAccordingToProfileAction(intent.action)

            is IPersonalScreenIntent.ProceedAccordingToSettingsAction ->
                proceedAccordingToSettingsAction(intent.action)
        }
    }

    private fun proceedAccordingToSettingsAction(action: SettingsActions) {
        when (action) {
            SettingsActions.APPLICATION_THEME -> openThemeSettingsScreen()
            SettingsActions.APPLICATION_LANGUAGE -> openLanguageSettingsScreen()
            SettingsActions.ACCOUNT_DATA -> openAccountDataScreen()
            SettingsActions.APPLICATION_BASE_CURRENCY -> openBaseCurrencySettingsScreen()
        }
    }

    private fun openBaseCurrencySettingsScreen() {
        _event.emit(
            IPersonalScreenEvent.OpenBaseCurrencySettingsScreen
        )
    }

    private fun openAccountDataScreen() {
        _event.emit(
            IPersonalScreenEvent.OpenAccountDataScreen
        )
    }

    private fun openThemeSettingsScreen() {
        _event.emit(
            IPersonalScreenEvent.OpenThemeSettingsScreen
        )
    }

    private fun openLanguageSettingsScreen() {
        _event.emit(
            IPersonalScreenEvent.OpenLanguageSettingsScreen
        )
    }

    private fun proceedAccordingToProfileAction(action: ProfileActions) {
        when (action) {
            ProfileActions.PERSONAL_DATA -> openPersonalDetailsScreen()
            ProfileActions.APPLIANCES_FOR_PRODUCTS -> openUserProductsAppliancesScreen()
        }
    }

    private fun openPersonalDetailsScreen() {
        _event.emit(
            IPersonalScreenEvent.OpenPersonalDetailsScreen
        )
    }

    private fun openUserProductsAppliancesScreen() {
        _event.emit(
            IPersonalScreenEvent.OpenUserProductsAppliancesScreen
        )
    }

    private fun signOut() {
        signOutUseCase.invoke()
    }

    private fun navigateBack() {
        _event.emit(IPersonalScreenEvent.NavigateBack)
    }

    private fun updateCurrentlyViewedCategory(newValue: PersonalScreenCategories) {
        _screenState.update {
            it.copy(
                currentViewedCategory = newValue
            )
        }
    }
}