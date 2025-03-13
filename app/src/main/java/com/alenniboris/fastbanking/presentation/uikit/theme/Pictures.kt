package com.alenniboris.fastbanking.presentation.uikit.theme

import com.alenniboris.fastbanking.R

val PasswordShowPicture
    get() = when (currentThemeMode.value.isThemeDark) {
        false -> R.drawable.password_opened_light_theme
        true -> R.drawable.password_opened_dark_theme
    }


val PasswordHidePicture
    get() = when (currentThemeMode.value.isThemeDark) {
        false -> R.drawable.password_closed_light_theme
        true -> R.drawable.password_closed_dark_theme
    }

val BackButtonPicture
    get() = when (currentThemeMode.value.isThemeDark) {
        false -> R.drawable.back_icon_light
        true -> R.drawable.back_icon_dark
    }

val ForwardButtonPicture
    get() = when (currentThemeMode.value.isThemeDark) {
        false -> R.drawable.forward_icon_light
        true -> R.drawable.forward_icon_dark
    }
