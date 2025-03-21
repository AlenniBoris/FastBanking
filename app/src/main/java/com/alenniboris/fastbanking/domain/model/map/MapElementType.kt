package com.alenniboris.fastbanking.domain.model.map

enum class MapElementType {
    OFFICE,
    ATM,
    OFFICE_WITH_ATM,
    UNDEFINED
}

fun String.toMapElementType() = when (this) {
    "Office" -> MapElementType.OFFICE
    "Atm" -> MapElementType.ATM
    "Office with atm" -> MapElementType.OFFICE_WITH_ATM
    else -> MapElementType.UNDEFINED
}