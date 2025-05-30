package com.alenniboris.fastbanking.presentation.screens.transactions_history.views

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.alenniboris.fastbanking.R
import com.alenniboris.fastbanking.domain.model.currency.CurrencyModelDomain
import com.alenniboris.fastbanking.domain.model.transaction.TransactionModelDomain
import com.alenniboris.fastbanking.domain.model.transaction.TransactionType
import com.alenniboris.fastbanking.presentation.model.bank_product.TransactionModelUi
import com.alenniboris.fastbanking.presentation.screens.transactions_history.ITransactionsHistoryScreenEvent
import com.alenniboris.fastbanking.presentation.screens.transactions_history.ITransactionsHistoryScreenIntent
import com.alenniboris.fastbanking.presentation.screens.transactions_history.TransactionsHistoryScreenState
import com.alenniboris.fastbanking.presentation.screens.transactions_history.TransactionsHistoryScreenViewModel
import com.alenniboris.fastbanking.presentation.uikit.theme.FastBankingTheme
import com.alenniboris.fastbanking.presentation.uikit.theme.TopBarPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.TransactionsHistoryScreenContentPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.TransactionsHistoryScreenTextFontSize
import com.alenniboris.fastbanking.presentation.uikit.theme.appColor
import com.alenniboris.fastbanking.presentation.uikit.theme.appTopBarElementsColor
import com.alenniboris.fastbanking.presentation.uikit.theme.bodyStyle
import com.alenniboris.fastbanking.presentation.uikit.values.TransactionsHistoryScreenRoute
import com.alenniboris.fastbanking.presentation.uikit.views.AppProgressBar
import com.alenniboris.fastbanking.presentation.uikit.views.AppTopBar
import com.alenniboris.fastbanking.presentation.uikit.views.AppTransactionDetailsSection
import com.alenniboris.fastbanking.presentation.uikit.views.AppTransactionsSection
import com.ramcosta.composedestinations.annotation.Destination
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import java.util.Calendar

@Destination(route = TransactionsHistoryScreenRoute)
@Composable
fun TransactionsHistoryScreen() {

    val viewModel = koinViewModel<TransactionsHistoryScreenViewModel>()
    val state by viewModel.state.collectAsStateWithLifecycle()
    val event by remember { mutableStateOf(viewModel.event) }
    val context = LocalContext.current
    var toastMessage by remember {
        mutableStateOf(
            Toast.makeText(context, "", Toast.LENGTH_SHORT)
        )
    }
    val proceedIntent by remember { mutableStateOf(viewModel::proceedIntent) }

    LaunchedEffect(event) {

        launch {
            event.filterIsInstance<ITransactionsHistoryScreenEvent.ShowToastMessage>()
                .collect { coming ->
                    toastMessage?.cancel()
                    toastMessage = Toast.makeText(
                        context,
                        context.getString(coming.messageId),
                        Toast.LENGTH_SHORT
                    )
                    toastMessage?.show()
                }
        }
    }

    TransactionsHistoryScreenUi(
        state = state,
        proceedIntent = proceedIntent
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TransactionsHistoryScreenUi(
    state: TransactionsHistoryScreenState,
    proceedIntent: (ITransactionsHistoryScreenIntent) -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(appColor)
    ) {

        AppTopBar(
            modifier = Modifier
                .padding(TopBarPadding)
                .fillMaxWidth(),
            headerTextString = stringResource(R.string.history_screen_text)
        )

        when {

            state.isLoading -> {
                AppProgressBar(
                    modifier = Modifier.fillMaxSize()
                )
            }

            state.isError -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(TransactionsHistoryScreenContentPadding),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Text(
                        modifier = Modifier
                            .clickable {
                                proceedIntent(
                                    ITransactionsHistoryScreenIntent.ReloadData
                                )
                            },
                        text = stringResource(R.string.error_loading_text),
                        style = bodyStyle.copy(
                            fontSize = TransactionsHistoryScreenTextFontSize,
                            color = appTopBarElementsColor,
                            textAlign = TextAlign.Center
                        )
                    )
                }
            }

            else -> {

                AppTransactionsSection(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(TransactionsHistoryScreenContentPadding),
                    isLoading = state.isLoading,
                    transactions = state.transactions,
                    onItemClicked = { selected ->
                        proceedIntent(
                            ITransactionsHistoryScreenIntent.UpdateSelectedTransaction(
                                selected
                            )
                        )
                    }
                )

                state.selectedTransaction?.let { transaction ->
                    AppTransactionDetailsSection(
                        transaction = transaction,
                        onDismiss = {
                            proceedIntent(
                                ITransactionsHistoryScreenIntent.UpdateSelectedTransaction(null)
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

            TransactionsHistoryScreenUi(
                state = TransactionsHistoryScreenState(
                    isLoading = true
                ),
                proceedIntent = {}
            )

//            TransactionsHistoryScreenUi(
//                state = TransactionsHistoryScreenState(
//                    isError = true
//                ),
//                proceedIntent = {}
//            )

//            TransactionsHistoryScreenUi(
//                state = TransactionsHistoryScreenState(
//                    transactions = listOf(
//                        TransactionModelUi(
//                            domainModel = TransactionModelDomain(
//                                currency = CurrencyModelDomain(
//                                    code = "usd",
//                                    fullName = "usd"
//                                ),
//                                date = Calendar.getInstance().time,
//                                details = "dkscsdlkmc",
//                                id = "cdsmkcds",
//                                type = TransactionType.P2P,
//                                receiverId = "dqwdwwqd",
//                                senderId = "sasadasd",
//                                priceAmount = 11.0
//                            )
//                        ),
//                        TransactionModelUi(
//                            domainModel = TransactionModelDomain(
//                                currency = CurrencyModelDomain(
//                                    code = "usd",
//                                    fullName = "usd"
//                                ),
//                                date = Calendar.getInstance().time,
//                                details = "awdawdawdawdawdwwdwadw",
//                                id = "cdsmkcds",
//                                type = TransactionType.Undefined,
//                                receiverId = "dqwdwwqd",
//                                senderId = "sasadasd",
//                                priceAmount = 11.0
//                            )
//                        )
//                    )
//                ),
//                proceedIntent = {}
//            )
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

//            TransactionsHistoryScreenUi(
//                state = TransactionsHistoryScreenState(
//                    isLoading = true
//                ),
//                proceedIntent = {}
//            )

//            TransactionsHistoryScreenUi(
//                state = TransactionsHistoryScreenState(
//                    isError = true
//                ),
//                proceedIntent = {}
//            )

            TransactionsHistoryScreenUi(
                state = TransactionsHistoryScreenState(
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
                                receiverId = "dqwdwwqd",
                                senderId = "sasadasd",
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
                                details = "awdawdawdawdawdwwdwadw",
                                id = "cdsmkcds",
                                type = TransactionType.Undefined,
                                receiverId = "dqwdwwqd",
                                senderId = "sasadasd",
                                priceAmount = 11.0
                            )
                        )
                    )
                ),
                proceedIntent = {}
            )
        }
    }
}