package com.alenniboris.fastbanking.presentation.screens.product_appliance_details.views

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.graphics.painter.Painter
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
import com.alenniboris.fastbanking.presentation.model.appliance.ProductApplianceType
import com.alenniboris.fastbanking.presentation.model.bank_product.toUiPicture
import com.alenniboris.fastbanking.presentation.model.bank_product.toUiString
import com.alenniboris.fastbanking.presentation.screens.product_appliance_details.IProductApplianceDetailsScreenEvent
import com.alenniboris.fastbanking.presentation.screens.product_appliance_details.IProductApplianceDetailsScreenEventIntent
import com.alenniboris.fastbanking.presentation.screens.product_appliance_details.ProductApplianceDetailsScreenState
import com.alenniboris.fastbanking.presentation.screens.product_appliance_details.ProductApplianceDetailsScreenViewModel
import com.alenniboris.fastbanking.presentation.uikit.theme.FastBankingTheme
import com.alenniboris.fastbanking.presentation.uikit.theme.ProductApplianceDetailsScreenContentPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.ProductApplianceDetailsScreenInfoSectionImagePadding
import com.alenniboris.fastbanking.presentation.uikit.theme.ProductApplianceDetailsScreenInfoSectionImageSize
import com.alenniboris.fastbanking.presentation.uikit.theme.ProductApplianceDetailsScreenInfoSectionPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.ProductApplianceDetailsScreenInfoSectionTextPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.ProductApplianceDetailsScreenTextSize
import com.alenniboris.fastbanking.presentation.uikit.theme.TopBarPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.appColor
import com.alenniboris.fastbanking.presentation.uikit.theme.appTopBarElementsColor
import com.alenniboris.fastbanking.presentation.uikit.theme.bodyStyle
import com.alenniboris.fastbanking.presentation.uikit.values.ProductApplianceDetailsScreenRoute
import com.alenniboris.fastbanking.presentation.uikit.views.AppCheckButton
import com.alenniboris.fastbanking.presentation.uikit.views.AppEmptyScreen
import com.alenniboris.fastbanking.presentation.uikit.views.AppProgressBar
import com.alenniboris.fastbanking.presentation.uikit.views.AppTopBar
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf
import java.util.Calendar

