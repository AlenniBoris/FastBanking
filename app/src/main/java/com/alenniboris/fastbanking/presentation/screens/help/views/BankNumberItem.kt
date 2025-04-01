package com.alenniboris.fastbanking.presentation.screens.help.views

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.alenniboris.fastbanking.R
import com.alenniboris.fastbanking.presentation.screens.help.BankPhoneNumber
import com.alenniboris.fastbanking.presentation.uikit.theme.FilterTextSize
import com.alenniboris.fastbanking.presentation.uikit.theme.HelpScreenFilterElementTopPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.HelpScreenFilterTextStartPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.appColor
import com.alenniboris.fastbanking.presentation.uikit.theme.appFilterTextColor
import com.alenniboris.fastbanking.presentation.uikit.theme.bodyStyle

@Composable
fun BankNumberItem(
    modifier: Modifier = Modifier,
    number: BankPhoneNumber,
    onClick: () -> Unit
) {

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {

            Icon(
                painter = painterResource(R.drawable.contact_phone_icon),
                tint = appFilterTextColor,
                contentDescription = stringResource(R.string.phone_icon_description)
            )

            Text(
                modifier = Modifier.padding(HelpScreenFilterTextStartPadding),
                text = number.cellOperator,
                style = bodyStyle.copy(
                    color = appFilterTextColor,
                    fontSize = FilterTextSize
                )
            )
        }

        Text(
            modifier = Modifier.clickable {
                onClick()
            },
            text = number.phoneNumber,
            style = bodyStyle.copy(
                color = appFilterTextColor,
                fontSize = FilterTextSize
            )
        )
    }
}

@Composable
@Preview
private fun BankNumberItemPreview() {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(appColor)
    ) {
        BankNumberItem(
            modifier = Modifier
                .padding(HelpScreenFilterElementTopPadding)
                .fillMaxWidth(),
            number = BankPhoneNumber(
                cellOperator = "a1",
                phoneNumber = "111111"
            ),
            onClick = {}
        )
        BankNumberItem(
            modifier = Modifier
                .padding(HelpScreenFilterElementTopPadding)
                .fillMaxWidth(),
            number = BankPhoneNumber(
                cellOperator = "a1",
                phoneNumber = "111111"
            ),
            onClick = {}
        )
        BankNumberItem(
            modifier = Modifier
                .padding(HelpScreenFilterElementTopPadding)
                .fillMaxWidth(),
            number = BankPhoneNumber(
                cellOperator = "a1",
                phoneNumber = "111111"
            ),
            onClick = {}
        )
    }
}