package com.alenniboris.fastbanking.presentation.screens.help.views

import android.content.ClipData
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
import androidx.compose.ui.platform.ClipEntry
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.alenniboris.fastbanking.R
import com.alenniboris.fastbanking.presentation.screens.help.BankInfoCategory
import com.alenniboris.fastbanking.presentation.screens.help.toUiName
import com.alenniboris.fastbanking.presentation.screens.help.toUiValue
import com.alenniboris.fastbanking.presentation.uikit.theme.FilterTextSize
import com.alenniboris.fastbanking.presentation.uikit.theme.HelpScreenFilterElementTopPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.appColor
import com.alenniboris.fastbanking.presentation.uikit.theme.appFilterTextColor
import com.alenniboris.fastbanking.presentation.uikit.theme.bodyStyle

@Composable
fun BankInfoItem(
    modifier: Modifier = Modifier,
    infoCategory: BankInfoCategory
) {

    val clipboardManager = LocalClipboardManager.current
    val context = LocalContext.current
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = stringResource(infoCategory.toUiName()),
                style = bodyStyle.copy(
                    color = appFilterTextColor,
                    fontSize = FilterTextSize
                )
            )

            Text(
                text = stringResource(infoCategory.toUiValue()),
                style = bodyStyle.copy(
                    fontWeight = FontWeight.Bold,
                    color = appFilterTextColor,
                    fontSize = FilterTextSize
                )
            )
        }

        Icon(
            modifier = Modifier.clickable {
                val text = context.getString(infoCategory.toUiValue())
                val clipData = ClipData.newPlainText("text", text)
                val clipEntry = ClipEntry(clipData)
                clipboardManager.setClip(clipEntry)
            },
            painter = painterResource(R.drawable.copy_icon),
            tint = appFilterTextColor,
            contentDescription = stringResource(R.string.copy_to_clipboard_description)
        )
    }
}

@Composable
@Preview
fun BankInfoItemPreview() {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(appColor)
    ) {

        BankInfoItem(
            modifier = Modifier
                .padding(HelpScreenFilterElementTopPadding)
                .fillMaxWidth(),
            infoCategory = BankInfoCategory.SWIFT_CODE
        )
    }
}