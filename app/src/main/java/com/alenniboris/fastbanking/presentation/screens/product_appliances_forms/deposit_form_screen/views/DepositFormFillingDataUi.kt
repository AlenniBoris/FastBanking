package com.alenniboris.fastbanking.presentation.screens.product_appliances_forms.deposit_form_screen.views

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.alenniboris.fastbanking.R
import com.alenniboris.fastbanking.domain.model.currency.CurrencyModelDomain
import com.alenniboris.fastbanking.presentation.screens.product_appliances_forms.ApplianceFormSupportedCurrenciesSection
import com.alenniboris.fastbanking.presentation.screens.product_appliances_forms.deposit_form_screen.IDepositApplianceFormScreenIntent
import com.alenniboris.fastbanking.presentation.uikit.theme.CardApplianceFormScreenCardTypeButtonTextSize
import com.alenniboris.fastbanking.presentation.uikit.theme.CardApplianceFormScreenCheckboxesFontSize
import com.alenniboris.fastbanking.presentation.uikit.theme.CreditApplianceFormScreenCreditGoalFieldInnerPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.CreditApplianceFormScreenCreditGoalFieldOuterPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.EnterValueTextFieldShape
import com.alenniboris.fastbanking.presentation.uikit.theme.FastBankingTheme
import com.alenniboris.fastbanking.presentation.uikit.theme.ProductApplianceFormScreenContentPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.ProductApplianceFormScreenItemsOuterPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.ProductApplianceFormScreenMainSectionInnerPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.ProductApplianceFormScreenMainSectionOuterPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.ProductApplianceFormScreenSheetHeaderDescriptionFontSize
import com.alenniboris.fastbanking.presentation.uikit.theme.appColor
import com.alenniboris.fastbanking.presentation.uikit.theme.appTopBarElementsColor
import com.alenniboris.fastbanking.presentation.uikit.theme.bodyStyle
import com.alenniboris.fastbanking.presentation.uikit.theme.enterTextFieldTextColor
import com.alenniboris.fastbanking.presentation.uikit.theme.mainScreenItemColor
import com.alenniboris.fastbanking.presentation.uikit.theme.mainScreenOnItemColor
import com.alenniboris.fastbanking.presentation.uikit.theme.mainScreenTextColor
import com.alenniboris.fastbanking.presentation.uikit.theme.productApplianceFormCheckboxCheckedTrackColor
import com.alenniboris.fastbanking.presentation.uikit.theme.productApplianceFormCheckboxUncheckedTrackColor
import com.alenniboris.fastbanking.presentation.uikit.theme.productApplianceFormScreenCheckboxUncheckedContentColor
import com.alenniboris.fastbanking.presentation.uikit.views.AppTextField
import com.alenniboris.fastbanking.presentation.uikit.views.AppTextSectionWithSwitcher

