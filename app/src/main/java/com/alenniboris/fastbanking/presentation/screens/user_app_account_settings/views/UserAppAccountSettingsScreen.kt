package com.alenniboris.fastbanking.presentation.screens.user_app_account_settings.views

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.alenniboris.fastbanking.R
import com.alenniboris.fastbanking.domain.model.user.UserGender
import com.alenniboris.fastbanking.domain.model.user.UserModelDomain
import com.alenniboris.fastbanking.presentation.model.user.toModelUi
import com.alenniboris.fastbanking.presentation.screens.user_app_account_settings.UserAppAccountSettingsScreenState
import com.alenniboris.fastbanking.presentation.screens.user_app_account_settings.UserAppAccountSettingsScreenViewModel
import com.alenniboris.fastbanking.presentation.screens.user_app_account_settings.IUserAppAccountSettingsScreenEvent
import com.alenniboris.fastbanking.presentation.screens.user_app_account_settings.IUserAppAccountSettingsScreenIntent
import com.alenniboris.fastbanking.presentation.screens.destinations.PasswordResetScreenDestination
import com.alenniboris.fastbanking.presentation.uikit.theme.AccountSettingsScreenContentPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.AccountSettingsScreenContentTextPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.EmptyScreenFontSize
import com.alenniboris.fastbanking.presentation.uikit.theme.EmptyScreenSpacerHeight
import com.alenniboris.fastbanking.presentation.uikit.theme.FastBankingTheme
import com.alenniboris.fastbanking.presentation.uikit.theme.PersonalDetailsScreenDetailsContentTextSize
import com.alenniboris.fastbanking.presentation.uikit.theme.TopBarPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.appColor
import com.alenniboris.fastbanking.presentation.uikit.theme.appTopBarElementsColor
import com.alenniboris.fastbanking.presentation.uikit.theme.bodyStyle
import com.alenniboris.fastbanking.presentation.uikit.values.UserAppAccountSettingsScreenRoute
import com.alenniboris.fastbanking.presentation.uikit.views.AppCustomButton
import com.alenniboris.fastbanking.presentation.uikit.views.AppTopBar
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import java.util.Calendar

@Destination(route = UserAppAccountSettingsScreenRoute)
@Composable
fun UserAppAccountSettingsScreen(
    navigator: DestinationsNavigator
) {

    val viewModel = koinViewModel<UserAppAccountSettingsScreenViewModel>()
    val state by viewModel.state.collectAsStateWithLifecycle()
    val event by remember { mutableStateOf(viewModel.event) }
    val proceedIntent by remember { mutableStateOf(viewModel::proceedIntent) }

    LaunchedEffect(event) {

        launch {
            event.filterIsInstance<IUserAppAccountSettingsScreenEvent.NavigateBack>().collect {
                navigator.popBackStack()
            }
        }

        launch {
            event.filterIsInstance<IUserAppAccountSettingsScreenEvent.OpenPasswordResetScreen>().collect {
                navigator.navigate(PasswordResetScreenDestination)
            }
        }
    }

    AccountSettingsScreenUi(
        state = state,
        proceedIntent = proceedIntent
    )
}

@Composable
private fun AccountSettingsScreenUi(
    state: UserAppAccountSettingsScreenState,
    proceedIntent: (IUserAppAccountSettingsScreenIntent) -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(appColor),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        AppTopBar(
            modifier = Modifier
                .fillMaxWidth()
                .padding(TopBarPadding),
            leftBtnPainter = painterResource(R.drawable.back_icon),
            onLeftBtnClicked = {
                proceedIntent(IUserAppAccountSettingsScreenIntent.NavigateBack)
            },
            headerTextString = stringResource(R.string.accounts_settings_header_text)
        )

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
                modifier = Modifier.padding(AccountSettingsScreenContentPadding)
            ) {

                Image(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    painter = painterResource(state.user.userPictureId),
                    contentDescription = stringResource(R.string.user_gender_text)
                )

                Text(
                    modifier = Modifier.padding(AccountSettingsScreenContentTextPadding),
                    text = stringResource(R.string.category_login_text),
                    style = bodyStyle.copy(
                        color = appTopBarElementsColor,
                        fontWeight = FontWeight.Bold,
                        fontSize = PersonalDetailsScreenDetailsContentTextSize
                    )
                )

                Text(
                    modifier = Modifier.padding(AccountSettingsScreenContentTextPadding),
                    text = state.user.domainModel.id,
                    style = bodyStyle.copy(
                        color = appTopBarElementsColor,
                        fontSize = PersonalDetailsScreenDetailsContentTextSize
                    )
                )

                Text(
                    modifier = Modifier.padding(AccountSettingsScreenContentTextPadding),
                    text = stringResource(R.string.category_password_text),
                    style = bodyStyle.copy(
                        color = appTopBarElementsColor,
                        fontWeight = FontWeight.Bold,
                        fontSize = PersonalDetailsScreenDetailsContentTextSize
                    )
                )

                AppCustomButton(
                    modifier = Modifier
                        .padding(AccountSettingsScreenContentTextPadding)
                        .fillMaxWidth(),
                    text = stringResource(R.string.password_reset_text),
                    onClick = {
                        proceedIntent(IUserAppAccountSettingsScreenIntent.OpenPasswordResetScreen)
                    }
                )
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
//            AccountSettingsScreenUi(
//                state = AccountSettingsScreenState(),
//                proceedIntent = {}
//            )

            AccountSettingsScreenUi(
                state = UserAppAccountSettingsScreenState(
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

@Composable
@Preview
private fun DarkTheme() {
    FastBankingTheme(
        darkTheme = true
    ) {
        Surface {
//            AccountSettingsScreenUi(
//                state = AccountSettingsScreenState(),
//                proceedIntent = {}
//            )

            AccountSettingsScreenUi(
                state = UserAppAccountSettingsScreenState(
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