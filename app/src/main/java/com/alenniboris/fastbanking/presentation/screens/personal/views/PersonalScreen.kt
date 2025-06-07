package com.alenniboris.fastbanking.presentation.screens.personal.views

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
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
import com.alenniboris.fastbanking.domain.model.user.UserGender
import com.alenniboris.fastbanking.domain.model.user.UserModelDomain
import com.alenniboris.fastbanking.presentation.model.user.toModelUi
import com.alenniboris.fastbanking.presentation.screens.destinations.BaseCurrencySettingsScreenDestination
import com.alenniboris.fastbanking.presentation.screens.destinations.LanguageSettingsScreenDestination
import com.alenniboris.fastbanking.presentation.screens.destinations.PersonalDetailsScreenDestination
import com.alenniboris.fastbanking.presentation.screens.destinations.ThemeSettingsScreenDestination
import com.alenniboris.fastbanking.presentation.screens.destinations.UserAppAccountSettingsScreenDestination
import com.alenniboris.fastbanking.presentation.screens.destinations.UserAppliancesScreenDestination
import com.alenniboris.fastbanking.presentation.screens.personal.IPersonalScreenEvent
import com.alenniboris.fastbanking.presentation.screens.personal.IPersonalScreenIntent
import com.alenniboris.fastbanking.presentation.screens.personal.PersonalScreenCategories
import com.alenniboris.fastbanking.presentation.screens.personal.PersonalScreenState
import com.alenniboris.fastbanking.presentation.screens.personal.PersonalScreenViewModel
import com.alenniboris.fastbanking.presentation.screens.personal.toUiString
import com.alenniboris.fastbanking.presentation.uikit.theme.EmptyScreenFontSize
import com.alenniboris.fastbanking.presentation.uikit.theme.EmptyScreenSpacerHeight
import com.alenniboris.fastbanking.presentation.uikit.theme.FastBankingTheme
import com.alenniboris.fastbanking.presentation.uikit.theme.PersonalScreenButtonRowPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.PersonalScreenContentPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.PersonalScreenUserTitlePadding
import com.alenniboris.fastbanking.presentation.uikit.theme.PersonalScreenUserTitleTextSize
import com.alenniboris.fastbanking.presentation.uikit.theme.TopBarPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.appColor
import com.alenniboris.fastbanking.presentation.uikit.theme.appTopBarElementsColor
import com.alenniboris.fastbanking.presentation.uikit.theme.bodyStyle
import com.alenniboris.fastbanking.presentation.uikit.values.ClickableElement
import com.alenniboris.fastbanking.presentation.uikit.values.PersonalScreenRoute
import com.alenniboris.fastbanking.presentation.uikit.views.AppButtonRow
import com.alenniboris.fastbanking.presentation.uikit.views.AppTopBar
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import java.util.Calendar


@Destination(route = PersonalScreenRoute)
@Composable
fun PersonalScreen(
    navigator: DestinationsNavigator
) {

    val viewModel = koinViewModel<PersonalScreenViewModel>()
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
            event.filterIsInstance<IPersonalScreenEvent.NavigateBack>().collect {
                navigator.popBackStack()
            }
        }

        launch {
            event.filterIsInstance<IPersonalScreenEvent.ShowToastMessage>().collect { coming ->
                toastMessage?.cancel()
                toastMessage =
                    Toast.makeText(context, context.getString(coming.messageId), Toast.LENGTH_SHORT)
                toastMessage?.show()
            }
        }

        launch {
            event.filterIsInstance<IPersonalScreenEvent.OpenThemeSettingsScreen>().collect {
                navigator.navigate(ThemeSettingsScreenDestination)
            }
        }

        launch {
            event.filterIsInstance<IPersonalScreenEvent.OpenLanguageSettingsScreen>().collect {
                navigator.navigate(LanguageSettingsScreenDestination)
            }
        }

        launch {
            event.filterIsInstance<IPersonalScreenEvent.OpenBaseCurrencySettingsScreen>().collect {
                navigator.navigate(BaseCurrencySettingsScreenDestination)
            }
        }

        launch {
            event.filterIsInstance<IPersonalScreenEvent.OpenAccountDataScreen>().collect {
                navigator.navigate(UserAppAccountSettingsScreenDestination)
            }
        }

        launch {
            event.filterIsInstance<IPersonalScreenEvent.OpenPersonalDetailsScreen>().collect {
                navigator.navigate(PersonalDetailsScreenDestination)
            }
        }

        launch {
            event.filterIsInstance<IPersonalScreenEvent.OpenUserProductsAppliancesScreen>()
                .collect {
                    navigator.navigate(UserAppliancesScreenDestination)
                }
        }
    }

    PersonalScreenUi(
        state = state,
        proceedIntent = proceedIntent
    )
}

