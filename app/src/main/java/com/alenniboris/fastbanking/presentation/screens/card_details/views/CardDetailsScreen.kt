package com.alenniboris.fastbanking.presentation.screens.card_details.views

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.alenniboris.fastbanking.R
import com.alenniboris.fastbanking.domain.model.OwnerModelDomain
import com.alenniboris.fastbanking.domain.model.card.CardModelDomain
import com.alenniboris.fastbanking.domain.model.card.CardSystem
import com.alenniboris.fastbanking.domain.model.card.CardType
import com.alenniboris.fastbanking.domain.utils.GsonUtil.toJson
import com.alenniboris.fastbanking.presentation.model.bank_product.CardModelUi
import com.alenniboris.fastbanking.presentation.screens.card_details.CardDetailsScreenActions
import com.alenniboris.fastbanking.presentation.screens.card_details.CardDetailsScreenState
import com.alenniboris.fastbanking.presentation.screens.card_details.CardDetailsScreenViewModel
import com.alenniboris.fastbanking.presentation.screens.card_details.ICardDetailsScreenEvent
import com.alenniboris.fastbanking.presentation.screens.card_details.ICardDetailsScreenIntent
import com.alenniboris.fastbanking.presentation.screens.card_details.toUiPicture
import com.alenniboris.fastbanking.presentation.screens.card_details.toUiString
import com.alenniboris.fastbanking.presentation.screens.destinations.ProductHistoryScreenDestination
import com.alenniboris.fastbanking.presentation.screens.destinations.ProductInformationScreenDestination
import com.alenniboris.fastbanking.presentation.uikit.theme.CardDetailsScreenActionButtonSize
import com.alenniboris.fastbanking.presentation.uikit.theme.CardDetailsScreenActionButtonTextPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.CardDetailsScreenActionButtonTextSize
import com.alenniboris.fastbanking.presentation.uikit.theme.CardDetailsScreenButtonsVerticalSpacing
import com.alenniboris.fastbanking.presentation.uikit.theme.CardDetailsScreenCardPlaceholderPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.CardDetailsScreenContentPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.CardDetailsScreenNumberOfColumns
import com.alenniboris.fastbanking.presentation.uikit.theme.FastBankingTheme
import com.alenniboris.fastbanking.presentation.uikit.theme.TopBarPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.appColor
import com.alenniboris.fastbanking.presentation.uikit.theme.appTopBarElementsColor
import com.alenniboris.fastbanking.presentation.uikit.theme.bodyStyle
import com.alenniboris.fastbanking.presentation.uikit.theme.mainScreenItemColor
import com.alenniboris.fastbanking.presentation.uikit.theme.mainScreenTextColor
import com.alenniboris.fastbanking.presentation.uikit.values.BankProduct
import com.alenniboris.fastbanking.presentation.uikit.values.CardDetailsScreenRoute
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
@Destination(route = CardDetailsScreenRoute)
fun CardDetailsScreen(
    cardId: String,
    navigator: DestinationsNavigator
) {

    val viewModel = koinViewModel<CardDetailsScreenViewModel> { parametersOf(cardId) }
    val state by viewModel.state.collectAsStateWithLifecycle()
    val event by remember { mutableStateOf(viewModel.event) }
    val proceedIntent by remember { mutableStateOf(viewModel::proceedIntent) }
    val context = LocalContext.current
    var toastMessage by remember { mutableStateOf(Toast.makeText(context, "", Toast.LENGTH_SHORT)) }

    LaunchedEffect(event) {

        launch {
            event.filterIsInstance<ICardDetailsScreenEvent.NavigateBack>().collect {
                navigator.popBackStack()
            }
        }

        launch {
            event.filterIsInstance<ICardDetailsScreenEvent.ShowToastMessage>().collect { coming ->
                toastMessage?.cancel()
                toastMessage =
                    Toast.makeText(context, context.getString(coming.messageId), Toast.LENGTH_SHORT)
                toastMessage?.cancel()
            }
        }

        launch {
            event.filterIsInstance<ICardDetailsScreenEvent.NavigateToProductInfoScreen>()
                .collect { coming ->
                    navigator.navigate(
                        ProductInformationScreenDestination(
                            productType = BankProduct.CARD,
                            product = coming.card.toJson()
                        )
                    )
                }
        }

        launch {
            event.filterIsInstance<ICardDetailsScreenEvent.NavigateToProductHistoryScreen>()
                .collect { coming ->
                    navigator.navigate(
                        ProductHistoryScreenDestination(
                            productType = BankProduct.CARD,
                            product = coming.card.toJson()
                        )
                    )
                }
        }
    }

    CardDetailsScreenUi(
        state = state,
        proceedIntent = proceedIntent
    )
}

@Composable
private fun CardDetailsScreenUi(
    state: CardDetailsScreenState,
    proceedIntent: (ICardDetailsScreenIntent) -> Unit
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
                    ICardDetailsScreenIntent.NavigateBack
                )
            },
            headerTextString = state.card?.name ?: ""
        )

        when {
            state.isLoading -> {
                AppProgressBar(
                    modifier = Modifier.fillMaxSize()
                )
            }

            !state.isLoading && state.card == null -> {
                AppEmptyScreen(
                    modifier = Modifier.fillMaxSize()
                )
            }

            state.card != null -> {

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(CardDetailsScreenContentPadding)
                ) {

                    CardDetailsPlaceholder(
                        modifier = Modifier
                            .padding(CardDetailsScreenCardPlaceholderPadding)
                            .fillMaxWidth(),
                        card = state.card
                    )

                    LazyVerticalGrid(
                        columns = GridCells.Fixed(CardDetailsScreenNumberOfColumns),
                        horizontalArrangement = Arrangement.Center,
                        verticalArrangement = Arrangement.spacedBy(
                            CardDetailsScreenButtonsVerticalSpacing
                        )
                    ) {
                        items(state.allActions) { action ->
                            CardActionButton(
                                onClick = {
                                    proceedIntent(
                                        ICardDetailsScreenIntent.ProceedDetailsAction(
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
private fun CardActionButton(
    onClick: () -> Unit,
    action: CardDetailsScreenActions
) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        AppIconButton(
            modifier = Modifier
                .size(CardDetailsScreenActionButtonSize)
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
                .padding(CardDetailsScreenActionButtonTextPadding)
                .width(IntrinsicSize.Min),
            text = stringResource(action.toUiString()),
            style = bodyStyle.copy(
                color = appTopBarElementsColor,
                fontSize = CardDetailsScreenActionButtonTextSize,
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
//            CardDetailsScreenUi(
//                state = CardDetailsScreenState(),
//                proceedIntent = {}
//            )

//            CardDetailsScreenUi(
//                state = CardDetailsScreenState(
//                    isLoading = true
//                ),
//                proceedIntent = {}
//            )

            CardDetailsScreenUi(
                state = CardDetailsScreenState(
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
//            CardDetailsScreenUi(
//                state = CardDetailsScreenState(),
//                proceedIntent = {}
//            )

//            CardDetailsScreenUi(
//                state = CardDetailsScreenState(
//                    isLoading = true
//                ),
//                proceedIntent = {}
//            )

            CardDetailsScreenUi(
                state = CardDetailsScreenState(
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
                ),
                proceedIntent = {}
            )
        }
    }
}