package com.alenniboris.fastbanking.presentation.screens.product_appliances_forms.credit_form_screen.views

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.alenniboris.fastbanking.R
import com.alenniboris.fastbanking.domain.model.appliances.CreditDetailedApplianceType
import com.alenniboris.fastbanking.domain.model.appliances.OfficeModelDomain
import com.alenniboris.fastbanking.domain.model.currency.CurrencyModelDomain
import com.alenniboris.fastbanking.domain.model.user.UserGender
import com.alenniboris.fastbanking.domain.model.user.UserModelDomain
import com.alenniboris.fastbanking.presentation.mappers.toUiString
import com.alenniboris.fastbanking.presentation.model.user.toModelUi
import com.alenniboris.fastbanking.presentation.screens.product_appliances_forms.ApplianceFormOfficeSelectionScreen
import com.alenniboris.fastbanking.presentation.uikit.views.AppProcessProceedButton
import com.alenniboris.fastbanking.presentation.screens.product_appliances_forms.ApplianceFormProcessFinalScreen
import com.alenniboris.fastbanking.presentation.screens.product_appliances_forms.ProductApplianceFormScreenProcess
import com.alenniboris.fastbanking.presentation.screens.product_appliances_forms.credit_form_screen.CreditApplianceFormScreenState
import com.alenniboris.fastbanking.presentation.screens.product_appliances_forms.credit_form_screen.CreditApplianceFormScreenViewModel
import com.alenniboris.fastbanking.presentation.screens.product_appliances_forms.credit_form_screen.ICreditApplianceFormScreenEvent
import com.alenniboris.fastbanking.presentation.screens.product_appliances_forms.credit_form_screen.ICreditApplianceFormScreenIntent
import com.alenniboris.fastbanking.presentation.uikit.theme.ProductApplianceFormScreenFillingDataContainerOuterPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.ProductApplianceFormScreenProcessProgressBarPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.FastBankingTheme
import com.alenniboris.fastbanking.presentation.uikit.theme.ProductApplianceFormScreenBottomButtonInnerPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.ProductApplianceFormScreenContentPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.ProductApplianceFormScreenTextFontSize
import com.alenniboris.fastbanking.presentation.uikit.theme.ProductApplianceScreenOfficeSelectionContainerOuterPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.TopBarPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.appColor
import com.alenniboris.fastbanking.presentation.uikit.theme.appTopBarElementsColor
import com.alenniboris.fastbanking.presentation.uikit.theme.bodyStyle
import com.alenniboris.fastbanking.presentation.uikit.theme.mainScreenItemColor
import com.alenniboris.fastbanking.presentation.uikit.values.CreditApplianceFormScreenRoute
import com.alenniboris.fastbanking.presentation.uikit.views.AppProcessProgressBar
import com.alenniboris.fastbanking.presentation.uikit.views.AppTopBar
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf
import java.util.Calendar

@Composable
@Destination(route = CreditApplianceFormScreenRoute)
fun CreditApplianceFormScreen(
    detailedApplianceType: CreditDetailedApplianceType,
    navigator: DestinationsNavigator
) {

    val viewModel = koinViewModel<CreditApplianceFormScreenViewModel>() {
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
            event.filterIsInstance<ICreditApplianceFormScreenEvent.NavigateBack>().collect {
                navigator.popBackStack()
            }
        }

        launch {
            event.filterIsInstance<ICreditApplianceFormScreenEvent.ShowToastMessage>()
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

    CreditApplianceFormScreenUi(
        state = state,
        proceedIntent = proceedIntent
    )
}

@Composable
private fun CreditApplianceFormScreenUi(
    state: CreditApplianceFormScreenState,
    proceedIntent: (ICreditApplianceFormScreenIntent) -> Unit
) {

    state.currentUser?.let { currentUser ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(appColor)
        ) {

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
                                ICreditApplianceFormScreenIntent.ProceedBackwardAction
                            )
                        }
                    )
                }

                when {

                    !currentUser.isCreditsAllowed -> {

                        Column(
                            modifier = Modifier
                                .padding(ProductApplianceFormScreenContentPadding)
                                .fillMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {

                            Text(
                                text = stringResource(R.string.credit_not_allowed_text),
                                style = bodyStyle.copy(
                                    color = appTopBarElementsColor,
                                    fontWeight = FontWeight.Normal,
                                    fontSize = ProductApplianceFormScreenTextFontSize,
                                    textAlign = TextAlign.Center
                                )
                            )
                        }
                    }

                    else -> {
                        AppProcessProgressBar(
                            modifier = Modifier
                                .padding(ProductApplianceFormScreenProcessProgressBarPadding)
                                .fillMaxWidth(),
                            currentProcess = state.currentProcess,
                            allProcesses = state.allProcessParts
                        )

                        when (state.currentProcess) {
                            ProductApplianceFormScreenProcess.FILLING_DATA -> {
                                CreditFormFillingDataUi(
                                    modifier = Modifier
                                        .padding(
                                            ProductApplianceFormScreenFillingDataContainerOuterPadding
                                        )
                                        .fillMaxSize()
                                        .verticalScroll(rememberScrollState()),
                                    isAllSupportedCurrenciesLoading = state.isAllCurrenciesLoading,
                                    allSupportedCurrencies = state.supportedCurrencies,
                                    currentSelectedCurrency = state.selectedCurrency,
                                    isPolicyAccepted = state.isPolicyAccepted,
                                    creditGoal = state.creditGoal,
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
                                            ICreditApplianceFormScreenIntent.UpdateSelectedOffice(
                                                office
                                            )
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
                }
            }


            if (currentUser.isCreditsAllowed) {

                AppProcessProceedButton(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .fillMaxWidth()
                        .background(mainScreenItemColor)
                        .padding(ProductApplianceFormScreenBottomButtonInnerPadding),
                    onClick = {
                        proceedIntent(
                            ICreditApplianceFormScreenIntent.ProceedForwardAction
                        )
                    }
                )
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
            CreditApplianceFormScreenUi(
                state = CreditApplianceFormScreenState(
                    currentUser = UserModelDomain(
                        id = "111",
                        password = "111",
                        name = "botis",
                        surname = "botissss",
                        email = "sddsd@dsdsds",
                        age = 1,
                        gender = UserGender.Male,
                        country = "bel",
                        accountId = "aaa",
                        hasOnlineBanking = true,
                        phoneNumber = "1111111",
                        job = "asasas",
                        dateOfBirth = Calendar.getInstance().time
                    ).toModelUi(),
                    applianceType = CreditDetailedApplianceType.BBANK_CREDIT,
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
                    currentProcess = ProductApplianceFormScreenProcess.FILLING_DATA,
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
            Surface {
                CreditApplianceFormScreenUi(
                    state = CreditApplianceFormScreenState(
                        currentUser = UserModelDomain(
                            id = "111",
                            password = "111",
                            name = "botis",
                            surname = "botissss",
                            email = "sddsd@dsdsds",
                            age = 23,
                            gender = UserGender.Male,
                            country = "bel",
                            accountId = "aaa",
                            hasOnlineBanking = true,
                            phoneNumber = "1111111",
                            job = "asasas",
                            dateOfBirth = Calendar.getInstance().time
                        ).toModelUi(),
                        applianceType = CreditDetailedApplianceType.BBANK_CREDIT,
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
                        currentProcess = ProductApplianceFormScreenProcess.FILLING_DATA,
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
}