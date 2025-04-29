package com.alenniboris.fastbanking.presentation.screens.main.views

import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.alenniboris.fastbanking.R
import com.alenniboris.fastbanking.presentation.screens.destinations.HelpScreenDestination
import com.alenniboris.fastbanking.presentation.screens.destinations.PersonalScreenDestination
import com.alenniboris.fastbanking.presentation.screens.destinations.ProductApplianceChoosingScreenDestination
import com.alenniboris.fastbanking.presentation.screens.main.IMainScreenEvent
import com.alenniboris.fastbanking.presentation.screens.main.IMainScreenIntent
import com.alenniboris.fastbanking.presentation.screens.main.MainScreenState
import com.alenniboris.fastbanking.presentation.screens.main.MainScreenViewModel
import com.alenniboris.fastbanking.presentation.uikit.theme.FastBankingTheme
import com.alenniboris.fastbanking.presentation.uikit.theme.MainScreenContentHeaderSize
import com.alenniboris.fastbanking.presentation.uikit.theme.MainScreenContentOuterPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.MainScreenContentPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.MainScreenContentShape
import com.alenniboris.fastbanking.presentation.uikit.theme.MainScreenInformationSectionContainerPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.MainScreenProductSectionHeight
import com.alenniboris.fastbanking.presentation.uikit.theme.MainScreenProductSectionInnerPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.MainScreenProductSectionShape
import com.alenniboris.fastbanking.presentation.uikit.theme.MainScreenProductsButtonPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.MainScreenProductsButtonShape
import com.alenniboris.fastbanking.presentation.uikit.theme.MainScreenRowItemPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.MainScreenTopBarShape
import com.alenniboris.fastbanking.presentation.uikit.theme.MainScreenTransactionsHistoryMinHeight
import com.alenniboris.fastbanking.presentation.uikit.theme.MainScreenTransactionsHistoryOuterPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.MainScreenTransactionsHistoryPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.MainScreenTransactionsHistoryShape
import com.alenniboris.fastbanking.presentation.uikit.theme.TopBarPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.appColor
import com.alenniboris.fastbanking.presentation.uikit.theme.bodyStyle
import com.alenniboris.fastbanking.presentation.uikit.theme.mainScreenFilterItemColor
import com.alenniboris.fastbanking.presentation.uikit.theme.mainScreenFilterItemSelectedColor
import com.alenniboris.fastbanking.presentation.uikit.theme.mainScreenFilterItemSelectedTextColor
import com.alenniboris.fastbanking.presentation.uikit.theme.mainScreenFilterItemTextColor
import com.alenniboris.fastbanking.presentation.uikit.theme.mainScreenItemColor
import com.alenniboris.fastbanking.presentation.uikit.theme.mainScreenOnItemColor
import com.alenniboris.fastbanking.presentation.uikit.theme.mainScreenProductsButtonColor
import com.alenniboris.fastbanking.presentation.uikit.theme.mainScreenProductsButtonIconTintColor
import com.alenniboris.fastbanking.presentation.uikit.theme.mainScreenTextColor
import com.alenniboris.fastbanking.presentation.uikit.utils.baseCurrencyFlow
import com.alenniboris.fastbanking.presentation.uikit.values.ClickableElement
import com.alenniboris.fastbanking.presentation.uikit.values.MainScreenRoute
import com.alenniboris.fastbanking.presentation.uikit.values.toUiText
import com.alenniboris.fastbanking.presentation.uikit.views.AppIconButton
import com.alenniboris.fastbanking.presentation.uikit.views.AppRowFilter
import com.alenniboris.fastbanking.presentation.uikit.views.AppTopBar
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@RootNavGraph
@Destination(route = MainScreenRoute)
@Composable
fun MainScreen(
    navigator: DestinationsNavigator
) {

    val viewModel = koinViewModel<MainScreenViewModel>()
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
            event.filterIsInstance<IMainScreenEvent.ShowToastMessage>().collect { coming ->
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
            event.filterIsInstance<IMainScreenEvent.OpenHelpScreen>().collect {
                navigator.navigate(
                    HelpScreenDestination(
                        isBackButtonNeeded = true
                    )
                )
            }
        }

        launch {
            event.filterIsInstance<IMainScreenEvent.OpenPersonalDetailsScreen>().collect {
                navigator.navigate(
                    PersonalScreenDestination
                )
            }
        }

        launch {
            event.filterIsInstance<IMainScreenEvent.OpenRecommendedNewsDetails>()
                .collect { coming ->
                    toastMessage?.cancel()
                    toastMessage = Toast.makeText(
                        context,
                        context.getString(R.string.in_development_text),
                        Toast.LENGTH_SHORT
                    )
                    toastMessage?.show()
                }
        }

        launch {
            event.filterIsInstance<IMainScreenEvent.OpenProductApplianceChoosingScreen>()
                .collect { coming ->
                    navigator.navigate(
                        ProductApplianceChoosingScreenDestination(
                            bankProduct = coming.productType
                        )
                    )
                }
        }
    }

    MainScreenUi(
        state = state,
        proceedIntent = proceedIntent
    )
}

