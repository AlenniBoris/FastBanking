package com.alenniboris.fastbanking.presentation.screens.additions.views

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
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
import com.alenniboris.fastbanking.presentation.screens.additions.AdditionsCategoriesAction
import com.alenniboris.fastbanking.presentation.screens.additions.AdditionsScreenCategory
import com.alenniboris.fastbanking.presentation.screens.additions.AdditionsScreenState
import com.alenniboris.fastbanking.presentation.screens.additions.AdditionsScreenViewModel
import com.alenniboris.fastbanking.presentation.screens.additions.AuthorizedAdditionsCategories
import com.alenniboris.fastbanking.presentation.screens.additions.IAdditionsScreenEvent
import com.alenniboris.fastbanking.presentation.screens.additions.IAdditionsScreenIntent
import com.alenniboris.fastbanking.presentation.screens.additions.toListOfActions
import com.alenniboris.fastbanking.presentation.screens.additions.toUiIcon
import com.alenniboris.fastbanking.presentation.screens.additions.toUiString
import com.alenniboris.fastbanking.presentation.screens.destinations.ApplicationInformationScreenDestination
import com.alenniboris.fastbanking.presentation.screens.destinations.AtmMapScreenDestination
import com.alenniboris.fastbanking.presentation.screens.destinations.BankNewsScreenDestination
import com.alenniboris.fastbanking.presentation.screens.destinations.CurrencyScreenDestination
import com.alenniboris.fastbanking.presentation.screens.destinations.RegistrationOptionsScreenDestination
import com.alenniboris.fastbanking.presentation.uikit.theme.AdditionsScreenActionPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.AdditionsScreenCategoryHeaderSize
import com.alenniboris.fastbanking.presentation.uikit.theme.AdditionsScreenCategoryPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.AdditionsScreenCategoryTextSize
import com.alenniboris.fastbanking.presentation.uikit.theme.AdditionsScreenCategoryTextStartPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.AdditionsScreenCategoryZeroPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.AdditionsScreenContentPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.TopBarPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.appColor
import com.alenniboris.fastbanking.presentation.uikit.theme.appTopBarElementsColor
import com.alenniboris.fastbanking.presentation.uikit.theme.bodyStyle
import com.alenniboris.fastbanking.presentation.uikit.values.AdditionsScreenRoute
import com.alenniboris.fastbanking.presentation.uikit.views.AppTopBar
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
@Destination(route = AdditionsScreenRoute)
fun AdditionsScreen(
    isUserAuthenticated: Boolean = false,
    navigator: DestinationsNavigator
) {

    val viewModel = koinViewModel<AdditionsScreenViewModel>()
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
            event.filterIsInstance<IAdditionsScreenEvent.ShowToastMessage>().collect { coming ->
                toastMessage?.cancel()
                toastMessage =
                    Toast.makeText(context, context.getString(coming.messageId), Toast.LENGTH_SHORT)
                toastMessage?.show()
            }
        }

        launch {
            event.filterIsInstance<IAdditionsScreenEvent.OpenMapPage>().collect {
                navigator.navigate(
                    AtmMapScreenDestination(isBackButtonNeeded = true)
                )
            }
        }

        launch {
            event.filterIsInstance<IAdditionsScreenEvent.OpenPraisePage>().collect {
                toastMessage?.cancel()
                toastMessage =
                    Toast.makeText(
                        context,
                        context.getString(R.string.in_development_text),
                        Toast.LENGTH_SHORT
                    )
                toastMessage?.show()
            }
        }

        launch {
            event.filterIsInstance<IAdditionsScreenEvent.OpenCurrencyExchangePage>().collect {
                navigator.navigate(
                    CurrencyScreenDestination(isBackButtonNeeded = true)
                )
            }
        }

        launch {
            event.filterIsInstance<IAdditionsScreenEvent.OpenPasswordRecoveryPage>().collect {
                toastMessage?.cancel()
                toastMessage =
                    Toast.makeText(
                        context,
                        context.getString(R.string.in_development_text),
                        Toast.LENGTH_SHORT
                    )
                toastMessage?.show()
            }
        }

        launch {
            event.filterIsInstance<IAdditionsScreenEvent.OpenRegistrationOptionsPage>().collect {
                navigator.navigate(RegistrationOptionsScreenDestination)
            }
        }

        launch {
            event.filterIsInstance<IAdditionsScreenEvent.OpenApplicationInformationPage>().collect {
                navigator.navigate(
                    ApplicationInformationScreenDestination
                )
            }
        }

        launch {
            event.filterIsInstance<IAdditionsScreenEvent.OpenBankNewsPage>().collect {
                navigator.navigate(
                    BankNewsScreenDestination
                )
            }
        }
    }

    AdditionsScreenUi(
        isUserAuthenticated = isUserAuthenticated,
        state = state,
        proceedIntent = proceedIntent
    )
}

