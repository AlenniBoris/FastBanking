package com.alenniboris.fastbanking.presentation.screens.registration.registration_as_app_client.views

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
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
import com.alenniboris.fastbanking.presentation.screens.registration.registration_as_app_client.IRegistrationAsAppClientScreenEvent
import com.alenniboris.fastbanking.presentation.screens.registration.registration_as_app_client.IRegistrationAsAppClientScreenIntent
import com.alenniboris.fastbanking.presentation.screens.registration.registration_as_app_client.RegistrationAsAppClientScreenViewModel
import com.alenniboris.fastbanking.presentation.screens.registration.registration_as_app_client.state.RegistrationAsAppClientScreenState
import com.alenniboris.fastbanking.presentation.screens.registration.registration_as_app_client.state.values.RegistrationAsAppClientProcessPart
import com.alenniboris.fastbanking.presentation.screens.registration.registration_as_app_client.state.values.toDocumentTypeString
import com.alenniboris.fastbanking.presentation.uikit.theme.FilterTextPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.FilterTextSize
import com.alenniboris.fastbanking.presentation.uikit.theme.RegistrationAsAppClientContinueButtonPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.RegistrationAsAppClientProgressBarPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.appColor
import com.alenniboris.fastbanking.presentation.uikit.theme.appFilterTextColor
import com.alenniboris.fastbanking.presentation.uikit.theme.bodyStyle
import com.alenniboris.fastbanking.presentation.uikit.values.RegistrationAsAppClientScreenRoute
import com.alenniboris.fastbanking.presentation.uikit.views.AppCustomButton
import com.alenniboris.fastbanking.presentation.uikit.views.AppFilter
import com.alenniboris.fastbanking.presentation.uikit.views.AppProcessProgressBar
import com.alenniboris.fastbanking.presentation.uikit.views.AppTopBar
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@RootNavGraph
@Destination(route = RegistrationAsAppClientScreenRoute)
@Composable
fun RegistrationAsAppClientScreen(
    navigator: DestinationsNavigator
) {

    val viewModel = koinViewModel<RegistrationAsAppClientScreenViewModel>()
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
            event.filterIsInstance<IRegistrationAsAppClientScreenEvent.ShowToastMessage>()
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
            event.filterIsInstance<IRegistrationAsAppClientScreenEvent.NavigateToPreviousScreen>()
                .collect {
                    navigator.popBackStack(
                        route = RegistrationAsAppClientScreenRoute,
                        inclusive = true
                    )
                }
        }
    }

    RegistrationAsAppClientScreenUi(
        state = state,
        proceedIntent = proceedIntent
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun RegistrationAsAppClientScreenUi(
    state: RegistrationAsAppClientScreenState,
    proceedIntent: (IRegistrationAsAppClientScreenIntent) -> Unit
) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(appColor),
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            AppTopBar(
                modifier = Modifier.fillMaxWidth(),
                leftBtnPainter = painterResource(R.drawable.back_icon),
                onLeftBtnClicked = {
                    proceedIntent(IRegistrationAsAppClientScreenIntent.DoBackwardAction)
                },
                headerTextString = stringResource(R.string.registration_text)
            )

            AppProcessProgressBar(
                modifier = Modifier
                    .padding(RegistrationAsAppClientProgressBarPadding)
                    .fillMaxWidth(),
                currentProcess = state.currentProcessPart,
                allProcesses = state.processPartList
            )

            when (state.currentProcessPart) {
                RegistrationAsAppClientProcessPart.DataInput -> {
                    DataInputProcessUi(
                        modifier = Modifier
                            .fillMaxSize()
                            .weight(1f),
                        state = state.dataInputPartState,
                        proceedIntent = proceedIntent
                    )
                }

                RegistrationAsAppClientProcessPart.PhoneNumberInput -> {
                    PhoneNumberInputProcessUi(
                        modifier = Modifier
                            .fillMaxSize()
                            .weight(1f),
                        state = state.phoneNumberInputPartState,
                        proceedIntent = proceedIntent
                    )
                }

                RegistrationAsAppClientProcessPart.PhoneCodeCheck -> {
                    PhoneCodeCheckProcessUi(
                        modifier = Modifier
                            .fillMaxSize()
                            .weight(1f),
                        state = state.phoneCodeCheckPartState,
                        proceedIntent = proceedIntent
                    )
                }

                RegistrationAsAppClientProcessPart.SettingPassword -> {
                    SettingPasswordProcessUi(
                        modifier = Modifier
                            .fillMaxSize()
                            .weight(1f),
                        state = state.settingPasswordPartState,
                        proceedIntent = proceedIntent
                    )
                }

                RegistrationAsAppClientProcessPart.ConfirmPage -> {
                    ConfirmPageProcessUi(
                        modifier = Modifier
                            .fillMaxSize()
                            .weight(1f)
                    )
                }
            }

            AppCustomButton(
                modifier = Modifier
                    .padding(RegistrationAsAppClientContinueButtonPadding)
                    .fillMaxWidth(),
                onClick = {
                    proceedIntent(
                        IRegistrationAsAppClientScreenIntent.DoForwardAction
                    )
                },
                text = stringResource(R.string.continue_text)
            )
        }

        if (state.isOptionsBottomSheetVisible) {
            AppFilter(
                elements = state.dataInputPartState.possibleRegistrationDocuments,
                onDismiss = {
                    proceedIntent(
                        IRegistrationAsAppClientScreenIntent.UpdateOptionsBottomSheetVisibility
                    )
                },
                itemContent = { document ->
                    Text(
                        modifier = Modifier
                            .padding(FilterTextPadding)
                            .clickable {
                                proceedIntent(
                                    IRegistrationAsAppClientScreenIntent.UpdateRegistrationDocumentType(
                                        document
                                    )
                                )
                            },
                        text = stringResource(document.toDocumentTypeString()),
                        style = bodyStyle.copy(
                            fontWeight = if (document == state.dataInputPartState.registrationDocumentType) {
                                FontWeight.Bold
                            } else {
                                FontWeight.Normal
                            },
                            color = appFilterTextColor,
                            fontSize = FilterTextSize
                        )
                    )
                }
            )
        }
    }
}

@Composable
@Preview
private fun RegistrationAsAppClientScreenPreview() {
    RegistrationAsAppClientScreenUi(
        state = RegistrationAsAppClientScreenState(),
        proceedIntent = {}
    )
}