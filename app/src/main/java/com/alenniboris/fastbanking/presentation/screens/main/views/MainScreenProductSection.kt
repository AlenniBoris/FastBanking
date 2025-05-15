package com.alenniboris.fastbanking.presentation.screens.main.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alenniboris.fastbanking.R
import com.alenniboris.fastbanking.domain.model.OwnerModelDomain
import com.alenniboris.fastbanking.domain.model.account.AccountModelDomain
import com.alenniboris.fastbanking.domain.model.card.CardModelDomain
import com.alenniboris.fastbanking.domain.model.card.CardSystem
import com.alenniboris.fastbanking.domain.model.card.CardType
import com.alenniboris.fastbanking.domain.model.credit.CreditModelDomain
import com.alenniboris.fastbanking.presentation.model.bank_product.AccountModelUi
import com.alenniboris.fastbanking.presentation.model.bank_product.CardModelUi
import com.alenniboris.fastbanking.presentation.model.bank_product.CreditModelUi
import com.alenniboris.fastbanking.presentation.model.bank_product.IBankProductModelUi
import com.alenniboris.fastbanking.presentation.screens.main.IMainScreenIntent
import com.alenniboris.fastbanking.presentation.uikit.theme.CardProductUiNumberTextPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.CardProductUiReserveCurrencyPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.CardProductUiTextSize
import com.alenniboris.fastbanking.presentation.uikit.theme.CreditProductUiReserveCurrencyPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.CreditProductUiTextSize
import com.alenniboris.fastbanking.presentation.uikit.theme.FastBankingTheme
import com.alenniboris.fastbanking.presentation.uikit.theme.MainScreenProductSectionEmptyTextPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.MainScreenProductSectionHeight
import com.alenniboris.fastbanking.presentation.uikit.theme.MainScreenProductSectionInnerPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.MainScreenProductSectionNothingTextSize
import com.alenniboris.fastbanking.presentation.uikit.theme.MainScreenProductSectionOrderTextPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.MainScreenProductSectionOrderTextSize
import com.alenniboris.fastbanking.presentation.uikit.theme.MainScreenProductSectionShape
import com.alenniboris.fastbanking.presentation.uikit.theme.bodyStyle
import com.alenniboris.fastbanking.presentation.uikit.theme.mainScreenItemColor
import com.alenniboris.fastbanking.presentation.uikit.theme.mainScreenOnItemColor
import com.alenniboris.fastbanking.presentation.uikit.theme.mainScreenProductSectionElementOrderChanger
import com.alenniboris.fastbanking.presentation.uikit.theme.mainScreenTextColor
import com.alenniboris.fastbanking.presentation.uikit.values.BankProduct
import com.alenniboris.fastbanking.presentation.uikit.views.AppProgressBar
import java.util.Calendar

