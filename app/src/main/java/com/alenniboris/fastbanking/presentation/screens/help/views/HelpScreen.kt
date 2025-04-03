package com.alenniboris.fastbanking.presentation.screens.help.views

import android.os.Build
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.alenniboris.fastbanking.R
import com.alenniboris.fastbanking.presentation.screens.help.HelpScreenState
import com.alenniboris.fastbanking.presentation.screens.help.HelpScreenViewModel
import com.alenniboris.fastbanking.presentation.screens.help.IHelpScreenEvent
import com.alenniboris.fastbanking.presentation.screens.help.IHelpScreenIntent
import com.alenniboris.fastbanking.presentation.uikit.theme.DataInputProcessUiDocumentTypePadding
import com.alenniboris.fastbanking.presentation.uikit.theme.HelpScreenBottomSheetButtonContentPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.HelpScreenFilterElementTopPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.HelpScreenUiContainerPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.HelpScreenUiTextFontSize
import com.alenniboris.fastbanking.presentation.uikit.theme.TopBarPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.appColor
import com.alenniboris.fastbanking.presentation.uikit.theme.bodyStyle
import com.alenniboris.fastbanking.presentation.uikit.theme.enterTextFieldTextColor
import com.alenniboris.fastbanking.presentation.uikit.utils.PermissionType
import com.alenniboris.fastbanking.presentation.uikit.utils.launchForPermission
import com.alenniboris.fastbanking.presentation.uikit.utils.toPermission
import com.alenniboris.fastbanking.presentation.uikit.values.HelpScreenRoute
import com.alenniboris.fastbanking.presentation.uikit.views.AppCustomButton
import com.alenniboris.fastbanking.presentation.uikit.views.AppFilter
import com.alenniboris.fastbanking.presentation.uikit.views.AppRationaleDialog
import com.alenniboris.fastbanking.presentation.uikit.views.AppTopBar
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.flow.filterIsInstance
import org.koin.androidx.compose.koinViewModel

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
@Destination(route = HelpScreenRoute)
fun HelpScreen(
    isBackButtonNeeded: Boolean = false,
    navigator: DestinationsNavigator
) {

    val viewModel = koinViewModel<HelpScreenViewModel>()
    val state by viewModel.state.collectAsStateWithLifecycle()
    val proceedIntent by remember { mutableStateOf(viewModel::proceedIntent) }
    val event by remember { mutableStateOf(viewModel.event) }
    val context = LocalContext.current
    var toastMessage by remember {
        mutableStateOf(
            Toast.makeText(context, "", Toast.LENGTH_SHORT)
        )
    }

    LaunchedEffect(event) {

        event.filterIsInstance<IHelpScreenEvent.ShowToastMessage>().collect { coming ->
            toastMessage?.cancel()
            toastMessage = Toast.makeText(
                context,
                context.getString(coming.messageId),
                Toast.LENGTH_SHORT
            )
            toastMessage?.show()
        }

        event.filterIsInstance<IHelpScreenEvent.NavigateBack>().collect {
            navigator.popBackStack(
                route = HelpScreenRoute,
                inclusive = true
            )
        }
    }

    HelpScreenUi(
        isBackButtonNeeded = isBackButtonNeeded,
        state = state,
        proceedIntent = proceedIntent
    )
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HelpScreenUi(
    isBackButtonNeeded: Boolean,
    state: HelpScreenState,
    proceedIntent: (IHelpScreenIntent) -> Unit
) {


    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) {}
    val context = LocalContext.current

    if (state.isPermissionDialogVisible) {
        state.requestedPermission?.let {
            AppRationaleDialog(
                permissionType = it,
                onDismiss = {
                    proceedIntent(
                        IHelpScreenIntent.HidePermissionDialog
                    )
                },
                onOpenSettings = {
                    proceedIntent(
                        IHelpScreenIntent.OpenSettingsAndHidePermissionDialog
                    )
                }
            )
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(appColor)
    ) {

        Column(
            modifier = Modifier.fillMaxSize()
        ) {

            if (isBackButtonNeeded) {
                AppTopBar(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(TopBarPadding),
                    leftBtnPainter = painterResource(R.drawable.back_icon),
                    onLeftBtnClicked = {
                        proceedIntent(
                            IHelpScreenIntent.ProceedNavigateBack
                        )
                    },
                    headerTextString = stringResource(R.string.help_screen_header_text)
                )
            } else {
                AppTopBar(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(TopBarPadding),
                    headerTextString = stringResource(R.string.help_screen_header_text)
                )
            }

            Column(
                modifier = Modifier
                    .padding(HelpScreenUiContainerPadding)
                    .fillMaxSize()
            ) {

                AppCustomButton(
                    modifier = Modifier
                        .fillMaxWidth(),
                    onClick = {
                        proceedIntent(
                            IHelpScreenIntent.UpdatePhoneNumbersSheetVisibility
                        )
                    },
                    content = {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(HelpScreenBottomSheetButtonContentPadding),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                textAlign = TextAlign.Center,
                                text = stringResource(R.string.call_us_text),
                                style = bodyStyle.copy(
                                    color = enterTextFieldTextColor,
                                    fontSize = HelpScreenUiTextFontSize
                                )
                            )

                            Icon(
                                painter = painterResource(R.drawable.open_options_icon),
                                tint = enterTextFieldTextColor,
                                contentDescription = stringResource(R.string.document_options_description)
                            )
                        }
                    }
                )

                AppCustomButton(
                    modifier = Modifier
                        .padding(DataInputProcessUiDocumentTypePadding)
                        .fillMaxWidth(),
                    onClick = {
                        proceedIntent(
                            IHelpScreenIntent.UpdateMessengersSheetVisibility
                        )
                    },
                    content = {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(HelpScreenBottomSheetButtonContentPadding),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                textAlign = TextAlign.Center,
                                text = stringResource(R.string.message_us_text),
                                style = bodyStyle.copy(
                                    color = enterTextFieldTextColor,
                                    fontSize = HelpScreenUiTextFontSize
                                )
                            )

                            Icon(
                                painter = painterResource(R.drawable.open_options_icon),
                                tint = enterTextFieldTextColor,
                                contentDescription = stringResource(R.string.document_options_description)
                            )
                        }
                    }
                )

                AppCustomButton(
                    modifier = Modifier
                        .padding(DataInputProcessUiDocumentTypePadding)
                        .fillMaxWidth(),
                    onClick = {
                        proceedIntent(
                            IHelpScreenIntent.UpdateBankInfoSheetVisibility
                        )
                    },
                    content = {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(HelpScreenBottomSheetButtonContentPadding),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                textAlign = TextAlign.Center,
                                text = stringResource(R.string.bank_info_text),
                                style = bodyStyle.copy(
                                    color = enterTextFieldTextColor,
                                    fontSize = HelpScreenUiTextFontSize
                                )
                            )

                            Icon(
                                painter = painterResource(R.drawable.open_options_icon),
                                tint = enterTextFieldTextColor,
                                contentDescription = stringResource(R.string.document_options_description)
                            )
                        }
                    }
                )
            }
        }

        if (state.isPhoneNumbersSheetVisible) {

            AppFilter(
                elements = state.bankPhoneNumbers,
                onDismiss = {
                    proceedIntent(
                        IHelpScreenIntent.UpdatePhoneNumbersSheetVisibility
                    )
                },
                itemContent = { number ->

                    BankNumberItem(
                        modifier = Modifier
                            .padding(HelpScreenFilterElementTopPadding)
                            .fillMaxWidth(),
                        number = number,
                        onClick = {
                            launchForPermission(
                                permission = PermissionType.PERMISSION_MAKE_CALL,
                                context = context,
                                onPermissionGrantedAction = {
                                    proceedIntent(
                                        IHelpScreenIntent.MakePhoneCall(number)
                                    )
                                },
                                onPermissionNotGrantedAction = {},
                                onShowRationale = { permission ->
                                    proceedIntent(
                                        IHelpScreenIntent.UpdateRequestedPermissionAndShowDialog(
                                            newValue = permission
                                        )
                                    )
                                },
                                onLaunchAgain = { permission ->
                                    permissionLauncher.launch(permission.toPermission())
                                }
                            )
                        }
                    )
                }
            )
        }

        if (state.isMessengersSheetVisible) {

            AppFilter(
                elements = state.bankMessengers,
                onDismiss = {
                    proceedIntent(
                        IHelpScreenIntent.UpdateMessengersSheetVisibility
                    )
                },
                itemContent = { messenger ->
                    BankMessengerItem(
                        modifier = Modifier
                            .padding(HelpScreenFilterElementTopPadding)
                            .fillMaxWidth()
                            .clickable {
                                proceedIntent(
                                    IHelpScreenIntent.OpenMessenger(messenger)
                                )
                            },
                        messenger = messenger
                    )
                }
            )
        }

        if (state.isBankInfoSheetVisible) {

            AppFilter(
                elements = state.bankInfoCategories,
                onDismiss = {
                    proceedIntent(
                        IHelpScreenIntent.UpdateBankInfoSheetVisibility
                    )
                },
                itemContent = { infoCategory ->
                    BankInfoItem(
                        modifier = Modifier
                            .padding(HelpScreenFilterElementTopPadding)
                            .fillMaxWidth(),
                        infoCategory = infoCategory
                    )
                }
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
@Preview
private fun HelpScreenUiPreview() {

    HelpScreenUi(
        isBackButtonNeeded = false,
        state = HelpScreenState(),
        proceedIntent = {}
    )
}