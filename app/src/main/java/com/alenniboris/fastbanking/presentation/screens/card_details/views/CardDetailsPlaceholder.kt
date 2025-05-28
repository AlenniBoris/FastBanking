package com.alenniboris.fastbanking.presentation.screens.card_details.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alenniboris.fastbanking.R
import com.alenniboris.fastbanking.domain.model.OwnerModelDomain
import com.alenniboris.fastbanking.domain.model.card.CardModelDomain
import com.alenniboris.fastbanking.domain.model.card.CardSystem
import com.alenniboris.fastbanking.domain.model.card.CardType
import com.alenniboris.fastbanking.presentation.model.bank_product.CardModelUi
import com.alenniboris.fastbanking.presentation.model.bank_product.toUiString
import com.alenniboris.fastbanking.presentation.uikit.theme.CardDetailsPlaceholderContainerInnerPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.CardDetailsPlaceholderElementDoublePadding
import com.alenniboris.fastbanking.presentation.uikit.theme.CardDetailsPlaceholderElementPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.CardDetailsPlaceholderMainTextSize
import com.alenniboris.fastbanking.presentation.uikit.theme.CardDetailsPlaceholderShape
import com.alenniboris.fastbanking.presentation.uikit.theme.CardDetailsPlaceholderSubtleTextSize
import com.alenniboris.fastbanking.presentation.uikit.theme.FastBankingTheme
import com.alenniboris.fastbanking.presentation.uikit.theme.appColor
import com.alenniboris.fastbanking.presentation.uikit.theme.bodyStyle
import com.alenniboris.fastbanking.presentation.uikit.theme.cardDetailsPlaceholderTextColor
import com.alenniboris.fastbanking.presentation.uikit.theme.cardSystemMastercardColor
import com.alenniboris.fastbanking.presentation.uikit.theme.cardSystemMirColor
import com.alenniboris.fastbanking.presentation.uikit.theme.cardSystemUndefinedColor
import com.alenniboris.fastbanking.presentation.uikit.theme.cardSystemVisaColor
import java.util.Calendar

@Composable
fun CardDetailsPlaceholder(
    modifier: Modifier = Modifier,
    card: CardModelUi
) {

    Column(
        modifier = modifier
            .clip(CardDetailsPlaceholderShape)
            .background(
                color = when (card.domainModel.system) {
                    CardSystem.Visa -> cardSystemVisaColor
                    CardSystem.Mastercard -> cardSystemMastercardColor
                    CardSystem.Mir -> cardSystemMirColor
                    CardSystem.Undefined -> cardSystemUndefinedColor
                }
            )
            .padding(CardDetailsPlaceholderContainerInnerPadding)
    ) {

        Text(
            modifier = Modifier.fillMaxWidth(),
            text = card.name,
            style = bodyStyle.copy(
                fontSize = CardDetailsPlaceholderMainTextSize,
                color = cardDetailsPlaceholderTextColor
            )
        )

        Row(
            modifier = Modifier
                .padding(CardDetailsPlaceholderElementPadding)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Text(
                text = card.amountText,
                style = bodyStyle.copy(
                    fontSize = CardDetailsPlaceholderMainTextSize,
                    color = cardDetailsPlaceholderTextColor
                )
            )

            Text(
                text = card.reserveAmountText,
                style = bodyStyle.copy(
                    fontSize = CardDetailsPlaceholderSubtleTextSize,
                    color = cardDetailsPlaceholderTextColor
                )
            )
        }

        Text(
            modifier = Modifier
                .padding(CardDetailsPlaceholderElementDoublePadding)
                .fillMaxWidth(),
            text = card.numberText,
            style = bodyStyle.copy(
                fontSize = CardDetailsPlaceholderMainTextSize,
                color = cardDetailsPlaceholderTextColor
            )
        )

        Row(
            modifier = Modifier
                .padding(CardDetailsPlaceholderElementPadding)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Column {

                Text(
                    text = stringResource(R.string.card_date_of_expire_text),
                    style = bodyStyle.copy(
                        fontSize = CardDetailsPlaceholderSubtleTextSize,
                        color = cardDetailsPlaceholderTextColor
                    )
                )

                Text(
                    text = card.dateOfExpireText,
                    style = bodyStyle.copy(
                        fontSize = CardDetailsPlaceholderMainTextSize,
                        color = cardDetailsPlaceholderTextColor
                    )
                )
            }


            Text(
                text = stringResource(card.domainModel.system.toUiString()),
                style = bodyStyle.copy(
                    fontSize = CardDetailsPlaceholderMainTextSize,
                    color = cardDetailsPlaceholderTextColor
                )
            )
        }
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
                    .padding(PaddingValues(horizontal = 20.dp))
            ) {
                CardDetailsPlaceholder(
                    modifier = Modifier.fillMaxWidth(),
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
                    )
                )

                CardDetailsPlaceholder(
                    modifier = Modifier
                        .padding(PaddingValues(top = 15.dp))
                        .fillMaxWidth(),
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
                            system = CardSystem.Mastercard,
                            name = "odsklmclksd",
                            erip = "ncjasncjasnxk",
                            iban = "21912029nsknac",
                            bankIdCode = "dsjkcnkjsndc"
                        )
                    )
                )

                CardDetailsPlaceholder(
                    modifier = Modifier
                        .padding(PaddingValues(top = 15.dp))
                        .fillMaxWidth(),
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
                            system = CardSystem.Mir,
                            name = "odsklmclksd",
                            erip = "ncjasncjasnxk",
                            iban = "21912029nsknac",
                            bankIdCode = "dsjkcnkjsndc"
                        )
                    )
                )

                CardDetailsPlaceholder(
                    modifier = Modifier
                        .padding(PaddingValues(top = 15.dp))
                        .fillMaxWidth(),
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
                            system = CardSystem.Undefined,
                            name = "odsklmclksd",
                            erip = "ncjasncjasnxk",
                            iban = "21912029nsknac",
                            bankIdCode = "dsjkcnkjsndc"
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
                    .padding(PaddingValues(horizontal = 20.dp))
            ) {
                CardDetailsPlaceholder(
                    modifier = Modifier.fillMaxWidth(),
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
                    )
                )

                CardDetailsPlaceholder(
                    modifier = Modifier
                        .padding(PaddingValues(top = 15.dp))
                        .fillMaxWidth(),
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
                            system = CardSystem.Mastercard,
                            name = "odsklmclksd",
                            erip = "ncjasncjasnxk",
                            iban = "21912029nsknac",
                            bankIdCode = "dsjkcnkjsndc"
                        )
                    )
                )

                CardDetailsPlaceholder(
                    modifier = Modifier
                        .padding(PaddingValues(top = 15.dp))
                        .fillMaxWidth(),
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
                            system = CardSystem.Mir,
                            name = "odsklmclksd",
                            erip = "ncjasncjasnxk",
                            iban = "21912029nsknac",
                            bankIdCode = "dsjkcnkjsndc"
                        )
                    )
                )

                CardDetailsPlaceholder(
                    modifier = Modifier
                        .padding(PaddingValues(top = 15.dp))
                        .fillMaxWidth(),
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
                            system = CardSystem.Undefined,
                            name = "odsklmclksd",
                            erip = "ncjasncjasnxk",
                            iban = "21912029nsknac",
                            bankIdCode = "dsjkcnkjsndc"
                        )
                    )
                )
            }
        }
    }
}