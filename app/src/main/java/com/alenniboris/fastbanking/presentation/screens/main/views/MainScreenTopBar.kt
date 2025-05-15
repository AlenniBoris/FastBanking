package com.alenniboris.fastbanking.presentation.screens.main.views

import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.alenniboris.fastbanking.R
import com.alenniboris.fastbanking.domain.model.bank_info.BankNewsModelDomain
import com.alenniboris.fastbanking.presentation.screens.main.IMainScreenIntent
import com.alenniboris.fastbanking.presentation.uikit.theme.BlackColor
import com.alenniboris.fastbanking.presentation.uikit.theme.MainScreenRecommendationsPictureTextSize
import com.alenniboris.fastbanking.presentation.uikit.theme.MainScreenRecommendationsTextSize
import com.alenniboris.fastbanking.presentation.uikit.theme.MainScreenRecommendedNewsContainerGradientEnd
import com.alenniboris.fastbanking.presentation.uikit.theme.MainScreenRecommendedNewsContainerGradientStart
import com.alenniboris.fastbanking.presentation.uikit.theme.MainScreenRecommendedNewsContainerHeight
import com.alenniboris.fastbanking.presentation.uikit.theme.MainScreenRecommendedNewsContainerShape
import com.alenniboris.fastbanking.presentation.uikit.theme.MainScreenRecommendedNewsContainerWidth
import com.alenniboris.fastbanking.presentation.uikit.theme.MainScreenRecommendedNewsFirstItemPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.MainScreenRecommendedNewsHeaderPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.MainScreenRecommendedNewsItemPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.MainScreenTopBarButtonsPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.Placeholder
import com.alenniboris.fastbanking.presentation.uikit.theme.TransparentColor
import com.alenniboris.fastbanking.presentation.uikit.theme.appColor
import com.alenniboris.fastbanking.presentation.uikit.theme.appTopBarElementsColor
import com.alenniboris.fastbanking.presentation.uikit.theme.bodyStyle
import com.alenniboris.fastbanking.presentation.uikit.theme.mainScreenItemColor
import com.alenniboris.fastbanking.presentation.uikit.theme.mainScreenRecommendedNewsOnPictureTextColor
import com.alenniboris.fastbanking.presentation.uikit.theme.mainScreenTextColor
import com.alenniboris.fastbanking.presentation.uikit.views.AppIconButton
import com.alenniboris.fastbanking.presentation.uikit.views.AppProgressBar
import java.util.Calendar

@Composable
fun MainScreenTopBar(
    isLoading: Boolean,
    elements: List<BankNewsModelDomain>,
    isVisible: Boolean,
    proceedIntent: (IMainScreenIntent) -> Unit
) {

    Column {

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ) {

            AppIconButton(
                modifier = Modifier
                    .padding(MainScreenTopBarButtonsPadding)
                    .background(shape = CircleShape, color = mainScreenItemColor),
                onClick = {
                    proceedIntent(IMainScreenIntent.OpenHelpScreen)
                },
                iconPainter = painterResource(R.drawable.support_icon),
                tint = appTopBarElementsColor
            )

            AppIconButton(
                modifier = Modifier
                    .padding(MainScreenTopBarButtonsPadding)
                    .background(shape = CircleShape, color = mainScreenItemColor),
                onClick = {
                    proceedIntent(IMainScreenIntent.OpenPersonalDetailsScreen)
                },
                iconPainter = painterResource(R.drawable.personal_icon),
                tint = appTopBarElementsColor
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {

            AppIconButton(
                modifier = Modifier
                    .background(shape = CircleShape, color = mainScreenItemColor),
                onClick = {
                    proceedIntent(IMainScreenIntent.UpdateRecommendedNewsVisibility)
                },
                iconPainter = painterResource(
                    if (isVisible) R.drawable.close_icon
                    else R.drawable.open_icon
                ),
                isAnimated = true,
                tint = appTopBarElementsColor
            )

            Text(
                modifier = Modifier.padding(MainScreenTopBarButtonsPadding),
                text = stringResource(R.string.recommended_text),
                style = bodyStyle.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = MainScreenRecommendationsTextSize,
                    color = mainScreenTextColor
                )
            )
        }

        AnimatedVisibility(
            visible = isVisible
        ) {
            if (isLoading) {
                AppProgressBar(
                    modifier = Modifier
                        .height(MainScreenRecommendedNewsContainerHeight)
                        .fillMaxWidth()
                )
            } else {
                LazyRow {
                    itemsIndexed(elements) { index, element ->
                        RecommendedNewsItem(
                            index = index,
                            element = element,
                            onClick = {
                                proceedIntent(
                                    IMainScreenIntent.OpenRecommendedNewsDetails(element.id)
                                )
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun RecommendedNewsItem(
    index: Int,
    element: BankNewsModelDomain,
    onClick: () -> Unit
) {

    Box(
        modifier = Modifier
            .padding(
                if (index == 0) {
                    MainScreenRecommendedNewsFirstItemPadding
                } else MainScreenRecommendedNewsItemPadding
            )
            .clip(MainScreenRecommendedNewsContainerShape)
            .height(MainScreenRecommendedNewsContainerHeight)
            .width(MainScreenRecommendedNewsContainerWidth)
            .clickable { onClick() }
    ) {
        AsyncImage(
            modifier = Modifier.fillMaxSize(),
            model = element.image,
            placeholder = painterResource(Placeholder),
            contentDescription = stringResource(R.string.bank_news_text),
            error = painterResource(Placeholder),
            contentScale = ContentScale.FillBounds
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            TransparentColor,
                            BlackColor
                        ),
                        startY = MainScreenRecommendedNewsContainerGradientStart,
                        endY = MainScreenRecommendedNewsContainerGradientEnd
                    )
                )
        )

        Text(
            modifier = Modifier
                .padding(MainScreenRecommendedNewsHeaderPadding)
                .fillMaxWidth()
                .align(Alignment.BottomCenter),
            text = element.title,
            style = bodyStyle.copy(
                fontWeight = FontWeight.Bold,
                fontSize = MainScreenRecommendationsPictureTextSize,
                color = mainScreenRecommendedNewsOnPictureTextColor
            )
        )
    }
}

@Composable
@Preview
private fun MainScreenTopBarPreview() {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(appColor)
    ) {

        Column(
            modifier = Modifier.background(mainScreenItemColor)
        ) {
            MainScreenTopBar(
                isLoading = true,
                elements = listOf(
                    BankNewsModelDomain(
                        id = "2",
                        author = "aaaa",
                        creationDate = Calendar.getInstance().time,
                        image = "2322121",
                        mainText = "328928328923",
                        reference = Uri.parse("amdoimasdoiasoid"),
                        synopsys = "jknoiamclsamlas",
                        title = "2",
                        isRecommended = true
                    )
                ),
                isVisible = true,
                proceedIntent = {}
            )
        }

        Spacer(
            modifier = Modifier.height(60.dp)
        )

        Column(
            modifier = Modifier.background(mainScreenItemColor)
        ) {
            MainScreenTopBar(
                isLoading = false,
                elements = listOf(
                    BankNewsModelDomain(
                        id = "2",
                        author = "aaaa",
                        creationDate = Calendar.getInstance().time,
                        image = "2322121",
                        mainText = "328928328923",
                        reference = Uri.parse("amdoimasdoiasoid"),
                        synopsys = "jknoiamclsamlas",
                        title = "2",
                        isRecommended = true
                    )
                ),
                isVisible = true,
                proceedIntent = {}
            )
        }
    }
}