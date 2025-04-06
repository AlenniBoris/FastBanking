package com.alenniboris.fastbanking.presentation.screens.registration.registration_as_app_client.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import com.alenniboris.fastbanking.presentation.screens.registration.registration_as_app_client.IRegistrationAsAppClientScreenIntent
import com.alenniboris.fastbanking.presentation.screens.registration.registration_as_app_client.state.values.PhoneCodeCheckPartState
import com.alenniboris.fastbanking.presentation.uikit.theme.DataInputProcessUiDocumentTypePadding
import com.alenniboris.fastbanking.presentation.uikit.theme.DataInputProcessUiTextFontSize
import com.alenniboris.fastbanking.presentation.uikit.theme.DataInputProcessUiTextPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.EnterValueTextFieldPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.EnterValueTextFieldShape
import com.alenniboris.fastbanking.presentation.uikit.theme.PhoneNumberInputProcessUiContainerPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.appColor
import com.alenniboris.fastbanking.presentation.uikit.theme.appTopBarElementsColor
import com.alenniboris.fastbanking.presentation.uikit.theme.bodyStyle
import com.alenniboris.fastbanking.presentation.uikit.theme.enterTextFieldColor
import com.alenniboris.fastbanking.presentation.uikit.views.AppTextField

@Composable
fun PhoneCodeCheckProcessUi(
    modifier: Modifier = Modifier,
    state: PhoneCodeCheckPartState,
    proceedIntent: (IRegistrationAsAppClientScreenIntent) -> Unit
) {
    Column(
        modifier = modifier
            .padding(PhoneNumberInputProcessUiContainerPadding),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            modifier = Modifier
                .padding(DataInputProcessUiTextPadding)
                .fillMaxWidth(),
            textAlign = TextAlign.Center,
            text = stringResource(R.string.phone_verification_code_text),
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
            value = state.enteredCode,
            onValueChanged = { newValue ->
                proceedIntent(
                    IRegistrationAsAppClientScreenIntent.UpdatePhoneCode(
                        newValue = newValue
                    )
                )
            },
            placeholder = stringResource(R.string.code_check_placeholder)
        )
    }
}

@Composable
@Preview
private fun PhoneCodeCheckProcessUiPreview() {
    PhoneCodeCheckProcessUi(
        modifier = Modifier
            .fillMaxSize()
            .background(appColor),
        state = PhoneCodeCheckPartState(),
        proceedIntent = {}
    )
}