package com.alenniboris.fastbanking.presentation.screens.main.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alenniboris.fastbanking.R
import com.alenniboris.fastbanking.presentation.uikit.functions.CommonFunctions
import com.alenniboris.fastbanking.presentation.uikit.theme.FastBankingTheme
import com.alenniboris.fastbanking.presentation.uikit.theme.MainScreenInformationSectionContainerPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.MainScreenInformationSectionContentInnerPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.MainScreenInformationSectionContentMainTextSize
import com.alenniboris.fastbanking.presentation.uikit.theme.MainScreenInformationSectionContentPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.MainScreenInformationSectionContentShape
import com.alenniboris.fastbanking.presentation.uikit.theme.MainScreenInformationSectionContentTextPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.MainScreenInformationSectionContentTextSize
import com.alenniboris.fastbanking.presentation.uikit.theme.MainScreenProductSectionHeight
import com.alenniboris.fastbanking.presentation.uikit.theme.bodyStyle
import com.alenniboris.fastbanking.presentation.uikit.theme.mainScreenItemColor
import com.alenniboris.fastbanking.presentation.uikit.theme.mainScreenOnItemColor
import com.alenniboris.fastbanking.presentation.uikit.theme.mainScreenTextColor
import com.alenniboris.fastbanking.presentation.uikit.views.AppProgressBar

