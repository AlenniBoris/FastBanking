package com.alenniboris.fastbanking.presentation.uikit.views

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alenniboris.fastbanking.R
import com.alenniboris.fastbanking.domain.model.currency.CurrencyModelDomain
import com.alenniboris.fastbanking.domain.model.transaction.TransactionModelDomain
import com.alenniboris.fastbanking.domain.model.transaction.TransactionType
import com.alenniboris.fastbanking.presentation.model.bank_product.TransactionModelUi
import com.alenniboris.fastbanking.presentation.uikit.theme.FastBankingTheme
import com.alenniboris.fastbanking.presentation.uikit.theme.MainScreenTransactionsHistoryEmptyTextSize
import com.alenniboris.fastbanking.presentation.uikit.theme.MainScreenTransactionsHistoryMinHeight
import com.alenniboris.fastbanking.presentation.uikit.theme.MainScreenTransactionsHistoryPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.MainScreenTransactionsHistoryShape
import com.alenniboris.fastbanking.presentation.uikit.theme.TransactionHistoryItemColumnPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.TransactionHistoryItemExtraTextPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.TransactionHistoryItemExtraTextSize
import com.alenniboris.fastbanking.presentation.uikit.theme.TransactionHistoryItemFirstPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.TransactionHistoryItemPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.TransactionHistoryItemTextSize
import com.alenniboris.fastbanking.presentation.uikit.theme.bodyStyle
import com.alenniboris.fastbanking.presentation.uikit.theme.mainScreenItemColor
import com.alenniboris.fastbanking.presentation.uikit.theme.mainScreenOnItemColor
import com.alenniboris.fastbanking.presentation.uikit.theme.mainScreenTextColor
import java.util.Calendar

@Composable
fun AppTransactionsSection(
    modifier: Modifier = Modifier,
    isLoading: Boolean,
    transactions: List<TransactionModelUi>,
    onItemClicked: (TransactionModelUi) -> Unit = {}
) {

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        when {
            isLoading ->
                AppProgressBar(
                    modifier = Modifier.fillMaxWidth()
                )

            !isLoading && transactions.isEmpty() ->
                Text(
                    text = stringResource(R.string.no_transactions_found),
                    style = bodyStyle.copy(
                        color = mainScreenTextColor,
                        fontWeight = FontWeight.Normal,
                        fontSize = MainScreenTransactionsHistoryEmptyTextSize
                    )
                )

            else -> {

                transactions.forEachIndexed { index, transaction ->
                    TransactionItem(
                        modifier = Modifier
                            .padding(
                                if (index == 0) TransactionHistoryItemFirstPadding
                                else TransactionHistoryItemPadding
                            )
                            .fillMaxWidth()
                            .clickable {
                                onItemClicked(transaction)
                            },
                        transaction = transaction
                    )
                }
            }
        }
    }
}