@Composable
private fun MainScreenUi(
    state: MainScreenState,
    proceedIntent: (IMainScreenIntent) -> Unit
) {

    val baseCurrency by baseCurrencyFlow.collectAsStateWithLifecycle()

    Box(
        modifier = Modifier
            .background(appColor)
            .fillMaxSize()
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {

            AppTopBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = mainScreenItemColor,
                        shape = MainScreenTopBarShape
                    )
                    .padding(TopBarPadding),
                content = {
                    MainScreenTopBar(
                        isLoading = state.isRecommendedNewsLoading,
                        elements = state.recommendedNews,
                        isVisible = state.isRecommendedNewsVisible,
                        proceedIntent = proceedIntent
                    )
                }
            )

            Column(
                modifier = Modifier
                    .padding(MainScreenContentOuterPadding)
                    .clip(MainScreenContentShape)
                    .fillMaxSize()
                    .background(mainScreenItemColor)
                    .padding(MainScreenContentPadding)
                    .verticalScroll(rememberScrollState())
            ) {

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Text(
                        text = stringResource(R.string.my_products_text),
                        style = bodyStyle.copy(
                            color = mainScreenTextColor,
                            fontWeight = FontWeight.Bold,
                            fontSize = MainScreenContentHeaderSize
                        )
                    )

                    AppIconButton(
                        modifier = Modifier
                            .clip(MainScreenProductsButtonShape)
                            .background(mainScreenProductsButtonColor)
                            .padding(MainScreenProductsButtonPadding),
                        onClick = {
                            proceedIntent(
                                IMainScreenIntent.UpdateActionsWithProductsSheetVisibility
                            )
                        },
                        iconPainter = painterResource(R.drawable.add_icon),
                        tint = mainScreenProductsButtonIconTintColor
                    )
                }

                AppRowFilter(
                    modifier = Modifier
                        .padding(MainScreenRowItemPadding)
                        .fillMaxWidth(),
                    itemTint = mainScreenFilterItemColor,
                    itemTextColor = mainScreenFilterItemTextColor,
                    itemSelectedTint = mainScreenFilterItemSelectedColor,
                    itemSelectedTextColor = mainScreenFilterItemSelectedTextColor,
                    elements = state.bankProducts.map {
                        ClickableElement(
                            text = stringResource(it.toUiText()),
                            onClick = {
                                proceedIntent(IMainScreenIntent.UpdateCurrentlyViewedProductsType(it))
                            }
                        )
                    },
                    currentSelectedElement = ClickableElement(
                        text = stringResource(state.currentBankProduct.toUiText()),
                        onClick = {}
                    )
                )

                MainScreenProductSection(
                    modifier = Modifier
                        .clip(MainScreenProductSectionShape)
                        .fillMaxWidth()
                        .height(MainScreenProductSectionHeight)
                        .background(mainScreenOnItemColor)
                        .padding(MainScreenProductSectionInnerPadding),
                    isLoading = state.isUserBankProductsLoading,
                    currentProduct = state.currentBankProduct,
                    currentUserProduct = state.currentViewedUserProduct,
                    proceedIntent = proceedIntent
                )

                MainScreenTransactionsSection(
                    modifier = Modifier
                        .padding(MainScreenTransactionsHistoryOuterPadding)
                        .clip(MainScreenTransactionsHistoryShape)
                        .fillMaxWidth()
                        .heightIn(min = MainScreenTransactionsHistoryMinHeight)
                        .background(mainScreenOnItemColor)
                        .padding(MainScreenTransactionsHistoryPadding),
                    isLoading = state.isUserTransactionsHistoryLoading,
                    transactions = state.userTransactions
                )

                MainScreenInformationSection(
                    modifier = Modifier
                        .padding(MainScreenInformationSectionContainerPadding)
                        .fillMaxWidth()
                        .height(MainScreenProductSectionHeight),
                    isCurrencyDataLoading = state.isBaseCurrencyExchangeLoading,
                    baseCurrencyCode = baseCurrency.currencyCode,
                    baseCurrencyAmount = state.baseCurrencyAmount,
                    exchangeCurrencyAmount = state.exchangedCurrencyAmount,
                    isAccountsDataLoading = state.isUserAccountsSumLoading,
                    allAccountsAmount = state.userAccountsSum
                )
            }
        }

        if (state.isProductsSheetVisible) {

            MainScreenProductsActionsSheet(
                proceedIntent = proceedIntent
            )
        }
    }
}

@Composable
@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
private fun LightThemePreview() {
    FastBankingTheme {
        Surface {
//            MainScreenUi(
//                state = MainScreenState(
//                    isRecommendedNewsLoading = true,
//                    isRecommendedNewsVisible = true
//                ),
//                proceedIntent = {}
//            )
//
//            MainScreenUi(
//                state = MainScreenState(
//                    recommendedNews = listOf(
//                        RecommendedNewsModelDomain(
//                            picture = "",
//                            header = "First",
//                            text = "wesxrdcvgnjmk",
//                            id = "jdaenxasjkx"
//                        )
//                    ),
//                    isRecommendedNewsVisible = true
//                ),
//                proceedIntent = {}
//            )
            MainScreenUi(
                state = MainScreenState(),
                proceedIntent = {}
            )
        }
    }
}

@Composable
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
private fun DarkThemePreview() {
    FastBankingTheme {
        Surface {
            MainScreenUi(
                state = MainScreenState(),
                proceedIntent = {}
            )
        }
    }
}