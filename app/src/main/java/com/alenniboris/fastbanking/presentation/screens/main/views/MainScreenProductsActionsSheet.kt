package com.alenniboris.fastbanking.presentation.screens.main.views

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import com.alenniboris.fastbanking.presentation.screens.main.ActionsWithProducts
import com.alenniboris.fastbanking.presentation.screens.main.IMainScreenIntent
import com.alenniboris.fastbanking.presentation.screens.main.toUiPicture
import com.alenniboris.fastbanking.presentation.screens.main.toUiString
import com.alenniboris.fastbanking.presentation.uikit.theme.FastBankingTheme
import com.alenniboris.fastbanking.presentation.uikit.theme.FilterContainerPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.FilterDragHandleHeight
import com.alenniboris.fastbanking.presentation.uikit.theme.FilterDragHandlePadding
import com.alenniboris.fastbanking.presentation.uikit.theme.FilterDragHandleShape
import com.alenniboris.fastbanking.presentation.uikit.theme.FilterDragHandleWidth
import com.alenniboris.fastbanking.presentation.uikit.theme.FilterSheetShape
import com.alenniboris.fastbanking.presentation.uikit.theme.FilterSheetTonalElevation
import com.alenniboris.fastbanking.presentation.uikit.theme.MainScreenContentPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.MainScreenProductsActionSheetElementEndPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.MainScreenProductsActionSheetElementInnerPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.MainScreenProductsActionSheetElementPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.MainScreenProductsActionSheetElementShape
import com.alenniboris.fastbanking.presentation.uikit.theme.MainScreenProductsActionSheetElementStartPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.MainScreenProductsActionSheetHeaderFontSize
import com.alenniboris.fastbanking.presentation.uikit.theme.MainScreenProductsActionSheetTextFontSize
import com.alenniboris.fastbanking.presentation.uikit.theme.MainScreenProductsActionSheetTextTopPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.appColor
import com.alenniboris.fastbanking.presentation.uikit.theme.appTopBarElementsColor
import com.alenniboris.fastbanking.presentation.uikit.theme.bodyStyle
import com.alenniboris.fastbanking.presentation.uikit.theme.mainScreenItemColor
import com.alenniboris.fastbanking.presentation.uikit.theme.mainScreenTextColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreenProductsActionsSheet(
    proceedIntent: (IMainScreenIntent) -> Unit
) {

    ModalBottomSheet(
        onDismissRequest = {
            proceedIntent(IMainScreenIntent.UpdateActionsWithProductsSheetVisibility)
        },
        sheetState = rememberModalBottomSheetState(),
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

        Column(
            modifier = Modifier
                .padding(FilterContainerPadding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {

            Text(
                modifier = Modifier.padding(MainScreenProductsActionSheetElementPadding),
                text = stringResource(R.string.new_product_text),
                style = bodyStyle.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = MainScreenProductsActionSheetHeaderFontSize,
                    color = mainScreenTextColor
                )
            )

            MainScreenProductsActionsItem(
                modifier = Modifier
                    .padding(MainScreenProductsActionSheetElementPadding)
                    .clip(MainScreenProductsActionSheetElementShape)
                    .fillMaxWidth()
                    .background(mainScreenItemColor)
                    .padding(MainScreenProductsActionSheetElementInnerPadding)
                    .clickable {
                        proceedIntent(
                            IMainScreenIntent.ProceedProductAction(
                                ActionsWithProducts.GET_OWN_CARD
                            )
                        )
                    },
                action = ActionsWithProducts.GET_OWN_CARD
            )

            Row(
                modifier = Modifier.fillMaxWidth()
            ) {

                MainScreenProductsActionsItem(
                    modifier = Modifier
                        .weight(1f)
                        .padding(MainScreenProductsActionSheetElementPadding)
                        .padding(MainScreenProductsActionSheetElementEndPadding)
                        .clip(MainScreenProductsActionSheetElementShape)
                        .fillMaxWidth()
                        .background(mainScreenItemColor)
                        .padding(MainScreenProductsActionSheetElementInnerPadding)
                        .clickable {
                            proceedIntent(
                                IMainScreenIntent.ProceedProductAction(
                                    ActionsWithProducts.SEND_APPLY_FOR_CREDIT
                                )
                            )
                        },
                    action = ActionsWithProducts.SEND_APPLY_FOR_CREDIT,
                    isHorizontal = false
                )

                MainScreenProductsActionsItem(
                    modifier = Modifier
                        .weight(1f)
                        .padding(MainScreenProductsActionSheetElementPadding)
                        .padding(MainScreenProductsActionSheetElementStartPadding)
                        .clip(MainScreenProductsActionSheetElementShape)
                        .fillMaxWidth()
                        .background(mainScreenItemColor)
                        .padding(MainScreenProductsActionSheetElementInnerPadding)
                        .clickable {
                            proceedIntent(
                                IMainScreenIntent.ProceedProductAction(
                                    ActionsWithProducts.OPEN_ONLINE_DEPOSIT
                                )
                            )
                        },
                    action = ActionsWithProducts.OPEN_ONLINE_DEPOSIT,
                    isHorizontal = false
                )
            }
        }
    }
}

@Composable
private fun MainScreenProductsActionsItem(
    modifier: Modifier,
    action: ActionsWithProducts,
    isHorizontal: Boolean = true
) {

    if (isHorizontal) {

        Row(
            modifier = modifier,
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Icon(
                painter = painterResource(action.toUiPicture()),
                tint = mainScreenTextColor,
                contentDescription = stringResource(action.toUiString())
            )

            Text(
                text = stringResource(action.toUiString()),
                style = bodyStyle.copy(
                    fontSize = MainScreenProductsActionSheetTextFontSize,
                    color = mainScreenTextColor,
                    fontWeight = FontWeight.Bold
                )
            )

            Icon(
                painter = painterResource(R.drawable.forward_icon),
                tint = mainScreenTextColor,
                contentDescription = stringResource(R.string.forward_icon_description)
            )
        }
    } else {

        Column(
            modifier = modifier
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Icon(
                    painter = painterResource(action.toUiPicture()),
                    tint = mainScreenTextColor,
                    contentDescription = stringResource(action.toUiString())
                )

                Icon(
                    painter = painterResource(R.drawable.forward_icon),
                    tint = mainScreenTextColor,
                    contentDescription = stringResource(R.string.forward_icon_description)
                )
            }

            Text(
                modifier = Modifier.padding(MainScreenProductsActionSheetTextTopPadding),
                text = stringResource(action.toUiString()),
                style = bodyStyle.copy(
                    fontSize = MainScreenProductsActionSheetTextFontSize,
                    color = mainScreenTextColor,
                    fontWeight = FontWeight.Bold
                )
            )
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
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(mainScreenItemColor)
                    .padding(MainScreenContentPadding)
            ) {

                MainScreenProductsActionsItem(
                    modifier = Modifier
                        .padding(MainScreenProductsActionSheetElementPadding)
                        .clip(MainScreenProductsActionSheetElementShape)
                        .fillMaxWidth()
                        .background(mainScreenItemColor)
                        .padding(MainScreenProductsActionSheetElementInnerPadding)
                        .clickable {},
                    action = ActionsWithProducts.GET_OWN_CARD
                )

                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {

                    MainScreenProductsActionsItem(
                        modifier = Modifier
                            .weight(1f)
                            .padding(MainScreenProductsActionSheetElementPadding)
                            .padding(MainScreenProductsActionSheetElementEndPadding)
                            .clip(MainScreenProductsActionSheetElementShape)
                            .fillMaxWidth()
                            .background(mainScreenItemColor)
                            .padding(MainScreenProductsActionSheetElementInnerPadding)
                            .clickable {},
                        action = ActionsWithProducts.SEND_APPLY_FOR_CREDIT,
                        isHorizontal = false
                    )

                    MainScreenProductsActionsItem(
                        modifier = Modifier
                            .weight(1f)
                            .padding(MainScreenProductsActionSheetElementPadding)
                            .padding(MainScreenProductsActionSheetElementStartPadding)
                            .clip(MainScreenProductsActionSheetElementShape)
                            .fillMaxWidth()
                            .background(mainScreenItemColor)
                            .padding(MainScreenProductsActionSheetElementInnerPadding)
                            .clickable {},
                        action = ActionsWithProducts.OPEN_ONLINE_DEPOSIT,
                        isHorizontal = false
                    )
                }
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
                    .background(mainScreenItemColor)
                    .padding(MainScreenContentPadding)
            ) {

                MainScreenProductsActionsItem(
                    modifier = Modifier
                        .padding(MainScreenProductsActionSheetElementPadding)
                        .clip(MainScreenProductsActionSheetElementShape)
                        .fillMaxWidth()
                        .background(mainScreenItemColor)
                        .padding(MainScreenProductsActionSheetElementInnerPadding)
                        .clickable {},
                    action = ActionsWithProducts.GET_OWN_CARD
                )

                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {

                    MainScreenProductsActionsItem(
                        modifier = Modifier
                            .weight(1f)
                            .padding(MainScreenProductsActionSheetElementPadding)
                            .padding(MainScreenProductsActionSheetElementEndPadding)
                            .clip(MainScreenProductsActionSheetElementShape)
                            .fillMaxWidth()
                            .background(mainScreenItemColor)
                            .padding(MainScreenProductsActionSheetElementInnerPadding)
                            .clickable {},
                        action = ActionsWithProducts.SEND_APPLY_FOR_CREDIT,
                        isHorizontal = false
                    )

                    MainScreenProductsActionsItem(
                        modifier = Modifier
                            .weight(1f)
                            .padding(MainScreenProductsActionSheetElementPadding)
                            .padding(MainScreenProductsActionSheetElementStartPadding)
                            .clip(MainScreenProductsActionSheetElementShape)
                            .fillMaxWidth()
                            .background(mainScreenItemColor)
                            .padding(MainScreenProductsActionSheetElementInnerPadding)
                            .clickable {},
                        action = ActionsWithProducts.OPEN_ONLINE_DEPOSIT,
                        isHorizontal = false
                    )
                }
            }
        }
    }
}