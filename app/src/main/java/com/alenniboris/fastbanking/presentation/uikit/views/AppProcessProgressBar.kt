package com.alenniboris.fastbanking.presentation.uikit.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alenniboris.fastbanking.presentation.screens.register.registration_as_app_client.state.values.RegistrationAsAppClientProcessPart
import com.alenniboris.fastbanking.presentation.uikit.theme.ProcessProgressBarItemHeight
import com.alenniboris.fastbanking.presentation.uikit.theme.ProcessProgressBarItemPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.appColor
import com.alenniboris.fastbanking.presentation.uikit.theme.progressBarDoneProcess
import com.alenniboris.fastbanking.presentation.uikit.theme.progressBarNotDoneProcess

@Composable
fun <T> AppProcessProgressBar(
    modifier: Modifier,
    currentProcess: T,
    allProcesses: List<T>
) {
    val currentProcessIndex = allProcesses.indexOf(currentProcess)
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        allProcesses.forEachIndexed { index, _ ->
            Spacer(
                modifier = Modifier
                    .padding(ProcessProgressBarItemPadding)
                    .height(ProcessProgressBarItemHeight)
                    .weight(1f)
                    .background(
                        if (index <= currentProcessIndex) {
                            progressBarDoneProcess
                        } else {
                            progressBarNotDoneProcess
                        }
                    )
            )
        }
    }
}

@Composable
@Preview
private fun ProcessProgressBarPreview() {

    Column(
        modifier = Modifier.background(appColor)
    ) {
        AppProcessProgressBar(
            modifier = Modifier
                .background(appColor)
                .fillMaxWidth()
                .padding(PaddingValues(vertical = 10.dp)),
            currentProcess = RegistrationAsAppClientProcessPart.DataInput,
            allProcesses = RegistrationAsAppClientProcessPart.entries.toList()
        )

        AppProcessProgressBar(
            modifier = Modifier
                .background(appColor)
                .fillMaxWidth()
                .height(30.dp)
                .padding(PaddingValues(vertical = 5.dp)),
            currentProcess = RegistrationAsAppClientProcessPart.PhoneCodeCheck,
            allProcesses = RegistrationAsAppClientProcessPart.entries.toList()
        )

        AppProcessProgressBar(
            modifier = Modifier
                .background(appColor)
                .fillMaxWidth()
                .height(30.dp)
                .padding(PaddingValues(vertical = 5.dp)),
            currentProcess = 10,
            allProcesses = listOf(
                0,1,3,2,4,5,10,7,8,9
            )
        )
    }
}