@Composable
fun MainScreenProductSection(
    modifier: Modifier = Modifier,
    isLoading: Boolean,
    isUserHaveProducts: Boolean,
    currentProduct: BankProduct,
    currentUserProduct: IBankProductModelUi?,
    proceedIntent: (IMainScreenIntent) -> Unit
) {

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        when {
            isLoading -> {
                AppProgressBar(
                    modifier = Modifier
                        .height(MainScreenProductSectionHeight)
                        .fillMaxWidth()
                )
            }

            !isLoading && !isUserHaveProducts -> {
                Text(
                    modifier = Modifier
                        .padding(MainScreenProductSectionEmptyTextPadding),
                    text = stringResource(R.string.no_bank_product_found_text),
                    style = bodyStyle.copy(
                        color = mainScreenTextColor,
                        fontWeight = FontWeight.Bold,
                        fontSize = MainScreenProductSectionNothingTextSize,
                        textAlign = TextAlign.Center
                    )
                )
            }

            isUserHaveProducts && currentUserProduct == null -> {

                Text(
                    modifier = Modifier
                        .padding(MainScreenProductSectionEmptyTextPadding),
                    text = stringResource(R.string.select_bank_product_text),
                    style = bodyStyle.copy(
                        color = mainScreenTextColor,
                        fontWeight = FontWeight.Bold,
                        fontSize = MainScreenProductSectionNothingTextSize,
                        textAlign = TextAlign.Center
                    )
                )

                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .clickable {
                            proceedIntent(IMainScreenIntent.UpdateUserBankProductsSheetVisibility)
                        },
                    verticalAlignment = Alignment.Bottom,
                    horizontalArrangement = Arrangement.End
                ) {
                    Icon(
                        painter = painterResource(R.drawable.order_button_icon),
                        contentDescription = stringResource(R.string.make_order_text),
                        tint = mainScreenProductSectionElementOrderChanger
                    )

                    Text(
                        modifier = Modifier.padding(MainScreenProductSectionOrderTextPadding),
                        text = stringResource(R.string.make_order_text),
                        style = bodyStyle.copy(
                            fontWeight = FontWeight.Normal,
                            color = mainScreenProductSectionElementOrderChanger,
                            fontSize = MainScreenProductSectionOrderTextSize
                        )
                    )
                }
            }

            else -> {

                when (currentProduct) {
                    BankProduct.CARD ->
                        (currentUserProduct as? CardModelUi)?.let { card ->
                            CardProductUi(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { },
                                card = card
                            )
                        }

                    BankProduct.CREDIT ->
                        (currentUserProduct as? CreditModelUi)?.let { credit ->
                            CreditProductUi(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { },
                                credit = credit
                            )
                        }


                    BankProduct.DEPOSITS_AND_ACCOUNTS ->
                        (currentUserProduct as? AccountModelUi)?.let { account ->
                            DepositsAndAccountsProductUi(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { },
                                account = account
                            )
                        }

                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            proceedIntent(IMainScreenIntent.UpdateUserBankProductsSheetVisibility)
                        },
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.End
                ) {
                    Icon(
                        painter = painterResource(R.drawable.order_button_icon),
                        contentDescription = stringResource(R.string.make_order_text),
                        tint = mainScreenProductSectionElementOrderChanger
                    )

                    Text(
                        modifier = Modifier.padding(MainScreenProductSectionOrderTextPadding),
                        text = stringResource(R.string.make_order_text),
                        style = bodyStyle.copy(
                            fontWeight = FontWeight.Normal,
                            color = mainScreenProductSectionElementOrderChanger,
                            fontSize = MainScreenProductSectionOrderTextSize
                        )
                    )
                }
            }
        }
    }
}

@Composable
private fun CardProductUi(
    modifier: Modifier = Modifier,
    card: CardModelUi
) {

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {

        Row {
            Image(
                painter = painterResource(card.picture),
                contentDescription = stringResource(R.string.payment_icon_text)
            )

            Text(
                modifier = Modifier.padding(CardProductUiNumberTextPadding),
                text = card.numberText,
                style = bodyStyle.copy(
                    color = mainScreenTextColor,
                    fontSize = CardProductUiTextSize
                )
            )
        }

        Column {

            Text(
                text = card.amountText,
                style = bodyStyle.copy(
                    color = mainScreenTextColor,
                    fontSize = CardProductUiTextSize
                )
            )

            Text(
                modifier = Modifier.padding(CardProductUiReserveCurrencyPadding),
                text = card.reserveAmountText,
                style = bodyStyle.copy(
                    color = mainScreenTextColor,
                    fontSize = CardProductUiTextSize
                )
            )
        }
    }
}

@Composable
private fun CreditProductUi(
    modifier: Modifier = Modifier,
    credit: CreditModelUi
) {

    Row(
        modifier = modifier
    ) {

        Text(
            modifier = Modifier.weight(1f),
            text = credit.domainModel.id,
            style = bodyStyle.copy(
                color = mainScreenTextColor,
                fontSize = CreditProductUiTextSize,
                fontWeight = FontWeight.Bold
            )
        )

        Column {
            Text(
                text = credit.creditBodyText,
                style = bodyStyle.copy(
                    color = mainScreenTextColor,
                    fontSize = CardProductUiTextSize
                )
            )

            Text(
                modifier = Modifier.padding(CreditProductUiReserveCurrencyPadding),
                text = credit.reserveCreditBodyText,
                style = bodyStyle.copy(
                    color = mainScreenTextColor,
                    fontSize = CardProductUiTextSize
                )
            )
        }
    }
}

