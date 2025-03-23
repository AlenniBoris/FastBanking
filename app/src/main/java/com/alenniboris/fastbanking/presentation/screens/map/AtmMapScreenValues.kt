package com.alenniboris.fastbanking.presentation.screens.map

import com.alenniboris.fastbanking.R

enum class MapScreenMode {
    Map,
    List
}

fun MapScreenMode.toUiString(): Int = when (this) {
    MapScreenMode.Map -> R.string.map_mode_text
    MapScreenMode.List -> R.string.list_mode_text
}