package com.alenniboris.fastbanking.presentation.screens.bank_news.views

import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.alenniboris.fastbanking.R
import com.alenniboris.fastbanking.domain.model.bank_info.BankNewsModelDomain
import com.alenniboris.fastbanking.presentation.model.bank_info.BankNewsModelUi
import com.alenniboris.fastbanking.presentation.screens.bank_news.BankNewsScreenState
import com.alenniboris.fastbanking.presentation.screens.bank_news.BankNewsScreenViewModel
import com.alenniboris.fastbanking.presentation.screens.bank_news.IBankNewsScreenEvent
import com.alenniboris.fastbanking.presentation.screens.bank_news.IBankNewsScreenIntent
import com.alenniboris.fastbanking.presentation.screens.destinations.NewsDetailsScreenDestination
import com.alenniboris.fastbanking.presentation.uikit.theme.BankNewsScreenContentPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.BankNewsScreenItemColumnPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.BankNewsScreenItemFieldShape
import com.alenniboris.fastbanking.presentation.uikit.theme.BankNewsScreenItemImageSize
import com.alenniboris.fastbanking.presentation.uikit.theme.BankNewsScreenItemInnerPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.BankNewsScreenItemOuterPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.BankNewsScreenItemOuterPaddingFirst
import com.alenniboris.fastbanking.presentation.uikit.theme.BankNewsScreenItemTextSize
import com.alenniboris.fastbanking.presentation.uikit.theme.BankNewsScreenItemTitleSize
import com.alenniboris.fastbanking.presentation.uikit.theme.Placeholder
import com.alenniboris.fastbanking.presentation.uikit.theme.TopBarPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.appColor
import com.alenniboris.fastbanking.presentation.uikit.theme.appTopBarElementsColor
import com.alenniboris.fastbanking.presentation.uikit.theme.bodyStyle
import com.alenniboris.fastbanking.presentation.uikit.theme.enterTextFieldColor
import com.alenniboris.fastbanking.presentation.uikit.values.BankNewsScreenRoute
import com.alenniboris.fastbanking.presentation.uikit.views.AppProgressBar
import com.alenniboris.fastbanking.presentation.uikit.views.AppTopBar
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import java.util.Calendar

@Composable
@Destination(route = BankNewsScreenRoute)
fun BankNewsScreen(
    navigator: DestinationsNavigator
) {

    val viewModel = koinViewModel<BankNewsScreenViewModel>()
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
            event.filterIsInstance<IBankNewsScreenEvent.NavigateBack>().collect {
                navigator.popBackStack(
                    route = BankNewsScreenRoute,
                    inclusive = true
                )
            }
        }

        launch {
            event.filterIsInstance<IBankNewsScreenEvent.OpenNewsDetailsScreen>().collect { coming ->
                navigator.navigate(
                    NewsDetailsScreenDestination(
                        id = coming.newsId
                    )
                )
            }
        }

        launch {
            event.filterIsInstance<IBankNewsScreenEvent.ShowToastMessage>().collect { coming ->
                toastMessage?.cancel()
                toastMessage =
                    Toast.makeText(context, context.getString(coming.messageId), Toast.LENGTH_SHORT)
                toastMessage?.show()
            }
        }
    }

    BankNewsScreenUi(
        state = state,
        proceedIntent = proceedIntent
    )
}

@Composable
private fun BankNewsScreenUi(
    state: BankNewsScreenState,
    proceedIntent: (IBankNewsScreenIntent) -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(appColor)
    ) {

        AppTopBar(
            modifier = Modifier
                .padding(TopBarPadding)
                .fillMaxWidth(),
            headerTextString = stringResource(R.string.bank_news_text),
            leftBtnPainter = painterResource(R.drawable.back_icon),
            onLeftBtnClicked = {
                proceedIntent(IBankNewsScreenIntent.NavigateBack)
            }
        )

        if (state.isLoading) {
            AppProgressBar(
                modifier = Modifier
                    .fillMaxSize()
            )
        } else {
            LazyColumn(
                modifier = Modifier.padding(BankNewsScreenContentPadding)
            ) {
                itemsIndexed(state.bankNews) { index, news ->
                    BankNewsItem(
                        index = index,
                        news = news,
                        proceedIntent = proceedIntent
                    )
                }
            }
        }
    }
}

