package com.alenniboris.fastbanking.presentation.uikit.theme

import com.alenniboris.fastbanking.R
import com.alenniboris.fastbanking.presentation.uikit.utils.currentThemeMode

val PasswordShowPicture = R.drawable.password_opened_icon

val PasswordHidePicture = R.drawable.password_closed_icon

val BackButtonPicture = R.drawable.back_icon

val ForwardButtonPicture = R.drawable.forward_icon

val EnteringIconPicture = R.drawable.enter_icon

val AtmLocalePicture = R.drawable.atm_locale_icon

val ExchangePicture = R.drawable.exchange_icon

val SupportPicture = R.drawable.support_icon

val AdditionsPicture = R.drawable.additions_icon

val HomePicture = R.drawable.home_icon

val HistoryPicture = R.drawable.history_icon

val PaymentPicture = R.drawable.payment_icon

val Placeholder
    get() = when (currentThemeMode.value.isThemeDark) {
        false -> R.drawable.ic_placeholder_light
        true -> R.drawable.ic_placeholder_dark
    }