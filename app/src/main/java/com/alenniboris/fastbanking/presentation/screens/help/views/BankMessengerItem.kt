package com.alenniboris.fastbanking.presentation.screens.help.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.alenniboris.fastbanking.presentation.screens.help.BankSupportedMessenger
import com.alenniboris.fastbanking.presentation.screens.help.toUiPicture
import com.alenniboris.fastbanking.presentation.screens.help.toUiString
import com.alenniboris.fastbanking.presentation.uikit.theme.FilterTextSize
import com.alenniboris.fastbanking.presentation.uikit.theme.HelpScreenFilterElementTopPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.appColor
import com.alenniboris.fastbanking.presentation.uikit.theme.appFilterTextColor
import com.alenniboris.fastbanking.presentation.uikit.theme.bodyStyle

@Composable
fun BankMessengerItem(
    modifier: Modifier = Modifier,
    messenger: BankSupportedMessenger
) {

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Image(
            painter = painterResource(messenger.toUiPicture()),
            contentDescription = stringResource(messenger.toUiString())
        )

        Text(
            text = stringResource(messenger.toUiString()),
            style = bodyStyle.copy(
                color = appFilterTextColor,
                fontSize = FilterTextSize
            )
        )
    }
}

@Composable
@Preview
private fun BankMessengerItemPreview() {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(appColor)
    ) {

        BankMessengerItem(
            modifier = Modifier
                .padding(HelpScreenFilterElementTopPadding)
                .fillMaxWidth(),
            messenger = BankSupportedMessenger.VIBER
        )
    }
}