@Composable
private fun TransactionItem(
    modifier: Modifier,
    transaction: TransactionModelUi
) {

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(transaction.picture),
            tint = mainScreenTextColor,
            contentDescription = stringResource(R.string.transaction_text)
        )

        Column(
            modifier = Modifier.padding(TransactionHistoryItemColumnPadding)
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Text(
                    modifier = Modifier.weight(1f),
                    text = transaction.numberText,
                    style = bodyStyle.copy(
                        color = mainScreenTextColor,
                        fontWeight = FontWeight.Bold,
                        fontSize = TransactionHistoryItemTextSize
                    ),
                    maxLines = 1
                )

                Text(
                    modifier = Modifier.padding(TransactionHistoryItemExtraTextPadding),
                    text = transaction.priceText,
                    style = bodyStyle.copy(
                        color = mainScreenTextColor,
                        fontWeight = FontWeight.Normal,
                        fontSize = TransactionHistoryItemExtraTextSize,
                    )
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Text(
                    modifier = Modifier.weight(1f),
                    text = transaction.domainModel.details,
                    style = bodyStyle.copy(
                        color = mainScreenTextColor,
                        fontWeight = FontWeight.Normal,
                        fontSize = TransactionHistoryItemTextSize
                    ),
                    maxLines = 1
                )

                Text(
                    modifier = Modifier.padding(TransactionHistoryItemExtraTextPadding),
                    text = transaction.dateText,
                    style = bodyStyle.copy(
                        color = mainScreenTextColor,
                        fontWeight = FontWeight.Normal,
                        fontSize = TransactionHistoryItemExtraTextSize,
                    )
                )
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
                    .background(mainScreenItemColor)
                    .fillMaxSize()
            ) {

                AppTransactionsSection(
                    modifier = Modifier
                        .clip(MainScreenTransactionsHistoryShape)
                        .fillMaxWidth()
                        .heightIn(min = MainScreenTransactionsHistoryMinHeight)
                        .background(mainScreenOnItemColor)
                        .padding(MainScreenTransactionsHistoryPadding),
                    isLoading = true,
                    transactions = emptyList()
                )

                Spacer(Modifier.height(25.dp))

                AppTransactionsSection(
                    modifier = Modifier
                        .clip(MainScreenTransactionsHistoryShape)
                        .fillMaxWidth()
                        .heightIn(min = MainScreenTransactionsHistoryMinHeight)
                        .background(mainScreenOnItemColor)
                        .padding(MainScreenTransactionsHistoryPadding),
                    isLoading = false,
                    transactions = emptyList()
                )

                Spacer(Modifier.height(25.dp))

                AppTransactionsSection(
                    modifier = Modifier
                        .clip(MainScreenTransactionsHistoryShape)
                        .fillMaxWidth()
                        .heightIn(min = MainScreenTransactionsHistoryMinHeight)
                        .background(mainScreenOnItemColor)
                        .padding(MainScreenTransactionsHistoryPadding),
                    isLoading = false,
                    transactions = listOf(
                        TransactionModelUi(
                            domainModel = TransactionModelDomain(
                                currency = CurrencyModelDomain(
                                    code = "usd",
                                    fullName = "usd"
                                ),
                                date = Calendar.getInstance().time,
                                details = "awdawdawdawdawdwwdwadw",
                                id = "cdsmkcds",
                                type = TransactionType.P2P,
                                usedCardId = "dqwdwwqd",
                                senderId = "sasadasd",
                                cardNumber = "1111222233334444",
                                priceAmount = 11.0
                            )
                        ),
                        TransactionModelUi(
                            domainModel = TransactionModelDomain(
                                currency = CurrencyModelDomain(
                                    code = "usd",
                                    fullName = "usd"
                                ),
                                date = Calendar.getInstance().time,
                                details = "dkscsdlkmc",
                                id = "cdsmkcds",
                                type = TransactionType.Undefined,
                                usedCardId = "dqwdwwqd",
                                senderId = "sasadasd",
                                cardNumber = "1111222233334444",
                                priceAmount = 11.0
                            )
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
                    .background(mainScreenItemColor)
                    .fillMaxSize()
            ) {

                AppTransactionsSection(
                    modifier = Modifier
                        .clip(MainScreenTransactionsHistoryShape)
                        .fillMaxWidth()
                        .heightIn(min = MainScreenTransactionsHistoryMinHeight)
                        .background(mainScreenOnItemColor)
                        .padding(MainScreenTransactionsHistoryPadding),
                    isLoading = true,
                    transactions = emptyList()
                )

                Spacer(Modifier.height(15.dp))

                AppTransactionsSection(
                    modifier = Modifier
                        .clip(MainScreenTransactionsHistoryShape)
                        .fillMaxWidth()
                        .heightIn(min = MainScreenTransactionsHistoryMinHeight)
                        .background(mainScreenOnItemColor)
                        .padding(MainScreenTransactionsHistoryPadding),
                    isLoading = false,
                    transactions = emptyList()
                )

                Spacer(Modifier.height(15.dp))

                AppTransactionsSection(
                    modifier = Modifier
                        .clip(MainScreenTransactionsHistoryShape)
                        .fillMaxWidth()
                        .heightIn(min = MainScreenTransactionsHistoryMinHeight)
                        .background(mainScreenOnItemColor)
                        .padding(MainScreenTransactionsHistoryPadding),
                    isLoading = false,
                    transactions = listOf(
                        TransactionModelUi(
                            domainModel = TransactionModelDomain(
                                currency = CurrencyModelDomain(
                                    code = "usd",
                                    fullName = "usd"
                                ),
                                date = Calendar.getInstance().time,
                                details = "dkscsdlkmc",
                                id = "cdsmkcds",
                                type = TransactionType.P2P,
                                usedCardId = "dqwdwwqd",
                                senderId = "sasadasd",
                                priceAmount = 11.0,
                                cardNumber = "1111222233334444",
                            )
                        ),
                        TransactionModelUi(
                            domainModel = TransactionModelDomain(
                                currency = CurrencyModelDomain(
                                    code = "usd",
                                    fullName = "usd"
                                ),
                                date = Calendar.getInstance().time,
                                details = "awdawdawdawdawdwwdwadw",
                                id = "cdsmkcds",
                                type = TransactionType.Undefined,
                                usedCardId = "dqwdwwqd",
                                senderId = "sasadasd",
                                priceAmount = 11.0,
                                cardNumber = "1111222233334444",
                            )
                        )
                    )
                )

            }

        }
    }
}