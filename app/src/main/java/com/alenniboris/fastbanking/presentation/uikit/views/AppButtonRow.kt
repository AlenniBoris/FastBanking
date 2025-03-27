package com.alenniboris.fastbanking.presentation.uikit.views

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.alenniboris.fastbanking.presentation.uikit.theme.MapLocationItemContentTextSize
import com.alenniboris.fastbanking.presentation.uikit.theme.appButtonRowButtonInnerPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.appButtonRowButtonNotSelectedColor
import com.alenniboris.fastbanking.presentation.uikit.theme.appButtonRowButtonOuterPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.appButtonRowButtonSelectedColor
import com.alenniboris.fastbanking.presentation.uikit.theme.appButtonRowButtonShape
import com.alenniboris.fastbanking.presentation.uikit.theme.appButtonRowButtonTextNotSelectedColor
import com.alenniboris.fastbanking.presentation.uikit.theme.appButtonRowButtonTextSelectedColor
import com.alenniboris.fastbanking.presentation.uikit.theme.appColor
import com.alenniboris.fastbanking.presentation.uikit.theme.bodyStyle
import com.alenniboris.fastbanking.presentation.uikit.values.ClickableElement

@Composable
fun AppButtonRow(
    modifier: Modifier = Modifier,
    currentElement: ClickableElement,
    listOfElements: List<ClickableElement>,
) {

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        listOfElements.forEach { element ->

            val backgroundColor by animateColorAsState(
                if (element == currentElement)
                    appButtonRowButtonSelectedColor
                else
                    appButtonRowButtonNotSelectedColor
            )

            val textColor by animateColorAsState(
                if (element == currentElement)
                    appButtonRowButtonTextSelectedColor
                else
                    appButtonRowButtonTextNotSelectedColor
            )

            Box(
                modifier = Modifier
                    .clickable {
                        element.onClick()
                    }
                    .padding(appButtonRowButtonOuterPadding)
                    .weight(1f)
                    .background(
                        color = backgroundColor,
                        shape = appButtonRowButtonShape
                    )
                    .padding(appButtonRowButtonInnerPadding)
            ) {

                Text(
                    modifier = Modifier.align(Alignment.Center),
                    text = element.text,
                    style = bodyStyle.copy(
                        color = textColor,
                        fontSize = MapLocationItemContentTextSize
                    )
                )
            }
        }
    }
}

@Composable
@Preview
private fun AppButtonRowPreview() {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(appColor)
    ) {
        AppButtonRow(
            modifier = Modifier.fillMaxWidth(),
            currentElement = ClickableElement(
                text = "1",
                onClick = {}
            ),
            listOfElements = listOf(
                ClickableElement(
                    text = "1",
                    onClick = {}
                ),
                ClickableElement(
                    text = "2",
                    onClick = {}
                ), ClickableElement(
                    text = "3",
                    onClick = {}
                ),
                ClickableElement(
                    text = "4",
                    onClick = {}
                ),
                ClickableElement(
                    text = "5",
                    onClick = {}
                )
            )
        )
    }
}