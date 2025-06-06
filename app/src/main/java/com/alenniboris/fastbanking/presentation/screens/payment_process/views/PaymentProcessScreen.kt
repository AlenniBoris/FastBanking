package com.alenniboris.fastbanking.presentation.screens.payment_process.views

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.SwitchDefaults
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.alenniboris.fastbanking.R
import com.alenniboris.fastbanking.domain.model.OwnerModelDomain
import com.alenniboris.fastbanking.domain.model.card.CardModelDomain
import com.alenniboris.fastbanking.domain.model.card.CardSystem
import com.alenniboris.fastbanking.domain.model.card.CardType
import com.alenniboris.fastbanking.presentation.model.bank_product.CardModelUi
import com.alenniboris.fastbanking.presentation.screens.payment_process.IPaymentProcessScreenEvent
import com.alenniboris.fastbanking.presentation.screens.payment_process.IPaymentProcessScreenIntent
import com.alenniboris.fastbanking.presentation.screens.payment_process.PaymentProcessScreenState
import com.alenniboris.fastbanking.presentation.screens.payment_process.PaymentProcessScreenViewModel
import com.alenniboris.fastbanking.presentation.screens.payment_type_selection.PaymentType
import com.alenniboris.fastbanking.presentation.screens.payment_type_selection.toUiPlaceholderText
import com.alenniboris.fastbanking.presentation.screens.payment_type_selection.toUiString
import com.alenniboris.fastbanking.presentation.uikit.theme.FastBankingTheme
import com.alenniboris.fastbanking.presentation.uikit.theme.MainScreenInformationSectionContentTextSize
import com.alenniboris.fastbanking.presentation.uikit.theme.MainScreenUserProductsModalSheetItemOuterPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.PaymentProcessScreenCardPlaceholderColumnTextPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.PaymentProcessScreenCardPlaceholderInnerPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.PaymentProcessScreenCardPlaceholderShape
import com.alenniboris.fastbanking.presentation.uikit.theme.PaymentProcessScreenContentPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.PaymentProcessScreenTextFieldInnerPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.PaymentProcessScreenTextFieldShape
import com.alenniboris.fastbanking.presentation.uikit.theme.PaymentProcessScreenTextRowFontSize
import com.alenniboris.fastbanking.presentation.uikit.theme.PaymentProcessScreenTextRowPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.PaymentProcessScreenTextRowTextPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.PaymentTypeSelectionScreenContentPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.ProductApplianceFormScreenBottomButtonInnerPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.TopBarPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.appColor
import com.alenniboris.fastbanking.presentation.uikit.theme.appTopBarElementsColor
import com.alenniboris.fastbanking.presentation.uikit.theme.bodyStyle
import com.alenniboris.fastbanking.presentation.uikit.theme.mainScreenItemColor
import com.alenniboris.fastbanking.presentation.uikit.theme.productApplianceFormCheckboxCheckedTrackColor
import com.alenniboris.fastbanking.presentation.uikit.values.PaymentProcessScreenRoute
import com.alenniboris.fastbanking.presentation.uikit.views.AppEmptyScreen
import com.alenniboris.fastbanking.presentation.uikit.views.AppFilter
import com.alenniboris.fastbanking.presentation.uikit.views.AppProcessProceedButton
import com.alenniboris.fastbanking.presentation.uikit.views.AppProgressBar
import com.alenniboris.fastbanking.presentation.uikit.views.AppTextField
import com.alenniboris.fastbanking.presentation.uikit.views.AppTextSectionWithSwitcher
import com.alenniboris.fastbanking.presentation.uikit.views.AppTopBar
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf
import java.util.Calendar

