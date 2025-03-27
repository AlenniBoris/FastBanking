package com.alenniboris.fastbanking.presentation.screens.map

import android.app.Application
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alenniboris.fastbanking.domain.model.CustomResultModelDomain
import com.alenniboris.fastbanking.domain.usecase.logic.map.IGetBankLocationsUseCase
import com.alenniboris.fastbanking.domain.utils.SingleFlowEvent
import com.alenniboris.fastbanking.presentation.mappers.toUiMessageString
import com.alenniboris.fastbanking.presentation.model.MapsElementModelUi
import com.alenniboris.fastbanking.presentation.screens.map.views.MapScreenMode
import com.alenniboris.fastbanking.presentation.uikit.utils.PermissionType
import com.google.android.gms.location.FusedLocationProviderClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class AtmMapScreenViewModel(
    private val apl: Application,
    private val getBankLocationsUseCase: IGetBankLocationsUseCase
) : ViewModel() {

    private val _screenState = MutableStateFlow(AtmMapScreenState())
    val state = _screenState.asStateFlow()

    private val _event = SingleFlowEvent<IAtmMapScreenEvent>(viewModelScope)
    val event = _event.flow

    init {
        getBankLocations()
    }

    private fun getBankLocations() {
        viewModelScope.launch {
            _screenState.update { it.copy(isBankItemsLoading = true) }
            when (
                val result = getBankLocationsUseCase.invoke()
            ) {
                is CustomResultModelDomain.Success -> {
                    val mappedResult = result.result.map { MapsElementModelUi(domainModel = it) }
                    _screenState.update {
                        it.copy(
                            bankMapItems = mappedResult
                        )
                    }
                }

                is CustomResultModelDomain.Error -> {
                    _event.emit(
                        IAtmMapScreenEvent.ShowToastMessage(
                            result.exception.toUiMessageString()
                        )
                    )
                }
            }
            _screenState.update { it.copy(isBankItemsLoading = false) }
        }
    }

    fun proceedIntent(intent: IAtmMapScreenIntent) {
        when (intent) {
            is IAtmMapScreenIntent.GetUserCurrentLocation -> getUserCurrentLocation(
                intent.fusedLocationProviderClient
            )

            is IAtmMapScreenIntent.UpdateRequestedPermissionAndShowDialog ->
                updateRequestedPermissionAndShowDialog(intent.newRequestedPermission)

            is IAtmMapScreenIntent.HidePermissionDialog ->
                hidePermissionDialog()

            is IAtmMapScreenIntent.OpenSettingsAndHidePermissionDialog ->
                openSettingsAndHidePermissionDialog()

            is IAtmMapScreenIntent.UpdateClickedMapElement ->
                updateClickedMapElement(intent.newValue)

            is IAtmMapScreenIntent.UpdateCurrentScreenMode ->
                updateCurrentScreenMode(intent.newValue)

            is IAtmMapScreenIntent.NavigateBack ->
                navigateBack()
        }
    }

    private fun navigateBack() {
        _event.emit(
            IAtmMapScreenEvent.NavigateBack
        )
    }

    private fun updateCurrentScreenMode(newValue: MapScreenMode) {
        _screenState.update { it.copy(screenMode = newValue) }
    }

    private fun updateClickedMapElement(newValue: MapsElementModelUi?) {
        _screenState.update { it.copy(clickedMapItem = newValue) }
    }

    private fun openSettingsAndHidePermissionDialog() {
        val openingIntent =
            Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                data = Uri.fromParts("package", apl.packageName, null)
            }
        apl.startActivity(openingIntent)
        hidePermissionDialog()
    }

    private fun hidePermissionDialog() {
        _screenState.update {
            it.copy(isPermissionDialogVisible = false)
        }
    }

    private fun updateRequestedPermissionAndShowDialog(newPermission: PermissionType) {
        _screenState.update {
            it.copy(
                requestedPermission = newPermission,
                isPermissionDialogVisible = true
            )
        }
    }

    @Suppress("MissingPermission")
    private fun getUserCurrentLocation(fusedLocationProviderClient: FusedLocationProviderClient) {
        viewModelScope.launch {
            val location = fusedLocationProviderClient.lastLocation.await()
            _screenState.update {
                it.copy(
                    userLocation = location
                )
            }
        }
    }
}