package com.alenniboris.fastbanking.presentation.screens.map.views

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.alenniboris.fastbanking.domain.model.map.MapElementType
import com.alenniboris.fastbanking.domain.model.map.MapsElementModelDomain
import com.alenniboris.fastbanking.presentation.model.bank_info.MapsElementModelUi
import com.alenniboris.fastbanking.presentation.model.bank_info.toTypeIcon
import com.alenniboris.fastbanking.presentation.model.bank_info.toTypeTitle
import com.alenniboris.fastbanking.presentation.uikit.theme.AtmMapListScreenItemBorderShape
import com.alenniboris.fastbanking.presentation.uikit.theme.AtmMapListScreenItemBorderSize
import com.alenniboris.fastbanking.presentation.uikit.theme.AtmMapListScreenItemIconPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.AtmMapListScreenItemInnerPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.AtmMapListScreenItemPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.MapLocationItemContentTextSize
import com.alenniboris.fastbanking.presentation.uikit.theme.MapLocationItemTextPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.MapLocationItemTitleTextSize
import com.alenniboris.fastbanking.presentation.uikit.theme.appColor
import com.alenniboris.fastbanking.presentation.uikit.theme.appTopBarElementsColor
import com.alenniboris.fastbanking.presentation.uikit.theme.bodyStyle
import com.alenniboris.fastbanking.presentation.uikit.theme.mapLocationItemTextColor

@Composable
fun AtmListUi(
    modifier: Modifier = Modifier,
    items: List<MapsElementModelUi>,
    onItemClicked: (MapsElementModelUi) -> Unit = {}
) {

    LazyColumn(modifier = modifier) {
        items(items) { mapElement ->
            AtmListItem(
                modifier = Modifier
                    .padding(AtmMapListScreenItemPadding)
                    .border(
                        width = AtmMapListScreenItemBorderSize,
                        color = appTopBarElementsColor,
                        shape = AtmMapListScreenItemBorderShape
                    )
                    .fillMaxWidth()
                    .padding(AtmMapListScreenItemInnerPadding),
                element = mapElement,
                onItemClicked = { onItemClicked(mapElement) }
            )
        }
    }
}

@Composable
private fun AtmListItem(
    modifier: Modifier = Modifier,
    element: MapsElementModelUi,
    onItemClicked: () -> Unit
) {

    Row(
        modifier = modifier
            .background(appColor)
            .clickable { onItemClicked() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier.padding(AtmMapListScreenItemIconPadding),
            painter = painterResource(element.type.toTypeIcon()),
            tint = mapLocationItemTextColor,
            contentDescription = stringResource(element.type.toTypeTitle())
        )

        Column(
            modifier = Modifier.fillMaxWidth()
        ) {

            Text(
                text = stringResource(element.type.toTypeTitle()),
                style = bodyStyle.copy(
                    color = mapLocationItemTextColor,
                    fontSize = MapLocationItemTitleTextSize,
                    fontWeight = FontWeight.Bold
                )
            )

            Text(
                modifier = Modifier.padding(MapLocationItemTextPadding),
                text = element.addressString,
                style = bodyStyle.copy(
                    color = mapLocationItemTextColor,
                    fontSize = MapLocationItemContentTextSize
                )
            )

            Text(
                modifier = Modifier.padding(MapLocationItemTextPadding),
                text = element.workingTime,
                style = bodyStyle.copy(
                    color = mapLocationItemTextColor,
                    fontSize = MapLocationItemContentTextSize
                )
            )
        }
    }
}

@Composable
@Preview
private fun AtmListScreenPreview() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(appColor)

    ) {
        AtmListItem(
            modifier = Modifier
                .padding(AtmMapListScreenItemPadding)
                .border(
                    width = AtmMapListScreenItemBorderSize,
                    color = appTopBarElementsColor,
                    shape = AtmMapListScreenItemBorderShape
                )
                .fillMaxWidth()
                .padding(AtmMapListScreenItemInnerPadding),
            element = MapsElementModelUi(
                domainModel = MapsElementModelDomain(
                    latitude = 0.111,
                    longitude = 3.222,
                    type = MapElementType.ATM,
                    address = "sddsd",
                    workingTime = "11-22"
                )
            ),
            onItemClicked = {}
        )
    }
}