@Composable
fun DepositFormFillingDataUi(
    modifier: Modifier,
    isAllSupportedCurrenciesLoading: Boolean,
    allSupportedCurrencies: List<CurrencyModelDomain>,
    currentSelectedCurrency: CurrencyModelDomain?,
    minimalContributionText: String,
    procentText: String,
    isPolicyAccepted: Boolean,
    proceedIntent: (IDepositApplianceFormScreenIntent) -> Unit
) {

    Column(
        modifier = modifier
    ) {

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(ProductApplianceFormScreenContentPadding),
            text = stringResource(R.string.card_appliance_form_text),
            style = bodyStyle.copy(
                color = appTopBarElementsColor,
                fontWeight = FontWeight.Bold,
                fontSize = ProductApplianceFormScreenSheetHeaderDescriptionFontSize
            )
        )

        Column(
            modifier = Modifier
                .padding(ProductApplianceFormScreenMainSectionOuterPadding)
                .fillMaxWidth()
                .background(mainScreenItemColor)
                .padding(ProductApplianceFormScreenMainSectionInnerPadding)
        ) {

            ApplianceFormSupportedCurrenciesSection(
                isAllSupportedCurrenciesLoading = isAllSupportedCurrenciesLoading,
                allSupportedCurrencies = allSupportedCurrencies,
                currentSelectedCurrency = currentSelectedCurrency,
                onCurrencyClicked = { currency ->
                    proceedIntent(
                        IDepositApplianceFormScreenIntent.UpdateSelectedCurrency(
                            currency
                        )
                    )
                }
            )

            DepositPeriodSection(
                modifier = Modifier
                    .padding(ProductApplianceFormScreenItemsOuterPadding)
                    .fillMaxWidth()
                    .clickable {
                        proceedIntent(
                            IDepositApplianceFormScreenIntent.UpdatePeriodSheetVisibility
                        )
                    }
            )

            AppTextField(
                modifier = Modifier
                    .padding(CreditApplianceFormScreenCreditGoalFieldOuterPadding)
                    .background(
                        color = mainScreenOnItemColor,
                        shape = EnterValueTextFieldShape
                    )
                    .fillMaxWidth()
                    .padding(CreditApplianceFormScreenCreditGoalFieldInnerPadding),
                value = minimalContributionText,
                onValueChanged = { newValue ->
                    proceedIntent(
                        IDepositApplianceFormScreenIntent.UpdateMinimumContribution(newValue)
                    )
                },
                maxLines = Int.MAX_VALUE,
                placeholder = stringResource(R.string.minimal_contribution_placeholder_text),
                keyboardType = KeyboardType.Number
            )

            AppTextField(
                modifier = Modifier
                    .padding(CreditApplianceFormScreenCreditGoalFieldOuterPadding)
                    .background(
                        color = mainScreenOnItemColor,
                        shape = EnterValueTextFieldShape
                    )
                    .fillMaxWidth()
                    .padding(CreditApplianceFormScreenCreditGoalFieldInnerPadding),
                value = procentText,
                onValueChanged = { newValue ->
                    proceedIntent(
                        IDepositApplianceFormScreenIntent.UpdateDepositProcent(newValue)
                    )
                },
                maxLines = Int.MAX_VALUE,
                placeholder = stringResource(R.string.deposit_procent_placeholder_text),
                keyboardType = KeyboardType.Number
            )

            AppTextSectionWithSwitcher(
                modifier = Modifier
                    .padding(ProductApplianceFormScreenItemsOuterPadding)
                    .fillMaxWidth(),
                text = stringResource(R.string.card_policy_text),
                textStyle = bodyStyle.copy(
                    color = mainScreenTextColor,
                    fontSize = CardApplianceFormScreenCheckboxesFontSize
                ),
                isChecked = isPolicyAccepted,
                onCheckedChanged = { isChecked ->
                    proceedIntent(
                        IDepositApplianceFormScreenIntent.UpdateIsPolicyAccepted(isChecked)
                    )
                },
                switchColors = SwitchDefaults.colors().copy(
                    uncheckedTrackColor = productApplianceFormCheckboxUncheckedTrackColor,
                    uncheckedBorderColor = productApplianceFormScreenCheckboxUncheckedContentColor,
                    uncheckedIconColor = productApplianceFormScreenCheckboxUncheckedContentColor,
                    uncheckedThumbColor = productApplianceFormScreenCheckboxUncheckedContentColor,
                    checkedTrackColor = productApplianceFormCheckboxCheckedTrackColor
                )
            )
        }
    }
}

@Composable
private fun DepositPeriodSection(
    modifier: Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Text(
            textAlign = TextAlign.Center,
            text = stringResource(R.string.deposit_period_text),
            style = bodyStyle.copy(
                color = enterTextFieldTextColor,
                fontSize = CardApplianceFormScreenCardTypeButtonTextSize
            )
        )

        Icon(
            painter = painterResource(R.drawable.open_options_icon),
            tint = enterTextFieldTextColor,
            contentDescription = stringResource(R.string.document_options_description)
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
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(appColor)
            ) {
                DepositFormFillingDataUi(
                    modifier = Modifier.fillMaxSize(),
                    minimalContributionText = "99.21",
                    procentText = "3.4",
                    isAllSupportedCurrenciesLoading = true,
                    allSupportedCurrencies = listOf(
                        CurrencyModelDomain(
                            code = "us",
                            fullName = "dsdskc"
                        ),
                        CurrencyModelDomain(
                            code = "wqdqd",
                            fullName = "dsdskc"
                        )
                    ),
                    currentSelectedCurrency = null,
                    isPolicyAccepted = true,
                    proceedIntent = {}
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
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(appColor)
            ) {
                DepositFormFillingDataUi(
                    modifier = Modifier.fillMaxSize(),
                    minimalContributionText = "",
                    procentText = "",
                    isAllSupportedCurrenciesLoading = true,
                    allSupportedCurrencies = listOf(
                        CurrencyModelDomain(
                            code = "us",
                            fullName = "dsdskc"
                        ),
                        CurrencyModelDomain(
                            code = "wqdqd",
                            fullName = "dsdskc"
                        )
                    ),
                    currentSelectedCurrency = null,
                    isPolicyAccepted = true,
                    proceedIntent = {}
                )
            }
        }
    }
}