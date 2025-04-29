package com.alenniboris.fastbanking.presentation.screens.user_appliances.views

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.alenniboris.fastbanking.R
import com.alenniboris.fastbanking.domain.model.appliances.ApplianceStatus
import com.alenniboris.fastbanking.domain.model.appliances.CardApplianceModelDomain
import com.alenniboris.fastbanking.domain.model.appliances.CardDetailedApplianceType
import com.alenniboris.fastbanking.domain.model.appliances.CreditApplianceModelDomain
import com.alenniboris.fastbanking.domain.model.appliances.CreditDetailedApplianceType
import com.alenniboris.fastbanking.domain.model.appliances.DepositApplianceModelDomain
import com.alenniboris.fastbanking.domain.model.appliances.DepositDetailedApplianceType
import com.alenniboris.fastbanking.domain.model.appliances.OfficeModelDomain
import com.alenniboris.fastbanking.domain.model.card.CardSystem
import com.alenniboris.fastbanking.domain.model.card.CardType
import com.alenniboris.fastbanking.presentation.model.appliance.ProductApplianceModelUi
import com.alenniboris.fastbanking.presentation.model.appliance.toProductApplianceType
import com.alenniboris.fastbanking.presentation.screens.destinations.ProductApplianceDetailsScreenDestination
import com.alenniboris.fastbanking.presentation.screens.user_appliances.IUserAppliancesScreenEvent
import com.alenniboris.fastbanking.presentation.screens.user_appliances.IUserAppliancesScreenIntent
import com.alenniboris.fastbanking.presentation.screens.user_appliances.UserAppliancesScreenState
import com.alenniboris.fastbanking.presentation.screens.user_appliances.UserAppliancesScreenViewModel
import com.alenniboris.fastbanking.presentation.uikit.theme.FastBankingTheme
import com.alenniboris.fastbanking.presentation.uikit.theme.TopBarPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.UserAppliancesScreenCardColumnStartPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.UserAppliancesScreenCardMainTextSize
import com.alenniboris.fastbanking.presentation.uikit.theme.UserAppliancesScreenCardPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.UserAppliancesScreenCardSpacerHeight
import com.alenniboris.fastbanking.presentation.uikit.theme.UserAppliancesScreenCardTextSize
import com.alenniboris.fastbanking.presentation.uikit.theme.UserAppliancesScreenCardTextTopPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.UserAppliancesScreenContentPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.appColor
import com.alenniboris.fastbanking.presentation.uikit.theme.appTopBarElementsColor
import com.alenniboris.fastbanking.presentation.uikit.theme.bodyStyle
import com.alenniboris.fastbanking.presentation.uikit.values.UserAppliancesScreenRoute
import com.alenniboris.fastbanking.presentation.uikit.views.AppEmptyScreen
import com.alenniboris.fastbanking.presentation.uikit.views.AppProgressBar
import com.alenniboris.fastbanking.presentation.uikit.views.AppTopBar
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import java.util.Calendar

@Destination(route = UserAppliancesScreenRoute)
@Composable
fun UserAppliancesScreen(
    navigator: DestinationsNavigator
) {

    val viewModel = koinViewModel<UserAppliancesScreenViewModel>()
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
            event.filterIsInstance<IUserAppliancesScreenEvent.NavigateBack>().collect {
                navigator.popBackStack()
            }
        }

        launch {
            event.filterIsInstance<IUserAppliancesScreenEvent.ShowToastMessage>()
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
            event.filterIsInstance<IUserAppliancesScreenEvent.OpenDetailsPage>().collect { coming ->
                navigator.navigate(
                    ProductApplianceDetailsScreenDestination(
                        applianceId = coming.appliance.domainModel.id,
                        applianceType = coming.appliance.toProductApplianceType()
                    )
                )
            }
        }
    }

    UserAppliancesScreenUi(
        state = state,
        proceedIntent = proceedIntent
    )
}

