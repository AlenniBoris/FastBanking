package com.alenniboris.fastbanking.presentation.uikit.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchColors
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alenniboris.fastbanking.presentation.uikit.theme.FastBankingTheme
import com.alenniboris.fastbanking.presentation.uikit.theme.appTopBarElementsColor
import com.alenniboris.fastbanking.presentation.uikit.theme.bodyStyle
import com.alenniboris.fastbanking.presentation.uikit.theme.mainScreenItemColor
import com.alenniboris.fastbanking.presentation.uikit.theme.productApplianceFormCheckboxCheckedTrackColor
import com.alenniboris.fastbanking.presentation.uikit.theme.productApplianceFormCheckboxUncheckedTrackColor
import com.alenniboris.fastbanking.presentation.uikit.theme.productApplianceFormScreenCheckboxUncheckedContentColor

@Composable
fun AppTextSectionWithSwitcher(
    modifier: Modifier,
    text: String,
    textStyle: TextStyle,
    isChecked: Boolean,
    isSwitchEnabled: Boolean = true,
    onCheckedChanged: (Boolean) -> Unit = {},
    switchColors: SwitchColors = SwitchDefaults.colors()
) {

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            modifier = Modifier.weight(1f),
            text = text,
            style = textStyle
        )

        Switch(
            checked = isChecked,
            onCheckedChange = onCheckedChanged,
            enabled = isSwitchEnabled,
            colors = switchColors
        )
    }
}

@Composable
@Preview
private fun LightTheme() {

    FastBankingTheme(
        darkTheme = false
    ) {
        Surface {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(mainScreenItemColor)
                    .padding(horizontal = 20.dp)
            ) {

                AppTextSectionWithSwitcher(
                    modifier = Modifier.fillMaxWidth(),
                    text = "first",
                    textStyle = bodyStyle.copy(
                        color = appTopBarElementsColor,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    isChecked = false,
                    onCheckedChanged = {}
                )

                AppTextSectionWithSwitcher(
                    modifier = Modifier.fillMaxWidth(),
                    text = "first",
                    textStyle = bodyStyle.copy(
                        color = appTopBarElementsColor,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    isChecked = true,
                    onCheckedChanged = {}
                )

                AppTextSectionWithSwitcher(
                    modifier = Modifier.fillMaxWidth(),
                    text = "first",
                    textStyle = bodyStyle.copy(
                        color = appTopBarElementsColor,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    isChecked = false,
                    onCheckedChanged = {},
                    switchColors = SwitchDefaults.colors().copy(
                        uncheckedTrackColor = productApplianceFormCheckboxUncheckedTrackColor,
                        uncheckedBorderColor = productApplianceFormScreenCheckboxUncheckedContentColor,
                        uncheckedIconColor = productApplianceFormScreenCheckboxUncheckedContentColor,
                        uncheckedThumbColor = productApplianceFormScreenCheckboxUncheckedContentColor,
                        checkedTrackColor = productApplianceFormCheckboxCheckedTrackColor
                    )
                )

                AppTextSectionWithSwitcher(
                    modifier = Modifier.fillMaxWidth(),
                    text = "first",
                    textStyle = bodyStyle.copy(
                        color = appTopBarElementsColor,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    isChecked = true,
                    onCheckedChanged = {},
                    switchColors = SwitchDefaults.colors().copy(
                        uncheckedTrackColor = productApplianceFormCheckboxUncheckedTrackColor,
                        uncheckedBorderColor = productApplianceFormScreenCheckboxUncheckedContentColor,
                        uncheckedIconColor = productApplianceFormScreenCheckboxUncheckedContentColor,
                        uncheckedThumbColor = productApplianceFormScreenCheckboxUncheckedContentColor,
                        checkedTrackColor = productApplianceFormCheckboxCheckedTrackColor
                    )
                )
            }
        }
    }
}