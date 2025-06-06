package com.alenniboris.fastbanking.presentation.screens.payment_process.views

import androidx.compose.foundation.Image
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alenniboris.fastbanking.R
import com.alenniboris.fastbanking.domain.model.OwnerModelDomain
import com.alenniboris.fastbanking.domain.model.card.CardModelDomain
import com.alenniboris.fastbanking.domain.model.card.CardSystem
import com.alenniboris.fastbanking.domain.model.card.CardType
import com.alenniboris.fastbanking.presentation.model.bank_product.CardModelUi
import com.alenniboris.fastbanking.presentation.uikit.theme.CardProductUiTextSize
import com.alenniboris.fastbanking.presentation.uikit.theme.FastBankingTheme
import com.alenniboris.fastbanking.presentation.uikit.theme.PaymentProcessScreenCardPlaceholderColumnPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.PaymentProcessScreenCardPlaceholderColumnTextPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.PaymentProcessScreenCardPlaceholderColumnTextSize
import com.alenniboris.fastbanking.presentation.uikit.theme.PaymentProcessScreenCardPlaceholderImageTextPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.PaymentProcessScreenCardPlaceholderInnerPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.PaymentProcessScreenCardPlaceholderShape
import com.alenniboris.fastbanking.presentation.uikit.theme.appColor
import com.alenniboris.fastbanking.presentation.uikit.theme.bodyStyle
import com.alenniboris.fastbanking.presentation.uikit.theme.mainScreenItemColor
import com.alenniboris.fastbanking.presentation.uikit.theme.mainScreenTextColor
import com.alenniboris.fastbanking.presentation.uikit.views.AppProgressBar
import java.util.Calendar

@Composable
fun PaymentCardPlaceholder(
    modifier: Modifier = Modifier,
    isLoading: Boolean,
    card: CardModelUi?
) {

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {

        when {
            isLoading -> {
                AppProgressBar(
                    modifier = Modifier.fillMaxWidth(),
                    iconTint = mainScreenTextColor
                )
            }

            else -> {

                card?.let {

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {

                            Image(
                                painter = painterResource(card.picture),
                                contentDescription = stringResource(R.string.payment_icon_text)
                            )

                            Text(
                                modifier = Modifier
                                    .padding(PaymentProcessScreenCardPlaceholderImageTextPadding),
                                text = card.smallDateOfExpireText,
                                style = bodyStyle.copy(
                                    color = mainScreenTextColor,
                                    fontSize = CardProductUiTextSize
                                )
                            )
                        }

                        Text(
                            modifier = Modifier
                                .padding(PaymentProcessScreenCardPlaceholderColumnTextPadding),
                            text = card.numberText,
                            style = bodyStyle.copy(
                                color = mainScreenTextColor,
                                fontSize = CardProductUiTextSize
                            )
                        )
                    }

                    Column(
                        modifier = Modifier.padding(PaymentProcessScreenCardPlaceholderColumnPadding)
                    ) {

                        Text(
                            text = card.name,
                            style = bodyStyle.copy(
                                color = mainScreenTextColor,
                                fontSize = PaymentProcessScreenCardPlaceholderColumnTextSize
                            )
                        )

                        Text(
                            modifier = Modifier
                                .padding(PaymentProcessScreenCardPlaceholderColumnTextPadding),
                            text = card.amountText,
                            style = bodyStyle.copy(
                                color = mainScreenTextColor,
                                fontSize = PaymentProcessScreenCardPlaceholderColumnTextSize
                            )
                        )

                        Text(
                            modifier = Modifier
                                .padding(PaymentProcessScreenCardPlaceholderColumnTextPadding),
                            text = card.reserveAmountText,
                            style = bodyStyle.copy(
                                color = mainScreenTextColor,
                                fontSize = PaymentProcessScreenCardPlaceholderColumnTextSize,
                                fontWeight = FontWeight.Light
                            )
                        )
                    }
                }
            }
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

                PaymentCardPlaceholder(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = mainScreenItemColor,
                            shape = PaymentProcessScreenCardPlaceholderShape
                        )
                        .padding(PaymentProcessScreenCardPlaceholderInnerPadding),
                    isLoading = false,
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

                PaymentCardPlaceholder(
                    modifier = Modifier
                        .padding(top = 20.dp)
                        .fillMaxWidth()
                        .background(
                            color = mainScreenItemColor,
                            shape = PaymentProcessScreenCardPlaceholderShape
                        )
                        .padding(PaymentProcessScreenCardPlaceholderInnerPadding),
                    isLoading = false,
                    card = null
                )

                PaymentCardPlaceholder(
                    modifier = Modifier
                        .padding(top = 20.dp)
                        .fillMaxWidth()
                        .background(
                            color = mainScreenItemColor,
                            shape = PaymentProcessScreenCardPlaceholderShape
                        )
                        .padding(PaymentProcessScreenCardPlaceholderInnerPadding),
                    isLoading = true,
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

                PaymentCardPlaceholder(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = mainScreenItemColor,
                            shape = PaymentProcessScreenCardPlaceholderShape
                        )
                        .padding(PaymentProcessScreenCardPlaceholderInnerPadding),
                    isLoading = false,
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

                PaymentCardPlaceholder(
                    modifier = Modifier
                        .padding(top = 20.dp)
                        .fillMaxWidth()
                        .background(
                            color = mainScreenItemColor,
                            shape = PaymentProcessScreenCardPlaceholderShape
                        )
                        .padding(PaymentProcessScreenCardPlaceholderInnerPadding),
                    isLoading = false,
                    card = null
                )

                PaymentCardPlaceholder(
                    modifier = Modifier
                        .padding(top = 20.dp)
                        .fillMaxWidth()
                        .background(
                            color = mainScreenItemColor,
                            shape = PaymentProcessScreenCardPlaceholderShape
                        )
                        .padding(PaymentProcessScreenCardPlaceholderInnerPadding),
                    isLoading = true,
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
            }
        }
    }
}
