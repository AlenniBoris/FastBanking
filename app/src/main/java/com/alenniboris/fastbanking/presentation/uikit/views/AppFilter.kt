package com.alenniboris.fastbanking.presentation.uikit.views

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.alenniboris.fastbanking.presentation.uikit.theme.FilterContainerPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.FilterDragHandleHeight
import com.alenniboris.fastbanking.presentation.uikit.theme.FilterDragHandlePadding
import com.alenniboris.fastbanking.presentation.uikit.theme.FilterDragHandleShape
import com.alenniboris.fastbanking.presentation.uikit.theme.FilterDragHandleWidth
import com.alenniboris.fastbanking.presentation.uikit.theme.FilterSheetShape
import com.alenniboris.fastbanking.presentation.uikit.theme.FilterSheetTonalElevation
import com.alenniboris.fastbanking.presentation.uikit.theme.FilterTextPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.FilterTextSize
import com.alenniboris.fastbanking.presentation.uikit.theme.appColor
import com.alenniboris.fastbanking.presentation.uikit.theme.appFilterColor
import com.alenniboris.fastbanking.presentation.uikit.theme.appFilterTextColor
import com.alenniboris.fastbanking.presentation.uikit.theme.appTopBarElementsColor
import com.alenniboris.fastbanking.presentation.uikit.theme.bodyStyle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> AppFilter(
    elements: List<T>,
    currentElement: T,
    elementsStringsIds: List<Int>,
    onElementClick: (T) -> Unit = {},
    onDismiss: () -> Unit = {},
    sheetState: SheetState = rememberModalBottomSheetState()
) {
    ModalBottomSheet(
        modifier = Modifier
            .background(appFilterColor),
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        shape = FilterSheetShape,
        containerColor = appColor,
        tonalElevation = FilterSheetTonalElevation,
        dragHandle = {
            Box(
                modifier = Modifier
                    .padding(FilterDragHandlePadding)
                    .width(FilterDragHandleWidth)
                    .height(FilterDragHandleHeight)
                    .clip(FilterDragHandleShape)
                    .background(appTopBarElementsColor)

            )
        }
    ) {

        Column(
            modifier = Modifier
                .padding(FilterContainerPadding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            elements.forEachIndexed() { index, element ->
                Text(
                    modifier = Modifier
                        .padding(FilterTextPadding)
                        .clickable { onElementClick(element) },
                    text = stringResource(elementsStringsIds[index]),
                    style = bodyStyle.copy(
                        fontWeight = if (currentElement == element) {
                            FontWeight.Bold
                        } else {
                            FontWeight.Normal
                        },
                        color = appFilterTextColor,
                        fontSize = FilterTextSize
                    )
                )
            }
        }
    }
}