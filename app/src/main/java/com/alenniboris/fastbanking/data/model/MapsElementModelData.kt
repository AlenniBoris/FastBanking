package com.alenniboris.fastbanking.data.model

import android.util.Log
import com.alenniboris.fastbanking.domain.model.map.MapsElementModelDomain
import com.alenniboris.fastbanking.domain.model.map.toMapElementType

data class MapsElementModelData(
    val latitude: String?,
    val longitude: String?,
    val type: String?,
    val address: String?
) {
    val hasSomeValueMissing: Boolean
        get() = latitude == null
                || longitude == null
                || type == null
                || address == null
}

fun MapsElementModelData.toModelDomain(): MapsElementModelDomain? = runCatching {
    MapsElementModelDomain(
        latitude = this.latitude?.toFloat()!!,
        longitude = this.longitude?.toFloat()!!,
        type = this.type?.toMapElementType()!!,
        address = this.address!!
    )
}.getOrElse { exception ->
    Log.e(
        "!!!", """
        MapsElementModelData.toModelDomain, 
        ${exception.stackTraceToString()}
    """.trimIndent()
    )
    null
}