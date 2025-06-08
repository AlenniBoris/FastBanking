package com.alenniboris.fastbanking.presentation.uikit.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.alenniboris.fastbanking.R
import com.alenniboris.fastbanking.presentation.uikit.theme.AppChangingProductNameDialogContainerPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.AppChangingProductNameDialogContainerShape
import com.alenniboris.fastbanking.presentation.uikit.theme.AppChangingProductNameDialogTextFieldInnerPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.AppChangingProductNameDialogTextFieldOuterPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.AppChangingProductNameDialogTextSize
import com.alenniboris.fastbanking.presentation.uikit.theme.FastBankingTheme
import com.alenniboris.fastbanking.presentation.uikit.theme.appChangingProductNameDialogApproveColor
import com.alenniboris.fastbanking.presentation.uikit.theme.appChangingProductNameDialogDeclineColor
import com.alenniboris.fastbanking.presentation.uikit.theme.appColor
import com.alenniboris.fastbanking.presentation.uikit.theme.appTopBarElementsColor
import com.alenniboris.fastbanking.presentation.uikit.theme.bodyStyle
import com.alenniboris.fastbanking.presentation.uikit.theme.mainScreenItemColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppDialogWithTextField(
    header: String,
    value: String,
    onValueChanged: (String) -> Unit,
    onDismiss: () -> Unit,
    onApprove: () -> Unit
) {

    BasicAlertDialog(
        modifier = Modifier
            .background(
                color = appColor,
                shape = AppChangingProductNameDialogContainerShape
            )
            .padding(AppChangingProductNameDialogContainerPadding),
        onDismissRequest = onDismiss,
        content = {

            Column {

                Text(
                    text = header,
                    style = bodyStyle.copy(
                        color = appTopBarElementsColor,
                        fontSize = AppChangingProductNameDialogTextSize,
                        fontWeight = FontWeight.Bold
                    )
                )

                AppTextField(
                    modifier = Modifier
                        .padding(AppChangingProductNameDialogTextFieldOuterPadding)
                        .fillMaxWidth()
                        .background(
                            color = mainScreenItemColor,
                            shape = AppChangingProductNameDialogContainerShape
                        )
                        .padding(AppChangingProductNameDialogTextFieldInnerPadding),
                    value = value,
                    onValueChanged = onValueChanged
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    AppIconButton(
                        onClick = onDismiss,
                        iconPainter = painterResource(R.drawable.declined_status_icon),
                        tint = appChangingProductNameDialogDeclineColor
                    )

                    AppIconButton(
                        onClick = onApprove,
                        iconPainter = painterResource(R.drawable.approved_status_icon),
                        tint = appChangingProductNameDialogApproveColor
                    )
                }
            }
        }
    )
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
                AppDialogWithTextField(
                    header = "majkcnasjklcn",
                    value = "name",
                    onValueChanged = {},
                    onDismiss = {},
                    onApprove = {}
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
                AppDialogWithTextField(
                    header = "lmakmxlks",
                    value = "name",
                    onValueChanged = {},
                    onDismiss = {},
                    onApprove = {}
                )
            }
        }
    }
}