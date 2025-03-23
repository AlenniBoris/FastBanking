package com.alenniboris.fastbanking.domain.model.map

data class MapsElementModelDomain(
    val latitude: Double,
    val longitude: Double,
    val type: MapElementType,
    val address: String,
    val workingTime: String
)