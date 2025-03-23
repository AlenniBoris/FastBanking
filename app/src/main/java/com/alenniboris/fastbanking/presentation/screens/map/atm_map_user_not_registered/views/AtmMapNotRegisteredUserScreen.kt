package com.alenniboris.fastbanking.presentation.screens.map.atm_map_user_not_registered.views

import android.os.Build
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.alenniboris.fastbanking.R
import com.alenniboris.fastbanking.domain.model.map.MapElementType
import com.alenniboris.fastbanking.domain.model.map.MapsElementModelDomain
import com.alenniboris.fastbanking.presentation.model.MapsElementModelUi
import com.alenniboris.fastbanking.presentation.screens.map.AtmListScreen
import com.alenniboris.fastbanking.presentation.screens.map.AtmMapScreen
import com.alenniboris.fastbanking.presentation.screens.map.MapScreenMode
import com.alenniboris.fastbanking.presentation.screens.map.atm_map_user_not_registered.AtmMapNotRegisteredUserScreenState
import com.alenniboris.fastbanking.presentation.screens.map.atm_map_user_not_registered.AtmMapNotRegisteredUserScreenViewModel
import com.alenniboris.fastbanking.presentation.screens.map.atm_map_user_not_registered.IAtmMapNotRegisteredUserEvent
import com.alenniboris.fastbanking.presentation.screens.map.atm_map_user_not_registered.IAtmMapNotRegisteredUserIntent
import com.alenniboris.fastbanking.presentation.screens.map.toUiString
import com.alenniboris.fastbanking.presentation.uikit.theme.AtmMapNotRegisteredUserScreenContentPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.AtmMapNotRegisteredUserScreenTopBarButtonInnerPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.AtmMapNotRegisteredUserScreenTopBarButtonOuterPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.AtmMapNotRegisteredUserScreenTopBarButtonShape
import com.alenniboris.fastbanking.presentation.uikit.theme.AtmMapNotRegisteredUserScreenTopBarPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.MapLocationItemContentTextSize
import com.alenniboris.fastbanking.presentation.uikit.theme.TopBarHeaderTextSize
import com.alenniboris.fastbanking.presentation.uikit.theme.TopBarPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.appColor
import com.alenniboris.fastbanking.presentation.uikit.theme.appTopBarElementsColor
import com.alenniboris.fastbanking.presentation.uikit.theme.atmMapScreenButtonNotSelectedColor
import com.alenniboris.fastbanking.presentation.uikit.theme.atmMapScreenButtonSelectedColor
import com.alenniboris.fastbanking.presentation.uikit.theme.atmMapScreenButtonTextNotSelectedColor
import com.alenniboris.fastbanking.presentation.uikit.theme.atmMapScreenButtonTextSelectedColor
import com.alenniboris.fastbanking.presentation.uikit.theme.bodyStyle
import com.alenniboris.fastbanking.presentation.uikit.utils.PermissionType
import com.alenniboris.fastbanking.presentation.uikit.utils.launchForPermission
import com.alenniboris.fastbanking.presentation.uikit.utils.toPermission
import com.alenniboris.fastbanking.presentation.uikit.values.AtmMapNotRegisteredUserScreenRoute
import com.alenniboris.fastbanking.presentation.uikit.views.AppRationaleDialog
import com.alenniboris.fastbanking.presentation.uikit.views.AppTopBar
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberUpdatedMarkerState
import com.ramcosta.composedestinations.annotation.Destination
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
@Destination(route = AtmMapNotRegisteredUserScreenRoute)
fun AtmMapNotRegisteredUserScreen() {

    val viewModel = koinViewModel<AtmMapNotRegisteredUserScreenViewModel>()
    val proceedIntent by remember { mutableStateOf(viewModel::proceedIntent) }
    val state by viewModel.state.collectAsStateWithLifecycle()
    val event by remember { mutableStateOf(viewModel.event) }
    val context = LocalContext.current
    var toastMessage by remember {
        mutableStateOf(
            Toast.makeText(context, "", Toast.LENGTH_SHORT)
        )
    }

    LaunchedEffect(event) {
        launch {
            event.filterIsInstance<IAtmMapNotRegisteredUserEvent.ShowToastMessage>()
                .collect { coming ->
                    toastMessage.cancel()
                    toastMessage =
                        Toast.makeText(
                            context,
                            context.getString(coming.messageId),
                            Toast.LENGTH_SHORT
                        )
                    toastMessage.show()
                }
        }
    }

    AtmMapNotRegisteredUserScreenUi(
        state = state,
        proceedIntent = proceedIntent
    )
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
private fun AtmMapNotRegisteredUserScreenUi(
    state: AtmMapNotRegisteredUserScreenState,
    proceedIntent: (IAtmMapNotRegisteredUserIntent) -> Unit
) {

    val context = LocalContext.current
    val fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(
        context
    )
    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            proceedIntent(
                IAtmMapNotRegisteredUserIntent.GetUserCurrentLocation(
                    fusedLocationProviderClient = fusedLocationProviderClient
                )
            )
        }
    }

    LaunchedEffect(Unit) {
        launchForPermission(
            permission = PermissionType.PERMISSION_FINE_LOCATION,
            context = context,
            onPermissionGrantedAction = {
                proceedIntent(
                    IAtmMapNotRegisteredUserIntent.GetUserCurrentLocation(
                        fusedLocationProviderClient = fusedLocationProviderClient
                    )
                )
            },
            onPermissionNotGrantedAction = {},
            onShowRationale = { permission ->
                proceedIntent(
                    IAtmMapNotRegisteredUserIntent.UpdateRequestedPermissionAndShowDialog(
                        newRequestedPermission = permission
                    )
                )
            },
            onLaunchAgain = { permission ->
                permissionLauncher.launch(permission.toPermission())
            }
        )
    }
    if (state.isPermissionDialogVisible) {
        state.requestedPermission?.let {
            AppRationaleDialog(
                permissionType = it,
                onDismiss = {
                    proceedIntent(
                        IAtmMapNotRegisteredUserIntent.HidePermissionDialog
                    )
                },
                onOpenSettings = {
                    proceedIntent(
                        IAtmMapNotRegisteredUserIntent.OpenSettingsAndHidePermissionDialog
                    )
                }
            )
        }
    }

    val mapProperties = MapProperties(isBuildingEnabled = state.userLocation != null)
    val cameraPositionState = rememberCameraPositionState()
    val markerState = rememberUpdatedMarkerState()

    LaunchedEffect(state.userLocation) {
        state.userLocation?.let { location ->
            CoroutineScope(Dispatchers.Main + SupervisorJob()).launch {
                cameraPositionState.animate(
                    update = CameraUpdateFactory.newLatLngZoom(
                        LatLng(location.latitude, location.longitude),
                        15f
                    )
                )
            }
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(appColor)
    ) {

        AppTopBar(
            modifier = Modifier
                .fillMaxWidth()
                .background(appColor)
                .padding(TopBarPadding),
            content = {
                TopBarContent(
                    currentMode = state.screenMode,
                    listOfScreenModes = state.listOfScreenModes,
                    proceedIntent = proceedIntent
                )
            }
        )

        when (state.screenMode) {
            MapScreenMode.Map -> {
                AtmMapScreen(
                    modifier = Modifier
                        .padding(AtmMapNotRegisteredUserScreenContentPadding)
                        .fillMaxSize(),
                    mapLocations = state.bankMapItems,
                    mapProperties = mapProperties,
                    cameraPositionState = cameraPositionState,
                    onClusterClick = {
                        cameraPositionState.move(
                            update = CameraUpdateFactory.zoomIn()
                        )
                    },
                    onClusterItemClick = { item ->
                        proceedIntent(
                            IAtmMapNotRegisteredUserIntent.UpdateClickedMapElement(item)
                        )
                    },
                    selectedItem = state.clickedMapItem,
                    onItemInfoDismiss = {
                        proceedIntent(
                            IAtmMapNotRegisteredUserIntent.UpdateClickedMapElement(null)
                        )
                    }
                )
            }

            MapScreenMode.List -> {
                AtmListScreen(
                    modifier = Modifier
                        .padding(AtmMapNotRegisteredUserScreenContentPadding)
                        .fillMaxSize(),
                    items = state.bankMapItems,
                )
            }
        }
    }
}

