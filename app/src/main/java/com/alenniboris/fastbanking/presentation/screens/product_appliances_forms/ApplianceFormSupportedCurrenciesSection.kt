package com.alenniboris.fastbanking.presentation.screens.product_appliances_forms

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.alenniboris.fastbanking.R
import com.alenniboris.fastbanking.domain.model.currency.CurrencyModelDomain
import com.alenniboris.fastbanking.presentation.uikit.theme.FastBankingTheme
import com.alenniboris.fastbanking.presentation.uikit.theme.ProductApplianceFormScreenCardCurrencyListFirstItemOuterPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.ProductApplianceFormScreenCardCurrencyListItemInnerPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.ProductApplianceFormScreenCardCurrencyListItemOuterPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.ProductApplianceFormScreenCardCurrencyListOuterPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.ProductApplianceFormScreenCardCurrencyProgressBarHeight
import com.alenniboris.fastbanking.presentation.uikit.theme.ProductApplianceFormScreenItemsOuterPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.ProductApplianceFormScreenTextFontSize
import com.alenniboris.fastbanking.presentation.uikit.theme.appButtonRowButtonShape
import com.alenniboris.fastbanking.presentation.uikit.theme.appColor
import com.alenniboris.fastbanking.presentation.uikit.theme.bodyStyle
import com.alenniboris.fastbanking.presentation.uikit.theme.mainScreenFilterItemColor
import com.alenniboris.fastbanking.presentation.uikit.theme.mainScreenFilterItemSelectedColor
import com.alenniboris.fastbanking.presentation.uikit.theme.mainScreenFilterItemSelectedTextColor
import com.alenniboris.fastbanking.presentation.uikit.theme.mainScreenFilterItemTextColor
import com.alenniboris.fastbanking.presentation.uikit.theme.mainScreenTextColor
import com.alenniboris.fastbanking.presentation.uikit.views.AppProgressBar

@Composable
fun ApplianceFormSupportedCurrenciesSection(
    isAllSupportedCurrenciesLoading: Boolean,
    allSupportedCurrencies: List<CurrencyModelDomain>,
    currentSelectedCurrency: CurrencyModelDomain?,
    onCurrencyClicked: (CurrencyModelDomain) -> Unit
) {

    Text(
        modifier = Modifier.padding(ProductApplianceFormScreenItemsOuterPadding),
        text = stringResource(R.string.product_currency_text),
        style = bodyStyle.copy(
            color = mainScreenTextColor,
            fontSize = ProductApplianceFormScreenTextFontSize
        )
    )

    if (isAllSupportedCurrenciesLoading) {
        AppProgressBar(
            modifier = Modifier
                .fillMaxWidth()
                .height(ProductApplianceFormScreenCardCurrencyProgressBarHeight)
        )
    } else {
        LazyRow(
            modifier = Modifier.padding(ProductApplianceFormScreenCardCurrencyListOuterPadding)
        ) {
            itemsIndexed(allSupportedCurrencies) { index, currency ->

                val backgroundColor by animateColorAsState(
                    if (currency == currentSelectedCurrency)
                        mainScreenFilterItemSelectedColor
                    else
                        mainScreenFilterItemColor
                )

                val textColor by animateColorAsState(
                    if (currency == currentSelectedCurrency)
                        mainScreenFilterItemSelectedTextColor
                    else
                        mainScreenFilterItemTextColor
                )

                Box(
                    modifier = Modifier
                        .clickable {
                            onCurrencyClicked(currency)
                        }
                        .padding(
                            if (index == 0) ProductApplianceFormScreenCardCurrencyListFirstItemOuterPadding
                            else ProductApplianceFormScreenCardCurrencyListItemOuterPadding
                        )
                        .background(
                            color = backgroundColor,
                            shape = appButtonRowButtonShape
                        )
                        .padding(ProductApplianceFormScreenCardCurrencyListItemInnerPadding)
                ) {

                    Text(
                        modifier = Modifier.align(Alignment.Center),
                        text = currency.code,
                        style = bodyStyle.copy(
                            color = textColor,
                            fontWeight = FontWeight.Normal,
                            fontSize = ProductApplianceFormScreenTextFontSize
                        )
                    )
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
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(appColor)
            ) {
                ApplianceFormSupportedCurrenciesSection(
                    isAllSupportedCurrenciesLoading = false,
                    allSupportedCurrencies = listOf(
                        CurrencyModelDomain(
                            code = "sdcokmcd",
                            fullName = "l21"
                        ),
                        CurrencyModelDomain(
                            code = "1",
                            fullName = "1"
                        )
                    ),
                    currentSelectedCurrency = CurrencyModelDomain(
                        code = "1",
                        fullName = "1"
                    ),
                    onCurrencyClicked = {}
                )
            }
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
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(appColor)
            ) {
                ApplianceFormSupportedCurrenciesSection(
                    isAllSupportedCurrenciesLoading = false,
                    allSupportedCurrencies = listOf(
                        CurrencyModelDomain(
                            code = "sdcokmcd",
                            fullName = "l21"
                        ),
                        CurrencyModelDomain(
                            code = "1",
                            fullName = "1"
                        )
                    ),
                    currentSelectedCurrency = CurrencyModelDomain(
                        code = "1",
                        fullName = "1"
                    ),
                    onCurrencyClicked = {}
                )
            }
        }
    }
}