package com.alenniboris.fastbanking.presentation.screens.password_reset.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.alenniboris.fastbanking.R
import com.alenniboris.fastbanking.presentation.screens.password_reset.IPasswordResetScreenIntent
import com.alenniboris.fastbanking.presentation.screens.password_reset.state.PasswordChangePartState
import com.alenniboris.fastbanking.presentation.uikit.theme.DataInputProcessUiDocumentTypePadding
import com.alenniboris.fastbanking.presentation.uikit.theme.DataInputProcessUiTextFontSize
import com.alenniboris.fastbanking.presentation.uikit.theme.DataInputProcessUiTextPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.EnterValueTextFieldPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.EnterValueTextFieldShape
import com.alenniboris.fastbanking.presentation.uikit.theme.LoginScreenSpacer
import com.alenniboris.fastbanking.presentation.uikit.theme.SettingPasswordProcessUiContainerPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.appColor
import com.alenniboris.fastbanking.presentation.uikit.theme.appTopBarElementsColor
import com.alenniboris.fastbanking.presentation.uikit.theme.bodyStyle
import com.alenniboris.fastbanking.presentation.uikit.theme.enterTextFieldColor
import com.alenniboris.fastbanking.presentation.uikit.views.AppTextField

@Composable
fun PasswordChangeUi(
    modifier: Modifier = Modifier,
    state: PasswordChangePartState,
    proceedIntent: (IPasswordResetScreenIntent) -> Unit
) {

    Column(
        modifier = modifier
            .padding(SettingPasswordProcessUiContainerPadding),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        Text(
            modifier = Modifier
                .padding(DataInputProcessUiTextPadding)
                .fillMaxWidth(),
            textAlign = TextAlign.Center,
            text = stringResource(R.string.setting_password_text),
            style = bodyStyle.copy(
                color = appTopBarElementsColor,
                fontWeight = FontWeight.Bold,
                fontSize = DataInputProcessUiTextFontSize,
                textAlign = TextAlign.Center
            )
        )

        AppTextField(
            modifier = Modifier
                .padding(DataInputProcessUiDocumentTypePadding)
                .background(
                    color = enterTextFieldColor,
                    shape = EnterValueTextFieldShape
                )
                .padding(EnterValueTextFieldPadding)
                .fillMaxWidth(),
            value = state.enteredPassword,
            onValueChanged = { newPassword ->
                proceedIntent(
                    IPasswordResetScreenIntent.UpdateEnteredPassword(newPassword)
                )
            },
            placeholder = stringResource(R.string.password_placeholder_text),
            isPasswordField = true,
            isPasswordVisible = state.isPasswordVisible,
            onPasswordVisibilityChange = {
                proceedIntent(
                    IPasswordResetScreenIntent.UpdatePasswordVisibility
                )
            }
        )

        Spacer(modifier = Modifier.height(LoginScreenSpacer))

        AppTextField(
            modifier = Modifier
                .background(
                    color = enterTextFieldColor,
                    shape = EnterValueTextFieldShape
                )
                .padding(EnterValueTextFieldPadding)
                .fillMaxWidth(),
            value = state.passwordCheck,
            onValueChanged = { newPassword ->
                proceedIntent(
                    IPasswordResetScreenIntent.UpdatePasswordCheck(newPassword)
                )
            },
            placeholder = stringResource(R.string.password_check_placeholder_text),
            isPasswordField = true,
            isPasswordVisible = state.isPasswordCheckVisible,
            onPasswordVisibilityChange = {
                proceedIntent(
                    IPasswordResetScreenIntent.UpdatePasswordCheckVisibility
                )
            }
        )
    }
}

@Composable
@Preview
private fun PasswordChangeUiPreview() {
    PasswordChangeUi(
        modifier = Modifier
            .fillMaxSize()
            .background(appColor),
        state = PasswordChangePartState(),
        proceedIntent = {}
    )
}