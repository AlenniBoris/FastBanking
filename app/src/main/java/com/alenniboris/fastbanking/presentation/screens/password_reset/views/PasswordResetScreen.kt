package com.alenniboris.fastbanking.presentation.screens.password_reset.views

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
import com.alenniboris.fastbanking.presentation.screens.password_reset.IPasswordResetScreenEvent
import com.alenniboris.fastbanking.presentation.screens.password_reset.IPasswordResetScreenIntent
import com.alenniboris.fastbanking.presentation.screens.password_reset.PasswordResetScreenViewModel
import com.alenniboris.fastbanking.presentation.screens.password_reset.state.PasswordResetProcessPart
import com.alenniboris.fastbanking.presentation.screens.password_reset.state.PasswordResetScreenState
import com.alenniboris.fastbanking.presentation.uikit.theme.FilterTextPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.FilterTextSize
import com.alenniboris.fastbanking.presentation.uikit.theme.RegistrationAsAppClientContinueButtonPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.RegistrationAsAppClientProgressBarPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.appColor
import com.alenniboris.fastbanking.presentation.uikit.theme.appFilterTextColor
import com.alenniboris.fastbanking.presentation.uikit.theme.bodyStyle
import com.alenniboris.fastbanking.presentation.uikit.values.LoginScreenRoute
import com.alenniboris.fastbanking.presentation.uikit.values.PasswordResetScreenRoute
import com.alenniboris.fastbanking.presentation.uikit.values.toDocumentTypeString
import com.alenniboris.fastbanking.presentation.uikit.views.AppCustomButton
import com.alenniboris.fastbanking.presentation.uikit.views.AppFilter
import com.alenniboris.fastbanking.presentation.uikit.views.AppProcessProgressBar
import com.alenniboris.fastbanking.presentation.uikit.views.AppProgressBar
import com.alenniboris.fastbanking.presentation.uikit.views.AppTopBar
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
@Destination(route = PasswordResetScreenRoute)
fun PasswordResetScreen(
    navigator: DestinationsNavigator
) {

    val viewModel = koinViewModel<PasswordResetScreenViewModel>()
    val state by viewModel.state.collectAsStateWithLifecycle()
    val event by remember { mutableStateOf(viewModel.event) }
    val proceedIntent by remember { mutableStateOf(viewModel::proceedIntent) }
    val context = LocalContext.current
    var toastMessage by remember {
        mutableStateOf(
            Toast.makeText(context, "", Toast.LENGTH_SHORT)
        )
    }

    LaunchedEffect(event) {

        launch {
            event.filterIsInstance<IPasswordResetScreenEvent.ShowToastMessage>().collect { coming ->
                toastMessage?.cancel()
                toastMessage =
                    Toast.makeText(context, context.getString(coming.messageId), Toast.LENGTH_SHORT)
                toastMessage?.show()
            }
        }

        launch {
            event.filterIsInstance<IPasswordResetScreenEvent.NavigateBack>().collect {
                navigator.popBackStack(
                    route = PasswordResetScreenRoute,
                    inclusive = true
                )
            }
        }

        launch {
            event.filterIsInstance<IPasswordResetScreenEvent.NavigateToLoginPage>().collect {
                toastMessage?.cancel()
                toastMessage =
                    Toast.makeText(
                        context,
                        context.getString(R.string.password_reset_complete_text),
                        Toast.LENGTH_SHORT
                    )
                toastMessage?.show()
                navigator.popBackStack(
                    route = LoginScreenRoute,
                    inclusive = false
                )
            }
        }
    }

    PasswordResetScreenUi(
        state = state,
        proceedIntent = proceedIntent
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PasswordResetScreenUi(
    state: PasswordResetScreenState,
    proceedIntent: (IPasswordResetScreenIntent) -> Unit
) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(appColor)
    ) {

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            AppTopBar(
                modifier = Modifier.fillMaxWidth(),
                leftBtnPainter = painterResource(R.drawable.back_icon),
                onLeftBtnClicked = {
                    proceedIntent(IPasswordResetScreenIntent.DoBackwardAction)
                },
                headerTextString = stringResource(R.string.password_reset_text)
            )

            AppProcessProgressBar(
                modifier = Modifier
                    .padding(RegistrationAsAppClientProgressBarPadding)
                    .fillMaxWidth(),
                currentProcess = state.currentProcessPart,
                allProcesses = state.resettingProcessParts
            )

            if (state.isLoading) {
                AppProgressBar(
                    modifier = Modifier.fillMaxSize()
                )
            } else {
                when (state.currentProcessPart) {
                    PasswordResetProcessPart.DocumentInput ->
                        DocumentInputUi(
                            modifier = Modifier
                                .fillMaxSize()
                                .weight(1f),
                            state = state.dataInputPartState,
                            proceedIntent = proceedIntent
                        )

                    PasswordResetProcessPart.CheckingCode ->
                        CheckingCodeUi(
                            modifier = Modifier
                                .fillMaxSize()
                                .weight(1f),
                            enteredCode = state.enteredCode,
                            proceedIntent = proceedIntent
                        )

                    PasswordResetProcessPart.PasswordChange ->
                        PasswordChangeUi(
                            modifier = Modifier
                                .fillMaxSize()
                                .weight(1f),
                            state = state.passwordChangePartState,
                            proceedIntent = proceedIntent
                        )

                    PasswordResetProcessPart.ConfirmPage ->
                        ConfirmPageUi(
                            modifier = Modifier
                                .fillMaxSize()
                                .weight(1f)
                        )
                }
            }
        }

        AppCustomButton(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(RegistrationAsAppClientContinueButtonPadding)
                .fillMaxWidth(),
            isClickable = !state.isLoading,
            onClick = {
                proceedIntent(
                    IPasswordResetScreenIntent.DoForwardAction
                )
            },
            text = stringResource(R.string.continue_text)
        )

        if (state.isOptionsBottomSheetVisible) {
            AppFilter(
                elements = state.dataInputPartState.allPossibleDocumentTypes,
                onDismiss = {
                    proceedIntent(
                        IPasswordResetScreenIntent.UpdateOptionsBottomSheetVisibility
                    )
                },
                itemContent = { document ->
                    Text(
                        modifier = Modifier
                            .padding(FilterTextPadding)
                            .clickable {
                                proceedIntent(
                                    IPasswordResetScreenIntent.UpdateRegistrationDocumentType(
                                        document
                                    )
                                )
                            },
                        text = stringResource(document.toDocumentTypeString()),
                        style = bodyStyle.copy(
                            fontWeight = if (document == state.dataInputPartState.selectedDocument) {
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
private fun PasswordResetScreenUiPreview() {
    PasswordResetScreenUi(
        state = PasswordResetScreenState(),
        proceedIntent = {}
    )
}