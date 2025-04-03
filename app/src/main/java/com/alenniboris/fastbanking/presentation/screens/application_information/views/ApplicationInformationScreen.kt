package com.alenniboris.fastbanking.presentation.screens.application_information.views

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.alenniboris.fastbanking.R
import com.alenniboris.fastbanking.domain.model.bank_info.ApplicationInfoModelDomain
import com.alenniboris.fastbanking.presentation.screens.application_information.ApplicationInformationScreenState
import com.alenniboris.fastbanking.presentation.screens.application_information.ApplicationInformationScreenViewModel
import com.alenniboris.fastbanking.presentation.screens.application_information.IApplicationInformationScreenEvent
import com.alenniboris.fastbanking.presentation.screens.application_information.IApplicationInformationScreenIntent
import com.alenniboris.fastbanking.presentation.uikit.theme.ApplicationInformationScreenContentPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.ApplicationInformationScreenHeaderTextSize
import com.alenniboris.fastbanking.presentation.uikit.theme.ApplicationInformationScreenInfoSectionPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.ApplicationInformationScreenInfoSectionTextPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.ApplicationInformationScreenTextSize
import com.alenniboris.fastbanking.presentation.uikit.theme.TopBarPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.appColor
import com.alenniboris.fastbanking.presentation.uikit.theme.appTopBarElementsColor
import com.alenniboris.fastbanking.presentation.uikit.theme.bodyStyle
import com.alenniboris.fastbanking.presentation.uikit.values.ApplicationInformationScreenRoute
import com.alenniboris.fastbanking.presentation.uikit.views.AppProgressBar
import com.alenniboris.fastbanking.presentation.uikit.views.AppTopBar
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import java.util.Calendar

@Composable
@Destination(route = ApplicationInformationScreenRoute)
fun ApplicationInformationScreen(
    navigator: DestinationsNavigator
) {

    val viewModel = koinViewModel<ApplicationInformationScreenViewModel>()
    val state by viewModel.state.collectAsStateWithLifecycle()
    val event by remember { mutableStateOf(viewModel.event) }
    val context = LocalContext.current
    var toastMessage by remember {
        mutableStateOf(
            Toast.makeText(context, "", Toast.LENGTH_SHORT)
        )
    }
    val proceedIntent by remember { mutableStateOf(viewModel::proceedIntent) }

    LaunchedEffect(event) {

        launch {
            event.filterIsInstance<IApplicationInformationScreenEvent.ShowToastMessage>()
                .collect { coming ->
                    toastMessage?.cancel()
                    toastMessage = Toast.makeText(
                        context,
                        context.getString(coming.messageId),
                        Toast.LENGTH_SHORT
                    )
                    toastMessage?.show()
                }
        }

        launch {
            event.filterIsInstance<IApplicationInformationScreenEvent.NavigateBack>().collect {
                navigator.popBackStack(
                    route = ApplicationInformationScreenRoute,
                    inclusive = true
                )
            }
        }
    }

    ApplicationInformationScreenUi(
        state = state,
        proceedIntent = proceedIntent
    )
}

@Composable
private fun ApplicationInformationScreenUi(
    state: ApplicationInformationScreenState,
    proceedIntent: (IApplicationInformationScreenIntent) -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(appColor)
    ) {

        AppTopBar(
            modifier = Modifier
                .padding(TopBarPadding)
                .fillMaxWidth(),
            headerTextString = stringResource(R.string.application_info_screen_header_text),
            leftBtnPainter = painterResource(R.drawable.back_icon),
            onLeftBtnClicked = {
                proceedIntent(IApplicationInformationScreenIntent.NavigateBack)
            }
        )

        if (state.isLoading) {
            AppProgressBar(
                modifier = Modifier.fillMaxSize()
            )
        } else {
            Column(
                modifier = Modifier
                    .padding(ApplicationInformationScreenContentPadding)
                    .fillMaxSize()
            ) {

                InformationSectionUi(
                    modifier = Modifier
                        .padding(ApplicationInformationScreenInfoSectionPadding)
                        .fillMaxWidth(),
                    sectionHeader = stringResource(R.string.application_version_header_text),
                    sectionText = state.versionText ?: stringResource(R.string.no_info_text)
                )

                InformationSectionUi(
                    modifier = Modifier
                        .padding(ApplicationInformationScreenInfoSectionPadding)
                        .fillMaxWidth(),
                    sectionHeader = stringResource(R.string.application_date_header_text),
                    sectionText = state.dateText ?: stringResource(R.string.no_info_text)
                )

                InformationSectionUi(
                    modifier = Modifier
                        .fillMaxWidth()
                        .verticalScroll(rememberScrollState()),
                    sectionHeader = stringResource(R.string.application_updates_header_text),
                    sectionText = state.updatesText ?: stringResource(R.string.no_info_text)
                )
            }
        }
    }
}

@Composable
private fun InformationSectionUi(
    modifier: Modifier = Modifier,
    sectionHeader: String,
    sectionText: String
) {

    Column(
        modifier = modifier
    ) {

        Text(
            text = sectionHeader,
            style = bodyStyle.copy(
                color = appTopBarElementsColor,
                fontWeight = FontWeight.Bold,
                fontSize = ApplicationInformationScreenHeaderTextSize
            )
        )

        Text(
            modifier = Modifier.padding(ApplicationInformationScreenInfoSectionTextPadding),
            text = sectionText,
            style = bodyStyle.copy(
                color = appTopBarElementsColor,
                fontSize = ApplicationInformationScreenTextSize
            )
        )
    }
}

@Composable
@Preview
private fun ApplicationInformationScreenUiPreview() {
    ApplicationInformationScreenUi(
        state = ApplicationInformationScreenState(
            appInfo = ApplicationInfoModelDomain(
                applicationVersion = "1.0.0",
                updateDate = Calendar.getInstance().time,
                versionUpdates = "dkjdmdmc|dshjnckjsdn|||ksjcnkjsdnckjd"
            ),
            isLoading = false
        ),
        proceedIntent = {}
    )

//    ApplicationInformationScreenUi(
//        state = ApplicationInformationScreenState(
//            appInfo = null,
//            isLoading = false
//        ),
//        proceedIntent = {}
//    )

//    ApplicationInformationScreenUi(
//        state = ApplicationInformationScreenState(
//            appInfo = null,
//            isLoading = true
//        ),
//        proceedIntent = {}
//    )
}