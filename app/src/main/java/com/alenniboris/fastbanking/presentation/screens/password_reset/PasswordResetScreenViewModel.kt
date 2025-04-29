package com.alenniboris.fastbanking.presentation.screens.password_reset

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alenniboris.fastbanking.domain.model.CustomResultModelDomain
import com.alenniboris.fastbanking.domain.model.exception.AuthenticationExceptionModelDomain
import com.alenniboris.fastbanking.domain.usecase.logic.authorization.IChangePasswordUseCase
import com.alenniboris.fastbanking.domain.usecase.logic.authorization.ICheckVerificationCodeUseCase
import com.alenniboris.fastbanking.domain.usecase.logic.authorization.IGetUserByIdUseCase
import com.alenniboris.fastbanking.domain.usecase.logic.authorization.ISendVerificationCodeUseCase
import com.alenniboris.fastbanking.domain.utils.SingleFlowEvent
import com.alenniboris.fastbanking.presentation.mappers.toUiMessageString
import com.alenniboris.fastbanking.presentation.screens.password_reset.state.PasswordResetProcessPart
import com.alenniboris.fastbanking.presentation.screens.password_reset.state.PasswordResetScreenState
import com.alenniboris.fastbanking.presentation.uikit.functions.CommonFunctions
import com.alenniboris.fastbanking.presentation.uikit.values.RegistrationDocumentType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PasswordResetScreenViewModel(
    private val getUserByIdUseCase: IGetUserByIdUseCase,
    private val checkVerificationCode: ICheckVerificationCodeUseCase,
    private val sendVerificationCode: ISendVerificationCodeUseCase,
    private val changePasswordUseCase: IChangePasswordUseCase
) : ViewModel() {

    private val _screenState = MutableStateFlow(PasswordResetScreenState())
    val state = _screenState.asStateFlow()

    private val _event = SingleFlowEvent<IPasswordResetScreenEvent>(viewModelScope)
    val event = _event.flow

    fun proceedIntent(intent: IPasswordResetScreenIntent) {
        when (intent) {
            is IPasswordResetScreenIntent.DoForwardAction -> doForwardAction()

            is IPasswordResetScreenIntent.DoBackwardAction -> doBackwardAction()

            is IPasswordResetScreenIntent.UpdatePasswordCheck -> updatePasswordCheck(intent.newValue)

            is IPasswordResetScreenIntent.UpdateEnteredPassword -> updateEnteredPassword(intent.newValue)

            is IPasswordResetScreenIntent.UpdateCodeValue -> updateCodeValue(intent.newValue)

            is IPasswordResetScreenIntent.UpdateDocumentValue -> updateDocumentValue(
                intent.documentType,
                intent.newValue
            )

            is IPasswordResetScreenIntent.UpdateOptionsBottomSheetVisibility ->
                updateOptionsBottomSheetVisibility()

            is IPasswordResetScreenIntent.UpdateRegistrationDocumentType ->
                updateRegistrationDocumentType(
                    intent.newValue
                )

            is IPasswordResetScreenIntent.UpdatePasswordVisibility -> updatePasswordVisibility()
            is IPasswordResetScreenIntent.UpdatePasswordCheckVisibility ->
                updatePasswordCheckVisibility()
        }
    }

    private fun updatePasswordCheckVisibility() {
        _screenState.update { state ->
            state.copy(
                passwordChangePartState = state.passwordChangePartState.copy(
                    isPasswordCheckVisible = !state.passwordChangePartState.isPasswordCheckVisible
                )
            )
        }
    }

    private fun updatePasswordVisibility() {
        _screenState.update { state ->
            state.copy(
                passwordChangePartState = state.passwordChangePartState.copy(
                    isPasswordVisible = !state.passwordChangePartState.isPasswordVisible
                )
            )
        }
    }

    private fun updateRegistrationDocumentType(newValue: RegistrationDocumentType) {
        _screenState.update {
            it.copy(
                dataInputPartState = it.dataInputPartState.copy(
                    selectedDocument = newValue
                )
            )
        }
    }

    private fun updateOptionsBottomSheetVisibility() {
        _screenState.update { it.copy(isOptionsBottomSheetVisible = !it.isOptionsBottomSheetVisible) }
    }

    private fun updateDocumentValue(
        documentType: RegistrationDocumentType,
        newValue: String
    ) {
        _screenState.update { state ->
            when (documentType) {
                RegistrationDocumentType.Passport ->
                    state.copy(
                        dataInputPartState = state.dataInputPartState.copy(
                            identificationNumber = newValue
                        )
                    )
            }
        }
    }

    private fun updateCodeValue(newValue: String) {
        _screenState.update { it.copy(enteredCode = newValue) }
    }

    private fun updateEnteredPassword(newValue: String) {
        _screenState.update { state ->
            state.copy(
                passwordChangePartState = state.passwordChangePartState.copy(
                    enteredPassword = newValue
                )
            )
        }
    }

    private fun updatePasswordCheck(newValue: String) {
        _screenState.update { state ->
            state.copy(
                passwordChangePartState = state.passwordChangePartState.copy(
                    passwordCheck = newValue
                )
            )
        }
    }

    private fun doForwardAction() {
        when (_screenState.value.currentProcessPart) {
            PasswordResetProcessPart.DocumentInput ->
                getUserAndMoveToNextPart()

            PasswordResetProcessPart.PasswordChange ->
                moveToNextProcessPart()

            PasswordResetProcessPart.CheckingCode ->
                checkVerificationCodeAndMoveToNextPart()

            PasswordResetProcessPart.ConfirmPage ->
                resetPasswordAndMoveToLoginScreen()
        }
    }

    private fun resetPasswordAndMoveToLoginScreen() {
        viewModelScope.launch {
            _screenState.value.user?.let { user ->
                _screenState.update { it.copy(isLoading = true) }
                when (
                    val result = changePasswordUseCase.invoke(
                        userId = user.id,
                        newPassword = _screenState.value.passwordChangePartState.enteredPassword
                    )
                ) {
                    is CustomResultModelDomain.Success -> {
                        _event.emit(
                            IPasswordResetScreenEvent.NavigateToLoginPage
                        )
                    }

                    is CustomResultModelDomain.Error -> {
                        _event.emit(
                            IPasswordResetScreenEvent.ShowToastMessage(
                                result.exception.toUiMessageString()
                            )
                        )
                    }
                }
                _screenState.update { it.copy(isLoading = false) }
            }
        }
    }

    private fun checkVerificationCodeAndMoveToNextPart() {
        when (
            val result = checkVerificationCode.invoke(
                code = _screenState.value.enteredCode
            )
        ) {
            is CustomResultModelDomain.Success -> {
                moveToNextProcessPart()
            }

            is CustomResultModelDomain.Error -> {
                _event.emit(
                    IPasswordResetScreenEvent.ShowToastMessage(
                        result.exception.toUiMessageString()
                    )
                )
            }
        }
    }

    private fun getUserAndMoveToNextPart() {
        if (!checkIfProcessPartCanBeUpdated(processPart = _screenState.value.currentProcessPart)) return
        viewModelScope.launch {
            val state = _screenState.value.dataInputPartState
            when (
                val res = getUserByIdUseCase.invoke(
                    when (state.selectedDocument) {
                        RegistrationDocumentType.Passport -> state.identificationNumber
                    }
                )
            ) {
                is CustomResultModelDomain.Success -> {
                    _screenState.update { it.copy(user = res.result) }
                    sendVerificationCodeToNumber()
                    moveToNextProcessPart()
                }

                is CustomResultModelDomain.Error -> {
                    _event.emit(
                        IPasswordResetScreenEvent.ShowToastMessage(
                            res.exception.toUiMessageString()
                        )
                    )
                }
            }
        }

    }

    private fun sendVerificationCodeToNumber() {
        sendVerificationCode.invoke()
    }

    private fun moveToNextProcessPart() {
        val currentProcessPart = _screenState.value.currentProcessPart

        if (!checkIfProcessPartCanBeUpdated(processPart = currentProcessPart)) return

        val currentPartIndex = _screenState.value.resettingProcessParts
            .indexOf(
                element = currentProcessPart
            )

        val newProcessPart = _screenState.value.resettingProcessParts[currentPartIndex + 1]

        _screenState.update {
            it.copy(
                currentProcessPart = newProcessPart
            )
        }
    }

    private fun checkIfProcessPartCanBeUpdated(processPart: PasswordResetProcessPart): Boolean {
        when (processPart) {
            PasswordResetProcessPart.DocumentInput -> {
                val state = _screenState.value.dataInputPartState
                val documentValue = when (state.selectedDocument) {
                    RegistrationDocumentType.Passport -> state.identificationNumber
                }

                if (documentValue.isEmpty()) {
                    _event.emit(
                        IPasswordResetScreenEvent.ShowToastMessage(
                            AuthenticationExceptionModelDomain.NotAllInputDataWasEntered.toUiMessageString()
                        )
                    )
                    return false
                }

                when (state.selectedDocument) {
                    RegistrationDocumentType.Passport -> {
                        if (!CommonFunctions.checkIdentificationNumberFormat(state.identificationNumber)) {
                            _event.emit(
                                IPasswordResetScreenEvent.ShowToastMessage(
                                    AuthenticationExceptionModelDomain.IdentificationNumberIsInWrongFormat.toUiMessageString()
                                )
                            )
                            return false
                        }
                    }
                }
            }

            PasswordResetProcessPart.CheckingCode -> {}

            PasswordResetProcessPart.PasswordChange -> {
                val state = _screenState.value

                if (
                    CommonFunctions.checkIfSomePasswordIsEmpty(
                        first = state.passwordChangePartState.enteredPassword,
                        second = state.passwordChangePartState.passwordCheck
                    )
                ) {
                    _event.emit(
                        IPasswordResetScreenEvent.ShowToastMessage(
                            AuthenticationExceptionModelDomain.OneOfPasswordsIsEmpty.toUiMessageString()
                        )
                    )
                    return false
                }

                if (
                    !CommonFunctions.checkIfPasswordsAreSame(
                        first = state.passwordChangePartState.enteredPassword,
                        second = state.passwordChangePartState.passwordCheck
                    )
                ) {
                    _event.emit(
                        IPasswordResetScreenEvent.ShowToastMessage(
                            AuthenticationExceptionModelDomain.PasswordsAreNotTheSame.toUiMessageString()
                        )
                    )
                    return false
                }

                if (CommonFunctions.checkIfPasswordIsWeak(state.passwordChangePartState.enteredPassword)) {
                    _event.emit(
                        IPasswordResetScreenEvent.ShowToastMessage(
                            AuthenticationExceptionModelDomain.WeakPasswordException.toUiMessageString()
                        )
                    )
                    return false
                }
            }

            PasswordResetProcessPart.ConfirmPage -> {
                return false
            }
        }

        return true
    }

    private fun doBackwardAction() {
        when (_screenState.value.currentProcessPart) {
            PasswordResetProcessPart.DocumentInput ->
                navigateToThePreviousScreen()

            PasswordResetProcessPart.CheckingCode,
            PasswordResetProcessPart.PasswordChange,
            PasswordResetProcessPart.ConfirmPage ->
                moveToPreviousProcessPart()
        }
    }

    private fun moveToPreviousProcessPart() {
        val currentProcessPart = _screenState.value.currentProcessPart

        clearCurrentPartState(processPart = currentProcessPart)

        val currentPartIndex = _screenState.value.resettingProcessParts
            .indexOf(
                element = currentProcessPart
            )

        val newProcessPart = _screenState.value.resettingProcessParts[currentPartIndex - 1]

        _screenState.update {
            it.copy(
                currentProcessPart = newProcessPart
            )
        }
    }

    private fun clearCurrentPartState(processPart: PasswordResetProcessPart) {
        when (processPart) {
            PasswordResetProcessPart.DocumentInput -> {}
            PasswordResetProcessPart.CheckingCode -> {
                _screenState.update { it.copy(enteredCode = "") }
            }

            PasswordResetProcessPart.PasswordChange -> {
                _screenState.update { state ->
                    state.copy(
                        passwordChangePartState = state.passwordChangePartState.copy(
                            enteredPassword = "",
                            passwordCheck = ""
                        )
                    )
                }
            }

            PasswordResetProcessPart.ConfirmPage -> {}
        }
    }

    private fun navigateToThePreviousScreen() {
        _event.emit(
            IPasswordResetScreenEvent.NavigateBack
        )
    }
}