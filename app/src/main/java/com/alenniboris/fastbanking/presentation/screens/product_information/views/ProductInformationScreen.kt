package com.alenniboris.fastbanking.presentation.screens.product_information.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import com.alenniboris.fastbanking.R
import com.alenniboris.fastbanking.domain.model.OwnerModelDomain
import com.alenniboris.fastbanking.domain.model.card.CardModelDomain
import com.alenniboris.fastbanking.domain.model.card.CardSystem
import com.alenniboris.fastbanking.domain.model.card.CardType
import com.alenniboris.fastbanking.domain.model.credit.CreditModelDomain
import com.alenniboris.fastbanking.domain.utils.GsonUtil.fromJson
import com.alenniboris.fastbanking.presentation.model.bank_product.AccountModelUi
import com.alenniboris.fastbanking.presentation.model.bank_product.CardModelUi
import com.alenniboris.fastbanking.presentation.model.bank_product.CreditModelUi
import com.alenniboris.fastbanking.presentation.model.bank_product.IBankProductModelUi
import com.alenniboris.fastbanking.presentation.screens.product_information.IProductInformationScreenEvent
import com.alenniboris.fastbanking.presentation.screens.product_information.IProductInformationScreenIntent
import com.alenniboris.fastbanking.presentation.screens.product_information.ProductInformationScreenViewModel
import com.alenniboris.fastbanking.presentation.uikit.theme.FastBankingTheme
import com.alenniboris.fastbanking.presentation.uikit.theme.ProductInformationScreenContentPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.TopBarPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.appColor
import com.alenniboris.fastbanking.presentation.uikit.values.BankProduct
import com.alenniboris.fastbanking.presentation.uikit.values.ProductInformationScreenRoute
import com.alenniboris.fastbanking.presentation.uikit.views.AppTopBar
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import java.util.Calendar

@Composable
@Destination(route = ProductInformationScreenRoute)
fun ProductInformationScreen(
    productType: BankProduct,
    product: String,
    navigator: DestinationsNavigator
) {

    val viewModel =
        koinViewModel<ProductInformationScreenViewModel>()
    val event by remember { mutableStateOf(viewModel.event) }
    val proceedIntent by remember { mutableStateOf(viewModel::proceedIntent) }
    val clipboardManager = LocalClipboardManager.current
    val objectProduct by remember {
        mutableStateOf(
            when (productType) {
                BankProduct.CARD -> product.fromJson<CardModelUi>()
                BankProduct.CREDIT -> product.fromJson<CreditModelUi>()
                BankProduct.DEPOSITS_AND_ACCOUNTS -> product.fromJson<AccountModelUi>()
            }
        )
    }

    LaunchedEffect(event) {

        launch {
            event.filterIsInstance<IProductInformationScreenEvent.NavigateBack>().collect {
                navigator.popBackStack()
            }
        }

        launch {
            event.filterIsInstance<IProductInformationScreenEvent.CopyTextToClipboard>()
                .collect { coming ->
                    clipboardManager.setText(
                        AnnotatedString(coming.text)
                    )
                }
        }
    }

    ProductInformationScreenUi(
        product = objectProduct,
        proceedIntent = proceedIntent
    )
}

@Composable
private fun ProductInformationScreenUi(
    product: IBankProductModelUi,
    proceedIntent: (IProductInformationScreenIntent) -> Unit
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
                    IProductInformationScreenIntent.NavigateBack
                )
            },
            headerTextString = stringResource(R.string.information_header_text)
        )

        when (product) {
            is AccountModelUi -> {
                AccountInformationUi(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(appColor)
                        .padding(ProductInformationScreenContentPadding),
                    account = product,
                    proceedIntent = proceedIntent
                )
            }

            is CardModelUi -> {
                CardInformationUi(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(appColor)
                        .padding(ProductInformationScreenContentPadding),
                    card = product,
                    proceedIntent = proceedIntent
                )
            }

            is CreditModelUi -> {
                CreditInformationUi(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(appColor)
                        .padding(ProductInformationScreenContentPadding),
                    credit = product,
                    proceedIntent = proceedIntent
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
            ProductInformationScreenUi(
                product = CardModelUi(
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
                ),
                proceedIntent = {}
            )

//            ProductInformationScreenUi(
//                product = CreditModelUi(
//                    domainModel = CreditModelDomain(
//                        id = "21213",
//                        initialAmount = 11.2,
//                        currency = "byn",
//                        amountInReserveCurrency = 11.2,
//                        reserveCurrency = "usd",
//                        percentage = 5.0,
//                        lastPayment = Calendar.getInstance().time,
//                        startDate = Calendar.getInstance().time,
//                        goalDescription = "wwdwd",
//                        ownerId = "111",
//                        name = "odsklmclksd",
//                        erip = "kjjsdnkjcsndk",
//                        contractNumber = "kjdnak2313",
//                        bankIdCode = "sdilakjlkasnx"
//                    )
//                ),
//                proceedIntent = {}
//            )

//            ProductInformationScreenUi(
//                product = AccountModelUi(
//                    domainModel = AccountModelDomain(
//                        id = "21213",
//                        amount = 11.2,
//                        currency = "byn",
//                        amountInReserveCurrency = 11.2,
//                        reserveCurrency = "usd",
//                        attachedCards = emptyMap(),
//                        owner = OwnerModelDomain(
//                            id = "",
//                            name = "",
//                            surname = ""
//                        ),
//                        name = "odsklmclksd"
//                    )
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
//            ProductInformationScreenUi(
//                product = CardModelUi(
//                    domainModel = CardModelDomain(
//                        id = "",
//                        currencyCode = "bbyn",
//                        reserveCurrencyCode = "byn",
//                        amountInReserveCurrency = 0.0,
//                        amount = 0.0,
//                        owner = OwnerModelDomain(
//                            id = "",
//                            name = "",
//                            surname = ""
//                        ),
//                        expireDate = Calendar.getInstance().time,
//                        number = "1111111111111111",
//                        cvv = "",
//                        type = CardType.Dedut,
//                        system = CardSystem.Visa,
//                        name = "odsklmclksd",
//                        erip = "ncjasncjasnxk",
//                        iban = "21912029nsknac",
//                        bankIdCode = "dsjkcnkjsndc"
//                    )
//                ),
//                proceedIntent = {}
//            )

            ProductInformationScreenUi(
                product = CreditModelUi(
                    domainModel = CreditModelDomain(
                        id = "21213",
                        initialAmount = 11.2,
                        currency = "byn",
                        amountInReserveCurrency = 11.2,
                        reserveCurrency = "usd",
                        percentage = 5.0,
                        lastPayment = Calendar.getInstance().time,
                        startDate = Calendar.getInstance().time,
                        goalDescription = "wwdwd",
                        ownerId = "111",
                        name = "odsklmclksd",
                        erip = "kjjsdnkjcsndk",
                        contractNumber = "kjdnak2313",
                        bankIdCode = "sdilakjlkasnx"
                    )
                ),
                proceedIntent = {}
            )

//            ProductInformationScreenUi(
//                product = AccountModelUi(
//                    domainModel = AccountModelDomain(
//                        id = "21213",
//                        amount = 11.2,
//                        currency = "byn",
//                        amountInReserveCurrency = 11.2,
//                        reserveCurrency = "usd",
//                        attachedCards = emptyMap(),
//                        owner = OwnerModelDomain(
//                            id = "",
//                            name = "",
//                            surname = ""
//                        ),
//                        name = "odsklmclksd"
//                    )
//                ),
//                proceedIntent = {}
//            )
        }
    }
}