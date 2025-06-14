package com.alenniboris.fastbanking.presentation.screens.password_reset.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.alenniboris.fastbanking.R
import com.alenniboris.fastbanking.presentation.screens.password_reset.IPasswordResetScreenIntent
import com.alenniboris.fastbanking.presentation.screens.password_reset.state.DocumentInputPartState
import com.alenniboris.fastbanking.presentation.uikit.theme.DataInputProcessUiContainerPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.DataInputProcessUiContentHintFontSize
import com.alenniboris.fastbanking.presentation.uikit.theme.DataInputProcessUiContentTextFontSize
import com.alenniboris.fastbanking.presentation.uikit.theme.DataInputProcessUiDocumentTypePadding
import com.alenniboris.fastbanking.presentation.uikit.theme.DataInputProcessUiTextFontSize
import com.alenniboris.fastbanking.presentation.uikit.theme.DataInputProcessUiTextPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.EnterValueTextFieldPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.EnterValueTextFieldShape
import com.alenniboris.fastbanking.presentation.uikit.theme.appColor
import com.alenniboris.fastbanking.presentation.uikit.theme.appTopBarElementsColor
import com.alenniboris.fastbanking.presentation.uikit.theme.bodyStyle
import com.alenniboris.fastbanking.presentation.uikit.theme.enterTextFieldColor
import com.alenniboris.fastbanking.presentation.uikit.theme.enterTextFieldTextColor
import com.alenniboris.fastbanking.presentation.uikit.values.RegistrationDocumentType
import com.alenniboris.fastbanking.presentation.uikit.values.toDocumentHintDescriptionString
import com.alenniboris.fastbanking.presentation.uikit.values.toDocumentHintString
import com.alenniboris.fastbanking.presentation.uikit.values.toDocumentTypeString
import com.alenniboris.fastbanking.presentation.uikit.views.AppCustomButton
import com.alenniboris.fastbanking.presentation.uikit.views.AppTextField
import java.util.Locale

@Composable
fun DocumentInputUi(
    modifier: Modifier,
    state: DocumentInputPartState,
    proceedIntent: (IPasswordResetScreenIntent) -> Unit
) {

    Column(
        modifier = modifier
            .padding(DataInputProcessUiContainerPadding),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Image(
            painter = painterResource(R.drawable.registration_filling_data),
            contentDescription = stringResource(R.string.registration_data_input_description)
        )

        Text(
            modifier = Modifier
                .padding(DataInputProcessUiTextPadding)
                .width(IntrinsicSize.Max),
            textAlign = TextAlign.Center,
            text = stringResource(R.string.user_data_text),
            style = bodyStyle.copy(
                color = appTopBarElementsColor,
                fontWeight = FontWeight.Bold,
                fontSize = DataInputProcessUiTextFontSize
            )
        )

        AppCustomButton(
            modifier = Modifier
                .padding(DataInputProcessUiDocumentTypePadding)
                .fillMaxWidth(),
            onClick = {
                proceedIntent(IPasswordResetScreenIntent.UpdateOptionsBottomSheetVisibility)
            },
            content = {
                DocumentOptionsButtonContent(
                    registrationDocumentType = state.selectedDocument
                )
            }
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
            value = when (state.selectedDocument) {
                RegistrationDocumentType.Passport -> state.identificationNumber.uppercase(
                    Locale.getDefault()
                )
            },
            onValueChanged = { newValue ->
                proceedIntent(
                    IPasswordResetScreenIntent.UpdateDocumentValue(
                        documentType = state.selectedDocument,
                        newValue = newValue.uppercase(Locale.getDefault())
                    )
                )
            },
            placeholder = stringResource(state.selectedDocument.toDocumentHintString())
        )

        Text(
            modifier = Modifier
                .padding(DataInputProcessUiTextPadding)
                .width(IntrinsicSize.Max),
            text = stringResource(state.selectedDocument.toDocumentHintDescriptionString()),
            style = bodyStyle.copy(
                color = enterTextFieldTextColor,
                fontSize = DataInputProcessUiContentTextFontSize
            )
        )

    }
}

@Composable
private fun DocumentOptionsButtonContent(
    registrationDocumentType: RegistrationDocumentType
) {
    Row(
        modifier = Modifier
            .padding(EnterValueTextFieldPadding)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Column(
            modifier = Modifier.weight(1f)
        ) {

            Text(
                textAlign = TextAlign.Center,
                text = stringResource(R.string.document_type_text),
                style = bodyStyle.copy(
                    color = enterTextFieldTextColor,
                    fontSize = DataInputProcessUiContentHintFontSize
                )
            )

            Text(
                textAlign = TextAlign.Center,
                text = stringResource(registrationDocumentType.toDocumentTypeString()),
                style = bodyStyle.copy(
                    color = enterTextFieldTextColor,
                    fontSize = DataInputProcessUiContentTextFontSize
                )
            )
        }

        Icon(
            painter = painterResource(R.drawable.open_options_icon),
            tint = enterTextFieldTextColor,
            contentDescription = stringResource(R.string.document_options_description)
        )
    }
}

@Composable
@Preview
private fun DataInputProcessUiPreview() {

    DocumentInputUi(
        modifier = Modifier
            .fillMaxSize()
            .background(appColor),
        state = DocumentInputPartState(),
        proceedIntent = {}
    )

}