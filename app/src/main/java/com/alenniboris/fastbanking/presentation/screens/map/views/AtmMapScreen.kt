package com.alenniboris.fastbanking.presentation.screens.map.views

import android.os.Build
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.alenniboris.fastbanking.R
import com.alenniboris.fastbanking.domain.model.map.MapElementType
import com.alenniboris.fastbanking.domain.model.map.MapsElementModelDomain
import com.alenniboris.fastbanking.presentation.model.MapsElementModelUi
import com.alenniboris.fastbanking.presentation.screens.map.AtmMapScreenState
import com.alenniboris.fastbanking.presentation.screens.map.AtmMapScreenViewModel
import com.alenniboris.fastbanking.presentation.screens.map.IAtmMapScreenEvent
import com.alenniboris.fastbanking.presentation.screens.map.IAtmMapScreenIntent
import com.alenniboris.fastbanking.presentation.uikit.theme.AtmMapNotRegisteredUserScreenContentPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.AtmMapNotRegisteredUserScreenTopBarPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.TopBarHeaderTextSize
import com.alenniboris.fastbanking.presentation.uikit.theme.TopBarPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.appColor
import com.alenniboris.fastbanking.presentation.uikit.theme.appTopBarElementsColor
import com.alenniboris.fastbanking.presentation.uikit.theme.bodyStyle
import com.alenniboris.fastbanking.presentation.uikit.utils.PermissionType
import com.alenniboris.fastbanking.presentation.uikit.utils.launchForPermission
import com.alenniboris.fastbanking.presentation.uikit.utils.toPermission
import com.alenniboris.fastbanking.presentation.uikit.values.AtmMapScreenRoute
import com.alenniboris.fastbanking.presentation.uikit.values.ClickableElement
import com.alenniboris.fastbanking.presentation.uikit.views.AppButtonRow
import com.alenniboris.fastbanking.presentation.uikit.views.AppRationaleDialog
import com.alenniboris.fastbanking.presentation.uikit.views.AppTopBar
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.rememberCameraPositionState
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
@Destination(route = AtmMapScreenRoute)
fun AtmMapScreen(
    isUserAuthenticated: Boolean = false,
    navigator: DestinationsNavigator
) {

    val viewModel = koinViewModel<AtmMapScreenViewModel>()
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
            event.filterIsInstance<IAtmMapScreenEvent.ShowToastMessage>()
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

        launch {
            event.filterIsInstance<IAtmMapScreenEvent.NavigateBack>().collect {
                navigator.popBackStack()
            }
        }
    }

    AtmMapNotRegisteredUserScreenUi(
        isUserRegistered = isUserAuthenticated,
        state = state,
        proceedIntent = proceedIntent
    )
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
private fun AtmMapNotRegisteredUserScreenUi(
    isUserRegistered: Boolean,
    state: AtmMapScreenState,
    proceedIntent: (IAtmMapScreenIntent) -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(appColor)
    ) {

        if (isUserRegistered) {
            AppTopBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(appColor)
                    .padding(TopBarPadding),
                headerTextString = stringResource(R.string.map_top_bar_header_text),
                leftBtnPainter = painterResource(R.drawable.back_icon),
                onLeftBtnClicked = {
                    proceedIntent(
                        IAtmMapScreenIntent.NavigateBack
                    )
                }
            )
        } else {
            AppTopBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(appColor)
                    .padding(TopBarPadding),
                headerTextString = stringResource(R.string.map_top_bar_header_text)
            )
        }


        val context = LocalContext.current
        val fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(
            context
        )
        val permissionLauncher = rememberLauncherForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted ->
            if (isGranted) {
                proceedIntent(
                    IAtmMapScreenIntent.GetUserCurrentLocation(
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
                        IAtmMapScreenIntent.GetUserCurrentLocation(
                            fusedLocationProviderClient = fusedLocationProviderClient
                        )
                    )
                },
                onPermissionNotGrantedAction = {},
                onShowRationale = { permission ->
                    proceedIntent(
                        IAtmMapScreenIntent.UpdateRequestedPermissionAndShowDialog(
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
                            IAtmMapScreenIntent.HidePermissionDialog
                        )
                    },
                    onOpenSettings = {
                        proceedIntent(
                            IAtmMapScreenIntent.OpenSettingsAndHidePermissionDialog
                        )
                    }
                )
            }
        }

        val mapProperties = MapProperties(isBuildingEnabled = state.userLocation != null)
        val cameraPositionState = rememberCameraPositionState()

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

        AppButtonRow(
            modifier = Modifier
                .padding(AtmMapNotRegisteredUserScreenContentPadding)
                .fillMaxWidth()
                .padding(AtmMapNotRegisteredUserScreenTopBarPadding),
            listOfElements = state.listOfScreenModes.map { element ->
                ClickableElement(
                    text = stringResource(element.toUiString()),
                    onClick = {
                        proceedIntent(
                            IAtmMapScreenIntent.UpdateCurrentScreenMode(element)
                        )
                    }
                )
            },
            currentElement = ClickableElement(
                text = stringResource(state.screenMode.toUiString()),
                onClick = {}
            )
        )

        when (state.screenMode) {
            MapScreenMode.Map -> {
                AtmMapUi(
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
                            IAtmMapScreenIntent.UpdateClickedMapElement(item)
                        )
                    },
                    selectedItem = state.clickedMapItem,
                    onItemInfoDismiss = {
                        proceedIntent(
                            IAtmMapScreenIntent.UpdateClickedMapElement(null)
                        )
                    }
                )
            }

            MapScreenMode.List -> {
                AtmListUi(
                    modifier = Modifier
                        .padding(AtmMapNotRegisteredUserScreenContentPadding)
                        .fillMaxSize(),
                    items = state.bankMapItems,
                )
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
@Preview
private fun AtmMapNotRegisteredUserScreenPreview() {
    AtmMapNotRegisteredUserScreenUi(
        state = AtmMapScreenState(
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
        proceedIntent = {},
        isUserRegistered = false
    )
}