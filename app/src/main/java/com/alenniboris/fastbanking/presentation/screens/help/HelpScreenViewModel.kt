package com.alenniboris.fastbanking.presentation.screens.help

import android.app.Application
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alenniboris.fastbanking.domain.model.CustomResultModelDomain
import com.alenniboris.fastbanking.domain.usecase.logic.help.ICallPhoneNumberUseCase
import com.alenniboris.fastbanking.domain.usecase.logic.help.IOpenMessengerUseCase
import com.alenniboris.fastbanking.domain.utils.SingleFlowEvent
import com.alenniboris.fastbanking.presentation.mappers.toUiMessageString
import com.alenniboris.fastbanking.presentation.uikit.utils.PermissionType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class HelpScreenViewModel(
    private val apl: Application,
    private val callPhoneNumberUseCase: ICallPhoneNumberUseCase,
    private val openMessengerUseCase: IOpenMessengerUseCase
) : ViewModel() {

    private val _screenState = MutableStateFlow(HelpScreenState())
    val state = _screenState.asStateFlow()

    private val _event = SingleFlowEvent<IHelpScreenEvent>(viewModelScope)
    val event = _event.flow

    fun proceedIntent(intent: IHelpScreenIntent) {
        when (intent) {
            is IHelpScreenIntent.ProceedNavigateBack -> proceedNavigateBack()
            is IHelpScreenIntent.UpdatePhoneNumbersSheetVisibility ->
                updatePhoneNumbersSheetVisibility()

            is IHelpScreenIntent.UpdateMessengersSheetVisibility ->
                updateMessengersSheetVisibility()

            is IHelpScreenIntent.UpdateBankInfoSheetVisibility ->
                updateBankInfoSheetVisibility()

            is IHelpScreenIntent.MakePhoneCall -> makePhoneCall(intent.phoneNumber)
            is IHelpScreenIntent.UpdateRequestedPermissionAndShowDialog ->
                updateRequestedPermissionAndShowDialog(intent.newValue)

            is IHelpScreenIntent.HidePermissionDialog -> hidePermissionDialog()
            is IHelpScreenIntent.OpenSettingsAndHidePermissionDialog -> openSettingsAndHidePermissionDialog()
            is IHelpScreenIntent.OpenMessenger -> openMessenger(intent.messenger)
            is IHelpScreenIntent.CopyToClipboard -> copyToClipboard(intent.text)
        }
    }

    private fun copyToClipboard(text: String) {
        _event.emit(IHelpScreenEvent.CopyToClipboard(text = text))
    }

    private fun openMessenger(messenger: BankSupportedMessenger) {
        when (
            val res = openMessengerUseCase.invoke(messenger.toUriString())
        ) {
            is CustomResultModelDomain.Success -> {}
            is CustomResultModelDomain.Error -> {
                _event.emit(
                    IHelpScreenEvent.ShowToastMessage(
                        res.exception.toUiMessageString()
                    )
                )
            }
        }
    }

    private fun openSettingsAndHidePermissionDialog() {
        val openingIntent =
            Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                data = Uri.fromParts("package", apl.packageName, null)
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
        apl.startActivity(openingIntent)
        hidePermissionDialog()
    }

    private fun hidePermissionDialog() {
        _screenState.update {
            it.copy(isPermissionDialogVisible = false)
        }
    }

    private fun updateRequestedPermissionAndShowDialog(permission: PermissionType) {
        _screenState.update {
            it.copy(
                requestedPermission = permission,
                isPermissionDialogVisible = true
            )
        }
    }

    private fun makePhoneCall(phoneNumber: BankPhoneNumber) {
        when (
            val result = callPhoneNumberUseCase.invoke(phoneNumber = phoneNumber.phoneNumber)
        ) {
            is CustomResultModelDomain.Success -> {}
            is CustomResultModelDomain.Error -> {
                _event.emit(
                    IHelpScreenEvent.ShowToastMessage(
                        result.exception.toUiMessageString()
                    )
                )
            }
        }
    }

    private fun updateBankInfoSheetVisibility() {
        _screenState.update { it.copy(isBankInfoSheetVisible = !it.isBankInfoSheetVisible) }
    }

    private fun updateMessengersSheetVisibility() {
        _screenState.update { it.copy(isMessengersSheetVisible = !it.isMessengersSheetVisible) }
    }

    private fun updatePhoneNumbersSheetVisibility() {
        _screenState.update { it.copy(isPhoneNumbersSheetVisible = !it.isPhoneNumbersSheetVisible) }
    }

    private fun proceedNavigateBack() {
        _event.emit(IHelpScreenEvent.NavigateBack)
    }
}