@Composable
private fun PersonalScreenUi(
    state: PersonalScreenState,
    proceedIntent: (IPersonalScreenIntent) -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(appColor)
    ) {

        AppTopBar(
            modifier = Modifier
                .fillMaxWidth()
                .padding(TopBarPadding),
            leftBtnPainter = painterResource(R.drawable.back_icon),
            onLeftBtnClicked = {
                proceedIntent(
                    IPersonalScreenIntent.NavigateBack
                )
            },
            rightBtnPainter = painterResource(R.drawable.sign_out_icon),
            onRightBtnClicked = {
                proceedIntent(
                    IPersonalScreenIntent.SignOut
                )
            }
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(PersonalScreenContentPadding)
        ) {

            if (state.user == null) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Image(
                        painter = painterResource(R.drawable.no_user_icon),
                        contentDescription = stringResource(R.string.exception_no_such_user)
                    )
                    Spacer(
                        modifier = Modifier.height(EmptyScreenSpacerHeight)
                    )
                    Text(
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        text = stringResource(R.string.exception_unknown),
                        color = appTopBarElementsColor,
                        style = bodyStyle.copy(
                            fontSize = EmptyScreenFontSize
                        )
                    )
                }
            } else {

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Image(
                        painter = painterResource(state.user.userPictureId),
                        contentDescription = stringResource(R.string.user_gender_text)
                    )

                    Text(
                        modifier = Modifier.padding(PersonalScreenUserTitlePadding),
                        text = state.user.userTitleText,
                        style = bodyStyle.copy(
                            color = appTopBarElementsColor,
                            fontWeight = FontWeight.Bold,
                            fontSize = PersonalScreenUserTitleTextSize
                        )
                    )

                    Text(
                        modifier = Modifier.padding(PersonalScreenUserTitlePadding),
                        text = state.user.domainModel.phoneNumber,
                        style = bodyStyle.copy(
                            color = appTopBarElementsColor,
                            fontSize = PersonalScreenUserTitleTextSize
                        )
                    )
                }

                AppButtonRow(
                    modifier = Modifier
                        .padding(PersonalScreenButtonRowPadding)
                        .fillMaxWidth(),
                    currentElement = ClickableElement(
                        text = stringResource(state.currentViewedCategory.toUiString()),
                        onClick = {}
                    ),
                    listOfElements = state.allPossibleCategories.map { category ->
                        ClickableElement(
                            text = stringResource(category.toUiString()),
                            onClick = {
                                proceedIntent(
                                    IPersonalScreenIntent.UpdateCurrentlyViewedCategory(category)
                                )
                            }
                        )
                    }
                )

                when (state.currentViewedCategory) {
                    PersonalScreenCategories.PROFILE -> {
                        ProfileCategoryActions(
                            proceedIntent = proceedIntent
                        )
                    }

                    PersonalScreenCategories.SETTING -> {
                        SettingsCategoryActions(
                            proceedIntent = proceedIntent
                        )
                    }
                }
            }
        }
    }
}

@Composable
@Preview
private fun LightTheme() {
    FastBankingTheme(
        darkTheme = false
    ) {
        Surface {
            PersonalScreenUi(
                state = PersonalScreenState(),
                proceedIntent = {}
            )

//            PersonalScreenUi(
//                state = PersonalScreenState(
//                    user = UserModelDomain(
//                        id = "111",
//                        password = "111",
//                        name = "botis",
//                        surname = "botissss",
//                        email = "sddsd@dsdsds",
//                        age = 1,
//                        gender = UserGender.Male,
//                        country = "bel",
//                        accountId = "aaa",
//                        hasOnlineBanking = true,
//                        phoneNumber = "1111111",
//                        job = "asasas"
//                    ).toModelUi()
//                ),
//                proceedIntent = {}
//            )
        }
    }
}

@Composable
@Preview
private fun DarkTheme() {
    FastBankingTheme(
        darkTheme = true
    ) {
        Surface {
//            PersonalScreenUi(
//                state = PersonalScreenState(),
//                proceedIntent = {}
//            )

            PersonalScreenUi(
                state = PersonalScreenState(
                    currentViewedCategory = PersonalScreenCategories.SETTING,
                    user = UserModelDomain(
                        id = "111",
                        password = "111",
                        name = "botis",
                        surname = "botissss",
                        email = "sddsd@dsdsds",
                        age = 1,
                        gender = UserGender.Male,
                        country = "bel",
                        accountId = "aaa",
                        hasOnlineBanking = true,
                        phoneNumber = "1111111",
                        job = "asasas",
                        dateOfBirth = Calendar.getInstance().time
                    ).toModelUi()
                ),
                proceedIntent = {}
            )
        }
    }
}