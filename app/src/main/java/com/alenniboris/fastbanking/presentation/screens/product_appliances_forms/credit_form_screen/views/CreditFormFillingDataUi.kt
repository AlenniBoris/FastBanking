package com.alenniboris.fastbanking.presentation.screens.product_appliances_forms.credit_form_screen.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.alenniboris.fastbanking.R
import com.alenniboris.fastbanking.domain.model.currency.CurrencyModelDomain
import com.alenniboris.fastbanking.presentation.screens.product_appliances_forms.ApplianceFormSupportedCurrenciesSection
import com.alenniboris.fastbanking.presentation.screens.product_appliances_forms.credit_form_screen.ICreditApplianceFormScreenIntent
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
import com.alenniboris.fastbanking.presentation.uikit.theme.mainScreenItemColor
import com.alenniboris.fastbanking.presentation.uikit.theme.mainScreenOnItemColor
import com.alenniboris.fastbanking.presentation.uikit.theme.mainScreenTextColor
import com.alenniboris.fastbanking.presentation.uikit.theme.productApplianceFormCheckboxCheckedTrackColor
import com.alenniboris.fastbanking.presentation.uikit.theme.productApplianceFormCheckboxUncheckedTrackColor
import com.alenniboris.fastbanking.presentation.uikit.theme.productApplianceFormScreenCheckboxUncheckedContentColor
import com.alenniboris.fastbanking.presentation.uikit.views.AppTextField
import com.alenniboris.fastbanking.presentation.uikit.views.AppTextSectionWithSwitcher

@Composable
fun CreditFormFillingDataUi(
    modifier: Modifier,
    isAllSupportedCurrenciesLoading: Boolean,
    allSupportedCurrencies: List<CurrencyModelDomain>,
    currentSelectedCurrency: CurrencyModelDomain?,
    isPolicyAccepted: Boolean,
    creditGoal: String,
    proceedIntent: (ICreditApplianceFormScreenIntent) -> Unit
) {

    Column(
        modifier = modifier
    ) {

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(ProductApplianceFormScreenContentPadding),
            text = stringResource(R.string.credit_appliance_form_text),
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
                        ICreditApplianceFormScreenIntent.UpdateSelectedCurrency(
                            currency
                        )
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
                value = creditGoal,
                onValueChanged = { newValue ->
                    proceedIntent(
                        ICreditApplianceFormScreenIntent.UpdateCreditGoal(newValue)
                    )
                },
                maxLines = Int.MAX_VALUE,
                placeholder = stringResource(R.string.credit_goal_placeholder_text)
            )

            AppTextSectionWithSwitcher(
                modifier = Modifier
                    .padding(ProductApplianceFormScreenItemsOuterPadding)
                    .fillMaxWidth(),
                text = stringResource(R.string.credit_policy_text),
                textStyle = bodyStyle.copy(
                    color = mainScreenTextColor,
                    fontSize = CardApplianceFormScreenCheckboxesFontSize
                ),
                isChecked = isPolicyAccepted,
                onCheckedChanged = { isChecked ->
                    proceedIntent(
                        ICreditApplianceFormScreenIntent.UpdateIsPolicyAccepted(isChecked)
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
                CreditFormFillingDataUi(
                    modifier = Modifier.fillMaxSize(),
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
                    creditGoal = "",
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
                CreditFormFillingDataUi(
                    modifier = Modifier.fillMaxSize(),
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
                    creditGoal = "dscsdc",
                    proceedIntent = {}
                )
            }
        }
    }
}