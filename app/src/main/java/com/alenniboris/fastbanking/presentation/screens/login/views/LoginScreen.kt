package com.alenniboris.fastbanking.presentation.screens.login.views

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import com.alenniboris.fastbanking.presentation.screens.destinations.RegistrationOptionsScreenDestination
import com.alenniboris.fastbanking.presentation.screens.login.ILoginScreenEvent
import com.alenniboris.fastbanking.presentation.screens.login.ILoginScreenIntent
import com.alenniboris.fastbanking.presentation.screens.login.LoginScreenState
import com.alenniboris.fastbanking.presentation.screens.login.LoginScreenViewModel
import com.alenniboris.fastbanking.presentation.uikit.theme.EnterValueTextFieldPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.EnterValueTextFieldShape
import com.alenniboris.fastbanking.presentation.uikit.theme.LoginScreenBottomTextPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.LoginScreenButtonTextSize
import com.alenniboris.fastbanking.presentation.uikit.theme.LoginScreenContainerPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.LoginScreenDoubleSpacer
import com.alenniboris.fastbanking.presentation.uikit.theme.LoginScreenHeaderPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.LoginScreenSpacer
import com.alenniboris.fastbanking.presentation.uikit.theme.TopBarHeaderTextSize
import com.alenniboris.fastbanking.presentation.uikit.theme.appColor
import com.alenniboris.fastbanking.presentation.uikit.theme.appTopBarElementsColor
import com.alenniboris.fastbanking.presentation.uikit.theme.bodyStyle
import com.alenniboris.fastbanking.presentation.uikit.theme.enterTextFieldColor
import com.alenniboris.fastbanking.presentation.uikit.theme.enterTextFieldTextColor
import com.alenniboris.fastbanking.presentation.uikit.values.LoginScreenRoute
import com.alenniboris.fastbanking.presentation.uikit.views.AppTextField
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@RootNavGraph(start = true)
@Destination(route = LoginScreenRoute)
@Composable
fun LoginScreen(
    navigator: DestinationsNavigator
) {

    val viewModel = koinViewModel<LoginScreenViewModel>()
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
            event.filterIsInstance<ILoginScreenEvent.ShowToastMessage>().collect { coming ->
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
            event.filterIsInstance<ILoginScreenEvent.NavigateToRegistrationOptionsScreen>()
                .collect {
                    navigator.navigate(RegistrationOptionsScreenDestination)
                }
        }
    }

    LoginScreenUi(
        state = state,
        proceedIntent = proceedIntent
    )
}

@Composable
private fun LoginScreenUi(
    state: LoginScreenState,
    proceedIntent: (ILoginScreenIntent) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(appColor)
            .padding(LoginScreenContainerPadding)
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(R.drawable.bank_logo),
                    contentDescription = stringResource(R.string.login_icon_description)
                )
                Text(
                    modifier = Modifier.padding(LoginScreenHeaderPadding),
                    text = stringResource(R.string.bank_name),
                    style = bodyStyle.copy(
                        fontSize = TopBarHeaderTextSize,
                        fontWeight = FontWeight.Bold
                    ),
                    color = appTopBarElementsColor
                )
            }

            Spacer(modifier = Modifier.height(LoginScreenDoubleSpacer))

            AppTextField(
                modifier = Modifier
                    .background(
                        color = enterTextFieldColor,
                        shape = EnterValueTextFieldShape
                    )
                    .padding(EnterValueTextFieldPadding)
                    .fillMaxWidth(),
                value = state.login,
                onValueChanged = { newLogin ->
                    proceedIntent(ILoginScreenIntent.UpdateLogin(newLogin = newLogin))
                },
                placeholder = stringResource(R.string.login_placeholder_text)
            )

            Spacer(modifier = Modifier.height(LoginScreenSpacer))

            AppTextField(
                modifier = Modifier
                    .background(
                        color = enterTextFieldColor,
                        shape = EnterValueTextFieldShape
                    )
                    .padding(EnterValueTextFieldPadding)
                    .fillMaxWidth(),
                value = state.password,
                onValueChanged = { newPassword ->
                    proceedIntent(ILoginScreenIntent.UpdatePassword(newPassword = newPassword))
                },
                placeholder = stringResource(R.string.password_placeholder_text),
                isPasswordField = true,
                isPasswordVisible = state.isPasswordVisible,
                onPasswordVisibilityChange = {
                    proceedIntent(ILoginScreenIntent.UpdatePasswordVisibility)
                }
            )

            Spacer(modifier = Modifier.height(LoginScreenDoubleSpacer))

            TextButton(
                modifier = Modifier
                    .background(
                        color = enterTextFieldColor,
                        shape = EnterValueTextFieldShape
                    ),
                onClick = {
                    proceedIntent(ILoginScreenIntent.LoginIntoBanking)
                }
            ) {
                Text(
                    text = stringResource(R.string.login_text),
                    color = enterTextFieldTextColor,
                    style = bodyStyle.copy(
                        fontSize = LoginScreenButtonTextSize
                    )
                )
            }

        }

        Text(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(LoginScreenBottomTextPadding)
                .clickable {
                    proceedIntent(ILoginScreenIntent.StartRegistration)
                },
            text = stringResource(R.string.register_text),
            style = bodyStyle.copy(
                fontSize = LoginScreenButtonTextSize,
                fontWeight = FontWeight.Medium
            ),
            color = appTopBarElementsColor
        )
    }
}

@Composable
@Preview
private fun LoginScreenPreview() {
    LoginScreenUi(
        state = LoginScreenState(),
        proceedIntent = {}
    )
}