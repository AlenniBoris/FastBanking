package com.alenniboris.fastbanking.presentation.screens.product_information.views

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.alenniboris.fastbanking.R
import com.alenniboris.fastbanking.presentation.uikit.theme.FastBankingTheme
import com.alenniboris.fastbanking.presentation.uikit.theme.ProductInformationScreenContentPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.ProductInformationScreenCopySectionIconPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.ProductInformationScreenCopySectionTextPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.ProductInformationScreenCopySectionTextSize
import com.alenniboris.fastbanking.presentation.uikit.theme.ProductInformationScreenSectionPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.ProductInformationScreenSectionSpacerHeight
import com.alenniboris.fastbanking.presentation.uikit.theme.ProductInformationScreenSectionSpacerPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.appColor
import com.alenniboris.fastbanking.presentation.uikit.theme.appTopBarElementsColor
import com.alenniboris.fastbanking.presentation.uikit.theme.bodyStyle

@Composable
fun ProductInformationCopySection(
    modifier: Modifier = Modifier,
    header: String,
    text: String,
    onClick: () -> Unit
) {

    Column(
        modifier = modifier
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Column(
                modifier = Modifier.weight(1f)
            ) {

                Text(
                    text = header,
                    style = bodyStyle.copy(
                        color = appTopBarElementsColor,
                        fontSize = ProductInformationScreenCopySectionTextSize,
                        fontWeight = FontWeight.Light
                    )
                )

                Text(
                    modifier = Modifier.padding(ProductInformationScreenCopySectionTextPadding),
                    text = text,
                    style = bodyStyle.copy(
                        color = appTopBarElementsColor,
                        fontSize = ProductInformationScreenCopySectionTextSize
                    )
                )
            }

            Icon(
                modifier = Modifier
                    .padding(ProductInformationScreenCopySectionIconPadding)
                    .clickable { onClick() },
                painter = painterResource(R.drawable.copy_icon),
                tint = appTopBarElementsColor,
                contentDescription = stringResource(R.string.copy_to_clipboard_description)
            )
        }

        Spacer(
            modifier = Modifier
                .padding(ProductInformationScreenSectionSpacerPadding)
                .fillMaxWidth()
                .height(ProductInformationScreenSectionSpacerHeight)
                .background(appTopBarElementsColor)
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
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(appColor)
                    .padding(ProductInformationScreenContentPadding)
            ) {
                ProductInformationCopySection(
                    modifier = Modifier
                        .padding(ProductInformationScreenSectionPadding)
                        .fillMaxWidth(),
                    header = "klamskmdlsad",
                    text = "nasxhybxkjsan31",
                    onClick = {}
                )

                ProductInformationCopySection(
                    modifier = Modifier
                        .padding(ProductInformationScreenSectionPadding)
                        .fillMaxWidth(),
                    header = "121212",
                    text = "aslmx,;asl,xl;as,x",
                    onClick = {}
                )

                ProductInformationCopySection(
                    modifier = Modifier
                        .padding(ProductInformationScreenSectionPadding)
                        .fillMaxWidth(),
                    header = "somi0918723912",
                    text = "acslMosiaogabgxyttgbi",
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
                modifier = Modifier
                    .fillMaxSize()
                    .background(appColor)
                    .padding(ProductInformationScreenContentPadding)
            ) {
                ProductInformationCopySection(
                    modifier = Modifier
                        .padding(ProductInformationScreenSectionPadding)
                        .fillMaxWidth(),
                    header = "klamskmdlsad",
                    text = "nasxhybxkjsan31",
                    onClick = {}
                )

                ProductInformationCopySection(
                    modifier = Modifier
                        .padding(ProductInformationScreenSectionPadding)
                        .fillMaxWidth(),
                    header = "121212",
                    text = "aslmx,;asl,xl;as,x",
                    onClick = {}
                )

                ProductInformationCopySection(
                    modifier = Modifier
                        .padding(ProductInformationScreenSectionPadding)
                        .fillMaxWidth(),
                    header = "somi0918723912",
                    text = "acslMosiaogabgxyttgbi",
                    onClick = {}
                )
            }
        }
    }
}