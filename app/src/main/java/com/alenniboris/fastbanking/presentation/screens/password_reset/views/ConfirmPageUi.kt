package com.alenniboris.fastbanking.presentation.screens.password_reset.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.alenniboris.fastbanking.R
import com.alenniboris.fastbanking.presentation.uikit.theme.ConfirmPageProcessUiContainerPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.DataInputProcessUiContentTextFontSize
import com.alenniboris.fastbanking.presentation.uikit.theme.appColor
import com.alenniboris.fastbanking.presentation.uikit.theme.bodyStyle
import com.alenniboris.fastbanking.presentation.uikit.theme.enterTextFieldTextColor

@Composable
fun ConfirmPageUi(
    modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier
            .padding(ConfirmPageProcessUiContainerPadding),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            text = stringResource(R.string.resetting_password_confirmation_process_text),
            style = bodyStyle.copy(
                color = enterTextFieldTextColor,
                fontSize = DataInputProcessUiContentTextFontSize
            )
        )
    }
}

@Composable
@Preview
private fun ConfirmPageUiPreview() {
    ConfirmPageUi(
        modifier = Modifier
            .fillMaxSize()
            .background(appColor)
    )
}