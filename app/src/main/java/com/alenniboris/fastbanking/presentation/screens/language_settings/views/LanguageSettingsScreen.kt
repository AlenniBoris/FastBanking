package com.alenniboris.fastbanking.presentation.screens.language_settings.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.alenniboris.fastbanking.R
import com.alenniboris.fastbanking.presentation.screens.language_settings.ILanguageSettingsScreenEvent
import com.alenniboris.fastbanking.presentation.screens.language_settings.ILanguageSettingsScreenIntent
import com.alenniboris.fastbanking.presentation.screens.language_settings.LanguageSettingsScreenState
import com.alenniboris.fastbanking.presentation.screens.language_settings.LanguageSettingsScreenViewModel
import com.alenniboris.fastbanking.presentation.uikit.theme.FastBankingTheme
import com.alenniboris.fastbanking.presentation.uikit.theme.SettingsScreenContentPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.SettingsScreenFirstItemPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.SettingsScreenItemFontSize
import com.alenniboris.fastbanking.presentation.uikit.theme.SettingsScreenItemPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.TopBarPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.appColor
import com.alenniboris.fastbanking.presentation.uikit.theme.appTopBarElementsColor
import com.alenniboris.fastbanking.presentation.uikit.theme.bodyStyle
import com.alenniboris.fastbanking.presentation.uikit.utils.setLanguage
import com.alenniboris.fastbanking.presentation.uikit.utils.toUiString
import com.alenniboris.fastbanking.presentation.uikit.values.LanguageSettingsScreenRoute
import com.alenniboris.fastbanking.presentation.uikit.views.AppCheckButton
import com.alenniboris.fastbanking.presentation.uikit.views.AppTopBar
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Destination(route = LanguageSettingsScreenRoute)
@Composable
fun LanguageSettingsScreen(
    navigator: DestinationsNavigator
) {

    val viewModel = koinViewModel<LanguageSettingsScreenViewModel>()
    val state by viewModel.state.collectAsStateWithLifecycle()
    val event by remember { mutableStateOf(viewModel.event) }
    val proceedIntent by remember { mutableStateOf(viewModel::proceedIntent) }
    val context = LocalContext.current

    LaunchedEffect(event) {

        launch {
            event.filterIsInstance<ILanguageSettingsScreenEvent.NavigateBack>().collect {
                navigator.popBackStack()
            }
        }
        launch {
            event.filterIsInstance<ILanguageSettingsScreenEvent.UpdateAppLanguage>()
                .collect { coming ->
                    context.setLanguage(coming.newValue)
                }
        }
    }

    LanguageSettingsScreenUi(
        state = state,
        proceedIntent = proceedIntent
    )
}

@Composable
private fun LanguageSettingsScreenUi(
    state: LanguageSettingsScreenState,
    proceedIntent: (ILanguageSettingsScreenIntent) -> Unit
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
                    ILanguageSettingsScreenIntent.NavigateBack
                )
            },
            headerTextString = stringResource(R.string.application_Language_text)
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(SettingsScreenContentPadding)
        ) {

            state.allLanguages.forEachIndexed { index, language ->

                Row(
                    modifier = Modifier
                        .padding(
                            if (index == 0) SettingsScreenFirstItemPadding
                            else SettingsScreenItemPadding
                        )
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Text(
                        text = stringResource(language.toUiString()),
                        style = bodyStyle.copy(
                            color = appTopBarElementsColor,
                            fontWeight = FontWeight.Normal,
                            fontSize = SettingsScreenItemFontSize
                        )
                    )

                    AppCheckButton(
                        isChecked = state.currentLanguage == language,
                        onClick = {
                            proceedIntent(
                                ILanguageSettingsScreenIntent.UpdateAppLanguage(language)
                            )
                        }
                    )
                }
            }
        }
    }
}

@Composable
@Preview
private fun LightLanguage() {
    FastBankingTheme(
        darkTheme = false
    ) {
        Surface {
            LanguageSettingsScreenUi(
                state = LanguageSettingsScreenState(),
                proceedIntent = {}
            )
        }
    }
}

@Composable
@Preview
private fun DarkLanguage() {
    FastBankingTheme(
        darkTheme = true
    ) {
        Surface {
            LanguageSettingsScreenUi(
                state = LanguageSettingsScreenState(),
                proceedIntent = {}
            )
        }
    }
}