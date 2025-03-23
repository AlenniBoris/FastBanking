package com.alenniboris.fastbanking.presentation.screens.map

import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.alenniboris.fastbanking.R
import com.alenniboris.fastbanking.presentation.model.MapsElementModelUi
import com.alenniboris.fastbanking.presentation.uikit.theme.locationPinColor
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapsComposeExperimentalApi
import com.google.maps.android.compose.clustering.Clustering
import com.google.maps.android.compose.rememberCameraPositionState

@OptIn(MapsComposeExperimentalApi::class)
@Composable
fun AtmMapScreen(
    modifier: Modifier = Modifier,
    mapLocations: List<MapsElementModelUi>,
    mapProperties: MapProperties = MapProperties(),
    cameraPositionState: CameraPositionState = rememberCameraPositionState(),
    onClusterClick: () -> Unit,
    onClusterItemClick: (MapsElementModelUi) -> Unit,
    selectedItem: MapsElementModelUi?,
    onItemInfoDismiss: () -> Unit
) {

    GoogleMap(
        modifier = modifier,
        properties = mapProperties,
        cameraPositionState = cameraPositionState
    ) {
        Clustering(
            items = mapLocations,
            onClusterClick = {
                onClusterClick()
                false
            },
            onClusterItemClick = { item ->
                onClusterItemClick(item)
                false
            },
            clusterItemContent = {
                Icon(
                    painter = painterResource(R.drawable.location_pin),
                    tint = locationPinColor,
                    contentDescription = stringResource(R.string.location_pin_description)
                )
            }
        )
    }
    selectedItem?.let {
        MapElementInfoCard(
            mapElement = it,
            onDismiss = {
                onItemInfoDismiss()
            }
        )
    }
}
