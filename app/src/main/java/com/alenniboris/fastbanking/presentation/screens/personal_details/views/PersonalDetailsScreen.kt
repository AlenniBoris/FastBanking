package com.alenniboris.fastbanking.presentation.screens.personal_details.views

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
import com.alenniboris.fastbanking.presentation.model.toModelUi
import com.alenniboris.fastbanking.presentation.screens.personal_details.IPersonalDetailsScreenEvent
import com.alenniboris.fastbanking.presentation.screens.personal_details.IPersonalDetailsScreenIntent
import com.alenniboris.fastbanking.presentation.screens.personal_details.PersonalDetailsScreenState
import com.alenniboris.fastbanking.presentation.screens.personal_details.PersonalDetailsScreenViewModel
import com.alenniboris.fastbanking.presentation.uikit.theme.EmptyScreenFontSize
import com.alenniboris.fastbanking.presentation.uikit.theme.EmptyScreenSpacerHeight
import com.alenniboris.fastbanking.presentation.uikit.theme.FastBankingTheme
import com.alenniboris.fastbanking.presentation.uikit.theme.PersonalDetailsScreenContentPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.PersonalDetailsScreenDetailsContentItemPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.PersonalDetailsScreenDetailsContentTextPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.PersonalDetailsScreenDetailsContentTextSize
import com.alenniboris.fastbanking.presentation.uikit.theme.TopBarPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.appColor
import com.alenniboris.fastbanking.presentation.uikit.theme.appTopBarElementsColor
import com.alenniboris.fastbanking.presentation.uikit.theme.bodyStyle
import com.alenniboris.fastbanking.presentation.uikit.values.PersonalDetailsScreenRoute
import com.alenniboris.fastbanking.presentation.uikit.views.AppTopBar
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Destination(route = PersonalDetailsScreenRoute)
@Composable
fun PersonalDetailsScreen(
    navigator: DestinationsNavigator
) {

    val viewModel = koinViewModel<PersonalDetailsScreenViewModel>()
    val state by viewModel.state.collectAsStateWithLifecycle()
    val event by remember { mutableStateOf(viewModel.event) }
    val proceedIntent by remember { mutableStateOf(viewModel::proceedIntent) }

    LaunchedEffect(event) {

        launch {
            event.filterIsInstance<IPersonalDetailsScreenEvent.NavigateBack>().collect {
                navigator.popBackStack()
            }
        }
    }

    PersonalDetailsScreenUi(
        state = state,
        proceedIntent = proceedIntent
    )
}

@Composable
private fun PersonalDetailsScreenUi(
    state: PersonalDetailsScreenState,
    proceedIntent: (IPersonalDetailsScreenIntent) -> Unit
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
                proceedIntent(IPersonalDetailsScreenIntent.NavigateBack)
            },
            headerTextString = stringResource(R.string.personal_details_screen_header)
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
                modifier = Modifier.padding(PersonalDetailsScreenContentPadding)
            ) {

                Image(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    painter = painterResource(state.user.userPictureId),
                    contentDescription = stringResource(R.string.user_gender_text)
                )

                DetailsContentPart(
                    modifier = Modifier
                        .padding(PersonalDetailsScreenDetailsContentItemPadding)
                        .fillMaxWidth(),
                    header = stringResource(R.string.user_name_text),
                    value = state.user.domainModel.name
                )

                DetailsContentPart(
                    modifier = Modifier
                        .padding(PersonalDetailsScreenDetailsContentItemPadding)
                        .fillMaxWidth(),
                    header = stringResource(R.string.user_surname_text),
                    value = state.user.domainModel.surname
                )

                DetailsContentPart(
                    modifier = Modifier
                        .padding(PersonalDetailsScreenDetailsContentItemPadding)
                        .fillMaxWidth(),
                    header = stringResource(R.string.user_phone_number_text),
                    value = state.user.domainModel.phoneNumber
                )

                DetailsContentPart(
                    modifier = Modifier
                        .padding(PersonalDetailsScreenDetailsContentItemPadding)
                        .fillMaxWidth(),
                    header = stringResource(R.string.user_phone_email_text),
                    value = state.user.domainModel.email
                )
            }
        }
    }
}

@Composable
private fun DetailsContentPart(
    modifier: Modifier = Modifier,
    header: String,
    value: String
) {
    Column(
        modifier = modifier
    ) {

        Text(
            text = header,
            style = bodyStyle.copy(
                color = appTopBarElementsColor,
                fontWeight = FontWeight.Bold,
                fontSize = PersonalDetailsScreenDetailsContentTextSize
            )
        )

        Text(
            modifier = Modifier.padding(PersonalDetailsScreenDetailsContentTextPadding),
            text = value,
            style = bodyStyle.copy(
                color = appTopBarElementsColor,
                fontSize = PersonalDetailsScreenDetailsContentTextSize
            )
        )
    }
}

@Composable
@Preview
private fun LightTheme() {
    FastBankingTheme(
        darkTheme = false
    ) {
        Surface {
            PersonalDetailsScreenUi(
                state = PersonalDetailsScreenState(
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
                        job = "asasas"
                    ).toModelUi()
                ),
                proceedIntent = {}
            )

//            PersonalDetailsScreenUi(
//                state = PersonalDetailsScreenState(),
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
            PersonalDetailsScreenUi(
                state = PersonalDetailsScreenState(
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
                        job = "asasas"
                    ).toModelUi()
                ),
                proceedIntent = {}
            )

//            PersonalDetailsScreenUi(
//                state = PersonalDetailsScreenState(),
//                proceedIntent = {}
//            )
        }
    }
}