@Composable
private fun DepositsAndAccountsProductUi(
    modifier: Modifier = Modifier,
    account: AccountModelUi
) {

    Row(
        modifier = modifier
    ) {

        Text(
            modifier = Modifier.weight(1f),
            text = account.accountIdText,
            style = bodyStyle.copy(
                color = mainScreenTextColor,
                fontSize = CreditProductUiTextSize,
                fontWeight = FontWeight.Bold
            )
        )

        Column {
            Text(
                text = account.mainCurrencyText,
                style = bodyStyle.copy(
                    color = mainScreenTextColor,
                    fontSize = CardProductUiTextSize
                )
            )

            Text(
                modifier = Modifier.padding(CreditProductUiReserveCurrencyPadding),
                text = account.reserveCurrencyText,
                style = bodyStyle.copy(
                    color = mainScreenTextColor,
                    fontSize = CardProductUiTextSize
                )
            )
        }
    }
}

@Composable
@Preview
private fun LightThemePreview() {

    FastBankingTheme(
        darkTheme = false
    ) {
        Surface {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(mainScreenItemColor)
            ) {

                MainScreenProductSection(
                    modifier = Modifier
                        .clip(MainScreenProductSectionShape)
                        .fillMaxWidth()
                        .heightIn(min = MainScreenProductSectionHeight)
                        .background(mainScreenOnItemColor)
                        .padding(MainScreenProductSectionInnerPadding),
                    currentProduct = BankProduct.CARD,
                    currentUserProduct = CardModelUi(
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
                            name = "odsklmclksd"
                        )
                    ),
                    isLoading = false,
                    isUserHaveProducts = true,
                    proceedIntent = {}
                )

                Spacer(modifier = Modifier.height(20.dp))

                MainScreenProductSection(
                    modifier = Modifier
                        .clip(MainScreenProductSectionShape)
                        .fillMaxWidth()
                        .heightIn(min = MainScreenProductSectionHeight)
                        .background(mainScreenOnItemColor)
                        .padding(MainScreenProductSectionInnerPadding),
                    currentProduct = BankProduct.CREDIT,
                    currentUserProduct = CreditModelUi(
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
                            name = "odsklmclksd"
                        )
                    ),
                    isLoading = false,
                    isUserHaveProducts = true,
                    proceedIntent = {}
                )

                Spacer(modifier = Modifier.height(20.dp))

                MainScreenProductSection(
                    modifier = Modifier
                        .clip(MainScreenProductSectionShape)
                        .fillMaxWidth()
                        .heightIn(min = MainScreenProductSectionHeight)
                        .background(mainScreenOnItemColor)
                        .padding(MainScreenProductSectionInnerPadding),
                    currentProduct = BankProduct.DEPOSITS_AND_ACCOUNTS,
                    currentUserProduct = AccountModelUi(
                        domainModel = AccountModelDomain(
                            id = "21213",
                            amount = 11.2,
                            currency = "byn",
                            amountInReserveCurrency = 11.2,
                            reserveCurrency = "usd",
                            attachedCards = emptyMap(),
                            owner = OwnerModelDomain(
                                id = "",
                                name = "",
                                surname = ""
                            ),
                            name = "odsklmclksd"
                        )
                    ),
                    isLoading = false,
                    isUserHaveProducts = true,
                    proceedIntent = {}
                )

                Spacer(modifier = Modifier.height(20.dp))

                MainScreenProductSection(
                    modifier = Modifier
                        .clip(MainScreenProductSectionShape)
                        .fillMaxWidth()
                        .heightIn(min = MainScreenProductSectionHeight)
                        .background(mainScreenOnItemColor)
                        .padding(MainScreenProductSectionInnerPadding),
                    currentProduct = BankProduct.CARD,
                    currentUserProduct = null,
                    isLoading = true,
                    isUserHaveProducts = true,
                    proceedIntent = {}
                )

                Spacer(modifier = Modifier.height(20.dp))

                MainScreenProductSection(
                    modifier = Modifier
                        .clip(MainScreenProductSectionShape)
                        .fillMaxWidth()
                        .heightIn(min = MainScreenProductSectionHeight)
                        .background(mainScreenOnItemColor)
                        .padding(MainScreenProductSectionInnerPadding),
                    currentProduct = BankProduct.CARD,
                    currentUserProduct = null,
                    isLoading = false,
                    isUserHaveProducts = true,
                    proceedIntent = {}
                )

                Spacer(modifier = Modifier.height(20.dp))

                MainScreenProductSection(
                    modifier = Modifier
                        .clip(MainScreenProductSectionShape)
                        .clip(MainScreenProductSectionShape)
                        .fillMaxWidth()
                        .heightIn(min = MainScreenProductSectionHeight)
                        .background(mainScreenOnItemColor)
                        .padding(MainScreenProductSectionInnerPadding),
                    currentProduct = BankProduct.CARD,
                    currentUserProduct = null,
                    isLoading = false,
                    isUserHaveProducts = false,
                    proceedIntent = {}
                )
            }
        }
    }
}

