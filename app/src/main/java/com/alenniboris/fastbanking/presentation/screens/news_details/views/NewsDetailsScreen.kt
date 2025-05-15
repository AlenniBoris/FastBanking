package com.alenniboris.fastbanking.presentation.screens.news_details.views

import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import com.alenniboris.fastbanking.presentation.screens.news_details.INewsDetailsScreenEvent
import com.alenniboris.fastbanking.presentation.screens.news_details.INewsDetailsScreenIntent
import com.alenniboris.fastbanking.presentation.screens.news_details.NewsDetailsScreenState
import com.alenniboris.fastbanking.presentation.screens.news_details.NewsDetailsScreenViewModel
import com.alenniboris.fastbanking.presentation.uikit.theme.NewsDetailsScreenContentPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.NewsDetailsScreenHeaderTextSize
import com.alenniboris.fastbanking.presentation.uikit.theme.NewsDetailsScreenImageFieldShape
import com.alenniboris.fastbanking.presentation.uikit.theme.NewsDetailsScreenImagePadding
import com.alenniboris.fastbanking.presentation.uikit.theme.NewsDetailsScreenMainTextSize
import com.alenniboris.fastbanking.presentation.uikit.theme.NewsDetailsScreenSmallTextSize
import com.alenniboris.fastbanking.presentation.uikit.theme.NewsDetailsScreenTextPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.Placeholder
import com.alenniboris.fastbanking.presentation.uikit.theme.TopBarPadding
import com.alenniboris.fastbanking.presentation.uikit.theme.appColor
import com.alenniboris.fastbanking.presentation.uikit.theme.appTopBarElementsColor
import com.alenniboris.fastbanking.presentation.uikit.theme.bodyStyle
import com.alenniboris.fastbanking.presentation.uikit.values.NewsDetailsScreenRoute
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

@Composable
@Destination(route = NewsDetailsScreenRoute)
fun NewsDetailsScreen(
    id: String,
    navigator: DestinationsNavigator
) {

    val viewModel = koinViewModel<NewsDetailsScreenViewModel>() { parametersOf(id) }
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
            event.filterIsInstance<INewsDetailsScreenEvent.NavigateBack>().collect {
                navigator.popBackStack()
            }
        }

        launch {
            event.filterIsInstance<INewsDetailsScreenEvent.ShowToastMessage>().collect { coming ->
                toastMessage?.cancel()
                toastMessage =
                    Toast.makeText(context, context.getString(coming.messageId), Toast.LENGTH_SHORT)
                toastMessage?.show()
            }
        }
    }

    NewsDetailsScreenUi(
        state = state,
        proceedIntent = proceedIntent
    )
}

@Composable
private fun NewsDetailsScreenUi(
    state: NewsDetailsScreenState,
    proceedIntent: (INewsDetailsScreenIntent) -> Unit
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
                proceedIntent(INewsDetailsScreenIntent.NavigateBack)
            }
        )

        if (state.isLoading) {
            AppProgressBar(
                modifier = Modifier.fillMaxSize()
            )
        } else {

            state.news?.let { news ->

                NewsDetailsContent(
                    news = news
                )
            } ?: AppEmptyScreen(
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@Composable
private fun NewsDetailsContent(
    news: BankNewsModelUi
) {

    Column(
        modifier = Modifier
            .padding(NewsDetailsScreenContentPadding)
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {

        Text(
            modifier = Modifier.fillMaxWidth(),
            text = news.domainModel.title,
            style = bodyStyle.copy(
                color = appTopBarElementsColor,
                fontSize = NewsDetailsScreenHeaderTextSize,
                fontWeight = FontWeight.Bold
            )
        )

        AsyncImage(
            modifier = Modifier
                .padding(NewsDetailsScreenImagePadding)
                .clip(NewsDetailsScreenImageFieldShape)
                .fillMaxWidth(),
            model = news.domainModel.image,
            error = painterResource(Placeholder),
            placeholder = painterResource(Placeholder),
            contentDescription = stringResource(R.string.bank_news_text),
        )

        Row(
            modifier = Modifier
                .padding(NewsDetailsScreenTextPadding)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Text(
                modifier = Modifier.width(IntrinsicSize.Min),
                text = news.domainModel.author,
                style = bodyStyle.copy(
                    color = appTopBarElementsColor,
                    fontSize = NewsDetailsScreenSmallTextSize
                )
            )

            Text(
                text = news.dateText,
                style = bodyStyle.copy(
                    color = appTopBarElementsColor,
                    fontSize = NewsDetailsScreenSmallTextSize
                )
            )
        }

        Text(
            modifier = Modifier.fillMaxWidth(),
            text = news.formattedMainText,
            style = bodyStyle.copy(
                color = appTopBarElementsColor,
                fontWeight = FontWeight.Normal,
                fontSize = NewsDetailsScreenMainTextSize
            )
        )
    }
}

@Composable
@Preview
private fun NewsDetailsScreenUiPreview() {

//    NewsDetailsScreenUi(
//        state = NewsDetailsScreenState(
//            news = null,
//            isLoading = false
//        ),
//        proceedIntent = {}
//    )

//    NewsDetailsScreenUi(
//        state = NewsDetailsScreenState(
//            news = null,
//            isLoading = true
//        ),
//        proceedIntent = {}
//    )

    NewsDetailsScreenUi(
        state = NewsDetailsScreenState(
            news = BankNewsModelUi(
                domainModel = BankNewsModelDomain(
                    id = "3",
                    author = "bbb",
                    creationDate = Calendar.getInstance().time,
                    image = "2322121",
                    mainText = "3289283289|||23|sxsajhbascxhjbsxjhbaxsjbjasxbjhsb",
                    reference = Uri.parse("amdoimasdoiasoid"),
                    synopsys = ".;;;;;;;;;;;wdwdwd",
                    title = "3",
                    isRecommended = true
                )
            ),
            isLoading = false
        ),
        proceedIntent = {}
    )
}