@Composable
private fun BankNewsItem(
    index: Int,
    news: BankNewsModelUi,
    proceedIntent: (IBankNewsScreenIntent) -> Unit
) {

    Row(
        modifier = Modifier
            .padding(
                if (index == 0) BankNewsScreenItemOuterPaddingFirst
                else BankNewsScreenItemOuterPadding
            )
            .fillMaxWidth()
            .background(enterTextFieldColor, BankNewsScreenItemFieldShape)
            .padding(BankNewsScreenItemInnerPadding)
            .clickable {
                proceedIntent(IBankNewsScreenIntent.OpenNewsDetailsPage(news.domainModel.id))
            },
        verticalAlignment = Alignment.CenterVertically
    ) {

        AsyncImage(
            modifier = Modifier
                .clip(BankNewsScreenItemFieldShape)
                .size(BankNewsScreenItemImageSize),
            model = news.domainModel.image,
            placeholder = painterResource(Placeholder),
            error = painterResource(Placeholder),
            contentDescription = stringResource(R.string.bank_news_text),
            contentScale = ContentScale.FillBounds
        )

        Column(
            modifier = Modifier
                .padding(BankNewsScreenItemColumnPadding)
                .weight(1f)
                .fillMaxHeight()
        ) {

            Text(
                text = news.domainModel.title,
                style = bodyStyle.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = BankNewsScreenItemTitleSize,
                    color = appTopBarElementsColor
                )
            )

            Text(
                text = news.dateText,
                style = bodyStyle.copy(
                    fontSize = BankNewsScreenItemTextSize,
                    color = appTopBarElementsColor
                )
            )
        }
    }
}

@Composable
@Preview
private fun BankNewsScreenUiPreview() {

    BankNewsScreenUi(
        state = BankNewsScreenState(
            bankNews = listOf(
                BankNewsModelUi(
                    domainModel = BankNewsModelDomain(
                        id = "1",
                        author = "sss",
                        creationDate = Calendar.getInstance().time,
                        image = "2322121",
                        mainText = "328928328923",
                        reference = Uri.parse("amdoimasdoiasoid"),
                        synopsys = "ajkndjaskndkjnasd",
                        title = "1",
                        isRecommended = true
                    )
                ),
                BankNewsModelUi(
                    domainModel = BankNewsModelDomain(
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
                BankNewsModelUi(
                    domainModel = BankNewsModelDomain(
                        id = "3",
                        author = "bbb",
                        creationDate = Calendar.getInstance().time,
                        image = "2322121",
                        mainText = "328928328923",
                        reference = Uri.parse("amdoimasdoiasoid"),
                        synopsys = ".;;;;;;;;;;;wdwdwd",
                        title = "3",
                        isRecommended = true
                    )
                ),
                BankNewsModelUi(
                    domainModel = BankNewsModelDomain(
                        id = "3",
                        author = "bbb",
                        creationDate = Calendar.getInstance().time,
                        image = "2322121",
                        mainText = "328928328923",
                        reference = Uri.parse("amdoimasdoiasoid"),
                        synopsys = ".;;;;;;;;;;;wdwdwd",
                        title = "3",
                        isRecommended = true
                    )
                ),
                BankNewsModelUi(
                    domainModel = BankNewsModelDomain(
                        id = "3",
                        author = "bbb",
                        creationDate = Calendar.getInstance().time,
                        image = "2322121",
                        mainText = "328928328923",
                        reference = Uri.parse("amdoimasdoiasoid"),
                        synopsys = ".;;;;;;;;;;;wdwdwd",
                        title = "3",
                        isRecommended = true
                    )
                )
            )
        ),
        proceedIntent = {}
    )
}
