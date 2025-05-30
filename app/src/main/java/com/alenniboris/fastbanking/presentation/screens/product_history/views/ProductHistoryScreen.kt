package com.alenniboris.fastbanking.presentation.screens.product_history.views

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.alenniboris.fastbanking.R
import com.alenniboris.fastbanking.domain.model.currency.CurrencyModelDomain
import com.alenniboris.fastbanking.domain.model.transaction.TransactionModelDomain
import com.alenniboris.fastbanking.domain.model.transaction.TransactionType
import com.alenniboris.fastbanking.presentation.model.bank_product.TransactionModelUi
import com.alenniboris.fastbanking.presentation.screens.product_history.IProductHistoryScreenEvent
import com.alenniboris.fastbanking.presentation.screens.product_history.IProductHistoryScreenIntent
import com.alenniboris.fastbanking.presentation.screens.product_history.ProductHistoryScreenState
import com.alenniboris.fastbanking.presentation.screens.product_history.ProductHistoryScreenViewModel
import com.alenniboris.fastbanking.presentation.uikit.theme.FastBankingTheme
import com.alenniboris.fastbanking.presentation.uikit.theme.TopBarPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.TransactionsHistoryScreenContentPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.appColor
import com.alenniboris.fastbanking.presentation.uikit.values.BankProduct
import com.alenniboris.fastbanking.presentation.uikit.values.ProductHistoryScreenRoute
import com.alenniboris.fastbanking.presentation.uikit.views.AppTopBar
import com.alenniboris.fastbanking.presentation.uikit.views.AppTransactionDetailsSection
import com.alenniboris.fastbanking.presentation.uikit.views.AppTransactionsSection
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf
import java.util.Calendar

@Composable
@Destination(route = ProductHistoryScreenRoute)
fun ProductHistoryScreen(
    navigator: DestinationsNavigator,
    productType: BankProduct,
    product: String
) {

    val viewModel =
        koinViewModel<ProductHistoryScreenViewModel>() { parametersOf(productType, product) }
    val state by viewModel.state.collectAsStateWithLifecycle()
    val event by remember { mutableStateOf(viewModel.event) }
    val proceedIntent by remember { mutableStateOf(viewModel::proceedIntent) }
    val context = LocalContext.current
    var toastMessage by remember {
        mutableStateOf(
            Toast.makeText(context, "", Toast.LENGTH_SHORT)
        )
    }

    LaunchedEffect(event) {

        launch {
            event.filterIsInstance<IProductHistoryScreenEvent.ShowToastMessage>()
                .collect { coming ->
                    toastMessage?.cancel()
                    toastMessage = Toast.makeText(
                        context,
                        context.getString(coming.messageIg),
                        Toast.LENGTH_SHORT
                    )
                    toastMessage?.show()
                }
        }

        launch {
            event.filterIsInstance<IProductHistoryScreenEvent.NavigateBack>().collect {
                navigator.popBackStack()
            }
        }
    }

    ProductHistoryScreenUi(
        state = state,
        proceedIntent = proceedIntent
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ProductHistoryScreenUi(
    state: ProductHistoryScreenState,
    proceedIntent: (IProductHistoryScreenIntent) -> Unit
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
            headerTextString = stringResource(R.string.history_screen_text),
            leftBtnPainter = painterResource(R.drawable.back_icon),
            onLeftBtnClicked = {
                proceedIntent(IProductHistoryScreenIntent.NavigateBack)
            }
        )

        AppTransactionsSection(
            modifier = Modifier
                .fillMaxWidth()
                .padding(TransactionsHistoryScreenContentPadding),
            isLoading = state.isLoading,
            transactions = state.transactions,
            onItemClicked = { selected ->
                proceedIntent(
                    IProductHistoryScreenIntent.UpdateSelectedTransaction(
                        selected
                    )
                )
            }
        )

        state.selected?.let { transaction ->
            AppTransactionDetailsSection(
                transaction = transaction,
                onDismiss = {
                    proceedIntent(
                        IProductHistoryScreenIntent.UpdateSelectedTransaction(null)
                    )
                }
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
            ProductHistoryScreenUi(
                proceedIntent = {},
                state = ProductHistoryScreenState(
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
                )
            )

//            ProductHistoryScreenUi(
//                proceedIntent = {},
//                state = ProductHistoryScreenState()
//            )
//
//            ProductHistoryScreenUi(
//                proceedIntent = {},
//                state = ProductHistoryScreenState(
//                    isLoading = true
//                )
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
//            ProductHistoryScreenUi(
//                proceedIntent = {},
//                state = ProductHistoryScreenState(
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
//                )
//            )

            ProductHistoryScreenUi(
                proceedIntent = {},
                state = ProductHistoryScreenState()
            )

//            ProductHistoryScreenUi(
//                proceedIntent = {},
//                state = ProductHistoryScreenState(
//                    isLoading = true
//                )
//            )
        }
    }
}