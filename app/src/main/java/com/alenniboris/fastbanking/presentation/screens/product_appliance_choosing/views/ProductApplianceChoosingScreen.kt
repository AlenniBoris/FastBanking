package com.alenniboris.fastbanking.presentation.screens.product_appliance_choosing.views

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.alenniboris.fastbanking.R
import com.alenniboris.fastbanking.domain.model.appliances.CardDetailedApplianceType
import com.alenniboris.fastbanking.domain.model.appliances.CreditDetailedApplianceType
import com.alenniboris.fastbanking.domain.model.appliances.DepositDetailedApplianceType
import com.alenniboris.fastbanking.presentation.mappers.toUiString
import com.alenniboris.fastbanking.presentation.screens.destinations.CardApplianceFormScreenDestination
import com.alenniboris.fastbanking.presentation.screens.destinations.CreditApplianceFormScreenDestination
import com.alenniboris.fastbanking.presentation.screens.destinations.DepositApplianceFormScreenDestination
import com.alenniboris.fastbanking.presentation.screens.product_appliance_choosing.IProductApplianceChoosingScreenEvent
import com.alenniboris.fastbanking.presentation.screens.product_appliance_choosing.IProductApplianceChoosingScreenIntent
import com.alenniboris.fastbanking.presentation.screens.product_appliance_choosing.ProductApplianceChoosingScreenState
import com.alenniboris.fastbanking.presentation.screens.product_appliance_choosing.ProductApplianceChoosingScreenViewModel
import com.alenniboris.fastbanking.presentation.uikit.theme.FastBankingTheme
import com.alenniboris.fastbanking.presentation.uikit.theme.ProductApplianceChoosingScreenContentPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.ProductApplianceChoosingScreenItemPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.ProductApplianceChoosingScreenItemSpacerHeight
import com.alenniboris.fastbanking.presentation.uikit.theme.ProductApplianceChoosingScreenItemSpacerPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.ProductApplianceChoosingScreenItemTextSize
import com.alenniboris.fastbanking.presentation.uikit.theme.TopBarPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.appColor
import com.alenniboris.fastbanking.presentation.uikit.theme.appTopBarElementsColor
import com.alenniboris.fastbanking.presentation.uikit.theme.bodyStyle
import com.alenniboris.fastbanking.presentation.uikit.values.BankProduct
import com.alenniboris.fastbanking.presentation.uikit.values.ProductApplianceChoosingScreenRoute
import com.alenniboris.fastbanking.presentation.uikit.values.toUiText
import com.alenniboris.fastbanking.presentation.uikit.views.AppProgressBar
import com.alenniboris.fastbanking.presentation.uikit.views.AppTopBar
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Destination(route = ProductApplianceChoosingScreenRoute)
@Composable
fun ProductApplianceChoosingScreen(
    navigator: DestinationsNavigator,
    bankProduct: BankProduct
) {

    val viewModel =
        koinViewModel<ProductApplianceChoosingScreenViewModel> {
            parametersOf(bankProduct)
        }
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
            event.filterIsInstance<IProductApplianceChoosingScreenEvent.NavigateBack>().collect {
                navigator.popBackStack()
            }
        }

        launch {
            event.filterIsInstance<IProductApplianceChoosingScreenEvent.ShowToastMessage>()
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
            event.filterIsInstance<IProductApplianceChoosingScreenEvent.OpenCardApplianceScreenForm>()
                .collect { coming ->
                    navigator.navigate(
                        CardApplianceFormScreenDestination(
                            detailedApplianceType = coming.option
                        )
                    )
                }
        }

        launch {
            event.filterIsInstance<IProductApplianceChoosingScreenEvent.OpenCreditApplianceScreenForm>()
                .collect { coming ->
                    navigator.navigate(
                        CreditApplianceFormScreenDestination(
                            detailedApplianceType = coming.option
                        )
                    )
                }
        }

        launch {
            event.filterIsInstance<IProductApplianceChoosingScreenEvent.OpenDepositApplianceScreenForm>()
                .collect { coming ->
                    navigator.navigate(
                        DepositApplianceFormScreenDestination(
                            detailedApplianceType = coming.option
                        )
                    )
                }
        }
    }

    ProductApplianceChoosingScreenUi(
        state = state,
        proceedIntent = proceedIntent
    )
}

