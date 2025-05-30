package com.alenniboris.fastbanking.presentation.screens.credit_details.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.alenniboris.fastbanking.R
import com.alenniboris.fastbanking.domain.model.credit.CreditModelDomain
import com.alenniboris.fastbanking.presentation.model.bank_product.CreditModelUi
import com.alenniboris.fastbanking.presentation.uikit.theme.CreditDetailsPlaceholderContainerInnerPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.CreditDetailsPlaceholderElementPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.CreditDetailsPlaceholderElementTextPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.CreditDetailsPlaceholderMainTextSize
import com.alenniboris.fastbanking.presentation.uikit.theme.CreditDetailsPlaceholderShape
import com.alenniboris.fastbanking.presentation.uikit.theme.CreditDetailsPlaceholderSubtleTextSize
import com.alenniboris.fastbanking.presentation.uikit.theme.CreditDetailsScreenCardPlaceholderPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.CreditDetailsScreenContentPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.FastBankingTheme
import com.alenniboris.fastbanking.presentation.uikit.theme.appColor
import com.alenniboris.fastbanking.presentation.uikit.theme.bodyStyle
import com.alenniboris.fastbanking.presentation.uikit.theme.mainScreenItemColor
import com.alenniboris.fastbanking.presentation.uikit.theme.mainScreenTextColor
import java.util.Calendar

@Composable
fun CreditDetailsPlaceholder(
    modifier: Modifier = Modifier,
    credit: CreditModelUi
) {

    Column(
        modifier = modifier
    ) {

        PlaceholderTextSection(
            modifier = Modifier.fillMaxWidth(),
            header = stringResource(R.string.amount_in_main_currency_text),
            text = credit.creditBodyText
        )

        PlaceholderTextSection(
            modifier = Modifier
                .padding(CreditDetailsPlaceholderElementPadding)
                .fillMaxWidth(),
            header = stringResource(R.string.amount_in_reserve_currency_text),
            text = credit.reserveCreditBodyText
        )

        PlaceholderTextSection(
            modifier = Modifier
                .padding(CreditDetailsPlaceholderElementPadding)
                .fillMaxWidth(),
            header = stringResource(R.string.credit_percentage_text),
            text = credit.percentageText
        )

        PlaceholderTextSection(
            modifier = Modifier
                .padding(CreditDetailsPlaceholderElementPadding)
                .fillMaxWidth(),
            header = stringResource(R.string.credit_start_date_text),
            text = credit.creditStartDateText
        )
    }
}

@Composable
private fun PlaceholderTextSection(
    modifier: Modifier = Modifier,
    header: String,
    text: String
) {

    Column(
        modifier = modifier
    ) {

        Text(
            text = header,
            style = bodyStyle.copy(
                color = mainScreenTextColor,
                fontWeight = FontWeight.Light,
                fontSize = CreditDetailsPlaceholderSubtleTextSize
            )
        )

        Text(
            modifier = Modifier.padding(CreditDetailsPlaceholderElementTextPadding),
            text = text,
            style = bodyStyle.copy(
                color = mainScreenTextColor,
                fontSize = CreditDetailsPlaceholderMainTextSize
            )
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
                    .background(appColor)
                    .padding(CreditDetailsScreenContentPadding)
            ) {

                CreditDetailsPlaceholder(
                    modifier = Modifier
                        .padding(CreditDetailsScreenCardPlaceholderPadding)
                        .clip(CreditDetailsPlaceholderShape)
                        .fillMaxWidth()
                        .background(mainScreenItemColor)
                        .padding(CreditDetailsPlaceholderContainerInnerPadding),
                    credit = CreditModelUi(
                        domainModel = CreditModelDomain(
                            id = "21213",
                            initialAmount = 11.2,
                            currency = "byn",
                            amountInReserveCurrency = 11.2,
                            reserveCurrency = "usd",
                            percentage = 5.0,
                            lastPayment = Calendar.getInstance().time,
                            startDate = Calendar.getInstance().time,
                            goalDescription = "wwdwd",
                            ownerId = "111",
                            name = "odsklmclksd",
                            erip = "kjjsdnkjcsndk",
                            contractNumber = "kjdnak2313",
                            bankIdCode = "sdilakjlkasnx"
                        )
                    )
                )
            }
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
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(appColor)
                    .padding(CreditDetailsScreenContentPadding)
            ) {

                CreditDetailsPlaceholder(
                    modifier = Modifier
                        .padding(CreditDetailsScreenCardPlaceholderPadding)
                        .clip(CreditDetailsPlaceholderShape)
                        .fillMaxWidth()
                        .background(mainScreenItemColor)
                        .padding(CreditDetailsPlaceholderContainerInnerPadding),
                    credit = CreditModelUi(
                        domainModel = CreditModelDomain(
                            id = "21213",
                            initialAmount = 11.2,
                            currency = "byn",
                            amountInReserveCurrency = 11.2,
                            reserveCurrency = "usd",
                            percentage = 5.0,
                            lastPayment = Calendar.getInstance().time,
                            startDate = Calendar.getInstance().time,
                            goalDescription = "wwdwd",
                            ownerId = "111",
                            name = "odsklmclksd",
                            erip = "kjjsdnkjcsndk",
                            contractNumber = "kjdnak2313",
                            bankIdCode = "sdilakjlkasnx"
                        )
                    )
                )
            }
        }
    }
}
