package com.alenniboris.fastbanking.data.model

import android.util.Log
import com.alenniboris.fastbanking.domain.model.currency.CurrencyModelDomain

data class CurrencyModelData(
    val code: String?,
    val fullName: String?
)

fun CurrencyModelData.toModelDomain(): CurrencyModelDomain? =
    runCatching {
        CurrencyModelDomain(
            code = this.code!!,
            fullName = this.fullName!!
        )
    }.getOrElse { exception ->
        Log.e("!!!", "CurrencyInfoModelData.toModelDomain(), ${exception.stackTraceToString()}")
        null
    }
