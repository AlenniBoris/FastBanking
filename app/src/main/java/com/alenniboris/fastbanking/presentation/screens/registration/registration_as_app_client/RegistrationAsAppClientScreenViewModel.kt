package com.alenniboris.fastbanking.presentation.screens.registration.registration_as_app_client

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alenniboris.fastbanking.R
import com.alenniboris.fastbanking.domain.model.CustomResultModelDomain
import com.alenniboris.fastbanking.domain.model.exception.AuthenticationExceptionModelDomain
import com.alenniboris.fastbanking.domain.usecase.logic.user.ICheckVerificationCodeUseCase
import com.alenniboris.fastbanking.domain.usecase.logic.user.IRegisterUserIntoBankingUseCase
import com.alenniboris.fastbanking.domain.usecase.logic.user.ISendVerificationCodeUseCase
import com.alenniboris.fastbanking.domain.utils.SingleFlowEvent
import com.alenniboris.fastbanking.presentation.mappers.toUiMessageString
import com.alenniboris.fastbanking.presentation.screens.registration.registration_as_app_client.state.RegistrationAsAppClientScreenState
import com.alenniboris.fastbanking.presentation.screens.registration.registration_as_app_client.state.values.RegistrationAsAppClientProcessPart
import com.alenniboris.fastbanking.presentation.uikit.functions.CommonFunctions
import com.alenniboris.fastbanking.presentation.uikit.values.RegistrationDocumentType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RegistrationAsAppClientScreenViewModel(
    private val sendVerificationCodeForRegistrationToNumberUseCase: ISendVerificationCodeUseCase,
    private val checkVerificationCodeForRegistrationUseCase: ICheckVerificationCodeUseCase,
    private val registerUserIntoBankingUseCase: IRegisterUserIntoBankingUseCase
) : ViewModel() {

    private val _screenState = MutableStateFlow(RegistrationAsAppClientScreenState())
    val state = _screenState.asStateFlow()

    private val _event = SingleFlowEvent<IRegistrationAsAppClientScreenEvent>(viewModelScope)
    val event = _event.flow

    fun proceedIntent(intent: IRegistrationAsAppClientScreenIntent) {
        when (intent) {
            is IRegistrationAsAppClientScreenIntent.DoForwardAction ->
                doForwardAction()

            is IRegistrationAsAppClientScreenIntent.DoBackwardAction ->
                doBackwardAction()

            is IRegistrationAsAppClientScreenIntent.UpdateRegistrationDocumentType ->
                updateRegistrationDocumentType(intent.type)

            is IRegistrationAsAppClientScreenIntent.UpdateIdentificationNumber ->
                updateIdentificationNumber(intent.number)

            is IRegistrationAsAppClientScreenIntent.UpdateOptionsBottomSheetVisibility ->
                updateOptionsBottomSheetVisibility()

            is IRegistrationAsAppClientScreenIntent.UpdateDataInputField ->
                updateDataInputField(
                    documentType = intent.documentType,
                    newValue = intent.newValue
                )

            is IRegistrationAsAppClientScreenIntent.UpdatePhoneNumber ->
                updatePhoneNumber(intent.newValue)

            is IRegistrationAsAppClientScreenIntent.UpdatePhoneCode ->
                updatePhoneCode(intent.newValue)

            is IRegistrationAsAppClientScreenIntent.UpdatePassword ->
                updatePassword(intent.newValue)

            is IRegistrationAsAppClientScreenIntent.UpdatePasswordCheck ->
                updatePasswordCheck(intent.newValue)

            IRegistrationAsAppClientScreenIntent.UpdatePasswordCheckVisibility ->
                updatePasswordCheckVisibility()

            IRegistrationAsAppClientScreenIntent.UpdatePasswordVisibility ->
                updatePasswordVisibility()
        }
    }

    private fun updatePasswordVisibility() {
        _screenState.update { state ->
            state.copy(
                settingPasswordPartState = state.settingPasswordPartState.copy(
                    isPasswordVisible = !state.settingPasswordPartState.isPasswordVisible
                )
            )
        }
    }

    private fun updatePasswordCheckVisibility() {
        _screenState.update { state ->
            state.copy(
                settingPasswordPartState = state.settingPasswordPartState.copy(
                    isPasswordCheckVisible = !state.settingPasswordPartState.isPasswordCheckVisible
                )
            )
        }
    }

    private fun updatePasswordCheck(newValue: String) {
        _screenState.update { state ->
            state.copy(
                settingPasswordPartState = state.settingPasswordPartState.copy(
                    passwordCheck = newValue
                )
            )
        }
    }

    private fun updatePassword(newValue: String) {
        _screenState.update { state ->
            state.copy(
                settingPasswordPartState = state.settingPasswordPartState.copy(
                    password = newValue
                )
            )
        }
    }

    private fun updatePhoneCode(newValue: String) {
        _screenState.update { state ->
            state.copy(
                phoneCodeCheckPartState = state.phoneCodeCheckPartState.copy(
                    enteredCode = newValue
                )
            )
        }
    }

    private fun sendVerificationCodeAndMoveToNextPart() {
        if (checkIfProcessPartCanBeUpdated(_screenState.value.currentProcessPart)) {
            sendVerificationCodeForRegistrationToNumberUseCase.invoke()
            moveToNextProcessPart()
        }
    }

    private fun updatePhoneNumber(newValue: String) {
        _screenState.update { state ->
            state.copy(
                phoneNumberInputPartState = state.phoneNumberInputPartState.copy(
                    phoneNumber = newValue
                )
            )
        }
    }

    private fun updateDataInputField(documentType: RegistrationDocumentType, newValue: String) {
        _screenState.update { state ->
            when (documentType) {
                RegistrationDocumentType.Passport -> {
                    state.copy(
                        dataInputPartState = state.dataInputPartState.copy(
                            identificationNumber = newValue
                        )
                    )
                }
            }
        }
    }

    private fun updateOptionsBottomSheetVisibility() {
        _screenState.update { state ->
            state.copy(
                isOptionsBottomSheetVisible = !state.isOptionsBottomSheetVisible
            )
        }
    }

    private fun updateRegistrationDocumentType(type: RegistrationDocumentType) {
        _screenState.update { state ->
            state.copy(
                dataInputPartState = state.dataInputPartState.copy(
                    registrationDocumentType = type
                )
            )
        }
    }

    private fun updateIdentificationNumber(number: String) {
        _screenState.update { state ->
            state.copy(
                dataInputPartState = state.dataInputPartState.copy(
                    identificationNumber = number
                )
            )
        }
    }

    private fun doBackwardAction() {
        when (_screenState.value.currentProcessPart) {
            RegistrationAsAppClientProcessPart.PhoneNumberInput,
            RegistrationAsAppClientProcessPart.PhoneCodeCheck,
            RegistrationAsAppClientProcessPart.SettingPassword,
            RegistrationAsAppClientProcessPart.ConfirmPage -> {
                moveToPreviousProcessPart()
            }

            RegistrationAsAppClientProcessPart.DataInput -> {
                _event.emit(
                    IRegistrationAsAppClientScreenEvent.NavigateToPreviousScreen
                )
            }
        }
    }

    private fun moveToPreviousProcessPart() {
        val currentProcessPart = _screenState.value.currentProcessPart

        clearCurrentPartState(processPart = currentProcessPart)

        val currentPartIndex = _screenState.value.processPartList
            .indexOf(
                element = currentProcessPart
            )

        val newProcessPart = _screenState.value.processPartList[currentPartIndex - 1]

        _screenState.update {
            it.copy(
                currentProcessPart = newProcessPart
            )
        }
    }

    private fun clearCurrentPartState(processPart: RegistrationAsAppClientProcessPart) {
        when (processPart) {
            RegistrationAsAppClientProcessPart.DataInput -> {}

            RegistrationAsAppClientProcessPart.PhoneNumberInput -> {
                _screenState.update { state ->
                    state.copy(
                        phoneNumberInputPartState = state.phoneNumberInputPartState.copy(
                            phoneNumber = ""
                        )
                    )
                }
            }

            RegistrationAsAppClientProcessPart.PhoneCodeCheck -> {
                _screenState.update { state ->
                    state.copy(
                        phoneCodeCheckPartState = state.phoneCodeCheckPartState.copy(
                            enteredCode = ""
                        )
                    )
                }
            }

            RegistrationAsAppClientProcessPart.SettingPassword -> {
                _screenState.update { state ->
                    state.copy(
                        settingPasswordPartState = state.settingPasswordPartState.copy(
                            password = "",
                            passwordCheck = ""
                        )
                    )
                }
            }

            RegistrationAsAppClientProcessPart.ConfirmPage -> {}
        }
    }

    private fun checkVerificationCodeAndMoveToNextPart() {
        when (
            val result = checkVerificationCodeForRegistrationUseCase.invoke(
                code = _screenState.value.phoneCodeCheckPartState.enteredCode
            )
        ) {
            is CustomResultModelDomain.Success -> {
                moveToNextProcessPart()
            }

            is CustomResultModelDomain.Error -> {
                _event.emit(
                    IRegistrationAsAppClientScreenEvent.ShowToastMessage(
                        result.exception.toUiMessageString()
                    )
                )
            }
        }
    }

    private fun doForwardAction() {
        when (_screenState.value.currentProcessPart) {
            RegistrationAsAppClientProcessPart.DataInput,
            RegistrationAsAppClientProcessPart.SettingPassword -> {
                moveToNextProcessPart()
            }

            RegistrationAsAppClientProcessPart.PhoneCodeCheck -> {
                checkVerificationCodeAndMoveToNextPart()
            }

            RegistrationAsAppClientProcessPart.PhoneNumberInput -> {
                sendVerificationCodeAndMoveToNextPart()
            }

            RegistrationAsAppClientProcessPart.ConfirmPage -> {
                registerUserAndMoveToLogin()
            }
        }
    }

    private fun registerUserAndMoveToLogin() {
        viewModelScope.launch {
            _screenState.update { it.copy(isLoading = true) }
            when (
                val result = registerUserIntoBankingUseCase.invoke(
                    login = when (_screenState.value.dataInputPartState.registrationDocumentType) {
                        RegistrationDocumentType.Passport -> _screenState.value.dataInputPartState.identificationNumber
                    },
                    password = _screenState.value.settingPasswordPartState.password
                )
            ) {
                is CustomResultModelDomain.Success -> {
                    _event.emit(
                        IRegistrationAsAppClientScreenEvent.ShowToastMessage(
                            R.string.welcome_to_app_text
                        )
                    )
                }

                is CustomResultModelDomain.Error -> {
                    _event.emit(
                        IRegistrationAsAppClientScreenEvent.ShowToastMessage(
                            result.exception.toUiMessageString()
                        )
                    )
                }
            }
            _screenState.update { it.copy(isLoading = false) }
        }
    }

    private fun moveToNextProcessPart() {

        val currentProcessPart = _screenState.value.currentProcessPart

        if (!checkIfProcessPartCanBeUpdated(processPart = currentProcessPart)) return

        val currentPartIndex = _screenState.value.processPartList
            .indexOf(
                element = currentProcessPart
            )

        val newProcessPart = _screenState.value.processPartList[currentPartIndex + 1]

        _screenState.update {
            it.copy(
                currentProcessPart = newProcessPart
            )
        }
    }

    private fun checkIfProcessPartCanBeUpdated(processPart: RegistrationAsAppClientProcessPart): Boolean {
        when (processPart) {
            RegistrationAsAppClientProcessPart.DataInput -> {
                val state = _screenState.value.dataInputPartState

                if (state.identificationNumber.isEmpty()) {
                    _event.emit(
                        IRegistrationAsAppClientScreenEvent.ShowToastMessage(
                            AuthenticationExceptionModelDomain.NotAllInputDataWasEntered.toUiMessageString()
                        )
                    )
                    return false
                }

                when (state.registrationDocumentType) {
                    RegistrationDocumentType.Passport -> {
                        if (!CommonFunctions.checkIdentificationNumberFormat(state.identificationNumber)) {
                            _event.emit(
                                IRegistrationAsAppClientScreenEvent.ShowToastMessage(
                                    AuthenticationExceptionModelDomain.IdentificationNumberIsInWrongFormat.toUiMessageString()
                                )
                            )
                            return false
                        }
                    }
                }
            }

            RegistrationAsAppClientProcessPart.PhoneNumberInput -> {
                val state = _screenState.value.phoneNumberInputPartState

                if (!CommonFunctions.checkPhoneNumberFormat(state.phoneNumber)) {
                    _event.emit(
                        IRegistrationAsAppClientScreenEvent.ShowToastMessage(
                            AuthenticationExceptionModelDomain.PhoneNumberIsInWrongFormat.toUiMessageString()
                        )
                    )
                    return false
                }
            }

            RegistrationAsAppClientProcessPart.PhoneCodeCheck -> {}

            RegistrationAsAppClientProcessPart.SettingPassword -> {
                val state = _screenState.value.settingPasswordPartState

                if (
                    CommonFunctions.checkIfSomePasswordIsEmpty(
                        first = state.password,
                        second = state.passwordCheck
                    )
                ) {
                    _event.emit(
                        IRegistrationAsAppClientScreenEvent.ShowToastMessage(
                            AuthenticationExceptionModelDomain.OneOfPasswordsIsEmpty.toUiMessageString()
                        )
                    )
                    return false
                }

                if (
                    !CommonFunctions.checkIfPasswordsAreSame(
                        first = state.password,
                        second = state.passwordCheck
                    )
                ) {
                    _event.emit(
                        IRegistrationAsAppClientScreenEvent.ShowToastMessage(
                            AuthenticationExceptionModelDomain.PasswordsAreNotTheSame.toUiMessageString()
                        )
                    )
                    return false
                }

                if (CommonFunctions.checkIfPasswordIsWeak(state.password)) {
                    _event.emit(
                        IRegistrationAsAppClientScreenEvent.ShowToastMessage(
                            AuthenticationExceptionModelDomain.WeakPasswordException.toUiMessageString()
                        )
                    )
                    return false
                }
            }

            RegistrationAsAppClientProcessPart.ConfirmPage -> {
                return false
            }
        }

        return true
    }
}