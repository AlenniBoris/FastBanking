package com.alenniboris.fastbanking.presentation.screens.currency.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import com.alenniboris.fastbanking.R
import com.alenniboris.fastbanking.domain.model.currency.CurrencyModelDomain
import com.alenniboris.fastbanking.presentation.screens.currency.ICurrencyScreenIntent
import com.alenniboris.fastbanking.presentation.uikit.theme.CurrencyScreeChangeButtonPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.CurrencyScreenEnterFieldPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.EnterValueTextFieldPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.EnterValueTextFieldShape
import com.alenniboris.fastbanking.presentation.uikit.theme.appColor
import com.alenniboris.fastbanking.presentation.uikit.theme.appDropdownColor
import com.alenniboris.fastbanking.presentation.uikit.theme.appTopBarElementsColor
import com.alenniboris.fastbanking.presentation.uikit.theme.enterTextFieldColor
import com.alenniboris.fastbanking.presentation.uikit.values.ClickableElement
import com.alenniboris.fastbanking.presentation.uikit.views.AppCustomButton
import com.alenniboris.fastbanking.presentation.uikit.views.AppDropdownMenu
import com.alenniboris.fastbanking.presentation.uikit.views.AppIconButton
import com.alenniboris.fastbanking.presentation.uikit.views.AppProgressBar
import com.alenniboris.fastbanking.presentation.uikit.views.AppTextField

@Composable
fun CurrencyExchangeUi(
    modifier: Modifier = Modifier,
    listOfCurrencies: List<CurrencyModelDomain>,
    fromCurrency: CurrencyModelDomain?,
    toCurrency: CurrencyModelDomain?,
    fromValueText: String,
    toValueText: String,
    conversionText: String,
    isConversionRateLoading: Boolean,
    isFromCurrencyListVisible: Boolean,
    isToCurrencyListVisible: Boolean,
    proceedIntent: (ICurrencyScreenIntent) -> Unit
) {

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        CurrencyBlock(
            currency = fromCurrency,
            valueText = fromValueText,
            onValueChanged = { newValue ->
                proceedIntent(
                    ICurrencyScreenIntent.UpdateFromValue(newValue)
                )
            },
            isEnabled = true,
            placeholder = stringResource(R.string.from_currecy_value_placeholder_text),
            isMenuVisible = isFromCurrencyListVisible,
            listOfCurrencies = listOfCurrencies,
            onDismiss = {
                proceedIntent(
                    ICurrencyScreenIntent.UpdateFromCurrencyMenuVisibility
                )
            },
            onOpenMenuClicked = {
                proceedIntent(
                    ICurrencyScreenIntent.UpdateFromCurrencyMenuVisibility
                )
            },
            onCurrencyClicked = { item ->
                proceedIntent(
                    ICurrencyScreenIntent.UpdateFromCurrency(
                        item
                    )
                )
            }
        )

        AppIconButton(
            modifier = Modifier
                .padding(CurrencyScreeChangeButtonPadding)
                .background(
                    color = enterTextFieldColor,
                    shape = CircleShape
                ),
            iconPainter = painterResource(R.drawable.exchange_icon),
            tint = appTopBarElementsColor,
            isAnimated = true,
            onClick = {
                proceedIntent(
                    ICurrencyScreenIntent.ChangeCurrenciesPlaces
                )
            }
        )

        CurrencyBlock(
            currency = toCurrency,
            valueText = toValueText,
            onValueChanged = {},
            isEnabled = false,
            placeholder = stringResource(R.string.to_currecy_value_placeholder_text),
            isMenuVisible = isToCurrencyListVisible,
            listOfCurrencies = listOfCurrencies,
            onDismiss = {
                proceedIntent(
                    ICurrencyScreenIntent.UpdateToCurrencyMenuVisibility
                )
            },
            onOpenMenuClicked = {
                proceedIntent(
                    ICurrencyScreenIntent.UpdateToCurrencyMenuVisibility
                )
            },
            onCurrencyClicked = { item ->
                proceedIntent(
                    ICurrencyScreenIntent.UpdateToCurrency(
                        item
                    )
                )
            }
        )

        if (isConversionRateLoading) {
            AppProgressBar(
                modifier = Modifier.fillMaxWidth(),
                iconTint = appTopBarElementsColor
            )
        } else {
            AppTextField(
                value = "1 ${fromCurrency?.code ?: stringResource(R.string.no_currency_text)} " + stringResource(
                    R.string.equals_text
                ) + " $conversionText ${toCurrency?.code ?: stringResource(R.string.no_currency_text)}",
                onValueChanged = {}
            )
        }

    }
}

@Composable
private fun CurrencyBlock(
    currency: CurrencyModelDomain?,
    valueText: String,
    onValueChanged: (String) -> Unit,
    isEnabled: Boolean,
    placeholder: String,
    isMenuVisible: Boolean,
    listOfCurrencies: List<CurrencyModelDomain>,
    onDismiss: () -> Unit,
    onOpenMenuClicked: () -> Unit,
    onCurrencyClicked: (CurrencyModelDomain) -> Unit

) {
    Box {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            AppTextField(
                modifier = Modifier
                    .padding(CurrencyScreenEnterFieldPadding)
                    .background(
                        color = enterTextFieldColor,
                        shape = EnterValueTextFieldShape
                    )
                    .weight(1f)
                    .padding(EnterValueTextFieldPadding),
                value = valueText,
                onValueChanged = { newValue ->
                    onValueChanged(newValue)
                },
                keyboardType = KeyboardType.Number,
                isEnabled = isEnabled,
                placeholder = placeholder
            )

            AppCustomButton(
                text = currency?.code ?: stringResource(R.string.no_currency_text),
                onClick = {
                    onOpenMenuClicked()
                }
            )
        }
        if (isMenuVisible){
            AppDropdownMenu(
                modifier = Modifier
                    .background(appDropdownColor)
                    .align(Alignment.CenterEnd),
                isVisible = isMenuVisible,
                listOfElements = listOfCurrencies.map {
                    ClickableElement(
                        text = it.code,
                        onClick = {
                            onCurrencyClicked(it)
                        }
                    )
                },
                onDismiss = { onDismiss() }
            )
        }
    }
}

@Composable
@Preview
private fun CurrencyExchangeUiPreview() {
    CurrencyExchangeUi(
        modifier = Modifier
            .fillMaxSize()
            .background(appColor),
        listOfCurrencies = emptyList(),
        fromCurrency = CurrencyModelDomain(
            code = "rub",
            fullName = "ruble"
        ),
        toCurrency = CurrencyModelDomain(
            code = "rub",
            fullName = "ruble"
        ),
        fromValueText = "99922.11",
        toValueText = "222.111",
        conversionText = "0.222",
        isConversionRateLoading = false,
        isFromCurrencyListVisible = false,
        isToCurrencyListVisible = true,
        proceedIntent = {}
    )
}