@Composable
private fun AdditionsScreenUi(
    isUserAuthenticated: Boolean,
    state: AdditionsScreenState,
    proceedIntent: (IAdditionsScreenIntent) -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(appColor)
            .padding(AdditionsScreenContentPadding)
    ) {

        AppTopBar(
            modifier = Modifier
                .fillMaxWidth()
                .background(appColor)
                .padding(TopBarPadding),
            headerTextString = stringResource(R.string.additions_screen_header_text)
        )

        AdditionsScreenContent(
            isUserAuthenticated = isUserAuthenticated,
            categories = if (isUserAuthenticated) state.authorizedCategories else state.notAuthorizedCategories,
            proceedIntent = proceedIntent
        )
    }
}

@Composable
private fun AdditionsScreenContent(
    isUserAuthenticated: Boolean,
    categories: List<AdditionsScreenCategory>,
    proceedIntent: (IAdditionsScreenIntent) -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {

        categories.forEachIndexed { index, category ->

            Column(
                modifier = Modifier.padding(
                    if (index == 0) AdditionsScreenCategoryZeroPadding
                    else AdditionsScreenCategoryPadding
                )
            ) {

                Text(
                    text = stringResource(category.toUiString()),
                    style = bodyStyle.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = AdditionsScreenCategoryHeaderSize,
                        color = appTopBarElementsColor
                    )
                )

                val listOfActions by remember {
                    mutableStateOf(
                        category.toListOfActions(
                            isUserAuthorized = isUserAuthenticated
                        )
                    )
                }

                listOfActions.forEach { action ->
                    AdditionsScreenActionContent(
                        action = action,
                        proceedIntent = proceedIntent
                    )
                }
            }
        }
    }
}

@Composable
private fun AdditionsScreenActionContent(
    action: AdditionsCategoriesAction,
    proceedIntent: (IAdditionsScreenIntent) -> Unit
) {

    Row(
        modifier = Modifier
            .padding(AdditionsScreenActionPadding)
            .fillMaxWidth()
            .clickable {
                proceedIntent(IAdditionsScreenIntent.ProceedAccordingToAction(action))
            },
        verticalAlignment = Alignment.CenterVertically
    ) {

        Icon(
            painter = painterResource(action.toUiIcon()),
            tint = appTopBarElementsColor,
            contentDescription = stringResource(action.toUiString())
        )

        Text(
            modifier = Modifier
                .weight(1f)
                .padding(AdditionsScreenCategoryTextStartPadding),
            text = stringResource(action.toUiString()),
            style = bodyStyle.copy(
                fontSize = AdditionsScreenCategoryTextSize,
                color = appTopBarElementsColor
            )
        )

        Icon(
            painter = painterResource(R.drawable.forward_icon),
            tint = appTopBarElementsColor,
            contentDescription = stringResource(action.toUiString())
        )
    }
}

@Composable
@Preview
private fun AdditionsScreenContentPreview() {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(appColor)
    ) {
//        AdditionsScreenContent(
//            isUserAuthenticated = false,
//            categories = NotAuthorizedAdditionsCategories,
//            proceedIntent = {}
//        )

        AdditionsScreenContent(
            isUserAuthenticated = true,
            categories = AuthorizedAdditionsCategories,
            proceedIntent = {}
        )
    }
}