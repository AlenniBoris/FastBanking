package com.alenniboris.fastbanking.domain.model.map

data class MapsElementModelDomain(
    val latitude: Float,
    val longitude: Float,
    val type: MapElementType,
    val address: String
)