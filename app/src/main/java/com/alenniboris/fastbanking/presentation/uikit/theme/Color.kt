package com.alenniboris.fastbanking.presentation.uikit.theme

import androidx.compose.ui.graphics.Color


private val AppColorLight = Color(0xffdbdbdb)
private val AppColorDark = Color(0xff404040)

val appColor
    get() = when (currentThemeMode.value.isThemeDark) {
        false -> AppColorLight
        true -> AppColorDark
    }

// Text field (LoginScreen, RegisterScreen)

private val EnterTextFieldColorLight = Color(0xffbcb8b8)
private val EnterTextFieldColorDark = Color(0xff1c1c1c)

val enterTextFieldColor
    get() = when (currentThemeMode.value.isThemeDark) {
        false -> EnterTextFieldColorLight
        true -> EnterTextFieldColorDark
    }

private val EnterTextFieldTextColorLight = Color(0xff000000)
private val EnterTextFieldTextColorDark = Color(0xffffffff)

val enterTextFieldTextColor
    get() = when (currentThemeMode.value.isThemeDark) {
        false -> EnterTextFieldTextColorLight
        true -> EnterTextFieldTextColorDark
    }


val selectedTextBackgroundColor = Color(0xfff9e753)

val selectedTextHandlesColor = Color(0xfff9e753)

// TopBar

private val AppTopBarElementsColorLight = Color(0xff000000)
private val AppTopBarElementsColorDark = Color(0xffffffff)
val appTopBarElementsColor
    get() = when (currentThemeMode.value.isThemeDark) {
        false -> AppTopBarElementsColorLight
        true -> AppTopBarElementsColorDark
    }