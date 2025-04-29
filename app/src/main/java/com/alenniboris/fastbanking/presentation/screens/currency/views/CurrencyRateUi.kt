package com.alenniboris.fastbanking.presentation.screens.currency.views

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.alenniboris.fastbanking.R
import com.alenniboris.fastbanking.domain.model.currency.RateModelDomain
import com.alenniboris.fastbanking.presentation.model.currency.CurrencyRatesModelUi
import com.alenniboris.fastbanking.presentation.uikit.theme.AtmMapListScreenItemBorderShape
import com.alenniboris.fastbanking.presentation.uikit.theme.AtmMapListScreenItemBorderSize
import com.alenniboris.fastbanking.presentation.uikit.theme.CurrencyRateItemTextColor
import com.alenniboris.fastbanking.presentation.uikit.theme.CurrencyScreenItemContentPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.CurrencyScreenItemPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.CurrencyScreenItemTextSize
import com.alenniboris.fastbanking.presentation.uikit.theme.CurrencyScreenRateUpdateTimeTextPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.CurrencyScreenRateUpdateTimeTextTopPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.CurrencyScreenUpdateTimeTextSize
import com.alenniboris.fastbanking.presentation.uikit.theme.appColor
import com.alenniboris.fastbanking.presentation.uikit.theme.appTopBarElementsColor
import com.alenniboris.fastbanking.presentation.uikit.theme.bodyStyle
import com.alenniboris.fastbanking.presentation.uikit.views.AppEmptyScreen
import com.alenniboris.fastbanking.presentation.uikit.views.AppProgressBar

@Composable
fun CurrencyRateUi(
    modifier: Modifier = Modifier,
    isLoading: Boolean,
    exchangeRate: CurrencyRatesModelUi
) {

    if (isLoading) {

        AppProgressBar(
            modifier = modifier,
        )
    } else {
        if (exchangeRate.listOfRates.isEmpty()) {

            AppEmptyScreen(
                modifier = modifier
            )
        } else {

            Column(
                modifier = modifier
            ) {

                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(R.string.last_update_text) + exchangeRate.lastUpdateDateText,
                    style = bodyStyle.copy(
                        color = appTopBarElementsColor,
                        fontSize = CurrencyScreenUpdateTimeTextSize
                    )
                )

                Text(
                    modifier = Modifier
                        .padding(CurrencyScreenRateUpdateTimeTextTopPadding)
                        .fillMaxWidth(),
                    text = stringResource(R.string.next_update_text) + exchangeRate.nextUpdateDateText,
                    style = bodyStyle.copy(
                        color = appTopBarElementsColor,
                        fontSize = CurrencyScreenUpdateTimeTextSize
                    )
                )

                Text(
                    modifier = Modifier
                        .padding(CurrencyScreenRateUpdateTimeTextPadding)
                        .fillMaxWidth(),
                    text = exchangeRate.baseCodeText,
                    style = bodyStyle.copy(
                        color = appTopBarElementsColor,
                        fontWeight = FontWeight.Bold,
                        fontSize = CurrencyScreenUpdateTimeTextSize
                    )
                )

                LazyColumn {
                    items(exchangeRate.listOfRates) { rate ->
                        CurrencyRateItem(
                            modifier = Modifier
                                .padding(CurrencyScreenItemPadding)
                                .border(
                                    width = AtmMapListScreenItemBorderSize,
                                    color = appTopBarElementsColor,
                                    shape = AtmMapListScreenItemBorderShape
                                )
                                .fillMaxWidth()
                                .padding(CurrencyScreenItemContentPadding),
                            item = rate
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun CurrencyRateItem(
    modifier: Modifier = Modifier,
    item: RateModelDomain
) {

    Row(
        modifier = modifier
            .background(appColor),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Text(
            text = item.code,
            style = bodyStyle.copy(
                color = CurrencyRateItemTextColor,
                fontSize = CurrencyScreenItemTextSize,
                fontWeight = FontWeight.Bold
            )
        )

        Text(
            text = item.rate.toString(),
            style = bodyStyle.copy(
                color = CurrencyRateItemTextColor,
                fontSize = CurrencyScreenItemTextSize,
            )
        )
    }
}

@Composable
@Preview
private fun CurrencyRateUiPreview() {

//    CurrencyRateUi(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(appColor),
//        isLoading = false,
//        exchangeRate = CurrencyRatesModelUi()
//    )

//    CurrencyRateUi(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(appColor),
//        isLoading = true,
//        exchangeRate = CurrencyRatesModelUi()
//    )

    CurrencyRateUi(
        modifier = Modifier
            .fillMaxSize()
            .background(appColor),
        isLoading = false,
        exchangeRate = CurrencyRatesModelUi(
            lastUpdateDateText = "11111",
            nextUpdateDateText = "222222",
            baseCodeText = "1 usd",
            listOfRates = listOf(
                RateModelDomain(
                    code = "usd",
                    rate = 1.0
                ),
                RateModelDomain(
                    code = "usd",
                    rate = 1.0
                ),
                RateModelDomain(
                    code = "usd",
                    rate = 1.0
                ),
                RateModelDomain(
                    code = "usd",
                    rate = 1.0
                ),
                RateModelDomain(
                    code = "usd",
                    rate = 1.0
                )
            )
        )
    )
}