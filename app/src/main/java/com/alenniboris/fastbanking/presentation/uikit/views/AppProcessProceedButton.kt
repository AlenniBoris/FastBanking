package com.alenniboris.fastbanking.presentation.uikit.views

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.alenniboris.fastbanking.R
import com.alenniboris.fastbanking.presentation.uikit.theme.FastBankingTheme
import com.alenniboris.fastbanking.presentation.uikit.theme.ProductApplianceFormScreenBottomButtonInnerPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.ProductApplianceScreenBottomButtonShape
import com.alenniboris.fastbanking.presentation.uikit.theme.ProductApplianceScreenBottomButtonTextSize
import com.alenniboris.fastbanking.presentation.uikit.theme.RegistrationOptionsScreenButtonPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.bodyStyle
import com.alenniboris.fastbanking.presentation.uikit.theme.mainScreenItemColor
import com.alenniboris.fastbanking.presentation.uikit.theme.productApplianceFormBottomButtonColor
import com.alenniboris.fastbanking.presentation.uikit.theme.productApplianceFormBottomButtonTextColor

@Composable
fun AppProcessProceedButton(
    modifier: Modifier,
    onClick: () -> Unit
) {

    Column(
        modifier = modifier
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    productApplianceFormBottomButtonColor,
                    ProductApplianceScreenBottomButtonShape
                )
                .padding(RegistrationOptionsScreenButtonPadding)
                .clickable {
                    onClick()
                }
        ) {

            Text(
                modifier = Modifier.align(Alignment.Center),
                text = stringResource(R.string.continue_text),
                style = bodyStyle.copy(
                    fontSize = ProductApplianceScreenBottomButtonTextSize,
                    fontWeight = FontWeight.Bold,
                    color = productApplianceFormBottomButtonTextColor
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
            Column(
                modifier = Modifier.fillMaxSize()
            ) {

                AppProcessProceedButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(mainScreenItemColor)
                        .padding(ProductApplianceFormScreenBottomButtonInnerPadding),
                    onClick = {}
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
                modifier = Modifier.fillMaxSize()
            ) {

                AppProcessProceedButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(mainScreenItemColor)
                        .padding(ProductApplianceFormScreenBottomButtonInnerPadding),
                    onClick = {}
                )
            }
        }
    }
}