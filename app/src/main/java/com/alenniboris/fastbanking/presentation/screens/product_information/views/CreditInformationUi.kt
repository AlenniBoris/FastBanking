package com.alenniboris.fastbanking.presentation.screens.product_information.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.alenniboris.fastbanking.R
import com.alenniboris.fastbanking.domain.model.credit.CreditModelDomain
import com.alenniboris.fastbanking.presentation.model.bank_product.CreditModelUi
import com.alenniboris.fastbanking.presentation.screens.product_information.IProductInformationScreenIntent
import com.alenniboris.fastbanking.presentation.uikit.theme.FastBankingTheme
import com.alenniboris.fastbanking.presentation.uikit.theme.ProductInformationScreenContentPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.ProductInformationScreenSectionPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.appColor
import java.util.Calendar

@Composable
fun CreditInformationUi(
    modifier: Modifier = Modifier,
    credit: CreditModelUi,
    proceedIntent: (IProductInformationScreenIntent) -> Unit
) {

    Column(
        modifier = modifier
    ) {

        ProductInformationCopySection(
            modifier = Modifier
                .padding(ProductInformationScreenSectionPadding)
                .fillMaxWidth(),
            header = stringResource(R.string.credit_start_date_text),
            text = credit.creditStartDateText,
            onClick = {
                proceedIntent(
                    IProductInformationScreenIntent.CopyTextToClipboard(
                        credit.creditStartDateText
                    )
                )
            }
        )

        ProductInformationCopySection(
            modifier = Modifier
                .padding(ProductInformationScreenSectionPadding)
                .fillMaxWidth(),
            header = stringResource(R.string.contract_number_text),
            text = credit.domainModel.contractNumber,
            onClick = {
                proceedIntent(
                    IProductInformationScreenIntent.CopyTextToClipboard(
                        credit.domainModel.contractNumber
                    )
                )
            }
        )

        ProductInformationCopySection(
            modifier = Modifier
                .padding(ProductInformationScreenSectionPadding)
                .fillMaxWidth(),
            header = stringResource(R.string.erip_number_text),
            text = credit.domainModel.erip,
            onClick = {
                proceedIntent(
                    IProductInformationScreenIntent.CopyTextToClipboard(
                        credit.domainModel.erip
                    )
                )
            }
        )

        ProductInformationCopySection(
            modifier = Modifier
                .padding(ProductInformationScreenSectionPadding)
                .fillMaxWidth(),
            header = stringResource(R.string.bank_id_code_text),
            text = credit.domainModel.bankIdCode,
            onClick = {
                proceedIntent(
                    IProductInformationScreenIntent.CopyTextToClipboard(
                        credit.domainModel.bankIdCode
                    )
                )
            }
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
            CreditInformationUi(
                modifier = Modifier
                    .fillMaxSize()
                    .background(appColor)
                    .padding(ProductInformationScreenContentPadding),
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
                ),
                proceedIntent = {}
            )
        }
    }
}