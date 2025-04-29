package com.alenniboris.fastbanking.presentation.screens.product_appliances_forms

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.alenniboris.fastbanking.R
import com.alenniboris.fastbanking.presentation.uikit.theme.FastBankingTheme
import com.alenniboris.fastbanking.presentation.uikit.theme.ProductApplianceScreenProcessFinalTextSize
import com.alenniboris.fastbanking.presentation.uikit.theme.appColor
import com.alenniboris.fastbanking.presentation.uikit.theme.bodyStyle
import com.alenniboris.fastbanking.presentation.uikit.views.AppProgressBar

@Composable
fun ApplianceFormProcessFinalScreen(
    modifier: Modifier,
    isLoading: Boolean,
    textColor: Color
) {

    if (isLoading) {
        AppProgressBar(
            modifier = Modifier.fillMaxSize()
        )
    } else {

        Column(
            modifier = modifier,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Text(
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                text = stringResource(R.string.appliance_final_okay_text),
                style = bodyStyle.copy(
                    color = textColor,
                    fontSize = ProductApplianceScreenProcessFinalTextSize
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
            ApplianceFormProcessFinalScreen(
                modifier = Modifier
                    .fillMaxSize()
                    .background(appColor),
                isLoading = false,
                textColor = Color.Black
            )
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
                ApplianceFormProcessFinalScreen(
                    modifier = Modifier
                        .fillMaxSize(),
                    isLoading = true,
                    textColor = Color.Black
                )
            }
        }
    }
}