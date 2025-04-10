package com.alenniboris.fastbanking.presentation.uikit.views

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.alenniboris.fastbanking.presentation.uikit.theme.AppRowFilterFirstItemPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.AppRowFilterItemPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.AppRowFilterItemShape
import com.alenniboris.fastbanking.presentation.uikit.theme.AppRowFilterItemTextPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.AppRowFilterItemTextSize
import com.alenniboris.fastbanking.presentation.uikit.theme.bodyStyle
import com.alenniboris.fastbanking.presentation.uikit.theme.mainScreenFilterItemColor
import com.alenniboris.fastbanking.presentation.uikit.theme.mainScreenFilterItemSelectedColor
import com.alenniboris.fastbanking.presentation.uikit.theme.mainScreenFilterItemSelectedTextColor
import com.alenniboris.fastbanking.presentation.uikit.theme.mainScreenFilterItemTextColor
import com.alenniboris.fastbanking.presentation.uikit.theme.mainScreenItemColor
import com.alenniboris.fastbanking.presentation.uikit.values.ClickableElement

@Composable
fun AppRowFilter(
    modifier: Modifier = Modifier,
    itemTint: Color,
    itemTextColor: Color,
    itemSelectedTint: Color,
    itemSelectedTextColor: Color,
    itemsLazyListState: LazyListState = rememberLazyListState(),
    elements: List<ClickableElement>,
    currentSelectedElement: ClickableElement,
) {

    LaunchedEffect(key1 = currentSelectedElement) {
        val index = elements.indexOfFirst { it == currentSelectedElement }
        if (index >= 0) {
            itemsLazyListState.animateScrollToItem(index)
        }
    }

    LazyRow(
        modifier = modifier,
        state = itemsLazyListState
    ) {
        itemsIndexed(elements) { index, element ->

            Box(
                modifier = Modifier
                    .animateItem(fadeInSpec = null, fadeOutSpec = null)
                    .padding(
                        if (index == 0) AppRowFilterFirstItemPadding
                        else AppRowFilterItemPadding
                    )
                    .clip(AppRowFilterItemShape)
                    .background(
                        color = if (element == currentSelectedElement) itemSelectedTint
                        else itemTint
                    )
                    .clickable { element.onClick() }

            ) {
                Text(
                    modifier = Modifier.padding(AppRowFilterItemTextPadding),
                    text = element.text,
                    style = bodyStyle.copy(
                        color = if (element == currentSelectedElement) itemSelectedTextColor
                        else itemTextColor,
                        fontWeight = FontWeight.Normal,
                        fontSize = AppRowFilterItemTextSize,
                    )
                )
            }
        }
    }
}

@Composable
@Preview
private fun AppRowFilterUiPreview() {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(mainScreenItemColor)
    ) {

        AppRowFilter(
            modifier = Modifier
                .fillMaxWidth(),
            elements = listOf(
                ClickableElement(
                    text = "1",
                    onClick = {}
                ),
                ClickableElement(
                    text = "2",
                    onClick = {}
                ),
                ClickableElement(
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
                ),
                ClickableElement(
                    text = "6",
                    onClick = {}
                ),
                ClickableElement(
                    text = "7",
                    onClick = {}
                ),
                ClickableElement(
                    text = "8",
                    onClick = {}
                ),
                ClickableElement(
                    text = "9",
                    onClick = {}
                ),
                ClickableElement(
                    text = "10",
                    onClick = {}
                ),
                ClickableElement(
                    text = "11",
                    onClick = {}
                )
            ),
            currentSelectedElement = ClickableElement(
                text = "3",
                onClick = {}
            ),
            itemTint = mainScreenFilterItemColor,
            itemTextColor = mainScreenFilterItemTextColor,
            itemSelectedTint = mainScreenFilterItemSelectedColor,
            itemSelectedTextColor = mainScreenFilterItemSelectedTextColor
        )
    }
}