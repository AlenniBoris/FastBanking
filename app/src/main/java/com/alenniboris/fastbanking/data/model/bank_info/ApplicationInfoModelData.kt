package com.alenniboris.fastbanking.data.model.bank_info

import android.util.Log
import com.alenniboris.fastbanking.domain.model.bank_info.ApplicationInfoModelDomain
import java.util.Date

data class ApplicationInfoModelData(
    val verison: String?,
    val updateDate: String?,
    val versionUpdates: String?
) {
    val hasSomeValueMissing: Boolean
        get() = verison == null
                || updateDate == null
                || versionUpdates == null
}

fun ApplicationInfoModelData.toModelDomain(): ApplicationInfoModelDomain? =
    runCatching {

        val date = Date(this.updateDate?.toLong()!!)

        ApplicationInfoModelDomain(
            applicationVersion = this.verison!!,
            updateDate = date,
            versionUpdates = this.versionUpdates!!
        )
    }.getOrElse { exception ->
        Log.e(
            "!!!", """
            ApplicationInfoModelData.toModelDomain()
            ${exception.stackTraceToString()}
        """.trimIndent()
        )
        null
    }