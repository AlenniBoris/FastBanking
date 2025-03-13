package com.alenniboris.fastbanking.presentation.screens.register.registration_options

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alenniboris.fastbanking.domain.utils.SingleFlowEvent

class RegistrationOptionsScreenViewModel : ViewModel() {

    private val _event = SingleFlowEvent<IRegistrationOptionsScreenEvent>(viewModelScope)
    val event = _event.flow

    fun proceedIntent(intent: IRegistrationOptionsScreenIntent) {
        when (intent) {
            is IRegistrationOptionsScreenIntent.NavigateToPreviousScreen ->
                navigateToPreviousScreen()

            is IRegistrationOptionsScreenIntent.NavigateToRegistrationAsAppClient ->
                navigateToRegistrationAsAppClient()
        }
    }

    private fun navigateToPreviousScreen() {
        _event.emit(IRegistrationOptionsScreenEvent.NavigateToPreviousScreen)
    }

    private fun navigateToRegistrationAsAppClient() {
        _event.emit(IRegistrationOptionsScreenEvent.NavigateToRegistrationAsAppClient)
    }

}