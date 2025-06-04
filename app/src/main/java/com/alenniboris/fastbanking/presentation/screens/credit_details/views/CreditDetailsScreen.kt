package com.alenniboris.fastbanking.presentation.screens.credit_details.views

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.alenniboris.fastbanking.R
import com.alenniboris.fastbanking.domain.model.credit.CreditModelDomain
import com.alenniboris.fastbanking.domain.utils.GsonUtil.toJson
import com.alenniboris.fastbanking.presentation.model.bank_product.CreditModelUi
import com.alenniboris.fastbanking.presentation.screens.credit_details.CreditDetailsScreenActions
import com.alenniboris.fastbanking.presentation.screens.credit_details.CreditDetailsScreenState
import com.alenniboris.fastbanking.presentation.screens.credit_details.CreditDetailsScreenViewModel
import com.alenniboris.fastbanking.presentation.screens.credit_details.ICreditDetailsScreenEvent
import com.alenniboris.fastbanking.presentation.screens.credit_details.ICreditDetailsScreenIntent
import com.alenniboris.fastbanking.presentation.screens.credit_details.toUiPicture
import com.alenniboris.fastbanking.presentation.screens.credit_details.toUiString
import com.alenniboris.fastbanking.presentation.screens.destinations.ProductHistoryScreenDestination
import com.alenniboris.fastbanking.presentation.screens.destinations.ProductInformationScreenDestination
import com.alenniboris.fastbanking.presentation.uikit.theme.CreditDetailsPlaceholderContainerInnerPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.CreditDetailsPlaceholderShape
import com.alenniboris.fastbanking.presentation.uikit.theme.CreditDetailsScreenActionButtonSize
import com.alenniboris.fastbanking.presentation.uikit.theme.CreditDetailsScreenActionButtonTextPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.CreditDetailsScreenActionButtonTextSize
import com.alenniboris.fastbanking.presentation.uikit.theme.CreditDetailsScreenButtonsVerticalSpacing
import com.alenniboris.fastbanking.presentation.uikit.theme.CreditDetailsScreenCreditPlaceholderPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.CreditDetailsScreenContentPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.CreditDetailsScreenNumberOfColumns
import com.alenniboris.fastbanking.presentation.uikit.theme.FastBankingTheme
import com.alenniboris.fastbanking.presentation.uikit.theme.TopBarPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.appColor
import com.alenniboris.fastbanking.presentation.uikit.theme.appTopBarElementsColor
import com.alenniboris.fastbanking.presentation.uikit.theme.bodyStyle
import com.alenniboris.fastbanking.presentation.uikit.theme.mainScreenItemColor
import com.alenniboris.fastbanking.presentation.uikit.theme.mainScreenTextColor
import com.alenniboris.fastbanking.presentation.uikit.values.BankProduct
import com.alenniboris.fastbanking.presentation.uikit.values.CreditDetailsScreenRoute
import com.alenniboris.fastbanking.presentation.uikit.views.AppEmptyScreen
import com.alenniboris.fastbanking.presentation.uikit.views.AppIconButton
import com.alenniboris.fastbanking.presentation.uikit.views.AppProgressBar
import com.alenniboris.fastbanking.presentation.uikit.views.AppTopBar
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf
import java.util.Calendar

@Composable
@Destination(route = CreditDetailsScreenRoute)
fun CreditDetailsScreen(
    creditId: String,
    navigator: DestinationsNavigator
) {

    val viewModel = koinViewModel<CreditDetailsScreenViewModel> { parametersOf(creditId) }
    val state by viewModel.state.collectAsStateWithLifecycle()
    val event by remember { mutableStateOf(viewModel.event) }
    val proceedIntent by remember { mutableStateOf(viewModel::proceedIntent) }
    val context = LocalContext.current
    var toastMessage by remember { mutableStateOf(Toast.makeText(context, "", Toast.LENGTH_SHORT)) }

    LaunchedEffect(event) {

        launch {
            event.filterIsInstance<ICreditDetailsScreenEvent.NavigateBack>().collect {
                navigator.popBackStack()
            }
        }

        launch {
            event.filterIsInstance<ICreditDetailsScreenEvent.NavigateToProductHistoryScreen>()
                .collect { coming ->
                    navigator.navigate(
                        ProductHistoryScreenDestination(
                            productType = BankProduct.CREDIT,
                            product = coming.credit.toJson()
                        )
                    )
                }
        }

        launch {
            event.filterIsInstance<ICreditDetailsScreenEvent.NavigateToProductInfoScreen>()
                .collect { coming ->
                    navigator.navigate(
                        ProductInformationScreenDestination(
                            productType = BankProduct.CREDIT,
                            product = coming.credit.toJson()
                        )
                    )
                }
        }

        launch {
            event.filterIsInstance<ICreditDetailsScreenEvent.ShowToastMessage>().collect { coming ->
                toastMessage?.cancel()
                toastMessage =
                    Toast.makeText(context, context.getString(coming.messageId), Toast.LENGTH_SHORT)
                toastMessage?.show()
            }
        }
    }

    CreditDetailsScreenUi(
        state = state,
        proceedIntent = proceedIntent
    )
}

