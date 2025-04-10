package com.alenniboris.fastbanking.presentation.screens.currency.views

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.alenniboris.fastbanking.R
import com.alenniboris.fastbanking.presentation.screens.currency.CurrencyScreenMode
import com.alenniboris.fastbanking.presentation.screens.currency.CurrencyScreenState
import com.alenniboris.fastbanking.presentation.screens.currency.CurrencyScreenViewModel
import com.alenniboris.fastbanking.presentation.screens.currency.ICurrencyScreenEvent
import com.alenniboris.fastbanking.presentation.screens.currency.ICurrencyScreenIntent
import com.alenniboris.fastbanking.presentation.screens.currency.toUiString
import com.alenniboris.fastbanking.presentation.uikit.theme.AtmMapNotRegisteredUserScreenContentPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.AtmMapNotRegisteredUserScreenTopBarPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.CurrencyScreenExchangeModePadding
import com.alenniboris.fastbanking.presentation.uikit.theme.TopBarPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.appColor
import com.alenniboris.fastbanking.presentation.uikit.values.ClickableElement
import com.alenniboris.fastbanking.presentation.uikit.values.CurrencyScreenRoute
import com.alenniboris.fastbanking.presentation.uikit.views.AppButtonRow
import com.alenniboris.fastbanking.presentation.uikit.views.AppTopBar
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
@Destination(route = CurrencyScreenRoute)
fun CurrencyScreen(
    isBackButtonNeeded: Boolean = false,
    navigator: DestinationsNavigator
) {

    val viewModel = koinViewModel<CurrencyScreenViewModel>()
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
            event.filterIsInstance<ICurrencyScreenEvent.NavigateBack>().collect {
                navigator.popBackStack(
                    route = CurrencyScreenRoute,
                    inclusive = true
                )
            }
        }

        launch {
            event.filterIsInstance<ICurrencyScreenEvent.ShowToastMessage>().collect { coming ->
                toastMessage?.cancel()
                toastMessage = Toast.makeText(
                    context,
                    coming.messageId,
                    Toast.LENGTH_SHORT
                )
                toastMessage?.show()
            }
        }
    }

    CurrencyScreenUi(
        isBackButtonNeeded = isBackButtonNeeded,
        state = state,
        proceedIntent = proceedIntent
    )
}

@Composable
private fun CurrencyScreenUi(
    isBackButtonNeeded: Boolean,
    state: CurrencyScreenState,
    proceedIntent: (ICurrencyScreenIntent) -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(appColor)
    ) {

        if (isBackButtonNeeded) {
            AppTopBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(appColor)
                    .padding(TopBarPadding),
                headerTextString = stringResource(R.string.currency_top_bar_header_text),
                leftBtnPainter = painterResource(R.drawable.back_icon),
                onLeftBtnClicked = {
                    proceedIntent(
                        ICurrencyScreenIntent.NavigateBack
                    )
                }
            )
        } else {
            AppTopBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(appColor)
                    .padding(TopBarPadding),
                headerTextString = stringResource(R.string.currency_top_bar_header_text)
            )
        }

        AppButtonRow(
            modifier = Modifier
                .padding(AtmMapNotRegisteredUserScreenContentPadding)
                .fillMaxWidth()
                .padding(AtmMapNotRegisteredUserScreenTopBarPadding),
            listOfElements = state.listOfScreenModes.map { element ->
                ClickableElement(
                    text = stringResource(element.toUiString()),
                    onClick = {
                        proceedIntent(
                            ICurrencyScreenIntent.UpdateCurrentScreenMode(element)
                        )
                    }
                )
            },
            currentElement = ClickableElement(
                text = stringResource(state.screenMode.toUiString()),
                onClick = {
                    proceedIntent(
                        ICurrencyScreenIntent.UpdateCurrentScreenMode(state.screenMode)
                    )
                }
            )
        )

        when (state.screenMode) {
            CurrencyScreenMode.Exchange ->
                CurrencyExchangeUi(
                    modifier = Modifier
                        .padding(CurrencyScreenExchangeModePadding)
                        .fillMaxSize(),
                    listOfCurrencies = state.listOfCurrencies,
                    fromCurrency = state.fromCurrency,
                    toCurrency = state.toCurrency,
                    fromValueText = state.fromValueText,
                    toValueText = state.toValueText,
                    conversionText = state.exchangeRate.toString(),
                    isConversionRateLoading = state.isExchangeRateLoading,
                    isFromCurrencyListVisible = state.isFromCurrencyMenuVisible,
                    isToCurrencyListVisible = state.isToCurrencyMenuVisible,
                    proceedIntent = proceedIntent
                )

            CurrencyScreenMode.Rate ->
                CurrencyRateUi(
                    modifier = Modifier
                        .padding(CurrencyScreenExchangeModePadding)
                        .fillMaxSize(),
                    isLoading = state.isAllExchangeRatesForBaseCurrencyLoading,
                    exchangeRate = state.currencyExchangeRatesForBaseCurrency
                )
        }
    }
}

@Composable
@Preview
private fun CurrencyUserNotRegisteredScreenPreview() {
    CurrencyScreenUi(
        isBackButtonNeeded = false,
        state = CurrencyScreenState(),
        proceedIntent = {}
    )
}