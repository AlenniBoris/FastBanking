package com.alenniboris.fastbanking.presentation.screens.password_reset.state

import com.alenniboris.fastbanking.domain.model.user.UserModelDomain

data class PasswordResetScreenState(
    val currentProcessPart: PasswordResetProcessPart = PasswordResetProcessPart.DocumentInput,
    val resettingProcessParts: List<PasswordResetProcessPart> = PasswordResetProcessPart.entries.toList(),
    val dataInputPartState: DocumentInputPartState = DocumentInputPartState(),
    val user: UserModelDomain? = null,
    val isLoading: Boolean = false,
    val isOptionsBottomSheetVisible: Boolean = false,
    val enteredCode: String = "",
    val passwordChangePartState: PasswordChangePartState = PasswordChangePartState()
)
