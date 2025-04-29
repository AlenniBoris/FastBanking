package com.alenniboris.fastbanking.presentation.screens.product_appliances_forms

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.alenniboris.fastbanking.domain.model.appliances.OfficeModelDomain
import com.alenniboris.fastbanking.presentation.uikit.theme.AtmMapListScreenItemBorderShape
import com.alenniboris.fastbanking.presentation.uikit.theme.AtmMapListScreenItemBorderSize
import com.alenniboris.fastbanking.presentation.uikit.theme.FastBankingTheme
import com.alenniboris.fastbanking.presentation.uikit.theme.MapLocationItemContentTextSize
import com.alenniboris.fastbanking.presentation.uikit.theme.MapLocationItemTextPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.MapLocationItemTitleTextSize
import com.alenniboris.fastbanking.presentation.uikit.theme.ProductApplianceScreenOfficeSelectionContainerItemInnerPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.ProductApplianceScreenOfficeSelectionContainerItemOuterPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.appColor
import com.alenniboris.fastbanking.presentation.uikit.theme.appTopBarElementsColor
import com.alenniboris.fastbanking.presentation.uikit.theme.bodyStyle
import com.alenniboris.fastbanking.presentation.uikit.theme.mapLocationItemTextColor
import com.alenniboris.fastbanking.presentation.uikit.views.AppCheckButton

@Composable
fun ApplianceFormOfficeSelectionScreen(
    modifier: Modifier,
    items: List<OfficeModelDomain>,
    currentItem: OfficeModelDomain?,
    onItemClicked: (OfficeModelDomain) -> Unit
) {

    LazyColumn(
        modifier = modifier
    ) {
        items(items) { office ->
            OfficeListItem(
                modifier = Modifier
                    .padding(ProductApplianceScreenOfficeSelectionContainerItemOuterPadding)
                    .border(
                        width = AtmMapListScreenItemBorderSize,
                        color = appTopBarElementsColor,
                        shape = AtmMapListScreenItemBorderShape
                    )
                    .fillMaxWidth()
                    .padding(ProductApplianceScreenOfficeSelectionContainerItemInnerPadding)
                    .clickable {
                        onItemClicked(office)
                    },
                office = office,
                isSelected = currentItem == office
            )
        }
    }
}

@Composable
private fun OfficeListItem(
    modifier: Modifier,
    office: OfficeModelDomain,
    isSelected: Boolean
) {

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Column() {

            Text(
                text = office.address,
                style = bodyStyle.copy(
                    color = mapLocationItemTextColor,
                    fontSize = MapLocationItemTitleTextSize,
                    fontWeight = FontWeight.Bold
                )
            )

            Text(
                modifier = Modifier.padding(MapLocationItemTextPadding),
                text = office.workingTime,
                style = bodyStyle.copy(
                    color = mapLocationItemTextColor,
                    fontSize = MapLocationItemContentTextSize
                )
            )
        }

        AppCheckButton(
            isChecked = isSelected
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
            ApplianceFormOfficeSelectionScreen(
                modifier = Modifier
                    .fillMaxSize()
                    .background(appColor),
                items = listOf(
                    OfficeModelDomain(
                        address = "sdadsasd",
                        workingTime = "dsakdmalkd"
                    ),
                    OfficeModelDomain(
                        address = "213213123",
                        workingTime = "dsakdmalkd"
                    ),
                    OfficeModelDomain(
                        address = "dcasdccasc12313",
                        workingTime = "dsakdmalkd"
                    )
                ),
                currentItem = OfficeModelDomain(
                    address = "213213123",
                    workingTime = "dsakdmalkd"
                )
            ) { }
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
            ApplianceFormOfficeSelectionScreen(
                modifier = Modifier
                    .fillMaxSize()
                    .background(appColor),
                items = listOf(
                    OfficeModelDomain(
                        address = "sdadsasd",
                        workingTime = "dsakdmalkd"
                    ),
                    OfficeModelDomain(
                        address = "213213123",
                        workingTime = "dsakdmalkd"
                    ),
                    OfficeModelDomain(
                        address = "dcasdccasc12313",
                        workingTime = "dsakdmalkd"
                    )
                ),
                currentItem = OfficeModelDomain(
                    address = "213213123",
                    workingTime = "dsakdmalkd"
                )
            ) { }
        }
    }
}