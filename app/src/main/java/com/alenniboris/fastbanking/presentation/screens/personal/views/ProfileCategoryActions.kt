package com.alenniboris.fastbanking.presentation.screens.personal.views

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.alenniboris.fastbanking.R
import com.alenniboris.fastbanking.presentation.screens.personal.IPersonalScreenIntent
import com.alenniboris.fastbanking.presentation.screens.personal.ProfileActions
import com.alenniboris.fastbanking.presentation.screens.personal.toUiDescriptionString
import com.alenniboris.fastbanking.presentation.screens.personal.toUiPicture
import com.alenniboris.fastbanking.presentation.screens.personal.toUiString
import com.alenniboris.fastbanking.presentation.uikit.theme.AdditionsScreenActionPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.FastBankingTheme
import com.alenniboris.fastbanking.presentation.uikit.theme.PersonalScreenProfileActionDescriptionTextPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.PersonalScreenProfileActionFontSize
import com.alenniboris.fastbanking.presentation.uikit.theme.PersonalScreenProfileActionTextPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.appColor
import com.alenniboris.fastbanking.presentation.uikit.theme.appTopBarElementsColor
import com.alenniboris.fastbanking.presentation.uikit.theme.bodyStyle

@Composable
fun ProfileCategoryActions(
    modifier: Modifier = Modifier,
    proceedIntent: (IPersonalScreenIntent) -> Unit
) {

    val profileActions by remember { mutableStateOf(ProfileActions.entries.toList()) }

    Column(
        modifier = modifier
    ) {

        profileActions.forEach { action ->
            ProfileActionUi(
                modifier = Modifier
                    .padding(AdditionsScreenActionPadding)
                    .fillMaxWidth()
                    .clickable {
                        proceedIntent(
                            IPersonalScreenIntent.ProceedAccordingToProfileAction(action)
                        )
                    },
                action = action
            )
        }
    }
}

@Composable
private fun ProfileActionUi(
    modifier: Modifier = Modifier,
    action: ProfileActions
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Icon(
            painter = painterResource(action.toUiPicture()),
            tint = appTopBarElementsColor,
            contentDescription = stringResource(action.toUiString())
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(PersonalScreenProfileActionTextPadding)
        ) {
            Text(
                text = stringResource(action.toUiString()),
                style = bodyStyle.copy(
                    fontSize = PersonalScreenProfileActionFontSize,
                    fontWeight = FontWeight.Bold,
                    color = appTopBarElementsColor
                )
            )

            Text(
                modifier = Modifier.padding(PersonalScreenProfileActionDescriptionTextPadding),
                text = stringResource(action.toUiDescriptionString()),
                style = bodyStyle.copy(
                    fontSize = PersonalScreenProfileActionFontSize,
                    color = appTopBarElementsColor
                )
            )
        }


        Icon(
            painter = painterResource(R.drawable.forward_icon),
            tint = appTopBarElementsColor,
            contentDescription = stringResource(action.toUiString())
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
            ProfileCategoryActions(
                modifier = Modifier
                    .fillMaxSize()
                    .background(appColor),
                proceedIntent = {}
            )
        }
    }
}

@Composable
@Preview
private fun DarkTheme() {
    FastBankingTheme(
        darkTheme = true
    ) {
        Surface {
            ProfileCategoryActions(
                modifier = Modifier
                    .fillMaxSize()
                    .background(appColor),
                proceedIntent = {}
            )
        }
    }
}