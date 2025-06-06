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
import com.alenniboris.fastbanking.domain.model.OwnerModelDomain
import com.alenniboris.fastbanking.domain.model.card.CardModelDomain
import com.alenniboris.fastbanking.domain.model.card.CardSystem
import com.alenniboris.fastbanking.domain.model.card.CardType
import com.alenniboris.fastbanking.presentation.model.bank_product.CardModelUi
import com.alenniboris.fastbanking.presentation.screens.product_information.IProductInformationScreenIntent
import com.alenniboris.fastbanking.presentation.uikit.theme.FastBankingTheme
import com.alenniboris.fastbanking.presentation.uikit.theme.ProductInformationScreenContentPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.ProductInformationScreenSectionPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.appColor
import java.util.Calendar

@Composable
fun CardInformationUi(
    modifier: Modifier = Modifier,
    card: CardModelUi,
    proceedIntent: (IProductInformationScreenIntent) -> Unit
) {

    Column(
        modifier = modifier
    ) {

        ProductInformationCopySection(
            modifier = Modifier
                .padding(ProductInformationScreenSectionPadding)
                .fillMaxWidth(),
            header = stringResource(R.string.erip_number_description_text),
            text = card.domainModel.erip,
            onClick = {
                proceedIntent(
                    IProductInformationScreenIntent.CopyTextToClipboard(
                        card.domainModel.erip
                    )
                )
            }
        )

        ProductInformationCopySection(
            modifier = Modifier
                .padding(ProductInformationScreenSectionPadding)
                .fillMaxWidth(),
            header = stringResource(R.string.iban_number_text),
            text = card.domainModel.iban,
            onClick = {
                proceedIntent(
                    IProductInformationScreenIntent.CopyTextToClipboard(
                        card.domainModel.iban
                    )
                )
            }
        )

        ProductInformationCopySection(
            modifier = Modifier
                .padding(ProductInformationScreenSectionPadding)
                .fillMaxWidth(),
            header = stringResource(R.string.bank_id_code_text),
            text = card.domainModel.bankIdCode,
            onClick = {
                proceedIntent(
                    IProductInformationScreenIntent.CopyTextToClipboard(
                        card.domainModel.bankIdCode
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
            CardInformationUi(
                modifier = Modifier
                    .fillMaxSize()
                    .background(appColor)
                    .padding(ProductInformationScreenContentPadding),
                card = CardModelUi(
                    domainModel = CardModelDomain(
                        id = "",
                        currencyCode = "bbyn",
                        reserveCurrencyCode = "byn",
                        amountInReserveCurrency = 0.0,
                        amount = 0.0,
                        owner = OwnerModelDomain(
                            id = "",
                            name = "",
                            surname = ""
                        ),
                        expireDate = Calendar.getInstance().time,
                        number = "1111111111111111",
                        cvv = "",
                        type = CardType.Dedut,
                        system = CardSystem.Visa,
                        name = "odsklmclksd",
                        erip = "ncjasncjasnxk",
                        iban = "21912029nsknac",
                        bankIdCode = "dsjkcnkjsndc"
                    )
                ),
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
            CardInformationUi(
                modifier = Modifier
                    .fillMaxSize()
                    .background(appColor)
                    .padding(ProductInformationScreenContentPadding),
                card = CardModelUi(
                    domainModel = CardModelDomain(
                        id = "",
                        currencyCode = "bbyn",
                        reserveCurrencyCode = "byn",
                        amountInReserveCurrency = 0.0,
                        amount = 0.0,
                        owner = OwnerModelDomain(
                            id = "",
                            name = "",
                            surname = ""
                        ),
                        expireDate = Calendar.getInstance().time,
                        number = "1111111111111111",
                        cvv = "",
                        type = CardType.Dedut,
                        system = CardSystem.Visa,
                        name = "odsklmclksd",
                        erip = "ncjasncjasnxk",
                        iban = "21912029nsknac",
                        bankIdCode = "dsjkcnkjsndc"
                    )
                ),
                proceedIntent = {}
            )
        }
    }
}