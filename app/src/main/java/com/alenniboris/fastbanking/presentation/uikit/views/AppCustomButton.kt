package com.alenniboris.fastbanking.presentation.uikit.views

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.alenniboris.fastbanking.presentation.uikit.theme.EnterValueTextFieldShape
import com.alenniboris.fastbanking.presentation.uikit.theme.EnterValueTextFieldTextPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.RegistrationOptionsScreenButtonBorderWidth
import com.alenniboris.fastbanking.presentation.uikit.theme.RegistrationOptionsScreenButtonPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.RegistrationOptionsScreenRegistrationTypeTextSize
import com.alenniboris.fastbanking.presentation.uikit.theme.appColor
import com.alenniboris.fastbanking.presentation.uikit.theme.bodyStyle
import com.alenniboris.fastbanking.presentation.uikit.theme.enterTextFieldColor
import com.alenniboris.fastbanking.presentation.uikit.theme.enterTextFieldTextColor

@Composable
fun AppCustomButton(
    modifier: Modifier = Modifier,
    isClickable: Boolean = true,
    onClick: () -> Unit = {},
    text: String = "",
    content: (@Composable () -> Unit)? = null
) {
    Box(
        modifier = modifier
            .background(enterTextFieldColor, EnterValueTextFieldShape)
            .border(
                RegistrationOptionsScreenButtonBorderWidth,
                enterTextFieldTextColor,
                EnterValueTextFieldShape
            )
            .padding(RegistrationOptionsScreenButtonPadding)
            .clickable {
                if (isClickable) {
                    onClick()
                }
            },
    ) {
        content?.let {
            it()
        } ?: Text(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(EnterValueTextFieldTextPadding),
            text = text,
            style = bodyStyle.copy(
                fontSize = RegistrationOptionsScreenRegistrationTypeTextSize,
                fontWeight = FontWeight.Bold
            ),
            color = enterTextFieldTextColor
        )
    }
}

@Composable
@Preview
private fun AppCustomButtonPreview() {
    Column(
        Modifier
            .fillMaxSize()
            .background(appColor)
    ) {
        AppCustomButton(
            text = "Loooo",
            onClick = {},
        )
    }
}