@Composable
@Preview
private fun DarkThemePreview() {
    FastBankingTheme(
        darkTheme = true
    ) {
        Surface {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(mainScreenItemColor)
            ) {

                MainScreenProductSection(
                    modifier = Modifier
                        .clip(MainScreenProductSectionShape)
                        .fillMaxWidth()
                        .heightIn(min = MainScreenProductSectionHeight)
                        .background(mainScreenOnItemColor)
                        .padding(MainScreenProductSectionInnerPadding),
                    currentProduct = BankProduct.CARD,
                    currentUserProduct = CardModelUi(
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
                            name = "odsklmclksd"
                        )
                    ),
                    isLoading = false,
                    isUserHaveProducts = true,
                    proceedIntent = {}
                )

                Spacer(modifier = Modifier.height(20.dp))

                MainScreenProductSection(
                    modifier = Modifier
                        .clip(MainScreenProductSectionShape)
                        .fillMaxWidth()
                        .heightIn(min = MainScreenProductSectionHeight)
                        .background(mainScreenOnItemColor)
                        .padding(MainScreenProductSectionInnerPadding),
                    currentProduct = BankProduct.CREDIT,
                    currentUserProduct = CreditModelUi(
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
                            name = "odsklmclksd"
                        )
                    ),
                    isLoading = false,
                    isUserHaveProducts = true,
                    proceedIntent = {}
                )

                Spacer(modifier = Modifier.height(20.dp))

                MainScreenProductSection(
                    modifier = Modifier
                        .clip(MainScreenProductSectionShape)
                        .fillMaxWidth()
                        .heightIn(min = MainScreenProductSectionHeight)
                        .background(mainScreenOnItemColor)
                        .padding(MainScreenProductSectionInnerPadding),
                    currentProduct = BankProduct.DEPOSITS_AND_ACCOUNTS,
                    currentUserProduct = AccountModelUi(
                        domainModel = AccountModelDomain(
                            id = "21213",
                            amount = 11.2,
                            currency = "byn",
                            amountInReserveCurrency = 11.2,
                            reserveCurrency = "usd",
                            attachedCards = emptyMap(),
                            owner = OwnerModelDomain(
                                id = "",
                                name = "",
                                surname = ""
                            ),
                            name = "odsklmclksd"
                        )
                    ),
                    isLoading = false,
                    isUserHaveProducts = true,
                    proceedIntent = {}
                )

                Spacer(modifier = Modifier.height(20.dp))

                MainScreenProductSection(
                    modifier = Modifier
                        .clip(MainScreenProductSectionShape)
                        .fillMaxWidth()
                        .heightIn(min = MainScreenProductSectionHeight)
                        .background(mainScreenOnItemColor)
                        .padding(MainScreenProductSectionInnerPadding),
                    currentProduct = BankProduct.CARD,
                    currentUserProduct = null,
                    isLoading = true,
                    isUserHaveProducts = true,
                    proceedIntent = {}
                )

                Spacer(modifier = Modifier.height(20.dp))

                MainScreenProductSection(
                    modifier = Modifier
                        .clip(MainScreenProductSectionShape)
                        .clip(MainScreenProductSectionShape)
                        .fillMaxWidth()
                        .heightIn(min = MainScreenProductSectionHeight)
                        .background(mainScreenOnItemColor)
                        .padding(MainScreenProductSectionInnerPadding),
                    currentProduct = BankProduct.CARD,
                    currentUserProduct = null,
                    isLoading = false,
                    isUserHaveProducts = false,
                    proceedIntent = {}
                )
            }
        }
    }
}