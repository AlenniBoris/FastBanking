package com.alenniboris.fastbanking.presentation.uikit.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alenniboris.fastbanking.R
import com.alenniboris.fastbanking.presentation.uikit.theme.BackButtonPicture
import com.alenniboris.fastbanking.presentation.uikit.theme.ForwardButtonPicture
import com.alenniboris.fastbanking.presentation.uikit.theme.TopBarHeaderTextSize
import com.alenniboris.fastbanking.presentation.uikit.theme.TopBarPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.appColor
import com.alenniboris.fastbanking.presentation.uikit.theme.appTopBarElementsColor
import com.alenniboris.fastbanking.presentation.uikit.theme.bodyStyle

@Composable
fun AppTopBar(
    modifier: Modifier = Modifier,
    headerTextString: String = "",
    textAlignment: Alignment = Alignment.Center,
    isLeftBtnAnimated: Boolean = false,
    leftBtnPainter: Painter? = null,
    onLeftBtnClicked: () -> Unit = {},
    isRightBtnAnimated: Boolean = false,
    rightBtnPainter: Painter? = null,
    onRightBtnClicked: () -> Unit = {},
    content: (@Composable () -> Unit)? = null,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {

        leftBtnPainter?.let {
            AppIconButton(
                modifier = Modifier.padding(end = 10.dp),
                isAnimated = isLeftBtnAnimated,
                iconPainter = leftBtnPainter,
                onClick = onLeftBtnClicked,
                tint = appTopBarElementsColor,
                contentDescription = stringResource(R.string.top_bar_left_btn_description)
            )
        }

        content?.let { content ->
            content()
        } ?: Text(
            modifier = Modifier.weight(1f),
            text = headerTextString,
            color = appTopBarElementsColor,
            style = bodyStyle.copy(
                fontSize = TopBarHeaderTextSize,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
        )


        rightBtnPainter?.let {
            AppIconButton(
                modifier = Modifier.padding(start = 10.dp),
                isAnimated = isRightBtnAnimated,
                iconPainter = rightBtnPainter,
                onClick = onRightBtnClicked,
                tint = appTopBarElementsColor,
                contentDescription = stringResource(R.string.top_bar_right_btn_description)
            )
        }

    }
}

@Composable
@Preview
fun UiPreview() {
    Column {
        AppTopBar(
            modifier = Modifier
                .background(appColor)
                .fillMaxWidth()
                .padding(TopBarPadding),
            headerTextString = "hello",
            isLeftBtnAnimated = true,
            leftBtnPainter = painterResource(BackButtonPicture),
            rightBtnPainter = painterResource(ForwardButtonPicture),
        )

        Spacer(Modifier.height(10.dp))

        AppTopBar(
            modifier = Modifier
                .background(appColor)
                .fillMaxWidth()
                .padding(TopBarPadding),
            headerTextString = "перевыпуск платежных карт",

            isLeftBtnAnimated = true,
            isRightBtnAnimated = true,
            leftBtnPainter = painterResource(BackButtonPicture)
        )

        Spacer(Modifier.height(10.dp))

        AppTopBar(
            modifier = Modifier
                .background(appColor)
                .fillMaxWidth()
                .padding(TopBarPadding),
            headerTextString = "перевыпуск платежных карт",
            isLeftBtnAnimated = true,
        )
    }
}