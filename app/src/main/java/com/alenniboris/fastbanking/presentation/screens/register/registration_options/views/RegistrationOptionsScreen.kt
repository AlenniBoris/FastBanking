package com.alenniboris.fastbanking.presentation.screens.register.registration_options.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.alenniboris.fastbanking.R
import com.alenniboris.fastbanking.presentation.screens.destinations.RegistrationAsAppClientScreenDestination
import com.alenniboris.fastbanking.presentation.screens.register.registration_options.IRegistrationOptionsScreenEvent
import com.alenniboris.fastbanking.presentation.screens.register.registration_options.IRegistrationOptionsScreenIntent
import com.alenniboris.fastbanking.presentation.screens.register.registration_options.RegistrationOptionsScreenViewModel
import com.alenniboris.fastbanking.presentation.uikit.theme.BackButtonPicture
import com.alenniboris.fastbanking.presentation.uikit.theme.RegistrationOptionsScreenColumnOnlyHorizontalPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.RegistrationOptionsScreenRegistrationTypeTextSize
import com.alenniboris.fastbanking.presentation.uikit.theme.RegistrationOptionsScreenSpacerDoubleHeight
import com.alenniboris.fastbanking.presentation.uikit.theme.RegistrationOptionsScreenSpacerHeight
import com.alenniboris.fastbanking.presentation.uikit.theme.RegistrationOptionsScreenTypeDescriptionTextSize
import com.alenniboris.fastbanking.presentation.uikit.theme.appColor
import com.alenniboris.fastbanking.presentation.uikit.theme.appTopBarElementsColor
import com.alenniboris.fastbanking.presentation.uikit.theme.bodyStyle
import com.alenniboris.fastbanking.presentation.uikit.values.RegistrationOptionsScreenRoute
import com.alenniboris.fastbanking.presentation.uikit.views.AppCustomButton
import com.alenniboris.fastbanking.presentation.uikit.views.AppTopBar
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@RootNavGraph
@Destination(route = RegistrationOptionsScreenRoute)
@Composable
fun RegistrationOptionsScreen(
    navigator: DestinationsNavigator
) {

    val viewModel = koinViewModel<RegistrationOptionsScreenViewModel>()
    val event by remember { mutableStateOf(viewModel.event) }
    val proceedIntent by remember { mutableStateOf(viewModel::proceedIntent) }

    LaunchedEffect(event) {
        launch {
            event.filterIsInstance<IRegistrationOptionsScreenEvent.NavigateToPreviousScreen>()
                .collect {
                    navigator.popBackStack()
                }
        }

        launch {
            event.filterIsInstance<IRegistrationOptionsScreenEvent.NavigateToRegistrationAsAppClient>()
                .collect {
                    navigator.navigate(RegistrationAsAppClientScreenDestination)
                }
        }
    }

    RegistrationOptionsScreenUi(
        proceedIntent = proceedIntent
    )
}

@Composable
private fun RegistrationOptionsScreenUi(
    proceedIntent: (IRegistrationOptionsScreenIntent) -> Unit
) {

    Box(
        modifier = Modifier
            .background(appColor)
            .fillMaxSize()
    ) {

        AppTopBar(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .fillMaxWidth(),
            headerTextString = stringResource(R.string.registration_text),
            leftBtnPainter = painterResource(BackButtonPicture),
            onLeftBtnClicked = {
                proceedIntent(IRegistrationOptionsScreenIntent.NavigateToPreviousScreen)
            }
        )

        Column(
            modifier = Modifier
                .padding(RegistrationOptionsScreenColumnOnlyHorizontalPadding)
                .fillMaxWidth()
                .align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Image(
                painter = painterResource(R.drawable.register_screen_icon),
                contentDescription = stringResource(R.string.login_icon_description)
            )

            Spacer(modifier = Modifier.height(RegistrationOptionsScreenSpacerHeight))

            Text(
                text = stringResource(R.string.registration_choose_type_text),
                style = bodyStyle.copy(
                    fontSize = RegistrationOptionsScreenRegistrationTypeTextSize,
                ),
                color = appTopBarElementsColor
            )

            Spacer(modifier = Modifier.height(RegistrationOptionsScreenSpacerDoubleHeight))

            AppCustomButton(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(R.string.being_bank_client_text),
                onClick = {
                    proceedIntent(IRegistrationOptionsScreenIntent.NavigateToRegistrationAsAppClient)
                }
            )

            Spacer(modifier = Modifier.height(RegistrationOptionsScreenSpacerHeight))

            Text(
                text = stringResource(R.string.being_bank_client_description_text),
                textAlign = TextAlign.Center,
                style = bodyStyle.copy(
                    fontSize = RegistrationOptionsScreenTypeDescriptionTextSize,
                ),
                color = appTopBarElementsColor
            )
        }
    }
}

@Composable
@Preview
private fun RegistrationOptionsScreenScreenPreview() {
    RegistrationOptionsScreenUi(
        proceedIntent = {}
    )
}