package com.alenniboris.fastbanking.presentation.uikit.values

import com.alenniboris.fastbanking.R
import com.alenniboris.fastbanking.presentation.uikit.theme.AdditionsPicture
import com.alenniboris.fastbanking.presentation.uikit.theme.AtmLocalePicture
import com.alenniboris.fastbanking.presentation.uikit.theme.EnteringIconPicture
import com.alenniboris.fastbanking.presentation.uikit.theme.ExchangePicture
import com.alenniboris.fastbanking.presentation.uikit.theme.HistoryPicture
import com.alenniboris.fastbanking.presentation.uikit.theme.HomePicture
import com.alenniboris.fastbanking.presentation.uikit.theme.PaymentPicture
import com.alenniboris.fastbanking.presentation.uikit.theme.SupportPicture

data class BottomBarModelUi(
    val onClick: () -> Unit,
    val iconId: Int,
    val textId: Int,
    val route: String
)

enum class NotAuthorizedActions {
    Entrance,
    Atms,
    Exchange,
    Help,
    Additions
}

fun NotAuthorizedActions.toBottomBarModel(onClick: () -> Unit) = when (this) {
    NotAuthorizedActions.Entrance ->
        BottomBarModelUi(
            onClick = onClick,
            iconId = EnteringIconPicture,
            textId = R.string.entering_icon_text,
            route = LoginScreenRoute
        )

    NotAuthorizedActions.Atms ->
        BottomBarModelUi(
            onClick = onClick,
            iconId = AtmLocalePicture,
            textId = R.string.atm_locale_icon_text,
            route = AtmMapNotRegisteredUserScreenRoute
        )

    NotAuthorizedActions.Exchange ->
        BottomBarModelUi(
            onClick = onClick,
            iconId = ExchangePicture,
            textId = R.string.exchange_icon_text,
            route = ExchangeScreenRoute
        )

    NotAuthorizedActions.Help ->
        BottomBarModelUi(
            onClick = onClick,
            iconId = SupportPicture,
            textId = R.string.support_icon_text,
            route = HelpScreenRoute
        )

    NotAuthorizedActions.Additions ->
        BottomBarModelUi(
            onClick = onClick,
            iconId = AdditionsPicture,
            textId = R.string.additions_icon_text,
            route = AdditionsScreenRoute
        )
}

enum class AuthorizedActions {
    Main,
    History,
    Payment,
    Additions
}

fun AuthorizedActions.toBottomBarModel(onClick: () -> Unit) = when (this) {
    AuthorizedActions.Main ->
        BottomBarModelUi(
            onClick = onClick,
            iconId = HomePicture,
            textId = R.string.home_icon_text,
            route = MainScreenRoute
        )

    AuthorizedActions.History ->
        BottomBarModelUi(
            onClick = onClick,
            iconId = HistoryPicture,
            textId = R.string.history_icon_text,
            route = HistoryScreenRoute
        )

    AuthorizedActions.Payment ->
        BottomBarModelUi(
            onClick = onClick,
            iconId = PaymentPicture,
            textId = R.string.payment_icon_text,
            route = PaymentScreenRoute
        )

    AuthorizedActions.Additions ->
        BottomBarModelUi(
            onClick = onClick,
            iconId = AdditionsPicture,
            textId = R.string.additions_icon_text,
            route = AdditionsScreenRoute
        )
}