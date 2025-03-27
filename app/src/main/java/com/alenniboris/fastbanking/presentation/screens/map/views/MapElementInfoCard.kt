package com.alenniboris.fastbanking.presentation.screens.map.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
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
import com.alenniboris.fastbanking.presentation.model.MapsElementModelUi
import com.alenniboris.fastbanking.presentation.model.toTypeIcon
import com.alenniboris.fastbanking.presentation.model.toTypeTitle
import com.alenniboris.fastbanking.presentation.uikit.theme.MapLocationItemContentTextSize
import com.alenniboris.fastbanking.presentation.uikit.theme.MapLocationItemTextPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.MapLocationItemTitleTextSize
import com.alenniboris.fastbanking.presentation.uikit.theme.appColor
import com.alenniboris.fastbanking.presentation.uikit.theme.bodyStyle
import com.alenniboris.fastbanking.presentation.uikit.theme.mapLocationItemTextColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapElementInfoCard(
    modifier: Modifier = Modifier,
    mapElement: MapsElementModelUi,
    onDismiss: () -> Unit
) {

    BasicAlertDialog(
        onDismissRequest = { onDismiss() },
        content = {
            Column(
                modifier = modifier.background(appColor),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    painter = painterResource(mapElement.type.toTypeIcon()),
                    tint = mapLocationItemTextColor,
                    contentDescription = stringResource(mapElement.type.toTypeTitle())
                )

                Text(
                    text = stringResource(mapElement.type.toTypeTitle()),
                    style = bodyStyle.copy(
                        color = mapLocationItemTextColor,
                        fontSize = MapLocationItemTitleTextSize,
                        fontWeight = FontWeight.Bold
                    )
                )

                Text(
                    modifier = Modifier.padding(MapLocationItemTextPadding),
                    text = mapElement.addressString,
                    style = bodyStyle.copy(
                        color = mapLocationItemTextColor,
                        fontSize = MapLocationItemContentTextSize
                    )
                )

                Text(
                    modifier = Modifier.padding(MapLocationItemTextPadding),
                    text = mapElement.workingTime,
                    style = bodyStyle.copy(
                        color = mapLocationItemTextColor,
                        fontSize = MapLocationItemContentTextSize
                    )
                )
            }
        }
    )
}

@Composable
@Preview
fun MapElementInfoCardPreview() {
    MapElementInfoCard(
        mapElement = MapsElementModelUi(
            domainModel = MapsElementModelDomain(
                latitude = 0.0,
                longitude = 0.0,
                type = MapElementType.ATM,
                address = "First",
                workingTime = "0-1"
            )
        ),
        onDismiss = {}
    )
}