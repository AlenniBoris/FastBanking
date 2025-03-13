package com.alenniboris.fastbanking.presentation.uikit.views

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.alenniboris.fastbanking.presentation.uikit.theme.BottomBarHeight
import com.alenniboris.fastbanking.presentation.uikit.theme.BottomBarIndicatorHeight
import com.alenniboris.fastbanking.presentation.uikit.theme.BottomBarIndicatorWidth
import com.alenniboris.fastbanking.presentation.uikit.theme.appTopBarElementsColor
import com.alenniboris.fastbanking.presentation.uikit.theme.enterTextFieldColor
import com.alenniboris.fastbanking.presentation.uikit.values.AuthorizedActions
import com.alenniboris.fastbanking.presentation.uikit.values.BottomBarModelUi
import com.alenniboris.fastbanking.presentation.uikit.values.LoginScreenRoute
import com.alenniboris.fastbanking.presentation.uikit.values.NotAuthorizedActions
import com.alenniboris.fastbanking.presentation.uikit.values.toBottomBarModel

@Composable
fun AppBottomBar(
    modifier: Modifier,
    items: List<BottomBarModelUi>,
    currentRoute: String
) {
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        items.forEach { screen ->
            BottomBarItem(
                item = screen,
                currentRoute = currentRoute
            )
        }
    }
}


@Composable
private fun BottomBarItem(
    item: BottomBarModelUi,
    currentRoute: String
) {

    Column(
        modifier = Modifier
            .clickable { item.onClick() },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            painter = painterResource(item.iconId),
            contentDescription = stringResource(item.textId),
            tint = appTopBarElementsColor
        )
        AnimatedVisibility(
            visible = item.route == currentRoute
        ) {
            HorizontalDivider(
                modifier = Modifier.width(BottomBarIndicatorWidth),
                color = appTopBarElementsColor,
                thickness = BottomBarIndicatorHeight
            )
        }
    }
}

@Composable
@Preview
fun BottomBarPreview() {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        AppBottomBar(
            modifier = Modifier
                .fillMaxWidth()
                .background(enterTextFieldColor)
                .height(BottomBarHeight),
            items = NotAuthorizedActions.entries.toList().map { action ->
                action.toBottomBarModel(
                    onClick = {}
                )
            },
            currentRoute = LoginScreenRoute
        )

        AppBottomBar(
            modifier = Modifier
                .fillMaxWidth()
                .background(enterTextFieldColor)
                .height(BottomBarHeight),
            items = AuthorizedActions.entries.toList().map { action ->
                action.toBottomBarModel(
                    onClick = {}
                )
            },
            currentRoute = ""
        )

    }
}