@Destination(route = ProductApplianceDetailsScreenRoute)
@Composable
fun ProductApplianceDetailsScreen(
    applianceId: String,
    applianceType: ProductApplianceType,
    navigator: DestinationsNavigator
) {

    val viewModel = koinViewModel<ProductApplianceDetailsScreenViewModel>() {
        parametersOf(applianceId, applianceType)
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
            event.filterIsInstance<IProductApplianceDetailsScreenEvent.NavigateBack>().collect {
                navigator.popBackStack()
            }
        }

        launch {
            event.filterIsInstance<IProductApplianceDetailsScreenEvent.ShowMessage>()
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

    ProductApplianceDetailsScreenUi(
        state = state,
        proceedIntent = proceedIntent
    )
}

@Composable
private fun ProductApplianceDetailsScreenUi(
    state: ProductApplianceDetailsScreenState,
    proceedIntent: (IProductApplianceDetailsScreenEventIntent) -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(appColor)
            .verticalScroll(rememberScrollState())
    ) {

        AppTopBar(
            modifier = Modifier
                .padding(TopBarPadding)
                .fillMaxWidth(),
            leftBtnPainter = painterResource(R.drawable.back_icon),
            textAlignment = Alignment.CenterEnd,
            onLeftBtnClicked = {
                proceedIntent(IProductApplianceDetailsScreenEventIntent.NavigateBack)
            },
            headerTextString = stringResource(
                state.appliance?.applianceTextString ?: R.string.empty_text
            )
        )

        when {
            state.isLoading -> {
                AppProgressBar(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f)
                )
            }

            else -> {
                state.appliance?.let {

                    Column(
                        modifier = Modifier
                            .padding(ProductApplianceDetailsScreenContentPadding)
                    ) {

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            Image(
                                painter = painterResource(state.appliance.statusImageId),
                                contentDescription = stringResource(state.appliance.statusTextId)
                            )

                            Text(
                                text = stringResource(state.appliance.statusTextId),
                                style = bodyStyle.copy(
                                    color = appTopBarElementsColor,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = ProductApplianceDetailsScreenTextSize
                                )
                            )
                        }

                        ApplianceDetailsInfoSection(
                            modifier = Modifier
                                .padding(ProductApplianceDetailsScreenInfoSectionPadding)
                                .fillMaxWidth(),
                            header = stringResource(R.string.appliance_date_header_text),
                            text = state.appliance.dateText
                        )

                        ApplianceDetailsInfoSection(
                            modifier = Modifier
                                .padding(ProductApplianceDetailsScreenInfoSectionPadding)
                                .fillMaxWidth(),
                            header = stringResource(R.string.appliance_office_header_text),
                            text = state.appliance.officeLocationText
                        )

                        ApplianceDetailsInfoSection(
                            modifier = Modifier
                                .padding(ProductApplianceDetailsScreenInfoSectionPadding)
                                .fillMaxWidth(),
                            header = stringResource(R.string.appliance_currency_header_text),
                            text = state.appliance.currencyText
                        )

                        (state.appliance.domainModel as? CardApplianceModelDomain)?.let {

                            val model = it
                            Log.e("!!!", model.toString())

                            ApplianceDetailsInfoSection(
                                modifier = Modifier
                                    .padding(ProductApplianceDetailsScreenInfoSectionPadding)
                                    .fillMaxWidth(),
                                header = stringResource(R.string.appliance_card_type_header_text),
                                text = stringResource(model.type.toUiString())
                            )

                            ApplianceDetailsInfoSection(
                                modifier = Modifier
                                    .padding(ProductApplianceDetailsScreenInfoSectionPadding)
                                    .fillMaxWidth(),
                                header = stringResource(R.string.appliance_card_system_header_text),
                                text = stringResource(model.system.toUiString()),
                                imagePainter = painterResource(model.system.toUiPicture())
                            )

                            ApplianceDetailsInfoSection(
                                modifier = Modifier
                                    .padding(ProductApplianceDetailsScreenInfoSectionPadding)
                                    .fillMaxWidth(),
                                header = stringResource(R.string.appliance_is_salary_card_header_text),
                                isCheckButtonNeeded = true,
                                isChecked = model.isSalaryCard
                            )

                            ApplianceDetailsInfoSection(
                                modifier = Modifier
                                    .padding(ProductApplianceDetailsScreenInfoSectionPadding)
                                    .fillMaxWidth(),
                                header = stringResource(R.string.appliance_is_new_account_neede_header_text),
                                isCheckButtonNeeded = true,
                                isChecked = model.isNewAccountNecessary
                            )
                        }

                        (state.appliance.domainModel as? CreditApplianceModelDomain)?.let {

                            val model = it

                            ApplianceDetailsInfoSection(
                                modifier = Modifier
                                    .padding(ProductApplianceDetailsScreenInfoSectionPadding)
                                    .fillMaxWidth(),
                                header = stringResource(R.string.appliance_credit_goal_header_text),
                                text = model.creditGoal
                            )
                        }

                        (state.appliance.domainModel as? DepositApplianceModelDomain)?.let {

                            val model = it

                            ApplianceDetailsInfoSection(
                                modifier = Modifier
                                    .padding(ProductApplianceDetailsScreenInfoSectionPadding)
                                    .fillMaxWidth(),
                                header = stringResource(R.string.appliance_deposit_minimum_contribution_header_text),
                                text = model.minimumContribution.toString()
                            )

                            ApplianceDetailsInfoSection(
                                modifier = Modifier
                                    .padding(ProductApplianceDetailsScreenInfoSectionPadding)
                                    .fillMaxWidth(),
                                header = stringResource(R.string.appliance_deposit_period_header_text),
                                text = model.period.toString()
                            )

                            ApplianceDetailsInfoSection(
                                modifier = Modifier
                                    .padding(ProductApplianceDetailsScreenInfoSectionPadding)
                                    .fillMaxWidth(),
                                header = stringResource(R.string.appliance_deposit_procent_header_text),
                                text = model.procent.toString()
                            )
                        }
                    }
                } ?: AppEmptyScreen(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f)
                )
            }
        }
    }
}

