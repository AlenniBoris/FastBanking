package com.alenniboris.fastbanking.presentation.uikit.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alenniboris.fastbanking.presentation.uikit.theme.appColor
import com.alenniboris.fastbanking.presentation.uikit.theme.appDropdownColor
import com.alenniboris.fastbanking.presentation.uikit.theme.appTopBarElementsColor
import com.alenniboris.fastbanking.presentation.uikit.theme.bodyStyle
import com.alenniboris.fastbanking.presentation.uikit.values.ClickableElement

@Composable
fun AppDropdownMenu(
    modifier: Modifier = Modifier,
    isVisible: Boolean,
    listOfElements: List<ClickableElement>,
    onDismiss: () -> Unit,
) {

    DropdownMenu(
        modifier = modifier,
        shadowElevation = 10.dp,
        expanded = isVisible,
        onDismissRequest = onDismiss,
        scrollState = rememberScrollState(),
        content = {
            listOfElements.forEach {
                DropdownMenuItem(
                    text = {
                        Text(
                            text = it.text,
                            style = bodyStyle.copy(
                                color = appTopBarElementsColor
                            )
                        )
                    },
                    onClick = {
                        it.onClick()
                        onDismiss()
                    }
                )
            }
        }
    )
}

@Composable
@Preview
private fun AppDropdownMenuPreview() {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(appColor)
    ) {
        AppDropdownMenu(
            modifier = Modifier
                .background(appDropdownColor)
                .align(Alignment.TopStart),
            isVisible = false,
            listOfElements = listOf(
                ClickableElement(
                    text = "1",
                    onClick = {}
                ),
                ClickableElement(
                    text = "1",
                    onClick = {}
                ),
                ClickableElement(
                    text = "1",
                    onClick = {}
                ),
                ClickableElement(
                    text = "1",
                    onClick = {}
                )
            ),
            onDismiss = {}
        )
    }


}