@Composable
private fun TopBarContent(
    currentMode: MapScreenMode,
    listOfScreenModes: List<MapScreenMode>,
    proceedIntent: (IAtmMapNotRegisteredUserIntent) -> Unit
) {

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.padding(AtmMapNotRegisteredUserScreenTopBarPadding),
            text = stringResource(R.string.map_top_bar_header_text),
            color = appTopBarElementsColor,
            style = bodyStyle.copy(
                fontSize = TopBarHeaderTextSize,
                fontWeight = FontWeight.Bold
            )
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            listOfScreenModes.forEach { mode ->

                val backgroundColor by animateColorAsState(
                    if (mode == currentMode)
                        atmMapScreenButtonSelectedColor
                    else
                        atmMapScreenButtonNotSelectedColor
                )

                val textColor by animateColorAsState(
                    if (mode == currentMode)
                        atmMapScreenButtonTextSelectedColor
                    else
                        atmMapScreenButtonTextNotSelectedColor
                )

                Box(
                    modifier = Modifier
                        .clickable {
                            proceedIntent(
                                IAtmMapNotRegisteredUserIntent.UpdateCurrentScreenMode(mode)
                            )
                        }
                        .padding(AtmMapNotRegisteredUserScreenTopBarButtonOuterPadding)
                        .weight(1f)
                        .background(
                            color = backgroundColor,
                            shape = AtmMapNotRegisteredUserScreenTopBarButtonShape
                        )
                        .padding(AtmMapNotRegisteredUserScreenTopBarButtonInnerPadding)
                ) {

                    Text(
                        modifier = Modifier.align(Alignment.Center),
                        text = stringResource(mode.toUiString()),
                        style = bodyStyle.copy(
                            color = textColor,
                            fontSize = MapLocationItemContentTextSize
                        )
                    )
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
@Preview
private fun AtmMapNotRegisteredUserScreenPreview() {
    AtmMapNotRegisteredUserScreenUi(
        state = AtmMapNotRegisteredUserScreenState(
            screenMode = MapScreenMode.List,
            bankMapItems = listOf(
                MapsElementModelUi(
                    domainModel = MapsElementModelDomain(
                        latitude = 0.111,
                        longitude = 3.222,
                        type = MapElementType.ATM,
                        address = "sddsd",
                        workingTime = "11-22"
                    )
                ),
                MapsElementModelUi(
                    domainModel = MapsElementModelDomain(
                        latitude = 0.111,
                        longitude = 3.222,
                        type = MapElementType.ATM,
                        address = "sddsd",
                        workingTime = "11-22"
                    )
                ),
                MapsElementModelUi(
                    domainModel = MapsElementModelDomain(
                        latitude = 0.111,
                        longitude = 3.222,
                        type = MapElementType.ATM,
                        address = "sddsd",
                        workingTime = "11-22"
                    )
                ),
                MapsElementModelUi(
                    domainModel = MapsElementModelDomain(
                        latitude = 0.111,
                        longitude = 3.222,
                        type = MapElementType.ATM,
                        address = "sddsd",
                        workingTime = "11-22"
                    )
                )
            )
        ),
        proceedIntent = {}
    )
}