@Composable
private fun ProductApplianceChoosingScreenUi(
    state: ProductApplianceChoosingScreenState,
    proceedIntent: (IProductApplianceChoosingScreenIntent) -> Unit
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
                proceedIntent(IProductApplianceChoosingScreenIntent.NavigateBack)
            },
            headerTextString = state.currentProduct?.let { stringResource(it.toUiText()) } ?: ""
        )

        when {
            state.currentProduct == null -> {
                AppProgressBar(
                    modifier = Modifier.fillMaxSize()
                )
            }

            else -> {
                LazyColumn(
                    modifier = Modifier.padding(ProductApplianceChoosingScreenContentPadding)
                ) {
                    items(
                        when (state.currentProduct) {
                            BankProduct.CARD -> state.cardOptions
                            BankProduct.CREDIT -> state.creditOptions
                            BankProduct.ACCOUNTS_AND_DEPOSITS -> state.depositOptions
                        }
                    ) { item ->

                        Column(
                            modifier = Modifier
                                .padding(ProductApplianceChoosingScreenItemPadding)
                                .fillMaxWidth()
                                .clickable {
                                    proceedIntent(
                                        when (state.currentProduct) {
                                            BankProduct.CARD -> {
                                                IProductApplianceChoosingScreenIntent.ProceedCardOption(
                                                    item as CardDetailedApplianceType
                                                )
                                            }

                                            BankProduct.CREDIT -> {
                                                IProductApplianceChoosingScreenIntent.ProceedCreditOption(
                                                    item as CreditDetailedApplianceType
                                                )
                                            }

                                            BankProduct.ACCOUNTS_AND_DEPOSITS -> {
                                                IProductApplianceChoosingScreenIntent.ProceedDepositOption(
                                                    item as DepositDetailedApplianceType
                                                )
                                            }
                                        }

                                    )
                                }
                        ) {

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {

                                Text(
                                    text = when (state.currentProduct) {
                                        BankProduct.CARD ->
                                            stringResource(
                                                (item as? CardDetailedApplianceType)?.toUiString()
                                                    ?: R.string.undefined_text
                                            )

                                        BankProduct.CREDIT ->
                                            stringResource(
                                                (item as? CreditDetailedApplianceType)?.toUiString()
                                                    ?: R.string.undefined_text
                                            )

                                        BankProduct.ACCOUNTS_AND_DEPOSITS ->
                                            stringResource(
                                                (item as? DepositDetailedApplianceType)?.toUiString()
                                                    ?: R.string.undefined_text
                                            )

                                    },
                                    style = bodyStyle.copy(
                                        color = appTopBarElementsColor,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = ProductApplianceChoosingScreenItemTextSize
                                    )
                                )

                                Icon(
                                    painter = painterResource(R.drawable.forward_icon),
                                    contentDescription = stringResource(R.string.forward_icon_description),
                                    tint = appTopBarElementsColor
                                )
                            }

                            Spacer(
                                modifier = Modifier
                                    .padding(ProductApplianceChoosingScreenItemSpacerPadding)
                                    .fillParentMaxWidth()
                                    .height(ProductApplianceChoosingScreenItemSpacerHeight)
                                    .background(appTopBarElementsColor)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun LightTheme() {
    FastBankingTheme(
        darkTheme = false
    ) {
        Surface {
//            ProductApplianceChoosingScreenUi(
//                state = ProductApplianceChoosingScreenState(),
//                proceedIntent = {}
//            )

//            ProductApplianceChoosingScreenUi(
//                state = ProductApplianceChoosingScreenState(
//                    currentProduct = BankProduct.CARD
//                ),
//                proceedIntent = {}
//            )

            ProductApplianceChoosingScreenUi(
                state = ProductApplianceChoosingScreenState(
                    currentProduct = BankProduct.CREDIT
                ),
                proceedIntent = {}
            )

//            ProductApplianceChoosingScreenUi(
//                state = ProductApplianceChoosingScreenState(
//                    currentProduct = BankProduct.DEPOSITS_AND_ACCOUNTS
//                ),
//                proceedIntent = {}
//            )
        }
    }
}

@Preview
@Composable
private fun DarkTheme() {
    FastBankingTheme(
        darkTheme = true
    ) {
        Surface {
//            ProductApplianceChoosingScreenUi(
//                state = ProductApplianceChoosingScreenState(),
//                proceedIntent = {}
//            )

            ProductApplianceChoosingScreenUi(
                state = ProductApplianceChoosingScreenState(
                    currentProduct = BankProduct.CARD
                ),
                proceedIntent = {}
            )

//            ProductApplianceChoosingScreenUi(
//                state = ProductApplianceChoosingScreenState(
//                    currentProduct = BankProduct.CREDIT
//                ),
//                proceedIntent = {}
//            )

//            ProductApplianceChoosingScreenUi(
//                state = ProductApplianceChoosingScreenState(
//                    currentProduct = BankProduct.DEPOSITS_AND_ACCOUNTS
//                ),
//                proceedIntent = {}
//            )
        }
    }
}