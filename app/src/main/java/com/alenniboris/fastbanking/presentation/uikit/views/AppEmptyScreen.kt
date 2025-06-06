package com.alenniboris.fastbanking.presentation.uikit.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.alenniboris.fastbanking.R
import com.alenniboris.fastbanking.presentation.uikit.theme.EmptyScreenFontSize
import com.alenniboris.fastbanking.presentation.uikit.theme.EmptyScreenSpacerHeight
import com.alenniboris.fastbanking.presentation.uikit.theme.appColor
import com.alenniboris.fastbanking.presentation.uikit.theme.appTopBarElementsColor
import com.alenniboris.fastbanking.presentation.uikit.theme.bodyStyle

@Composable
fun AppEmptyScreen(
    modifier: Modifier = Modifier,
    textId: Int = R.string.nothing_found
) {
    Box(
        modifier = modifier,
    ) {
        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(R.drawable.nothing_icon),
                contentDescription = stringResource(R.string.empty_screen_icon_description)
            )
            Spacer(
                modifier = Modifier.height(EmptyScreenSpacerHeight)
            )
            Text(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                text = stringResource(textId),
                color = appTopBarElementsColor,
                style = bodyStyle.copy(
                    fontSize = EmptyScreenFontSize
                )
            )
        }

    }
}

@Composable
@Preview
private fun AppEmptyScreenPreview() {
    AppEmptyScreen(
        modifier = Modifier
            .fillMaxSize()
            .background(appColor)
    )
}