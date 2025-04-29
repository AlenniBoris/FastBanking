package com.alenniboris.fastbanking.presentation.screens.product_appliances_forms.deposit_form_screen.views

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.SwitchDefaults
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
import com.alenniboris.fastbanking.domain.model.appliances.DepositDetailedApplianceType
import com.alenniboris.fastbanking.domain.model.appliances.OfficeModelDomain
import com.alenniboris.fastbanking.domain.model.currency.CurrencyModelDomain
import com.alenniboris.fastbanking.presentation.mappers.toUiString
import com.alenniboris.fastbanking.presentation.screens.product_appliances_forms.ApplianceFormOfficeSelectionScreen
import com.alenniboris.fastbanking.presentation.screens.product_appliances_forms.ApplianceFormProceedButton
import com.alenniboris.fastbanking.presentation.screens.product_appliances_forms.ApplianceFormProcessFinalScreen
import com.alenniboris.fastbanking.presentation.screens.product_appliances_forms.ProductApplianceFormScreenProcess
import com.alenniboris.fastbanking.presentation.screens.product_appliances_forms.deposit_form_screen.DepositApplianceFormScreenState
import com.alenniboris.fastbanking.presentation.screens.product_appliances_forms.deposit_form_screen.DepositApplianceFormScreenViewModel
import com.alenniboris.fastbanking.presentation.screens.product_appliances_forms.deposit_form_screen.IDepositApplianceFormScreenEvent
import com.alenniboris.fastbanking.presentation.screens.product_appliances_forms.deposit_form_screen.IDepositApplianceFormScreenIntent
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
import com.alenniboris.fastbanking.presentation.uikit.values.DepositApplianceFormScreenRoute
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
@Destination(route = DepositApplianceFormScreenRoute)
fun DepositApplianceFormScreen(
    detailedApplianceType: DepositDetailedApplianceType,
    navigator: DestinationsNavigator
) {

    val viewModel = koinViewModel<DepositApplianceFormScreenViewModel>() {
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
            event.filterIsInstance<IDepositApplianceFormScreenEvent.NavigateBack>().collect {
                navigator.popBackStack()
            }
        }

        launch {
            event.filterIsInstance<IDepositApplianceFormScreenEvent.ShowToastMessage>()
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

    DepositApplianceFormScreenUi(
        state = state,
        proceedIntent = proceedIntent
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DepositApplianceFormScreenUi(
    state: DepositApplianceFormScreenState,
    proceedIntent: (IDepositApplianceFormScreenIntent) -> Unit
) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(appColor)
    ) {

        if (state.isPeriodsSheetVisible) {
            AppFilter(
                elements = state.allPossiblePeriods,
                onDismiss = {
                    proceedIntent(
                        IDepositApplianceFormScreenIntent.UpdatePeriodSheetVisibility
                    )
                },
                itemContent = { period ->
                    AppTextSectionWithSwitcher(
                        modifier = Modifier
                            .padding(ProductApplianceFormScreenSheetItemPadding)
                            .fillMaxWidth(),
                        text = period.toString(),
                        textStyle = bodyStyle.copy(
                            color = appTopBarElementsColor,
                            fontWeight = FontWeight.Normal,
                            fontSize = ProductApplianceFormScreenSheetItemFontSize
                        ),
                        isChecked = period == state.periodTime,
                        onCheckedChanged = {
                            proceedIntent(
                                IDepositApplianceFormScreenIntent.UpdateSelectedPeriod(period)
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

        Column {

            if(state.currentProcess != ProductApplianceFormScreenProcess.SUCCEEDED){
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
                            IDepositApplianceFormScreenIntent.ProceedBackwardAction
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
                    DepositFormFillingDataUi(
                        modifier = Modifier
                            .padding(
                                ProductApplianceFormScreenFillingDataContainerOuterPadding
                            )
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState()),
                        isAllSupportedCurrenciesLoading = state.isAllCurrenciesLoading,
                        allSupportedCurrencies = state.supportedCurrencies,
                        currentSelectedCurrency = state.selectedCurrency,
                        minimalContributionText = state.minimumContribution,
                        procentText = state.procentText,
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
                                IDepositApplianceFormScreenIntent.UpdateSelectedOffice(office)
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

        ApplianceFormProceedButton(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .background(mainScreenItemColor)
                .padding(ProductApplianceFormScreenBottomButtonInnerPadding),
            onClick = {
                proceedIntent(
                    IDepositApplianceFormScreenIntent.ProceedForwardAction
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
            DepositApplianceFormScreenUi(
                state = DepositApplianceFormScreenState(
                    applianceType = DepositDetailedApplianceType.URGENT_DEPOSIT,
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
            DepositApplianceFormScreenUi(
                state = DepositApplianceFormScreenState(
                    applianceType = DepositDetailedApplianceType.URGENT_DEPOSIT,
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