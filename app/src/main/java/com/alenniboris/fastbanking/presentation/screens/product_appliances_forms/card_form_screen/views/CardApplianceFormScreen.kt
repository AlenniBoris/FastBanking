package com.alenniboris.fastbanking.presentation.screens.product_appliances_forms.card_form_screen.views

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.alenniboris.fastbanking.R
import com.alenniboris.fastbanking.domain.model.appliances.CardDetailedApplianceType
import com.alenniboris.fastbanking.domain.model.appliances.OfficeModelDomain
import com.alenniboris.fastbanking.domain.model.currency.CurrencyModelDomain
import com.alenniboris.fastbanking.presentation.mappers.toUiString
import com.alenniboris.fastbanking.presentation.model.bank_product.toUiPicture
import com.alenniboris.fastbanking.presentation.model.bank_product.toUiString
import com.alenniboris.fastbanking.presentation.screens.product_appliances_forms.ApplianceFormOfficeSelectionScreen
import com.alenniboris.fastbanking.presentation.uikit.views.AppProcessProceedButton
import com.alenniboris.fastbanking.presentation.screens.product_appliances_forms.ApplianceFormProcessFinalScreen
import com.alenniboris.fastbanking.presentation.screens.product_appliances_forms.ProductApplianceFormScreenProcess
import com.alenniboris.fastbanking.presentation.screens.product_appliances_forms.card_form_screen.CardApplianceFormScreenState
import com.alenniboris.fastbanking.presentation.screens.product_appliances_forms.card_form_screen.CardApplianceFormScreenViewModel
import com.alenniboris.fastbanking.presentation.screens.product_appliances_forms.card_form_screen.ICardApplianceFormScreenEvent
import com.alenniboris.fastbanking.presentation.screens.product_appliances_forms.card_form_screen.ICardApplianceFormScreenIntent
import com.alenniboris.fastbanking.presentation.uikit.theme.FastBankingTheme
import com.alenniboris.fastbanking.presentation.uikit.theme.ProductApplianceFormScreenBottomButtonInnerPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.ProductApplianceFormScreenFillingDataContainerOuterPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.ProductApplianceFormScreenProcessProgressBarPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.ProductApplianceFormScreenSheetItemFontSize
import com.alenniboris.fastbanking.presentation.uikit.theme.ProductApplianceFormScreenSheetItemPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.ProductApplianceScreenOfficeSelectionContainerOuterPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.TopBarPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.appColor
import com.alenniboris.fastbanking.presentation.uikit.theme.appTopBarElementsColor
import com.alenniboris.fastbanking.presentation.uikit.theme.bodyStyle
import com.alenniboris.fastbanking.presentation.uikit.theme.mainScreenItemColor
import com.alenniboris.fastbanking.presentation.uikit.theme.productApplianceFormCheckboxCheckedTrackColor
import com.alenniboris.fastbanking.presentation.uikit.theme.productApplianceFormCheckboxUncheckedTrackColor
import com.alenniboris.fastbanking.presentation.uikit.theme.productApplianceFormScreenCheckboxUncheckedContentColor
import com.alenniboris.fastbanking.presentation.uikit.values.CardApplianceFormScreenRoute
import com.alenniboris.fastbanking.presentation.uikit.views.AppFilter
import com.alenniboris.fastbanking.presentation.uikit.views.AppProcessProgressBar
import com.alenniboris.fastbanking.presentation.uikit.views.AppTextSectionWithSwitcher
import com.alenniboris.fastbanking.presentation.uikit.views.AppTopBar
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
@Destination(route = CardApplianceFormScreenRoute)
fun CardApplianceFormScreen(
    detailedApplianceType: CardDetailedApplianceType,
    navigator: DestinationsNavigator
) {

    val viewModel = koinViewModel<CardApplianceFormScreenViewModel>() {
        parametersOf(detailedApplianceType)
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
            event.filterIsInstance<ICardApplianceFormScreenEvent.NavigateBack>().collect {
                navigator.popBackStack()
            }
        }

        launch {
            event.filterIsInstance<ICardApplianceFormScreenEvent.ShowToastMessage>()
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

    CardApplianceFormScreenUi(
        state = state,
        proceedIntent = proceedIntent
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CardApplianceFormScreenUi(
    state: CardApplianceFormScreenState,
    proceedIntent: (ICardApplianceFormScreenIntent) -> Unit
) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(appColor)
    ) {

        if (state.isCardTypeSheetVisible) {
            AppFilter(
                elements = state.allCardTypes,
                onDismiss = {
                    proceedIntent(
                        ICardApplianceFormScreenIntent.UpdateCardTypeSheetVisibility
                    )
                },
                itemContent = { type ->
                    AppTextSectionWithSwitcher(
                        modifier = Modifier
                            .padding(ProductApplianceFormScreenSheetItemPadding)
                            .fillMaxWidth(),
                        text = stringResource(type.toUiString()),
                        textStyle = bodyStyle.copy(
                            color = appTopBarElementsColor,
                            fontWeight = FontWeight.Normal,
                            fontSize = ProductApplianceFormScreenSheetItemFontSize
                        ),
                        isChecked = type == state.selectedCardType,
                        onCheckedChanged = {
                            proceedIntent(
                                ICardApplianceFormScreenIntent.UpdateSelectedCardType(
                                    type
                                )
                            )
                        },
                        switchColors = SwitchDefaults.colors().copy(
                            uncheckedTrackColor = productApplianceFormCheckboxUncheckedTrackColor,
                            uncheckedBorderColor = productApplianceFormScreenCheckboxUncheckedContentColor,
                            uncheckedIconColor = productApplianceFormScreenCheckboxUncheckedContentColor,
                            uncheckedThumbColor = productApplianceFormScreenCheckboxUncheckedContentColor,
                            checkedTrackColor = productApplianceFormCheckboxCheckedTrackColor
                        )
                    )
                }
            )
        }

        if (state.isCardSystemSheetVisible) {
            AppFilter(
                elements = state.allCardSystems,
                onDismiss = {
                    proceedIntent(
                        ICardApplianceFormScreenIntent.UpdateCardTypeSheetVisibility
                    )
                },
                itemContent = { system ->
                    Row(
                        modifier = Modifier
                            .padding(ProductApplianceFormScreenSheetItemPadding)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {

                        Image(
                            painter = painterResource(system.toUiPicture()),
                            contentDescription = stringResource(system.toUiString())
                        )

                        Text(
                            text = stringResource(system.toUiString()),
                            style = bodyStyle.copy(
                                color = appTopBarElementsColor,
                                fontWeight = FontWeight.Normal,
                                fontSize = ProductApplianceFormScreenSheetItemFontSize
                            )
                        )
                    }
                }
            )
        }

        Column {

            if (state.currentProcess != ProductApplianceFormScreenProcess.SUCCEEDED) {
                AppTopBar(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(TopBarPadding),
                    leftBtnPainter = painterResource(R.drawable.back_icon),
                    headerTextString = stringResource(
                        state.applianceType.toUiString()
                    ),
                    textAlignment = Alignment.CenterEnd,
                    onLeftBtnClicked = {
                        proceedIntent(
                            ICardApplianceFormScreenIntent.ProceedBackwardAction
                        )
                    }
                )
            }

            AppProcessProgressBar(
                modifier = Modifier
                    .padding(ProductApplianceFormScreenProcessProgressBarPadding)
                    .fillMaxWidth(),
                currentProcess = state.currentProcess,
                allProcesses = state.allProcessParts
            )

            when (state.currentProcess) {
                ProductApplianceFormScreenProcess.FILLING_DATA -> {
                    CardFormFillingDataUi(
                        modifier = Modifier
                            .padding(
                                ProductApplianceFormScreenFillingDataContainerOuterPadding
                            )
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState()),
                        cardIssuingTypes = state.cardIssuingTypes,
                        currentCardIssueType = state.currentCardIssueType,
                        isAllSupportedCurrenciesLoading = state.isAllCurrenciesLoading,
                        allSupportedCurrencies = state.supportedCurrencies,
                        currentSelectedCurrency = state.selectedCurrency,
                        isSalaryCard = state.isSalaryCard,
                        isPolicyAccepted = state.isPolicyAccepted,
                        proceedIntent = proceedIntent
                    )
                }

                ProductApplianceFormScreenProcess.SELECTING_OFFICE -> {
                    ApplianceFormOfficeSelectionScreen(
                        modifier = Modifier
                            .padding(
                                ProductApplianceScreenOfficeSelectionContainerOuterPadding
                            ),
                        items = state.allBankOffices,
                        currentItem = state.selectedBankOffice,
                        onItemClicked = { office ->
                            proceedIntent(
                                ICardApplianceFormScreenIntent.UpdateSelectedOffice(office)
                            )
                        }
                    )
                }

                ProductApplianceFormScreenProcess.SUCCEEDED -> {
                    ApplianceFormProcessFinalScreen(
                        modifier = Modifier.fillMaxSize(),
                        isLoading = state.isApplianceProceeding,
                        textColor = appTopBarElementsColor
                    )
                }
            }
        }

        AppProcessProceedButton(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .background(mainScreenItemColor)
                .padding(ProductApplianceFormScreenBottomButtonInnerPadding),
            onClick = {
                proceedIntent(
                    ICardApplianceFormScreenIntent.ProceedForwardAction
                )
            }
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
            CardApplianceFormScreenUi(
                state = CardApplianceFormScreenState(
                    applianceType = CardDetailedApplianceType.ISSUE_VIRTUAL_CARD,
                    supportedCurrencies = listOf(
                        CurrencyModelDomain(
                            code = "us",
                            fullName = "usdt"
                        ),
                        CurrencyModelDomain(
                            code = "byn",
                            fullName = "usdt"
                        ),
                        CurrencyModelDomain(
                            code = "rub",
                            fullName = "usdt"
                        )
                    ),
                    currentProcess = ProductApplianceFormScreenProcess.SUCCEEDED,
                    allBankOffices = listOf(
                        OfficeModelDomain(
                            address = "sdasdasd",
                            workingTime = "11212"
                        ),
                        OfficeModelDomain(
                            address = "dskamsakmasd",
                            workingTime = "1212"
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
            CardApplianceFormScreenUi(
                state = CardApplianceFormScreenState(
                    applianceType = CardDetailedApplianceType.ISSUE_VIRTUAL_CARD,
                    supportedCurrencies = listOf(
                        CurrencyModelDomain(
                            code = "us",
                            fullName = "usdt"
                        ),
                        CurrencyModelDomain(
                            code = "byn",
                            fullName = "usdt"
                        ),
                        CurrencyModelDomain(
                            code = "rub",
                            fullName = "usdt"
                        )
                    )
                ),
                proceedIntent = {}
            )
        }
    }
}