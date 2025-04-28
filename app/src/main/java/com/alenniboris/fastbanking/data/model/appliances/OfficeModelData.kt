package com.alenniboris.fastbanking.data.model.appliances

import android.util.Log
import com.alenniboris.fastbanking.domain.model.appliances.OfficeModelDomain

data class OfficeModelData(
    val address: String? = null,
    val workingTime: String? = null
)

fun OfficeModelData.toModelDomain(): OfficeModelDomain? =
    runCatching {
        OfficeModelDomain(
            address = this.address!!,
            workingTime = this.workingTime!!
        )
    }.getOrElse { exception ->
        Log.e(
            "!!!", """
            OfficeModelData.toModelDomain
            ${exception.stackTraceToString()}
        """.trimIndent()
        )
        null
    }