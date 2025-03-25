package com.alenniboris.fastbanking.presentation.screens.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alenniboris.fastbanking.domain.model.CustomResultModelDomain
import com.alenniboris.fastbanking.domain.usecase.logic.user.ILoginUserIntoBankingUseCase
import com.alenniboris.fastbanking.domain.utils.SingleFlowEvent
import com.alenniboris.fastbanking.presentation.mappers.toUiMessageString
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginScreenViewModel(
    private val loginUserIntoBankingUseCase: ILoginUserIntoBankingUseCase
) : ViewModel() {

    private val _screenState = MutableStateFlow(LoginScreenState())
    val state = _screenState.asStateFlow()

    private val _event = SingleFlowEvent<ILoginScreenEvent>(viewModelScope)
    val event = _event.flow

    fun proceedIntent(intent: ILoginScreenIntent) {
        when (intent) {
            is ILoginScreenIntent.UpdateLogin -> updateLogin(newLogin = intent.newLogin)
            is ILoginScreenIntent.UpdatePassword -> updatePassword(newPassword = intent.newPassword)
            is ILoginScreenIntent.UpdatePasswordVisibility -> updatePasswordVisibility()
            is ILoginScreenIntent.LoginIntoBanking -> loginIntoBanking()
            is ILoginScreenIntent.StartRegistration -> startRegistration()
        }
    }

    private fun startRegistration() {
        _event.emit(ILoginScreenEvent.NavigateToRegistrationOptionsScreen)
    }

    private fun updateLogin(newLogin: String) {
        _screenState.update { it.copy(login = newLogin) }
    }

    private fun updatePassword(newPassword: String) {
        _screenState.update { it.copy(password = newPassword) }
    }

    private fun updatePasswordVisibility() {
        _screenState.update { it.copy(isPasswordVisible = !it.isPasswordVisible) }
    }

    private fun loginIntoBanking() {
        viewModelScope.launch {
            when (
                val res = loginUserIntoBankingUseCase.invoke(
                    login = _screenState.value.login,
                    password = _screenState.value.password
                )
            ) {
                is CustomResultModelDomain.Success -> {}
                is CustomResultModelDomain.Error -> {
                    _event.emit(
                        ILoginScreenEvent.ShowToastMessage(
                            messageId = res.exception.toUiMessageString()
                        )
                    )
                }
            }
        }
    }

}