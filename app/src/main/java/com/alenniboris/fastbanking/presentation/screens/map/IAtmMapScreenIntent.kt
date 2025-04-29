package com.alenniboris.fastbanking.presentation.screens.map

import com.alenniboris.fastbanking.presentation.model.bank_info.MapsElementModelUi
import com.alenniboris.fastbanking.presentation.uikit.utils.PermissionType
import com.google.android.gms.location.FusedLocationProviderClient

sealed interface IAtmMapScreenIntent {

    data class GetUserCurrentLocation(val fusedLocationProviderClient: FusedLocationProviderClient) :
        IAtmMapScreenIntent

    data class UpdateRequestedPermissionAndShowDialog(val newRequestedPermission: PermissionType) :
        IAtmMapScreenIntent

    data object HidePermissionDialog : IAtmMapScreenIntent

    data object OpenSettingsAndHidePermissionDialog : IAtmMapScreenIntent

    data class UpdateClickedMapElement(val newValue: MapsElementModelUi?) :
        IAtmMapScreenIntent

    data class UpdateCurrentScreenMode(val newValue: MapScreenMode) : IAtmMapScreenIntent

    data object NavigateBack : IAtmMapScreenIntent
}