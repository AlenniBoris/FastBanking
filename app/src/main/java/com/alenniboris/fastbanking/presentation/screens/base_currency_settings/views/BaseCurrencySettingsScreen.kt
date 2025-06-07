package com.alenniboris.fastbanking.presentation.screens.base_currency_settings.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
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
import com.alenniboris.fastbanking.presentation.screens.base_currency_settings.BaseCurrencySettingsScreenState
import com.alenniboris.fastbanking.presentation.screens.base_currency_settings.BaseCurrencySettingsScreenViewModel
import com.alenniboris.fastbanking.presentation.screens.base_currency_settings.IBaseCurrencySettingsScreenEvent
import com.alenniboris.fastbanking.presentation.screens.base_currency_settings.IBaseCurrencySettingsScreenIntent
import com.alenniboris.fastbanking.presentation.uikit.theme.FastBankingTheme
import com.alenniboris.fastbanking.presentation.uikit.theme.SettingsScreenContentPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.SettingsScreenFirstItemPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.SettingsScreenItemFontSize
import com.alenniboris.fastbanking.presentation.uikit.theme.SettingsScreenItemPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.TopBarPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.appColor
import com.alenniboris.fastbanking.presentation.uikit.theme.appTopBarElementsColor
import com.alenniboris.fastbanking.presentation.uikit.theme.bodyStyle
import com.alenniboris.fastbanking.presentation.uikit.utils.AppBaseCurrency
import com.alenniboris.fastbanking.presentation.uikit.utils.setBaseCurrency
import com.alenniboris.fastbanking.presentation.uikit.utils.toUiString
import com.alenniboris.fastbanking.presentation.uikit.values.BaseCurrencySettingsScreenRoute
import com.alenniboris.fastbanking.presentation.uikit.views.AppCheckButton
import com.alenniboris.fastbanking.presentation.uikit.views.AppTopBar
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Destination(route = BaseCurrencySettingsScreenRoute)
@Composable
fun BaseCurrencySettingsScreen(
    navigator: DestinationsNavigator
) {

    val viewModel = koinViewModel<BaseCurrencySettingsScreenViewModel>()
    val state by viewModel.state.collectAsStateWithLifecycle()
    val event by remember { mutableStateOf(viewModel.event) }
    val proceedIntent by remember { mutableStateOf(viewModel::proceedIntent) }
    val context = LocalContext.current

    LaunchedEffect(event) {

        launch {
            event.filterIsInstance<IBaseCurrencySettingsScreenEvent.NavigateBack>().collect {
                navigator.popBackStack()
            }
        }
        launch {
            event.filterIsInstance<IBaseCurrencySettingsScreenEvent.UpdateAppBaseCurrency>()
                .collect { coming ->
                    context.setBaseCurrency(coming.newValue)
                }
        }
    }

    BaseCurrencySettingsScreenUi(
        state = state,
        proceedIntent = proceedIntent
    )
}

@Composable
private fun BaseCurrencySettingsScreenUi(
    state: BaseCurrencySettingsScreenState,
    proceedIntent: (IBaseCurrencySettingsScreenIntent) -> Unit
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
                    IBaseCurrencySettingsScreenIntent.NavigateBack
                )
            },
            headerTextString = stringResource(R.string.application_base_currency_text)
        )

        LazyColumn(
            modifier = Modifier.padding(SettingsScreenContentPadding)
        ) {
            itemsIndexed(state.allBaseCurrencies) { index, baseCurrency ->

                AppBaseCurrencyItem(
                    modifier = Modifier
                        .padding(
                            if (index == 0) SettingsScreenFirstItemPadding
                            else SettingsScreenItemPadding
                        )
                        .fillMaxWidth(),
                    baseCurrency = baseCurrency,
                    currentBaseCurrency = state.currentBaseCurrency,
                    proceedIntent = proceedIntent
                )
            }
        }
    }
}

@Composable
private fun AppBaseCurrencyItem(
    modifier: Modifier = Modifier,
    baseCurrency: AppBaseCurrency,
    currentBaseCurrency: AppBaseCurrency,
    proceedIntent: (IBaseCurrencySettingsScreenIntent) -> Unit
) {

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            text = stringResource(baseCurrency.toUiString()),
            style = bodyStyle.copy(
                color = appTopBarElementsColor,
                fontWeight = FontWeight.Normal,
                fontSize = SettingsScreenItemFontSize
            )
        )

        AppCheckButton(
            isChecked = currentBaseCurrency == baseCurrency,
            onClick = {
                proceedIntent(
                    IBaseCurrencySettingsScreenIntent.UpdateAppBaseCurrency(baseCurrency)
                )
            }
        )
    }
}

@Composable
@Preview
private fun LightLanguage() {
    FastBankingTheme(
        darkTheme = false
    ) {
        Surface {
            BaseCurrencySettingsScreenUi(
                state = BaseCurrencySettingsScreenState(),
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
            BaseCurrencySettingsScreenUi(
                state = BaseCurrencySettingsScreenState(),
                proceedIntent = {}
            )
        }
    }
}