@Composable
private fun CreditDetailsScreenUi(
    state: CreditDetailsScreenState,
    proceedIntent: (ICreditDetailsScreenIntent) -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(appColor)
    ) {

        AppTopBar(
            modifier = Modifier
                .fillMaxWidth()
                .padding(TopBarPadding),
            leftBtnPainter = painterResource(R.drawable.back_icon),
            onLeftBtnClicked = {
                proceedIntent(
                    ICreditDetailsScreenIntent.NavigateBack
                )
            },
            headerTextString = state.credit?.name ?: ""
        )

        when {
            state.isLoading -> {
                AppProgressBar(
                    modifier = Modifier.fillMaxSize()
                )
            }

            !state.isLoading && state.credit == null -> {
                AppEmptyScreen(
                    modifier = Modifier.fillMaxSize()
                )
            }

            state.credit != null -> {

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(CreditDetailsScreenContentPadding)
                ) {

                    CreditDetailsPlaceholder(
                        modifier = Modifier
                            .padding(CreditDetailsScreenCreditPlaceholderPadding)
                            .clip(CreditDetailsPlaceholderShape)
                            .fillMaxWidth()
                            .background(mainScreenItemColor)
                            .padding(CreditDetailsPlaceholderContainerInnerPadding),
                        credit = state.credit
                    )

                    LazyVerticalGrid(
                        columns = GridCells.Fixed(CreditDetailsScreenNumberOfColumns),
                        horizontalArrangement = Arrangement.Center,
                        verticalArrangement = Arrangement.spacedBy(
                            CreditDetailsScreenButtonsVerticalSpacing
                        )
                    ) {
                        items(state.allActions) { action ->
                            CreditActionButton(
                                onClick = {
                                    proceedIntent(
                                        ICreditDetailsScreenIntent.ProceedDetailsAction(
                                            action
                                        )
                                    )
                                },
                                action = action
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun CreditActionButton(
    onClick: () -> Unit,
    action: CreditDetailsScreenActions
) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        AppIconButton(
            modifier = Modifier
                .size(CreditDetailsScreenActionButtonSize)
                .background(
                    color = mainScreenItemColor,
                    shape = CircleShape
                ),
            onClick = onClick,
            iconPainter = painterResource(action.toUiPicture()),
            tint = mainScreenTextColor
        )

        Text(
            modifier = Modifier
                .padding(CreditDetailsScreenActionButtonTextPadding)
                .width(IntrinsicSize.Min),
            text = stringResource(action.toUiString()),
            style = bodyStyle.copy(
                color = appTopBarElementsColor,
                fontSize = CreditDetailsScreenActionButtonTextSize,
                textAlign = TextAlign.Center
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
//            CreditDetailsScreenUi(
//                state = CreditDetailsScreenState(),
//                proceedIntent = {}
//            )

//            CreditDetailsScreenUi(
//                state = CreditDetailsScreenState(
//                    isLoading = true
//                ),
//                proceedIntent = {}
//            )

            CreditDetailsScreenUi(
                state = CreditDetailsScreenState(
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
                            contractNumber = "kjdnak2313",
                            bankIdCode = "sdilakjlkasnx"
                        )
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
//            CreditDetailsScreenUi(
//                state = CreditDetailsScreenState(),
//                proceedIntent = {}
//            )

//            CreditDetailsScreenUi(
//                state = CreditDetailsScreenState(
//                    isLoading = true
//                ),
//                proceedIntent = {}
//            )

            CreditDetailsScreenUi(
                state = CreditDetailsScreenState(
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
                            contractNumber = "kjdnak2313",
                            bankIdCode = "sdilakjlkasnx"
                        )
                    )
                ),
                proceedIntent = {}
            )
        }
    }
}