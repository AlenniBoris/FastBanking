package com.alenniboris.fastbanking.presentation.screens.payment_type_selection.views

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.alenniboris.fastbanking.R
import com.alenniboris.fastbanking.presentation.screens.destinations.PaymentProcessScreenDestination
import com.alenniboris.fastbanking.presentation.screens.payment_type_selection.IPaymentTypeSelectionScreenEvent
import com.alenniboris.fastbanking.presentation.screens.payment_type_selection.IPaymentTypeSelectionScreenIntent
import com.alenniboris.fastbanking.presentation.screens.payment_type_selection.PaymentType
import com.alenniboris.fastbanking.presentation.screens.payment_type_selection.PaymentTypeSelectionScreenState
import com.alenniboris.fastbanking.presentation.screens.payment_type_selection.PaymentTypeSelectionScreenViewModel
import com.alenniboris.fastbanking.presentation.screens.payment_type_selection.toUiPicture
import com.alenniboris.fastbanking.presentation.screens.payment_type_selection.toUiString
import com.alenniboris.fastbanking.presentation.uikit.theme.FastBankingTheme
import com.alenniboris.fastbanking.presentation.uikit.theme.PaymentTypeSelectionScreenButtonInnerPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.PaymentTypeSelectionScreenButtonOuterPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.PaymentTypeSelectionScreenButtonShape
import com.alenniboris.fastbanking.presentation.uikit.theme.PaymentTypeSelectionScreenButtonSize
import com.alenniboris.fastbanking.presentation.uikit.theme.PaymentTypeSelectionScreenButtonTextPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.PaymentTypeSelectionScreenButtonTextSize
import com.alenniboris.fastbanking.presentation.uikit.theme.PaymentTypeSelectionScreenContentPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.PaymentTypeSelectionScreenNumberOfColumns
import com.alenniboris.fastbanking.presentation.uikit.theme.TopBarPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.appColor
import com.alenniboris.fastbanking.presentation.uikit.theme.appTopBarElementsColor
import com.alenniboris.fastbanking.presentation.uikit.theme.bodyStyle
import com.alenniboris.fastbanking.presentation.uikit.theme.mainScreenItemColor
import com.alenniboris.fastbanking.presentation.uikit.theme.mainScreenOnItemColor
import com.alenniboris.fastbanking.presentation.uikit.theme.mainScreenTextColor
import com.alenniboris.fastbanking.presentation.uikit.values.PaymentTypeSelectionScreenRoute
import com.alenniboris.fastbanking.presentation.uikit.views.AppIconButton
import com.alenniboris.fastbanking.presentation.uikit.views.AppTopBar
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Destination(route = PaymentTypeSelectionScreenRoute)
@Composable
fun PaymentTypeSelectionScreen(
    navigator: DestinationsNavigator
) {

    val viewModel = koinViewModel<PaymentTypeSelectionScreenViewModel>()
    val state by viewModel.state.collectAsStateWithLifecycle()
    val event by remember { mutableStateOf(viewModel.event) }
    val proceedIntent by remember { mutableStateOf(viewModel::proceedIntent) }

    LaunchedEffect(event) {

        launch {
            event.filterIsInstance<IPaymentTypeSelectionScreenEvent.OpenPaymentToMyOwnCard>()
                .collect {
                    navigator.navigate(
                        PaymentProcessScreenDestination(
                            paymentType = PaymentType.OnMyCard
                        )
                    )
                }
        }

        launch {
            event.filterIsInstance<IPaymentTypeSelectionScreenEvent.OpenPaymentWithEripNumber>()
                .collect {
                    navigator.navigate(
                        PaymentProcessScreenDestination(
                            paymentType = PaymentType.ByEripNumber
                        )
                    )
                }
        }

        launch {
            event.filterIsInstance<IPaymentTypeSelectionScreenEvent.OpenPaymentWithCreditCardNumber>()
                .collect {
                    navigator.navigate(
                        PaymentProcessScreenDestination(
                            paymentType = PaymentType.ByCardNumber
                        )
                    )
                }
        }

        launch {
            event.filterIsInstance<IPaymentTypeSelectionScreenEvent.OpenPaymentForCreditByContractNumber>()
                .collect {
                    navigator.navigate(
                        PaymentProcessScreenDestination(
                            paymentType = PaymentType.ForCreditByContractNumber
                        )
                    )
                }
        }
    }

    PaymentTypeSelectionScreenUi(
        state = state,
        proceedIntent = proceedIntent
    )
}

@Composable
private fun PaymentTypeSelectionScreenUi(
    state: PaymentTypeSelectionScreenState,
    proceedIntent: (IPaymentTypeSelectionScreenIntent) -> Unit
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
            headerTextString = stringResource(R.string.payment_screen_header_text)
        )

        LazyVerticalStaggeredGrid(
            modifier = Modifier.padding(PaymentTypeSelectionScreenContentPadding),
            columns = StaggeredGridCells.Fixed(PaymentTypeSelectionScreenNumberOfColumns)
        ) {
            items(state.paymentTypes) { type ->
                PaymentTypeButton(
                    modifier = Modifier
                        .padding(PaymentTypeSelectionScreenButtonOuterPadding)
                        .background(
                            color = mainScreenItemColor,
                            shape = PaymentTypeSelectionScreenButtonShape
                        )
                        .padding(PaymentTypeSelectionScreenButtonInnerPadding)
                        .clickable {
                            proceedIntent(
                                IPaymentTypeSelectionScreenIntent.OpenPaymentProcessScreen(type)
                            )
                        },
                    type = type
                )
            }
        }
    }
}

@Composable
private fun PaymentTypeButton(
    modifier: Modifier = Modifier,
    type: PaymentType
) {

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        AppIconButton(
            modifier = Modifier
                .size(PaymentTypeSelectionScreenButtonSize)
                .background(
                    color = mainScreenOnItemColor,
                    shape = CircleShape
                ),
            iconPainter = painterResource(type.toUiPicture()),
            tint = mainScreenTextColor
        )

        Text(
            modifier = Modifier
                .padding(PaymentTypeSelectionScreenButtonTextPadding),
            text = stringResource(type.toUiString()),
            style = bodyStyle.copy(
                color = appTopBarElementsColor,
                fontSize = PaymentTypeSelectionScreenButtonTextSize,
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
            PaymentTypeSelectionScreenUi(
                state = PaymentTypeSelectionScreenState(),
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
            PaymentTypeSelectionScreenUi(
                state = PaymentTypeSelectionScreenState(),
                proceedIntent = {}
            )
        }
    }
}