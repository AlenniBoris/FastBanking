package com.alenniboris.fastbanking.presentation.uikit.views

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.alenniboris.fastbanking.presentation.uikit.theme.appColor
import com.alenniboris.fastbanking.presentation.uikit.utils.PermissionType
import com.alenniboris.fastbanking.presentation.uikit.utils.toPermissionExplanation
import com.alenniboris.fastbanking.presentation.uikit.utils.toPermissionName

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun AppPermissionRationaleDialog(
    permissionType: PermissionType,
    onDismiss: () -> Unit,
    onOpenSettings: () -> Unit
) {
    AlertDialog(
        onDismissRequest = { onDismiss() },
        confirmButton = {
            Button(
                onClick = onOpenSettings
            ) {
                Text(
                    text = "Go to settings"
                )
            }
        },
        dismissButton = {
            Button(onClick = { onDismiss() }) { Text("Dismiss") }
        },
        title = {
            Text(
                text = stringResource(permissionType.toPermissionName())
            )
        },
        text = {
            Text(
                stringResource(permissionType.toPermissionExplanation())
            )
        }
    )
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
@Preview
private fun AppRationaleDialogPreview() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(appColor)
    ) {
        AppPermissionRationaleDialog(
            permissionType = PermissionType.PERMISSION_COARSE_LOCATION,
            onDismiss = {},
            onOpenSettings = {}
        )
    }
}