@Composable
fun MainScreenInformationSection(
    modifier: Modifier = Modifier,
    isCurrencyDataLoading: Boolean,
    baseCurrencyCode: String,
    exchangeCurrencyCode: String,
    baseCurrencyAmount: Double?,
    exchangeCurrencyAmount: Double?,
    isAccountsDataLoading: Boolean,
    allAccountsAmount: Double?
) {

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Column(
            modifier = Modifier
                .clip(MainScreenInformationSectionContentShape)
                .weight(1f)
                .fillMaxHeight()
                .background(mainScreenOnItemColor)
                .padding(MainScreenInformationSectionContentInnerPadding)
        ) {
            if (isCurrencyDataLoading) {
                AppProgressBar(
                    modifier = Modifier.fillMaxSize()
                )
            } else {

                Text(
                    text = stringResource(R.string.online_text),
                    style = bodyStyle.copy(
                        color = mainScreenTextColor,
                        fontWeight = FontWeight.Light,
                        fontSize = MainScreenInformationSectionContentTextSize
                    )
                )

                Text(
                    modifier = Modifier.padding(MainScreenInformationSectionContentTextPadding),
                    text = baseCurrencyAmount?.let {
                        CommonFunctions.formatAmount(baseCurrencyAmount) + " " + baseCurrencyCode
                    } ?: (stringResource(R.string.no_value_text) + " " + baseCurrencyCode),
                    style = bodyStyle.copy(
                        color = mainScreenTextColor,
                        fontWeight = FontWeight.Bold,
                        fontSize = MainScreenInformationSectionContentMainTextSize
                    )
                )

                Text(
                    modifier = Modifier.padding(MainScreenInformationSectionContentTextPadding),
                    text = exchangeCurrencyAmount?.let {
                        CommonFunctions.formatAmount(exchangeCurrencyAmount) + " " + exchangeCurrencyCode
                    } ?: (stringResource(R.string.no_value_text) + " " + baseCurrencyCode),
                    style = bodyStyle.copy(
                        color = mainScreenTextColor,
                        fontWeight = FontWeight.Normal,
                        fontSize = MainScreenInformationSectionContentTextSize
                    )
                )
            }
        }

        Column(
            modifier = Modifier
                .padding(MainScreenInformationSectionContentPadding)
                .clip(MainScreenInformationSectionContentShape)
                .weight(1f)
                .fillMaxHeight()
                .background(mainScreenOnItemColor)
                .padding(MainScreenInformationSectionContentInnerPadding)
        ) {
            if (isAccountsDataLoading) {
                AppProgressBar(
                    modifier = Modifier.fillMaxSize()
                )
            } else {

                Row(
                    modifier = Modifier.fillMaxSize()
                ) {

                    Column {

                        Text(
                            text = stringResource(R.string.accounts_sum_text),
                            style = bodyStyle.copy(
                                color = mainScreenTextColor,
                                fontWeight = FontWeight.Light,
                                fontSize = MainScreenInformationSectionContentTextSize
                            )
                        )

                        Text(
                            modifier = Modifier.padding(
                                MainScreenInformationSectionContentTextPadding
                            ),
                            text = stringResource(R.string.all_accounts_text),
                            style = bodyStyle.copy(
                                color = mainScreenTextColor,
                                fontWeight = FontWeight.Bold,
                                fontSize = MainScreenInformationSectionContentMainTextSize
                            )
                        )

                        Text(
                            modifier = Modifier.padding(
                                MainScreenInformationSectionContentTextPadding
                            ),
                            text = allAccountsAmount?.let {
                                CommonFunctions.formatAmount(allAccountsAmount) + " " + baseCurrencyCode
                            } ?: (stringResource(R.string.no_value_text) + " " + baseCurrencyCode),
                            style = bodyStyle.copy(
                                color = mainScreenTextColor,
                                fontWeight = FontWeight.Normal,
                                fontSize = MainScreenInformationSectionContentTextSize
                            )
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
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(mainScreenItemColor)
            ) {

                MainScreenInformationSection(
                    modifier = Modifier
                        .padding(MainScreenInformationSectionContainerPadding)
                        .fillMaxWidth()
                        .height(MainScreenProductSectionHeight),
                    isCurrencyDataLoading = false,
                    baseCurrencyCode = "usd",
                    baseCurrencyAmount = 100.0,
                    exchangeCurrencyAmount = 2.0,
                    isAccountsDataLoading = false,
                    allAccountsAmount = 120.0,
                    exchangeCurrencyCode = "byn"
                )

                MainScreenInformationSection(
                    modifier = Modifier
                        .padding(MainScreenInformationSectionContainerPadding)
                        .fillMaxWidth()
                        .height(MainScreenProductSectionHeight),
                    isCurrencyDataLoading = true,
                    baseCurrencyCode = "usd",
                    baseCurrencyAmount = 1.0,
                    exchangeCurrencyAmount = 2.0,
                    isAccountsDataLoading = false,
                    allAccountsAmount = 120.0,
                    exchangeCurrencyCode = "byn"
                )

                MainScreenInformationSection(
                    modifier = Modifier
                        .padding(MainScreenInformationSectionContainerPadding)
                        .fillMaxWidth()
                        .height(MainScreenProductSectionHeight),
                    isCurrencyDataLoading = false,
                    baseCurrencyCode = "usd",
                    baseCurrencyAmount = 1.0,
                    exchangeCurrencyAmount = 2.0,
                    isAccountsDataLoading = true,
                    allAccountsAmount = 120.0,
                    exchangeCurrencyCode = "byn"
                )

                MainScreenInformationSection(
                    modifier = Modifier
                        .padding(MainScreenInformationSectionContainerPadding)
                        .fillMaxWidth()
                        .height(40.dp),
                    isCurrencyDataLoading = true,
                    baseCurrencyCode = "usd",
                    baseCurrencyAmount = 1.0,
                    exchangeCurrencyAmount = 2.0,
                    isAccountsDataLoading = true,
                    allAccountsAmount = 120.0,
                    exchangeCurrencyCode = "byn"
                )

                MainScreenInformationSection(
                    modifier = Modifier
                        .padding(MainScreenInformationSectionContainerPadding)
                        .fillMaxWidth()
                        .height(MainScreenProductSectionHeight),
                    isCurrencyDataLoading = false,
                    baseCurrencyCode = "usd",
                    baseCurrencyAmount = null,
                    exchangeCurrencyAmount = null,
                    isAccountsDataLoading = false,
                    allAccountsAmount = null,
                    exchangeCurrencyCode = "byn"
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
                    .background(mainScreenItemColor)
            ) {

                MainScreenInformationSection(
                    modifier = Modifier
                        .padding(MainScreenInformationSectionContainerPadding)
                        .fillMaxWidth()
                        .height(MainScreenProductSectionHeight),
                    isCurrencyDataLoading = false,
                    baseCurrencyCode = "usd",
                    baseCurrencyAmount = 1.0,
                    exchangeCurrencyAmount = 2.0,
                    isAccountsDataLoading = false,
                    allAccountsAmount = 120.0,
                    exchangeCurrencyCode = "byn"
                )

                MainScreenInformationSection(
                    modifier = Modifier
                        .padding(MainScreenInformationSectionContainerPadding)
                        .fillMaxWidth()
                        .height(MainScreenProductSectionHeight),
                    isCurrencyDataLoading = true,
                    baseCurrencyCode = "usd",
                    baseCurrencyAmount = 1.0,
                    exchangeCurrencyAmount = 2.0,
                    isAccountsDataLoading = false,
                    allAccountsAmount = 120.0,
                    exchangeCurrencyCode = "byn"
                )

                MainScreenInformationSection(
                    modifier = Modifier
                        .padding(MainScreenInformationSectionContainerPadding)
                        .fillMaxWidth()
                        .height(MainScreenProductSectionHeight),
                    isCurrencyDataLoading = false,
                    baseCurrencyCode = "usd",
                    baseCurrencyAmount = 1.0,
                    exchangeCurrencyAmount = 2.0,
                    isAccountsDataLoading = true,
                    allAccountsAmount = 120.0,
                    exchangeCurrencyCode = "byn"
                )

                MainScreenInformationSection(
                    modifier = Modifier
                        .padding(MainScreenInformationSectionContainerPadding)
                        .fillMaxWidth()
                        .height(40.dp),
                    isCurrencyDataLoading = true,
                    baseCurrencyCode = "usd",
                    baseCurrencyAmount = 1.0,
                    exchangeCurrencyAmount = 2.0,
                    isAccountsDataLoading = true,
                    allAccountsAmount = 120.0,
                    exchangeCurrencyCode = "byn"
                )

                MainScreenInformationSection(
                    modifier = Modifier
                        .padding(MainScreenInformationSectionContainerPadding)
                        .fillMaxWidth()
                        .height(MainScreenProductSectionHeight),
                    isCurrencyDataLoading = false,
                    baseCurrencyCode = "usd",
                    baseCurrencyAmount = null,
                    exchangeCurrencyAmount = null,
                    isAccountsDataLoading = false,
                    allAccountsAmount = null,
                    exchangeCurrencyCode = "byn"
                )
            }
        }
    }
}