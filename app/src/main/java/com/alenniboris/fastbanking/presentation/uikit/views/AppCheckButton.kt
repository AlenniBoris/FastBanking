package com.alenniboris.fastbanking.presentation.uikit.views

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.alenniboris.fastbanking.R
import com.alenniboris.fastbanking.presentation.uikit.theme.SettingsScreenItemCheckboxBorderWidth
import com.alenniboris.fastbanking.presentation.uikit.theme.appColor
import com.alenniboris.fastbanking.presentation.uikit.theme.appTopBarElementsColor
import com.alenniboris.fastbanking.presentation.uikit.theme.settingsScreenCheckBoxChecked
import com.alenniboris.fastbanking.presentation.uikit.theme.settingsScreenCheckBoxCheckedItemColor

@Composable
fun AppCheckButton(
    isChecked: Boolean,
    onClick: () -> Unit = {}
) {

    val boxBackgroundColor by animateColorAsState(
        if (isChecked) settingsScreenCheckBoxChecked
        else appColor
    )
    val boxElementColor by animateColorAsState(
        if (isChecked) settingsScreenCheckBoxCheckedItemColor
        else appTopBarElementsColor
    )
    Box(
        modifier = Modifier
            .clip(CircleShape)
            .border(
                width = SettingsScreenItemCheckboxBorderWidth,
                color = boxElementColor,
                shape = CircleShape
            )
            .background(boxBackgroundColor)
            .clickable {
                onClick()
            }
    ) {
        Icon(
            painter = painterResource(R.drawable.selected_check_box_icon),
            tint = if (isChecked) boxElementColor
            else appColor,
            contentDescription = stringResource(R.string.checkbox_button_description)
        )
    }
}