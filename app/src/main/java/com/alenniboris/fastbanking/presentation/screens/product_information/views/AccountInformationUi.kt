package com.alenniboris.fastbanking.presentation.screens.product_information.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.alenniboris.fastbanking.R
import com.alenniboris.fastbanking.domain.model.OwnerModelDomain
import com.alenniboris.fastbanking.domain.model.account.AccountModelDomain
import com.alenniboris.fastbanking.domain.model.card.CardSystem
import com.alenniboris.fastbanking.domain.model.card.SimpleCardModelDomain
import com.alenniboris.fastbanking.presentation.model.bank_product.AccountModelUi
import com.alenniboris.fastbanking.presentation.screens.product_information.IProductInformationScreenIntent
import com.alenniboris.fastbanking.presentation.uikit.theme.FastBankingTheme
import com.alenniboris.fastbanking.presentation.uikit.theme.ProductInformationScreenContentPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.ProductInformationScreenCopySectionHeaderTextSize
import com.alenniboris.fastbanking.presentation.uikit.theme.ProductInformationScreenSectionPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.appColor
import com.alenniboris.fastbanking.presentation.uikit.theme.appTopBarElementsColor
import com.alenniboris.fastbanking.presentation.uikit.theme.bodyStyle
import com.alenniboris.fastbanking.presentation.uikit.views.AppEmptyScreen

@Composable
fun AccountInformationUi(
    modifier: Modifier = Modifier,
    account: AccountModelUi,
    proceedIntent: (IProductInformationScreenIntent) -> Unit
) {

    Column(
        modifier = modifier
    ) {

        ProductInformationCopySection(
            modifier = Modifier
                .padding(ProductInformationScreenSectionPadding)
                .fillMaxWidth(),
            header = stringResource(R.string.account_number_text),
            text = account.domainModel.id,
            onClick = {
                proceedIntent(
                    IProductInformationScreenIntent.CopyTextToClipboard(
                        account.domainModel.id
                    )
                )
            }
        )

        Text(
            modifier = Modifier
                .padding(ProductInformationScreenSectionPadding)
                .fillMaxWidth(),
            text = stringResource(R.string.attached_cards_text),
            style = bodyStyle.copy(
                color = appTopBarElementsColor,
                fontWeight = FontWeight.Bold,
                fontSize = ProductInformationScreenCopySectionHeaderTextSize
            )
        )

        val listOfAttachedCards by remember { mutableStateOf(account.domainModel.attachedCards.values.toList()) }
        if (listOfAttachedCards.isEmpty()) {
            AppEmptyScreen(
                modifier = Modifier.fillMaxSize()
            )
        } else {
            LazyColumn {
                items(listOfAttachedCards) { card ->
                    ProductInformationCopySection(
                        modifier = Modifier
                            .padding(ProductInformationScreenSectionPadding)
                            .fillMaxWidth(),
                        header = "",
                        text = card.number,
                        onClick = {
                            proceedIntent(
                                IProductInformationScreenIntent.CopyTextToClipboard(
                                    card.number
                                )
                            )
                        }
                    )
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
//            AccountInformationUi(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .background(appColor)
//                    .padding(ProductInformationScreenContentPadding),
//                account = AccountModelUi(
//                    domainModel = AccountModelDomain(
//                        id = "21213",
//                        amount = 11.2,
//                        currency = "byn",
//                        amountInReserveCurrency = 11.2,
//                        reserveCurrency = "usd",
//                        attachedCards = emptyMap(),
//                        owner = OwnerModelDomain(
//                            id = "",
//                            name = "",
//                            surname = ""
//                        ),
//                        name = "odsklmclksd"
//                    )
//                ),
//                proceedIntent = {}
//            )

            AccountInformationUi(
                modifier = Modifier
                    .fillMaxSize()
                    .background(appColor)
                    .padding(ProductInformationScreenContentPadding),
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
                        name = "odsklmclksd"
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
            AccountInformationUi(
                modifier = Modifier
                    .fillMaxSize()
                    .background(appColor)
                    .padding(ProductInformationScreenContentPadding),
                account = AccountModelUi(
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
                proceedIntent = {}
            )

//            AccountInformationUi(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .background(appColor)
//                    .padding(ProductInformationScreenContentPadding),
//                account = AccountModelUi(
//                    domainModel = AccountModelDomain(
//                        id = "21213",
//                        amount = 11.2,
//                        currency = "byn",
//                        amountInReserveCurrency = 11.2,
//                        reserveCurrency = "usd",
//                        attachedCards = mapOf(
//                            Pair(
//                                "ksamklsd",
//                                SimpleCardModelDomain(
//                                    id = "1",
//                                    number = "12121221",
//                                    system = CardSystem.Mir
//                                )
//                            ),
//                            Pair(
//                                "dscdscdc",
//                                SimpleCardModelDomain(
//                                    id = "11",
//                                    number = "sai23u832y7832yd",
//                                    system = CardSystem.Mir
//                                )
//                            ),
//                            Pair(
//                                "dcjndsckjd",
//                                SimpleCardModelDomain(
//                                    id = "123",
//                                    number = "1111",
//                                    system = CardSystem.Mir
//                                )
//                            ),
//                            Pair(
//                                "7127198",
//                                SimpleCardModelDomain(
//                                    id = "131",
//                                    number = "3029876235738910",
//                                    system = CardSystem.Mir
//                                )
//                            ),
//                        ),
//                        owner = OwnerModelDomain(
//                            id = "",
//                            name = "",
//                            surname = ""
//                        ),
//                        name = "odsklmclksd"
//                    )
//                ),
//                proceedIntent = {}
//            )
        }
    }
}