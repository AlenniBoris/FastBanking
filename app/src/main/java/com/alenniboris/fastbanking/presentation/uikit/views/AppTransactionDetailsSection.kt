package com.alenniboris.fastbanking.presentation.uikit.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.alenniboris.fastbanking.R
import com.alenniboris.fastbanking.domain.model.currency.CurrencyModelDomain
import com.alenniboris.fastbanking.domain.model.transaction.TransactionModelDomain
import com.alenniboris.fastbanking.domain.model.transaction.TransactionType
import com.alenniboris.fastbanking.presentation.model.bank_product.TransactionModelUi
import com.alenniboris.fastbanking.presentation.uikit.theme.AppTransactionDetailsSectionCategoryTextSize
import com.alenniboris.fastbanking.presentation.uikit.theme.AppTransactionDetailsSectionElementContainerPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.AppTransactionDetailsSectionElementOuterPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.AppTransactionDetailsSectionElementSpacerHeight
import com.alenniboris.fastbanking.presentation.uikit.theme.AppTransactionDetailsSectionElementTextPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.AppTransactionDetailsSectionHeaderTextSize
import com.alenniboris.fastbanking.presentation.uikit.theme.FastBankingTheme
import com.alenniboris.fastbanking.presentation.uikit.theme.FilterContainerPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.FilterDragHandleHeight
import com.alenniboris.fastbanking.presentation.uikit.theme.FilterDragHandlePadding
import com.alenniboris.fastbanking.presentation.uikit.theme.FilterDragHandleShape
import com.alenniboris.fastbanking.presentation.uikit.theme.FilterDragHandleWidth
import com.alenniboris.fastbanking.presentation.uikit.theme.FilterSheetShape
import com.alenniboris.fastbanking.presentation.uikit.theme.FilterSheetTonalElevation
import com.alenniboris.fastbanking.presentation.uikit.theme.appColor
import com.alenniboris.fastbanking.presentation.uikit.theme.appTopBarElementsColor
import com.alenniboris.fastbanking.presentation.uikit.theme.bodyStyle
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTransactionDetailsSection(
    transaction: TransactionModelUi,
    sheetState: SheetState = rememberModalBottomSheetState(),
    onDismiss: () -> Unit = {}
) {

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        shape = FilterSheetShape,
        containerColor = appColor,
        tonalElevation = FilterSheetTonalElevation,
        dragHandle = {
            Box(
                modifier = Modifier
                    .padding(FilterDragHandlePadding)
                    .width(FilterDragHandleWidth)
                    .height(FilterDragHandleHeight)
                    .clip(FilterDragHandleShape)
                    .background(appTopBarElementsColor)

            )
        }
    ) {

        TransactionDetailsContent(
            modifier = Modifier
                .padding(FilterContainerPadding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            transaction = transaction
        )
    }
}

@Composable
private fun TransactionDetailsContent(
    modifier: Modifier = Modifier,
    transaction: TransactionModelUi
) {

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Icon(
            modifier = Modifier.padding(AppTransactionDetailsSectionElementOuterPadding),
            painter = painterResource(transaction.picture),
            tint = appTopBarElementsColor,
            contentDescription = stringResource(R.string.transaction_text)
        )

        Text(
            text = transaction.priceText,
            style = bodyStyle.copy(
                color = appTopBarElementsColor,
                fontWeight = FontWeight.Bold,
                fontSize = AppTransactionDetailsSectionHeaderTextSize
            )
        )

        Text(
            modifier = Modifier.padding(AppTransactionDetailsSectionElementOuterPadding),
            text = transaction.priceText,
            style = bodyStyle.copy(
                color = appTopBarElementsColor,
                fontSize = AppTransactionDetailsSectionCategoryTextSize
            )
        )

        Text(
            modifier = Modifier
                .padding(AppTransactionDetailsSectionElementOuterPadding)
                .fillMaxWidth(),
            text = stringResource(R.string.details_text),
            style = bodyStyle.copy(
                fontWeight = FontWeight.Bold,
                color = appTopBarElementsColor,
                fontSize = AppTransactionDetailsSectionHeaderTextSize
            )
        )

        TransactionDetailsTextSection(
            modifier = Modifier
                .padding(AppTransactionDetailsSectionElementContainerPadding)
                .fillMaxWidth(),
            sectionHeader = stringResource(R.string.transaction_details_card_section_text),
            sectionText = transaction.numberText
        )

        TransactionDetailsTextSection(
            modifier = Modifier
                .padding(AppTransactionDetailsSectionElementContainerPadding)
                .fillMaxWidth(),
            sectionHeader = stringResource(R.string.transaction_details_date_section_text),
            sectionText = transaction.dateAndTimeText
        )

        TransactionDetailsTextSection(
            modifier = Modifier
                .padding(AppTransactionDetailsSectionElementContainerPadding)
                .fillMaxWidth(),
            sectionHeader = stringResource(R.string.transaction_details_name_section_text),
            sectionText = transaction.domainModel.details
        )

        TransactionDetailsTextSection(
            modifier = Modifier
                .padding(AppTransactionDetailsSectionElementContainerPadding)
                .fillMaxWidth(),
            sectionHeader = stringResource(R.string.transaction_details_sum_section_text),
            sectionText = transaction.priceText
        )
    }
}

@Composable
private fun TransactionDetailsTextSection(
    modifier: Modifier = Modifier,
    sectionHeader: String,
    sectionText: String
) {

    Column(
        modifier = modifier
    ) {

        Text(
            text = sectionHeader,
            style = bodyStyle.copy(
                color = appTopBarElementsColor,
                fontWeight = FontWeight.Bold,
                fontSize = AppTransactionDetailsSectionCategoryTextSize
            )
        )

        Text(
            modifier = Modifier.padding(AppTransactionDetailsSectionElementTextPadding),
            text = sectionText,
            style = bodyStyle.copy(
                color = appTopBarElementsColor,
                fontSize = AppTransactionDetailsSectionCategoryTextSize
            )
        )

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(AppTransactionDetailsSectionElementSpacerHeight)
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

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(appColor)
            ) {

                TransactionDetailsContent(
                    transaction = TransactionModelUi(
                        domainModel = TransactionModelDomain(
                            currency = CurrencyModelDomain(
                                code = "usd",
                                fullName = "usd"
                            ),
                            date = Calendar.getInstance().time,
                            details = "dkscsdlkmc",
                            id = "cdsmkcds",
                            type = TransactionType.P2P,
                            usedCardId = "dqwdwwqd",
                            senderId = "sasadasd",
                            priceAmount = 11.0,
                            cardNumber = "1111222233334444",
                        )
                    )
                )
            }
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

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(appColor)
            ) {

                TransactionDetailsContent(
                    transaction = TransactionModelUi(
                        domainModel = TransactionModelDomain(
                            currency = CurrencyModelDomain(
                                code = "usd",
                                fullName = "usd"
                            ),
                            date = Calendar.getInstance().time,
                            details = "dkscsdlkmc",
                            id = "cdsmkcds",
                            type = TransactionType.P2P,
                            usedCardId = "dqwdwwqd",
                            senderId = "sasadasd",
                            priceAmount = 11.0,
                            cardNumber = "1111222233334444",
                        )
                    )
                )
            }
        }
    }
}