package com.alenniboris.fastbanking.presentation.screens.account_details.views

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.alenniboris.fastbanking.R
import com.alenniboris.fastbanking.domain.model.OwnerModelDomain
import com.alenniboris.fastbanking.domain.model.account.AccountModelDomain
import com.alenniboris.fastbanking.domain.model.card.CardSystem
import com.alenniboris.fastbanking.domain.model.card.SimpleCardModelDomain
import com.alenniboris.fastbanking.domain.utils.GsonUtil.toJson
import com.alenniboris.fastbanking.presentation.model.bank_product.AccountModelUi
import com.alenniboris.fastbanking.presentation.model.bank_product.CardModelUi
import com.alenniboris.fastbanking.presentation.screens.account_details.AccountDetailsScreenActions
import com.alenniboris.fastbanking.presentation.screens.account_details.AccountDetailsScreenState
import com.alenniboris.fastbanking.presentation.screens.account_details.AccountDetailsScreenViewModel
import com.alenniboris.fastbanking.presentation.screens.account_details.IAccountDetailsScreenEvent
import com.alenniboris.fastbanking.presentation.screens.account_details.IAccountDetailsScreenIntent
import com.alenniboris.fastbanking.presentation.screens.account_details.toUiPicture
import com.alenniboris.fastbanking.presentation.screens.account_details.toUiString
import com.alenniboris.fastbanking.presentation.screens.destinations.CardDetailsScreenDestination
import com.alenniboris.fastbanking.presentation.screens.destinations.ProductHistoryScreenDestination
import com.alenniboris.fastbanking.presentation.screens.destinations.ProductInformationScreenDestination
import com.alenniboris.fastbanking.presentation.uikit.theme.AccountDetailsPlaceholderElementTextPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.AccountDetailsPlaceholderMainTextSize
import com.alenniboris.fastbanking.presentation.uikit.theme.AccountDetailsScreenActionButtonSize
import com.alenniboris.fastbanking.presentation.uikit.theme.AccountDetailsScreenActionButtonTextPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.AccountDetailsScreenActionButtonTextSize
import com.alenniboris.fastbanking.presentation.uikit.theme.AccountDetailsScreenButtonsVerticalSpacing
import com.alenniboris.fastbanking.presentation.uikit.theme.AccountDetailsScreenNumberOfColumns
import com.alenniboris.fastbanking.presentation.uikit.theme.AccountDetailsScreenSpacerHeight
import com.alenniboris.fastbanking.presentation.uikit.theme.CreditDetailsPlaceholderContainerInnerPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.CreditDetailsPlaceholderElementPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.CreditDetailsPlaceholderShape
import com.alenniboris.fastbanking.presentation.uikit.theme.CreditDetailsScreenContentPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.CreditDetailsScreenCreditPlaceholderPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.FastBankingTheme
import com.alenniboris.fastbanking.presentation.uikit.theme.TopBarPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.appColor
import com.alenniboris.fastbanking.presentation.uikit.theme.appTopBarElementsColor
import com.alenniboris.fastbanking.presentation.uikit.theme.bodyStyle
import com.alenniboris.fastbanking.presentation.uikit.theme.mainScreenItemColor
import com.alenniboris.fastbanking.presentation.uikit.theme.mainScreenTextColor
import com.alenniboris.fastbanking.presentation.uikit.values.AccountDetailsScreenRoute
import com.alenniboris.fastbanking.presentation.uikit.values.BankProduct
import com.alenniboris.fastbanking.presentation.uikit.views.AppEmptyScreen
import com.alenniboris.fastbanking.presentation.uikit.views.AppFilter
import com.alenniboris.fastbanking.presentation.uikit.views.AppIconButton
import com.alenniboris.fastbanking.presentation.uikit.views.AppProgressBar
import com.alenniboris.fastbanking.presentation.uikit.views.AppTopBar
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
@Destination(route = AccountDetailsScreenRoute)
fun AccountDetailsScreen(
    accountId: String,
    navigator: DestinationsNavigator
) {

    val viewModel = koinViewModel<AccountDetailsScreenViewModel> { parametersOf(accountId) }
    val state by viewModel.state.collectAsStateWithLifecycle()
    val event by remember { mutableStateOf(viewModel.event) }
    val proceedIntent by remember { mutableStateOf(viewModel::proceedIntent) }
    val context = LocalContext.current
    var toastMessage by remember { mutableStateOf(Toast.makeText(context, "", Toast.LENGTH_SHORT)) }

    LaunchedEffect(event) {

        launch {
            event.filterIsInstance<IAccountDetailsScreenEvent.NavigateBack>().collect {
                navigator.popBackStack()
            }
        }

        launch {
            event.filterIsInstance<IAccountDetailsScreenEvent.NavigateToProductInfoScreen>()
                .collect { coming ->
                    navigator.navigate(
                        ProductInformationScreenDestination(
                            productType = BankProduct.DEPOSITS_AND_ACCOUNTS,
                            product = coming.account.toJson()
                        )
                    )
                }
        }

        launch {
            event.filterIsInstance<IAccountDetailsScreenEvent.NavigateToProductHistoryScreen>()
                .collect { coming ->
                    navigator.navigate(
                        ProductHistoryScreenDestination(
                            productType = BankProduct.DEPOSITS_AND_ACCOUNTS,
                            product = coming.account.toJson()
                        )
                    )
                }
        }

        launch {
            event.filterIsInstance<IAccountDetailsScreenEvent.ShowToastMessage>()
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

        launch {
            event.filterIsInstance<IAccountDetailsScreenEvent.OpenCardDetailsScreen>()
                .collect { coming ->
                    navigator.navigate(
                        CardDetailsScreenDestination(
                            cardId = coming.cardId
                        )
                    )
                }
        }
    }

    AccountDetailsScreenUi(
        state = state,
        proceedIntent = proceedIntent
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AccountDetailsScreenUi(
    state: AccountDetailsScreenState,
    proceedIntent: (IAccountDetailsScreenIntent) -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(appColor)
            .padding(CreditDetailsScreenContentPadding)
    ) {

        AppTopBar(
            modifier = Modifier
                .fillMaxWidth()
                .padding(TopBarPadding),
            headerTextString = state.account?.name ?: "",
            leftBtnPainter = painterResource(R.drawable.back_icon),
            onLeftBtnClicked = {
                proceedIntent(
                    IAccountDetailsScreenIntent.NavigateBack
                )
            }
        )

        when {
            state.isAccountLoading -> {
                AppProgressBar(
                    modifier = Modifier.fillMaxSize()
                )
            }

            state.account == null && !state.isAccountLoading -> {
                AppEmptyScreen(
                    modifier = Modifier.fillMaxSize()
                )
            }

            state.account != null -> {

                AccountDetailsPlaceholder(
                    modifier = Modifier
                        .padding(CreditDetailsScreenCreditPlaceholderPadding)
                        .clip(CreditDetailsPlaceholderShape)
                        .fillMaxWidth()
                        .background(mainScreenItemColor)
                        .padding(CreditDetailsPlaceholderContainerInnerPadding),
                    account = state.account
                )

                LazyVerticalGrid(
                    columns = GridCells.Fixed(AccountDetailsScreenNumberOfColumns),
                    horizontalArrangement = Arrangement.Center,
                    verticalArrangement = Arrangement.spacedBy(
                        AccountDetailsScreenButtonsVerticalSpacing
                    )
                ) {
                    items(state.allActions) { action ->
                        AccountActionButton(
                            onClick = {
                                proceedIntent(
                                    IAccountDetailsScreenIntent.ProceedDetailsAction(
                                        action
                                    )
                                )
                            },
                            action = action
                        )
                    }
                }

                if (state.isAttachedCardsSheetVisible) {
                    AppFilter(
                        elements = state.attachedCards,
                        onDismiss = {
                            proceedIntent(
                                IAccountDetailsScreenIntent.UpdateAttachedCardsSheetVisibility
                            )
                        },
                        itemContent = { card ->
                            AttachedCardItemContent(
                                modifier = Modifier
                                    .padding(CreditDetailsPlaceholderElementPadding)
                                    .fillMaxWidth()
                                    .clickable {
                                        proceedIntent(
                                            IAccountDetailsScreenIntent.OpenCardDetailsScreen(
                                                cardId = card.domainModel.id
                                            )
                                        )
                                    },
                                card = card
                            )
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun AttachedCardItemContent(
    modifier: Modifier = Modifier,
    card: CardModelUi
) {

    Column(
        modifier = modifier
    ) {

        Text(
            text = card.name,
            style = bodyStyle.copy(
                color = appTopBarElementsColor,
                fontWeight = FontWeight.Light,
                fontSize = AccountDetailsPlaceholderMainTextSize
            )
        )

        Text(
            modifier = Modifier.padding(
                AccountDetailsPlaceholderElementTextPadding
            ),
            text = card.numberText,
            style = bodyStyle.copy(
                color = appTopBarElementsColor,
                fontSize = AccountDetailsPlaceholderMainTextSize
            )
        )

        Spacer(
            modifier = Modifier
                .padding(AccountDetailsPlaceholderElementTextPadding)
                .height(AccountDetailsScreenSpacerHeight)
                .background(appTopBarElementsColor)
        )
    }
}

@Composable
private fun AccountActionButton(
    onClick: () -> Unit,
    action: AccountDetailsScreenActions
) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        AppIconButton(
            modifier = Modifier
                .size(AccountDetailsScreenActionButtonSize)
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
                .padding(AccountDetailsScreenActionButtonTextPadding)
                .width(IntrinsicSize.Min),
            text = stringResource(action.toUiString()),
            style = bodyStyle.copy(
                color = appTopBarElementsColor,
                fontSize = AccountDetailsScreenActionButtonTextSize,
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
//            AccountDetailsScreenUi(
//                state = AccountDetailsScreenState(),
//                proceedIntent = {}
//            )

            AccountDetailsScreenUi(
                state = AccountDetailsScreenState(
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
//            AccountDetailsScreenUi(
//                state = AccountDetailsScreenState(),
//                proceedIntent = {}
//            )

            AccountDetailsScreenUi(
                state = AccountDetailsScreenState(
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
                    )
                ),
                proceedIntent = {}
            )
        }
    }
}