@Composable
private fun ApplianceDetailsInfoSection(
    modifier: Modifier,
    header: String,
    text: String = "",
    imagePainter: Painter? = null,
    isCheckButtonNeeded: Boolean = false,
    isChecked: Boolean = false
) {

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Text(
            text = header,
            style = bodyStyle.copy(
                color = appTopBarElementsColor,
                fontWeight = FontWeight.Bold,
                fontSize = ProductApplianceDetailsScreenTextSize
            )
        )

        Column(
            modifier = Modifier.padding(ProductApplianceDetailsScreenInfoSectionTextPadding)
        ) {

            if (isCheckButtonNeeded) {
                AppCheckButton(
                    isChecked = isChecked
                )
            } else {
                imagePainter?.let {
                    Image(
                        modifier = Modifier
                            .padding(ProductApplianceDetailsScreenInfoSectionImagePadding)
                            .size(ProductApplianceDetailsScreenInfoSectionImageSize),
                        painter = imagePainter,
                        contentDescription = text
                    )
                }

                Text(
                    text = text,
                    style = bodyStyle.copy(
                        color = appTopBarElementsColor,
                        fontWeight = FontWeight.Normal,
                        fontSize = ProductApplianceDetailsScreenTextSize
                    )
                )
            }
        }
    }
}

@Composable
@Preview
private fun LightThemeCard() {
    FastBankingTheme(
        darkTheme = false
    ) {
        Surface {

            // for card
            ProductApplianceDetailsScreenUi(
                state = ProductApplianceDetailsScreenState(
                    appliance = ProductApplianceModelUi(
                        domainModel = CardApplianceModelDomain(
                            id = "121",
                            currencyCode = "byn",
                            dateOfAppliance = Calendar.getInstance().time,
                            status = ApplianceStatus.Approved,
                            selectedOffice = OfficeModelDomain(
                                address = "dadas",
                                workingTime = "saxasxadeajednkjdbancjkabsdxhbajsxbjhasdss"
                            ),
                            userId = "cacas",
                            detailedCardApplianceType = CardDetailedApplianceType.REISSUE_PAYMENT_CARD,
                            isNewAccountNecessary = false,
                            isSalaryCard = true,
                            isVirtual = true,
                            system = CardSystem.Mir,
                            type = CardType.Dedut
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
private fun LightThemeCredit() {
    FastBankingTheme(
        darkTheme = false
    ) {
        Surface {

            ProductApplianceDetailsScreenUi(
                state = ProductApplianceDetailsScreenState(
                    appliance = ProductApplianceModelUi(
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
                    )
                ),
                proceedIntent = {}
            )
        }
    }
}

@Composable
@Preview
private fun LightThemeDeposit() {
    FastBankingTheme(
        darkTheme = false
    ) {
        Surface {

            ProductApplianceDetailsScreenUi(
                state = ProductApplianceDetailsScreenState(
                    appliance = ProductApplianceModelUi(
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
                ),
                proceedIntent = {}
            )
        }
    }
}

@Composable
@Preview
private fun DarkThemeCard() {
    FastBankingTheme(
        darkTheme = true
    ) {
        Surface {

            // for card
            ProductApplianceDetailsScreenUi(
                state = ProductApplianceDetailsScreenState(
                    appliance = ProductApplianceModelUi(
                        domainModel = CardApplianceModelDomain(
                            id = "121",
                            currencyCode = "byn",
                            dateOfAppliance = Calendar.getInstance().time,
                            status = ApplianceStatus.Approved,
                            selectedOffice = OfficeModelDomain(
                                address = "dadas",
                                workingTime = "saxasxadeajednkjdbancjkabsdxhbajsxbjhasdss"
                            ),
                            userId = "cacas",
                            detailedCardApplianceType = CardDetailedApplianceType.REISSUE_PAYMENT_CARD,
                            isNewAccountNecessary = false,
                            isSalaryCard = true,
                            isVirtual = true,
                            system = CardSystem.Mir,
                            type = CardType.Dedut
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
private fun DarkThemeCredit() {
    FastBankingTheme(
        darkTheme = true
    ) {
        Surface {

            ProductApplianceDetailsScreenUi(
                state = ProductApplianceDetailsScreenState(
                    appliance = ProductApplianceModelUi(
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
                    )
                ),
                proceedIntent = {}
            )
        }
    }
}

@Composable
@Preview
private fun DarkThemeDeposit() {
    FastBankingTheme(
        darkTheme = true
    ) {
        Surface {

            ProductApplianceDetailsScreenUi(
                state = ProductApplianceDetailsScreenState(
                    appliance = ProductApplianceModelUi(
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
                ),
                proceedIntent = {}
            )
        }
    }
}