@Composable
private fun UserAppliancesScreenUi(
    state: UserAppliancesScreenState,
    proceedIntent: (IUserAppliancesScreenIntent) -> Unit
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
                proceedIntent(IUserAppliancesScreenIntent.NavigateBack)
            },
            headerTextString = stringResource(R.string.appliances_text)
        )

        when {
            state.isLoading -> {
                AppProgressBar(
                    modifier = Modifier.fillMaxSize()
                )
            }

            else -> {
                if (state.appliances.isEmpty()) {
                    AppEmptyScreen(
                        modifier = Modifier.fillMaxSize()
                    )
                } else {
                    LazyColumn(
                        modifier = Modifier.padding(UserAppliancesScreenContentPadding)
                    ) {
                        items(state.appliances) { appliance ->
                            ApplianceCard(
                                modifier = Modifier
                                    .padding(UserAppliancesScreenCardPadding)
                                    .fillMaxWidth()
                                    .clickable {
                                        proceedIntent(
                                            IUserAppliancesScreenIntent.OpenDetailsPage(
                                                appliance
                                            )
                                        )
                                    },
                                appliance = appliance
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ApplianceCard(
    modifier: Modifier,
    appliance: ProductApplianceModelUi
) {

    Column(
        modifier = modifier
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {

            Icon(
                painter = painterResource(appliance.statusIconId),
                tint = appTopBarElementsColor,
                contentDescription = stringResource(appliance.statusTextId)
            )

            Column(
                modifier = Modifier.padding(UserAppliancesScreenCardColumnStartPadding)
            ) {

                Text(
                    text = appliance.dateText,
                    style = bodyStyle.copy(
                        color = appTopBarElementsColor,
                        fontSize = UserAppliancesScreenCardTextSize,
                        fontWeight = FontWeight.Light
                    )
                )

                Text(
                    modifier = Modifier.padding(UserAppliancesScreenCardTextTopPadding),
                    text = stringResource(appliance.applianceTextString),
                    style = bodyStyle.copy(
                        color = appTopBarElementsColor,
                        fontSize = UserAppliancesScreenCardMainTextSize,
                        fontWeight = FontWeight.Normal
                    )
                )

                Text(
                    modifier = Modifier.padding(UserAppliancesScreenCardTextTopPadding),
                    text = stringResource(appliance.statusTextId),
                    style = bodyStyle.copy(
                        color = appTopBarElementsColor,
                        fontSize = UserAppliancesScreenCardTextSize,
                        fontWeight = FontWeight.Light
                    )
                )
            }
        }

        Spacer(
            modifier = Modifier
                .padding(UserAppliancesScreenCardTextTopPadding)
                .fillMaxWidth()
                .height(UserAppliancesScreenCardSpacerHeight)
                .background(appTopBarElementsColor)
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

            UserAppliancesScreenUi(
                state = UserAppliancesScreenState(
                    appliances = listOf(
                        ProductApplianceModelUi(
                            domainModel = CardApplianceModelDomain(
                                id = "121",
                                currencyCode = "byn",
                                dateOfAppliance = Calendar.getInstance().time,
                                status = ApplianceStatus.Approved,
                                selectedOffice = OfficeModelDomain(
                                    address = "dadas",
                                    workingTime = "saxasxas"
                                ),
                                userId = "cacas",
                                detailedCardApplianceType = CardDetailedApplianceType.REISSUE_PAYMENT_CARD,
                                isNewAccountNecessary = false,
                                isSalaryCard = false,
                                isVirtual = true,
                                system = CardSystem.Mir,
                                type = CardType.Dedut
                            )
                        ),
                        ProductApplianceModelUi(
                            domainModel = CreditApplianceModelDomain(
                                id = "121",
                                currencyCode = "byn",
                                dateOfAppliance = Calendar.getInstance().time,
                                status = ApplianceStatus.Approved,
                                selectedOffice = OfficeModelDomain(
                                    address = "dadas",
                                    workingTime = "saxasxas"
                                ),
                                userId = "cacas",
                                detailedCreditApplianceType = CreditDetailedApplianceType.BBANK_CREDIT,
                                creditGoal = "jasbkjasx"
                            )
                        ),
                        ProductApplianceModelUi(
                            domainModel = DepositApplianceModelDomain(
                                id = "121",
                                currencyCode = "byn",
                                dateOfAppliance = Calendar.getInstance().time,
                                status = ApplianceStatus.Approved,
                                selectedOffice = OfficeModelDomain(
                                    address = "dadas",
                                    workingTime = "saxasxas"
                                ),
                                userId = "cacas",
                                detailedDepositApplianceType = DepositDetailedApplianceType.URGENT_DEPOSIT,
                                minimumContribution = 0.0,
                                period = 21,
                                procent = 921.1
                            )
                        )
                    )
                ),
                proceedIntent = {}
            )

//            UserAppliancesScreenUi(
//                state = UserAppliancesScreenState(
//                    appliances = emptyList()
//                ),
//                proceedIntent = {}
//            )

//            UserAppliancesScreenUi(
//                state = UserAppliancesScreenState(
//                    isLoading = true
//                ),
//                proceedIntent = {}
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

            UserAppliancesScreenUi(
                state = UserAppliancesScreenState(
                    appliances = listOf(
                        ProductApplianceModelUi(
                            domainModel = CardApplianceModelDomain(
                                id = "121",
                                currencyCode = "byn",
                                dateOfAppliance = Calendar.getInstance().time,
                                status = ApplianceStatus.Approved,
                                selectedOffice = OfficeModelDomain(
                                    address = "dadas",
                                    workingTime = "saxasxas"
                                ),
                                userId = "cacas",
                                detailedCardApplianceType = CardDetailedApplianceType.REISSUE_PAYMENT_CARD,
                                isNewAccountNecessary = false,
                                isSalaryCard = false,
                                isVirtual = true,
                                system = CardSystem.Mir,
                                type = CardType.Dedut
                            )
                        ),
                        ProductApplianceModelUi(
                            domainModel = CreditApplianceModelDomain(
                                id = "121",
                                currencyCode = "byn",
                                dateOfAppliance = Calendar.getInstance().time,
                                status = ApplianceStatus.Approved,
                                selectedOffice = OfficeModelDomain(
                                    address = "dadas",
                                    workingTime = "saxasxas"
                                ),
                                userId = "cacas",
                                detailedCreditApplianceType = CreditDetailedApplianceType.BBANK_CREDIT,
                                creditGoal = "jasbkjasx"
                            )
                        ),
                        ProductApplianceModelUi(
                            domainModel = DepositApplianceModelDomain(
                                id = "121",
                                currencyCode = "byn",
                                dateOfAppliance = Calendar.getInstance().time,
                                status = ApplianceStatus.Approved,
                                selectedOffice = OfficeModelDomain(
                                    address = "dadas",
                                    workingTime = "saxasxas"
                                ),
                                userId = "cacas",
                                detailedDepositApplianceType = DepositDetailedApplianceType.URGENT_DEPOSIT,
                                minimumContribution = 0.0,
                                period = 21,
                                procent = 921.1
                            )
                        )
                    )
                ),
                proceedIntent = {}
            )

//            UserAppliancesScreenUi(
//                state = UserAppliancesScreenState(
//                    appliances = emptyList()
//                ),
//                proceedIntent = {}
//            )

//            UserAppliancesScreenUi(
//                state = UserAppliancesScreenState(
//                    isLoading = true
//                ),
//                proceedIntent = {}
//            )
        }
    }
}