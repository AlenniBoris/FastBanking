package com.alenniboris.fastbanking.presentation.screens.product_appliances_forms.card_form_screen.views

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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.alenniboris.fastbanking.R
import com.alenniboris.fastbanking.domain.model.currency.CurrencyModelDomain
import com.alenniboris.fastbanking.presentation.screens.product_appliances_forms.ApplianceFormSupportedCurrenciesSection
import com.alenniboris.fastbanking.presentation.screens.product_appliances_forms.card_form_screen.CardIssuingType
import com.alenniboris.fastbanking.presentation.screens.product_appliances_forms.card_form_screen.ICardApplianceFormScreenIntent
import com.alenniboris.fastbanking.presentation.screens.product_appliances_forms.card_form_screen.toUiString
import com.alenniboris.fastbanking.presentation.uikit.theme.CardApplianceFormScreenCardOpeningTypeItemPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.CardApplianceFormScreenCardOpeningTypeItemTextPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.CardApplianceFormScreenCardTypeButtonTextSize
import com.alenniboris.fastbanking.presentation.uikit.theme.CardApplianceFormScreenCheckboxesFontSize
import com.alenniboris.fastbanking.presentation.uikit.theme.ProductApplianceFormScreenContentPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.ProductApplianceFormScreenItemsOuterPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.ProductApplianceFormScreenMainSectionInnerPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.ProductApplianceFormScreenMainSectionOuterPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.ProductApplianceFormScreenSheetHeaderDescriptionFontSize
import com.alenniboris.fastbanking.presentation.uikit.theme.ProductApplianceFormScreenTextFontSize
import com.alenniboris.fastbanking.presentation.uikit.theme.FastBankingTheme
import com.alenniboris.fastbanking.presentation.uikit.theme.appColor
import com.alenniboris.fastbanking.presentation.uikit.theme.appTopBarElementsColor
import com.alenniboris.fastbanking.presentation.uikit.theme.bodyStyle
import com.alenniboris.fastbanking.presentation.uikit.theme.enterTextFieldTextColor
import com.alenniboris.fastbanking.presentation.uikit.theme.mainScreenItemColor
import com.alenniboris.fastbanking.presentation.uikit.theme.mainScreenTextColor
import com.alenniboris.fastbanking.presentation.uikit.theme.productApplianceFormCheckboxCheckedTrackColor
import com.alenniboris.fastbanking.presentation.uikit.theme.productApplianceFormCheckboxUncheckedTrackColor
import com.alenniboris.fastbanking.presentation.uikit.theme.productApplianceFormScreenCheckboxUncheckedContentColor
import com.alenniboris.fastbanking.presentation.uikit.views.AppCheckButton
import com.alenniboris.fastbanking.presentation.uikit.views.AppTextSectionWithSwitcher

@Composable
fun CardFormFillingDataUi(
    modifier: Modifier,
    cardIssuingTypes: List<CardIssuingType>,
    currentCardIssueType: CardIssuingType,
    isAllSupportedCurrenciesLoading: Boolean,
    allSupportedCurrencies: List<CurrencyModelDomain>,
    currentSelectedCurrency: CurrencyModelDomain?,
    isSalaryCard: Boolean,
    isPolicyAccepted: Boolean,
    proceedIntent: (ICardApplianceFormScreenIntent) -> Unit
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

            CardIssuingTypeRows(
                modifier = Modifier
                    .padding(CardApplianceFormScreenCardOpeningTypeItemPadding)
                    .fillMaxWidth(),
                types = cardIssuingTypes,
                current = currentCardIssueType,
                proceedIntent = proceedIntent
            )

            ApplianceFormSupportedCurrenciesSection(
                isAllSupportedCurrenciesLoading = isAllSupportedCurrenciesLoading,
                allSupportedCurrencies = allSupportedCurrencies,
                currentSelectedCurrency = currentSelectedCurrency,
                onCurrencyClicked = { currency ->
                    proceedIntent(
                        ICardApplianceFormScreenIntent.UpdateSelectedCurrency(
                            currency
                        )
                    )
                }
            )

            CardTypeSection(
                modifier = Modifier
                    .padding(ProductApplianceFormScreenItemsOuterPadding)
                    .fillMaxWidth()
                    .clickable {
                        proceedIntent(
                            ICardApplianceFormScreenIntent.UpdateCardTypeSheetVisibility
                        )
                    }
            )

            AppTextSectionWithSwitcher(
                modifier = Modifier
                    .padding(ProductApplianceFormScreenItemsOuterPadding)
                    .fillMaxWidth(),
                text = stringResource(R.string.salary_card_text),
                textStyle = bodyStyle.copy(
                    color = mainScreenTextColor,
                    fontSize = CardApplianceFormScreenCheckboxesFontSize
                ),
                isChecked = isSalaryCard,
                onCheckedChanged = { isChecked ->
                    proceedIntent(
                        ICardApplianceFormScreenIntent.UpdateIsSalaryCard(isChecked)
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
                        ICardApplianceFormScreenIntent.UpdateIsPolicyAccepted(isChecked)
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
private fun CardTypeSection(
    modifier: Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Text(
            textAlign = TextAlign.Center,
            text = stringResource(R.string.card_type_text),
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
private fun CardIssuingTypeRows(
    modifier: Modifier,
    types: List<CardIssuingType>,
    current: CardIssuingType,
    proceedIntent: (ICardApplianceFormScreenIntent) -> Unit
) {

    Text(
        text = stringResource(R.string.card_opens_text),
        style = bodyStyle.copy(
            color = mainScreenTextColor,
            fontSize = ProductApplianceFormScreenTextFontSize
        )
    )

    types.forEach { type ->

        Row(
            modifier = modifier,
            verticalAlignment = Alignment.CenterVertically
        ) {

            AppCheckButton(
                isChecked = current == type,
                onClick = {
                    proceedIntent(
                        ICardApplianceFormScreenIntent.ProceedCardIssuingTypeAction(
                            issueType = type
                        )
                    )
                }
            )

            Text(
                modifier = Modifier
                    .padding(CardApplianceFormScreenCardOpeningTypeItemTextPadding),
                text = stringResource(type.toUiString()),
                style = bodyStyle.copy(
                    color = mainScreenTextColor,
                    fontSize = ProductApplianceFormScreenTextFontSize
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
                CardFormFillingDataUi(
                    modifier = Modifier.fillMaxSize(),
                    cardIssuingTypes = CardIssuingType.entries.toList(),
                    currentCardIssueType = CardIssuingType.WITH_NEW_ACCOUNT,
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
                    isSalaryCard = true,
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
                CardFormFillingDataUi(
                    modifier = Modifier.fillMaxSize(),
                    cardIssuingTypes = CardIssuingType.entries.toList(),
                    currentCardIssueType = CardIssuingType.WITH_NEW_ACCOUNT,
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
                    isSalaryCard = true,
                    isPolicyAccepted = true,
                    proceedIntent = {}
                )
            }
        }
    }
}