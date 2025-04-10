package com.alenniboris.fastbanking.presentation.uikit.theme

import androidx.compose.ui.graphics.Color
import com.alenniboris.fastbanking.presentation.uikit.utils.currentThemeMode


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

// Process progress bar

val progressBarDoneProcess = Color(0xfff9e753)
private val ProgressBarNotDoneProcessLight = Color(0xff000000)
private val ProgressBarNotDoneProcessDark = Color(0xffffffff)
val progressBarNotDoneProcess
    get() = when (currentThemeMode.value.isThemeDark) {
        false -> ProgressBarNotDoneProcessLight
        true -> ProgressBarNotDoneProcessDark
    }

// App filter

private val AppFilterColorLight = Color(0xffbcb8b8)
private val AppFilterColorDark = Color(0xff1c1c1c)

val appFilterColor
    get() = when (currentThemeMode.value.isThemeDark) {
        false -> AppFilterColorLight
        true -> AppFilterColorDark
    }

private val AppFilterTextColorLight = Color(0xff000000)
private val AppFilterTextColorDark = Color(0xffffffff)

val appFilterTextColor
    get() = when (currentThemeMode.value.isThemeDark) {
        false -> AppFilterTextColorLight
        true -> AppFilterTextColorDark
    }

// Map location item

private val MapLocationItemColorLight = Color(0xffbcb8b8)
private val MapLocationItemColorDark = Color(0xff1c1c1c)

val mapLocationItemColor
    get() = when (currentThemeMode.value.isThemeDark) {
        false -> MapLocationItemColorLight
        true -> MapLocationItemColorDark
    }


private val MapLocationItemTextColorLight = Color(0xff000000)
private val MapLocationItemTextColorDark = Color(0xffffffff)

val mapLocationItemTextColor
    get() = when (currentThemeMode.value.isThemeDark) {
        false -> MapLocationItemTextColorLight
        true -> MapLocationItemTextColorDark
    }

val locationPinColor = Color(0xfff9e753)

// Atm map not registered user

private val AtmMapScreenButtonNotSelectedColorLight = Color(0xffbcb8b8)
private val AtmMapScreenButtonNotSelectedColorDark = Color(0xff1c1c1c)

val appButtonRowButtonNotSelectedColor
    get() = when (currentThemeMode.value.isThemeDark) {
        false -> AtmMapScreenButtonNotSelectedColorLight
        true -> AtmMapScreenButtonNotSelectedColorDark
    }

private val AtmMapScreenButtonSelectedColorLight = Color(0xFF555559)
private val AtmMapScreenButtonSelectedColorDark = Color(0xFFB2ADAD)

val appButtonRowButtonSelectedColor
    get() = when (currentThemeMode.value.isThemeDark) {
        false -> AtmMapScreenButtonSelectedColorLight
        true -> AtmMapScreenButtonSelectedColorDark
    }

private val AtmMapScreenButtonTextNotSelectedColorLight = Color(0xff000000)
private val AtmMapScreenButtonTextNotSelectedColorDark = Color(0xffffffff)

val appButtonRowButtonTextNotSelectedColor
    get() = when (currentThemeMode.value.isThemeDark) {
        false -> AtmMapScreenButtonTextNotSelectedColorLight
        true -> AtmMapScreenButtonTextNotSelectedColorDark
    }

private val AtmMapScreenButtonTextSelectedColorLight = Color(0xffffffff)
private val AtmMapScreenButtonTextSelectedColorDark = Color(0xff000000)
val appButtonRowButtonTextSelectedColor
    get() = when (currentThemeMode.value.isThemeDark) {
        false -> AtmMapScreenButtonTextSelectedColorLight
        true -> AtmMapScreenButtonTextSelectedColorDark
    }

// App dropdown menu

private val AppDropdownColorLight = Color(0xffbcb8b8)
private val AppDropdownColorDark = Color(0xff1c1c1c)

val appDropdownColor
    get() = when (currentThemeMode.value.isThemeDark) {
        false -> AppDropdownColorLight
        true -> AppDropdownColorDark
    }

//Bottom bar

private val BottomBarColorLight = Color(0xffbcb8b8)
private val BottomBarColorDark = Color(0xff1c1c1c)

val bottomBarColor
    get() = when (currentThemeMode.value.isThemeDark) {
        false -> BottomBarColorLight
        true -> BottomBarColorDark
    }

// currency rate item
private val CurrencyRateItemTextColorLight = Color(0xff000000)
private val CurrencyRateItemTextColorDark = Color(0xffffffff)

val CurrencyRateItemTextColor
    get() = when (currentThemeMode.value.isThemeDark) {
        false -> CurrencyRateItemTextColorLight
        true -> CurrencyRateItemTextColorDark
    }


// Main screen

private val MainScreenItemColorLight = Color(0xffbcb8b8)
private val MainScreenItemColorDark = Color(0xD21C1C1C)

val mainScreenItemColor
    get() = when (currentThemeMode.value.isThemeDark) {
        false -> MainScreenItemColorLight
        true -> MainScreenItemColorDark
    }

private val MainScreenOnItemColorLight = Color(0x60F5F4F4)
private val MainScreenOnItemColorDark = Color(0x520C0C0C)

val mainScreenOnItemColor
    get() = when (currentThemeMode.value.isThemeDark) {
        false -> MainScreenOnItemColorLight
        true -> MainScreenOnItemColorDark
    }

private val MainScreenTextColorLight = Color(0xff000000)
private val MainScreenTextColorDark = Color(0xffffffff)

val mainScreenTextColor
    get() = when (currentThemeMode.value.isThemeDark) {
        false -> MainScreenTextColorLight
        true -> MainScreenTextColorDark
    }

val mainScreenRecommendedNewsOnPictureTextColor = Color(0xffffffff)

val TransparentColor = Color.Transparent
val BlackColor = Color(0xBC000000)

val mainScreenProductsButtonColor = Color(0xfff9e753)
val mainScreenProductsButtonIconTintColor = Color(0xBC000000)

// main screen filter item

private val MainScreenFilterItemColorLight = Color(0xA4FDFDFD)
private val MainScreenFilterItemColorDark = Color(0x520C0C0C)

val mainScreenFilterItemColor
    get() = when (currentThemeMode.value.isThemeDark) {
        false -> MainScreenFilterItemColorLight
        true -> MainScreenFilterItemColorDark
    }

private val MainScreenFilterItemSelectedColorLight = Color(0xD51C1C1C)
private val MainScreenFilterItemSelectedColorDark = Color(0xA4FDFDFD)

val mainScreenFilterItemSelectedColor
    get() = when (currentThemeMode.value.isThemeDark) {
        false -> MainScreenFilterItemSelectedColorLight
        true -> MainScreenFilterItemSelectedColorDark
    }

private val MainScreenFilterItemTextColorLight = Color(0xFF000000)
private val MainScreenFilterItemTextColorDark = Color(0xFFFCFCFC)

val mainScreenFilterItemTextColor
    get() = when (currentThemeMode.value.isThemeDark) {
        false -> MainScreenFilterItemTextColorLight
        true -> MainScreenFilterItemTextColorDark
    }

private val MainScreenFilterItemSelectedTextColorLight = Color(0xFFFCFCFC)
private val MainScreenFilterItemSelectedTextColorDark = Color(0xFF000000)

val mainScreenFilterItemSelectedTextColor
    get() = when (currentThemeMode.value.isThemeDark) {
        false -> MainScreenFilterItemSelectedTextColorLight
        true -> MainScreenFilterItemSelectedTextColorDark
    }

// main screen product section
val mainScreenProductSectionElementOrderChanger = Color(0xFF0363F4)