package com.alenniboris.fastbanking.presentation.screens.map.atm_map_user_not_registered

import android.location.Location
import com.alenniboris.fastbanking.presentation.model.MapsElementModelUi
import com.alenniboris.fastbanking.presentation.screens.map.MapScreenMode
import com.alenniboris.fastbanking.presentation.uikit.utils.PermissionType

data class AtmMapNotRegisteredUserScreenState(
    val screenMode: MapScreenMode = MapScreenMode.Map,
    val listOfScreenModes: List<MapScreenMode> = MapScreenMode.entries.toList(),
    val isBankItemsLoading: Boolean = false,
    val bankMapItems: List<MapsElementModelUi> = emptyList(),
    val userLocation: Location? = null,
    val requestedPermission: PermissionType? = null,
    val isPermissionDialogVisible: Boolean = false,
    val clickedMapItem: MapsElementModelUi? = null
)