@Composable
@Destination(route = PaymentProcessScreenRoute)
fun PaymentProcessScreen(
    paymentType: PaymentType,
    navigator: DestinationsNavigator
) {

    val viewModel = koinViewModel<PaymentProcessScreenViewModel> { parametersOf(paymentType) }
    val state by viewModel.state.collectAsStateWithLifecycle()
    val event by remember { mutableStateOf(viewModel.event) }
    val proceedIntent by remember { mutableStateOf(viewModel::proceedIntent) }
    val context = LocalContext.current
    var toastMessage by remember { mutableStateOf(Toast.makeText(context, "", Toast.LENGTH_SHORT)) }

    LaunchedEffect(event) {

        launch {
            event.filterIsInstance<IPaymentProcessScreenEvent.ShowToastMessage>()
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
            event.filterIsInstance<IPaymentProcessScreenEvent.NavigateBack>().collect {
                navigator.popBackStack()
            }
        }
    }

    PaymentProcessScreenUi(
        state = state,
        proceedIntent = proceedIntent
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PaymentProcessScreenUi(
    state: PaymentProcessScreenState,
    proceedIntent: (IPaymentProcessScreenIntent) -> Unit
) {

    if (state.isCardsSheetForReceiverCardVisible) {

        AppFilter(
            elements = state.allUserCards,
            onDismiss = {
                proceedIntent(
                    IPaymentProcessScreenIntent.UpdateCardsSheetVisibilityForReceiverCard
                )
            },
            itemContent = { item ->
                AppTextSectionWithSwitcher(
                    modifier = Modifier
                        .padding(MainScreenUserProductsModalSheetItemOuterPadding)
                        .fillMaxWidth(),
                    text = item.name,
                    textStyle = bodyStyle.copy(
                        color = appTopBarElementsColor,
                        fontSize = MainScreenInformationSectionContentTextSize
                    ),
                    isChecked = item == state.selectedReceiverCard,
                    onCheckedChanged = {
                        proceedIntent(
                            IPaymentProcessScreenIntent.UpdateSelectedReceiverCard(
                                item
                            )
                        )
                    },
                    switchColors = SwitchDefaults.colors().copy(
                        uncheckedTrackColor = appTopBarElementsColor,
                        uncheckedBorderColor = appColor,
                        uncheckedIconColor = appTopBarElementsColor,
                        uncheckedThumbColor = appColor,
                        checkedTrackColor = productApplianceFormCheckboxCheckedTrackColor,
                        checkedThumbColor = appColor
                    )
                )
            }
        )
    }

    if (state.isCardsSheetForUsedCardVisible) {

        AppFilter(
            elements = state.allUserCards,
            onDismiss = {
                proceedIntent(
                    IPaymentProcessScreenIntent.UpdateCardsSheetVisibilityForUsedCard
                )
            },
            itemContent = { item ->
                AppTextSectionWithSwitcher(
                    modifier = Modifier
                        .padding(MainScreenUserProductsModalSheetItemOuterPadding)
                        .fillMaxWidth(),
                    text = item.name,
                    textStyle = bodyStyle.copy(
                        color = appTopBarElementsColor,
                        fontSize = MainScreenInformationSectionContentTextSize
                    ),
                    isChecked = item == state.selectedUsedCard,
                    onCheckedChanged = {
                        proceedIntent(
                            IPaymentProcessScreenIntent.UpdateSelectedUsedCard(
                                item
                            )
                        )
                    },
                    switchColors = SwitchDefaults.colors().copy(
                        uncheckedTrackColor = appTopBarElementsColor,
                        uncheckedBorderColor = appColor,
                        uncheckedIconColor = appTopBarElementsColor,
                        uncheckedThumbColor = appColor,
                        checkedTrackColor = productApplianceFormCheckboxCheckedTrackColor,
                        checkedThumbColor = appColor
                    )
                )
            }
        )
    }

    when {
        state.isPaymentProceeding -> {
            AppProgressBar(
                modifier = Modifier
                    .fillMaxSize()
                    .background(appColor)
            )
        }

        else -> {

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(appColor)
            ) {

                Column {

                    AppTopBar(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(TopBarPadding),
                        leftBtnPainter = painterResource(R.drawable.back_icon),
                        onLeftBtnClicked = {
                            proceedIntent(
                                IPaymentProcessScreenIntent.NavigateBack
                            )
                        },
                        headerTextString = stringResource(state.paymentType.toUiString())
                    )

                    when {
                        state.allUserCards.isEmpty() -> {
                            AppEmptyScreen(
                                modifier = Modifier.fillMaxSize(),
                                textId = R.string.user_has_no_cards
                            )
                        }

                        state.isPaymentSucceeded -> {

                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(PaymentTypeSelectionScreenContentPadding),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {

                                Image(
                                    painter = painterResource(R.drawable.appliance_approved_status_image),
                                    contentDescription = stringResource(R.string.approved_status_text)
                                )

                                Text(
                                    modifier = Modifier
                                        .padding(
                                            PaymentProcessScreenCardPlaceholderColumnTextPadding
                                        ),
                                    text = stringResource(R.string.transaction_approved_text),
                                    style = bodyStyle.copy(
                                        color = appTopBarElementsColor,
                                        fontSize = PaymentProcessScreenTextRowFontSize,
                                        textAlign = TextAlign.Center
                                    )
                                )
                            }
                        }

                        else -> {

                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(PaymentProcessScreenContentPadding)
                                    .verticalScroll(rememberScrollState())
                            ) {

                                PaymentCardPlaceholder(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .background(
                                            color = mainScreenItemColor,
                                            shape = PaymentProcessScreenCardPlaceholderShape
                                        )
                                        .padding(PaymentProcessScreenCardPlaceholderInnerPadding)
                                        .clickable {
                                            proceedIntent(
                                                IPaymentProcessScreenIntent.UpdateCardsSheetVisibilityForUsedCard
                                            )
                                        },
                                    isLoading = state.isAllUserCardsLoading,
                                    card = state.selectedUsedCard
                                )

                                if (state.paymentType != PaymentType.OnMyCard) {

                                    AppTextField(
                                        modifier = Modifier
                                            .padding(PaymentProcessScreenTextRowPadding)
                                            .fillMaxWidth()
                                            .background(
                                                color = mainScreenItemColor,
                                                shape = PaymentProcessScreenTextFieldShape
                                            )
                                            .padding(PaymentProcessScreenTextFieldInnerPadding),
                                        value = state.enteredReceiverData,
                                        onValueChanged = { value ->
                                            proceedIntent(
                                                IPaymentProcessScreenIntent.UpdateEnteredReceiverData(
                                                    value
                                                )
                                            )
                                        },
                                        placeholder = stringResource(state.paymentType.toUiPlaceholderText())
                                    )
                                }

                                Row(
                                    modifier = Modifier
                                        .padding(PaymentProcessScreenTextRowPadding)
                                        .fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {

                                    AppTextField(
                                        modifier = Modifier
                                            .weight(1f)
                                            .background(
                                                color = mainScreenItemColor,
                                                shape = PaymentProcessScreenTextFieldShape
                                            )
                                            .padding(PaymentProcessScreenTextFieldInnerPadding),
                                        value = state.enteredAmount,
                                        onValueChanged = { value ->
                                            proceedIntent(
                                                IPaymentProcessScreenIntent.UpdateEnteredAmount(
                                                    value
                                                )
                                            )
                                        },
                                        placeholder = stringResource(R.string.enter_amount_text),
                                        keyboardType = KeyboardType.Number
                                    )

                                    Text(
                                        modifier = Modifier.padding(
                                            PaymentProcessScreenTextRowTextPadding
                                        ),
                                        text = state.selectedUsedCard?.domainModel?.currencyCode
                                            ?: "",
                                        style = bodyStyle.copy(
                                            color = appTopBarElementsColor,
                                            fontSize = PaymentProcessScreenTextRowFontSize
                                        )
                                    )
                                }

                                if (state.paymentType == PaymentType.OnMyCard) {

                                    PaymentCardPlaceholder(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .background(
                                                color = mainScreenItemColor,
                                                shape = PaymentProcessScreenCardPlaceholderShape
                                            )
                                            .padding(PaymentProcessScreenCardPlaceholderInnerPadding)
                                            .clickable {
                                                proceedIntent(
                                                    IPaymentProcessScreenIntent.UpdateCardsSheetVisibilityForReceiverCard
                                                )
                                            },
                                        isLoading = state.isAllUserCardsLoading,
                                        card = state.selectedReceiverCard
                                    )
                                }
                            }
                        }
                    }
                }

                if (!state.isPaymentSucceeded && state.allUserCards.isNotEmpty()) {
                    AppProcessProceedButton(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .fillMaxWidth()
                            .background(mainScreenItemColor)
                            .padding(ProductApplianceFormScreenBottomButtonInnerPadding),
                        onClick = {
                            proceedIntent(
                                IPaymentProcessScreenIntent.ProceedTransaction
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
//            PaymentProcessScreenUi(
//                state = PaymentProcessScreenState(
//                    paymentType = PaymentType.OnMyCard
//                ),
//                proceedIntent = {}
//            )

//            PaymentProcessScreenUi(
//                state = PaymentProcessScreenState(
//                    paymentType = PaymentType.OnMyCard,
//                    allUserCards = listOf(
//                        CardModelUi(
//                            domainModel = CardModelDomain(
//                                id = "",
//                                currencyCode = "bbyn",
//                                reserveCurrencyCode = "byn",
//                                amountInReserveCurrency = 0.0,
//                                amount = 0.0,
//                                owner = OwnerModelDomain(
//                                    id = "",
//                                    name = "",
//                                    surname = ""
//                                ),
//                                expireDate = Calendar.getInstance().time,
//                                number = "1111111111111111",
//                                cvv = "",
//                                type = CardType.Dedut,
//                                system = CardSystem.Visa,
//                                name = "odsklmclksd",
//                                erip = "ncjasncjasnxk",
//                                iban = "21912029nsknac",
//                                bankIdCode = "dsjkcnkjsndc"
//                            )
//                        )
//                    )
//                ),
//                proceedIntent = {}
//            )

//            PaymentProcessScreenUi(
//                state = PaymentProcessScreenState(
//                    paymentType = PaymentType.OnMyCard,
//                    allUserCards = listOf(
//                        CardModelUi(
//                            domainModel = CardModelDomain(
//                                id = "",
//                                currencyCode = "bbyn",
//                                reserveCurrencyCode = "byn",
//                                amountInReserveCurrency = 0.0,
//                                amount = 0.0,
//                                owner = OwnerModelDomain(
//                                    id = "",
//                                    name = "",
//                                    surname = ""
//                                ),
//                                expireDate = Calendar.getInstance().time,
//                                number = "1111111111111111",
//                                cvv = "",
//                                type = CardType.Dedut,
//                                system = CardSystem.Visa,
//                                name = "odsklmclksd",
//                                erip = "ncjasncjasnxk",
//                                iban = "21912029nsknac",
//                                bankIdCode = "dsjkcnkjsndc"
//                            )
//                        )
//                    ),
//                    isPaymentSucceeded = true
//                ),
//                proceedIntent = {}
//            )

            PaymentProcessScreenUi(
                state = PaymentProcessScreenState(
                    paymentType = PaymentType.ByCardNumber,
                    allUserCards = listOf(
                        CardModelUi(
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
                                name = "odsklmclksd",
                                erip = "ncjasncjasnxk",
                                iban = "21912029nsknac",
                                bankIdCode = "dsjkcnkjsndc"
                            )
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
//            PaymentProcessScreenUi(
//                state = PaymentProcessScreenState(
//                    paymentType = PaymentType.OnMyCard
//                ),
//                proceedIntent = {}
//            )

            PaymentProcessScreenUi(
                state = PaymentProcessScreenState(
                    paymentType = PaymentType.OnMyCard,
                    allUserCards = listOf(
                        CardModelUi(
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
                                name = "odsklmclksd",
                                erip = "ncjasncjasnxk",
                                iban = "21912029nsknac",
                                bankIdCode = "dsjkcnkjsndc"
                            )
                        )
                    )
                ),
                proceedIntent = {}
            )

//            PaymentProcessScreenUi(
//                state = PaymentProcessScreenState(
//                    paymentType = PaymentType.OnMyCard,
//                    allUserCards = listOf(
//                        CardModelUi(
//                            domainModel = CardModelDomain(
//                                id = "",
//                                currencyCode = "bbyn",
//                                reserveCurrencyCode = "byn",
//                                amountInReserveCurrency = 0.0,
//                                amount = 0.0,
//                                owner = OwnerModelDomain(
//                                    id = "",
//                                    name = "",
//                                    surname = ""
//                                ),
//                                expireDate = Calendar.getInstance().time,
//                                number = "1111111111111111",
//                                cvv = "",
//                                type = CardType.Dedut,
//                                system = CardSystem.Visa,
//                                name = "odsklmclksd",
//                                erip = "ncjasncjasnxk",
//                                iban = "21912029nsknac",
//                                bankIdCode = "dsjkcnkjsndc"
//                            )
//                        )
//                    ),
//                    isPaymentSucceeded = true
//                ),
//                proceedIntent = {}
//            )

//            PaymentProcessScreenUi(
//                state = PaymentProcessScreenState(
//                    paymentType = PaymentType.ByCardNumber,
//                    allUserCards = listOf(
//                        CardModelUi(
//                            domainModel = CardModelDomain(
//                                id = "",
//                                currencyCode = "bbyn",
//                                reserveCurrencyCode = "byn",
//                                amountInReserveCurrency = 0.0,
//                                amount = 0.0,
//                                owner = OwnerModelDomain(
//                                    id = "",
//                                    name = "",
//                                    surname = ""
//                                ),
//                                expireDate = Calendar.getInstance().time,
//                                number = "1111111111111111",
//                                cvv = "",
//                                type = CardType.Dedut,
//                                system = CardSystem.Visa,
//                                name = "odsklmclksd",
//                                erip = "ncjasncjasnxk",
//                                iban = "21912029nsknac",
//                                bankIdCode = "dsjkcnkjsndc"
//                            )
//                        )
//                    )
//                ),
//                proceedIntent = {}
//            )
        }
    }
}