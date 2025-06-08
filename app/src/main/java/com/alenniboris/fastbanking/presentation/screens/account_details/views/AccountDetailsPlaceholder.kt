package com.alenniboris.fastbanking.presentation.screens.account_details.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.alenniboris.fastbanking.R
import com.alenniboris.fastbanking.domain.model.OwnerModelDomain
import com.alenniboris.fastbanking.domain.model.account.AccountModelDomain
import com.alenniboris.fastbanking.domain.model.card.CardSystem
import com.alenniboris.fastbanking.domain.model.card.SimpleCardModelDomain
import com.alenniboris.fastbanking.presentation.model.bank_product.AccountModelUi
import com.alenniboris.fastbanking.presentation.screens.account_details.IAccountDetailsScreenIntent
import com.alenniboris.fastbanking.presentation.uikit.theme.AccountDetailsPlaceholderElementTextPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.AccountDetailsPlaceholderMainTextSize
import com.alenniboris.fastbanking.presentation.uikit.theme.AccountDetailsPlaceholderSubtleTextSize
import com.alenniboris.fastbanking.presentation.uikit.theme.CreditDetailsPlaceholderContainerInnerPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.CreditDetailsPlaceholderElementPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.CreditDetailsPlaceholderShape
import com.alenniboris.fastbanking.presentation.uikit.theme.CreditDetailsScreenContentPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.CreditDetailsScreenCreditPlaceholderPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.FastBankingTheme
import com.alenniboris.fastbanking.presentation.uikit.theme.appColor
import com.alenniboris.fastbanking.presentation.uikit.theme.bodyStyle
import com.alenniboris.fastbanking.presentation.uikit.theme.mainScreenItemColor
import com.alenniboris.fastbanking.presentation.uikit.theme.mainScreenTextColor
import com.alenniboris.fastbanking.presentation.uikit.views.AppIconButton

@Composable
fun AccountDetailsPlaceholder(
    modifier: Modifier = Modifier,
    account: AccountModelUi,
    proceedIntent: (IAccountDetailsScreenIntent) -> Unit
) {

    Column(
        modifier = modifier
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                modifier = Modifier.weight(1f),
                text = account.name,
                style = bodyStyle.copy(
                    fontSize = AccountDetailsPlaceholderMainTextSize,
                    color = mainScreenTextColor
                )
            )

            AppIconButton(
                iconPainter = painterResource(R.drawable.settings_icon),
                tint = mainScreenTextColor,
                isAnimated = true,
                onClick = {
                    proceedIntent(
                        IAccountDetailsScreenIntent.ChangeAccountNameSettingsVisibility
                    )
                }
            )
        }

        PlaceholderTextSection(
            modifier = Modifier
                .padding(CreditDetailsPlaceholderElementPadding)
                .fillMaxWidth(),
            header = stringResource(R.string.amount_in_main_currency_text),
            text = account.mainCurrencyText
        )

        PlaceholderTextSection(
            modifier = Modifier
                .padding(CreditDetailsPlaceholderElementPadding)
                .fillMaxWidth(),
            header = stringResource(R.string.amount_in_reserve_currency_text),
            text = account.reserveCurrencyText
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
                fontSize = AccountDetailsPlaceholderSubtleTextSize
            )
        )

        Text(
            modifier = Modifier.padding(AccountDetailsPlaceholderElementTextPadding),
            text = text,
            style = bodyStyle.copy(
                color = mainScreenTextColor,
                fontSize = AccountDetailsPlaceholderMainTextSize
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
                AccountDetailsPlaceholder(
                    modifier = Modifier
                        .padding(CreditDetailsScreenCreditPlaceholderPadding)
                        .clip(CreditDetailsPlaceholderShape)
                        .fillMaxWidth()
                        .background(mainScreenItemColor)
                        .padding(CreditDetailsPlaceholderContainerInnerPadding),
                    account = AccountModelUi(
                        domainModel = AccountModelDomain(
                            id = "21213",
                            amount = 11.2,
                            currency = "byn",
                            amountInReserveCurrency = 11.2,
                            reserveCurrency = "usd",
                            attachedCards = mapOf(
                                Pair(
                                    "ksamklsd",
                                    SimpleCardModelDomain(
                                        id = "1",
                                        number = "12121221",
                                        system = CardSystem.Mir
                                    )
                                ),
                                Pair(
                                    "dscdscdc",
                                    SimpleCardModelDomain(
                                        id = "11",
                                        number = "sai23u832y7832yd",
                                        system = CardSystem.Mir
                                    )
                                ),
                                Pair(
                                    "dcjndsckjd",
                                    SimpleCardModelDomain(
                                        id = "123",
                                        number = "1111",
                                        system = CardSystem.Mir
                                    )
                                ),
                                Pair(
                                    "7127198",
                                    SimpleCardModelDomain(
                                        id = "131",
                                        number = "3029876235738910",
                                        system = CardSystem.Mir
                                    )
                                ),
                            ),
                            owner = OwnerModelDomain(
                                id = "",
                                name = "",
                                surname = ""
                            ),
                            name = "odsklmclksd",
                            erip = "dshc bsdhcbsdc"
                        )
                    ),
                    proceedIntent = {}
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
                AccountDetailsPlaceholder(
                    modifier = Modifier
                        .padding(CreditDetailsScreenCreditPlaceholderPadding)
                        .clip(CreditDetailsPlaceholderShape)
                        .fillMaxWidth()
                        .background(mainScreenItemColor)
                        .padding(CreditDetailsPlaceholderContainerInnerPadding),
                    account = AccountModelUi(
                        domainModel = AccountModelDomain(
                            id = "21213",
                            amount = 11.2,
                            currency = "byn",
                            amountInReserveCurrency = 11.2,
                            reserveCurrency = "usd",
                            attachedCards = mapOf(
                                Pair(
                                    "ksamklsd",
                                    SimpleCardModelDomain(
                                        id = "1",
                                        number = "12121221",
                                        system = CardSystem.Mir
                                    )
                                ),
                                Pair(
                                    "dscdscdc",
                                    SimpleCardModelDomain(
                                        id = "11",
                                        number = "sai23u832y7832yd",
                                        system = CardSystem.Mir
                                    )
                                ),
                                Pair(
                                    "dcjndsckjd",
                                    SimpleCardModelDomain(
                                        id = "123",
                                        number = "1111",
                                        system = CardSystem.Mir
                                    )
                                ),
                                Pair(
                                    "7127198",
                                    SimpleCardModelDomain(
                                        id = "131",
                                        number = "3029876235738910",
                                        system = CardSystem.Mir
                                    )
                                ),
                            ),
                            owner = OwnerModelDomain(
                                id = "",
                                name = "",
                                surname = ""
                            ),
                            name = "odsklmclksd",
                            erip = "dshc bsdhcbsdc"
                        )
                    ),
                    proceedIntent = {}
                )
            }
        }
    }
}