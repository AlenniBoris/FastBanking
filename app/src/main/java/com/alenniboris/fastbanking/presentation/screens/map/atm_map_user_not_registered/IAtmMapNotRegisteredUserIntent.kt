package com.alenniboris.fastbanking.presentation.screens.map.atm_map_user_not_registered

import com.alenniboris.fastbanking.presentation.model.MapsElementModelUi
import com.alenniboris.fastbanking.presentation.screens.map.MapScreenMode
import com.alenniboris.fastbanking.presentation.uikit.utils.PermissionType
import com.google.android.gms.location.FusedLocationProviderClient

sealed interface IAtmMapNotRegisteredUserIntent {

    data class GetUserCurrentLocation(val fusedLocationProviderClient: FusedLocationProviderClient) :
        IAtmMapNotRegisteredUserIntent

    data class UpdateRequestedPermissionAndShowDialog(val newRequestedPermission: PermissionType) :
        IAtmMapNotRegisteredUserIntent

    data object HidePermissionDialog : IAtmMapNotRegisteredUserIntent

    data object OpenSettingsAndHidePermissionDialog : IAtmMapNotRegisteredUserIntent

    data class UpdateClickedMapElement(val newValue: MapsElementModelUi?) :
        IAtmMapNotRegisteredUserIntent

    data class UpdateCurrentScreenMode(val newValue: MapScreenMode) : IAtmMapNotRegisteredUserIntent
}