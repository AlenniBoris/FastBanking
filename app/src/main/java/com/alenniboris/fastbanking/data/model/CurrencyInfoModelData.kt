package com.alenniboris.fastbanking.data.model

import android.util.Log
import com.alenniboris.fastbanking.domain.model.currency.CurrencyInfoModelDomain

data class CurrencyInfoModelData(
    val code: String?,
    val fullName: String?
)

fun CurrencyInfoModelData.toModelDomain(): CurrencyInfoModelDomain? =
    runCatching {
        CurrencyInfoModelDomain(
            code = this.code!!,
            fullName = this.fullName!!
        )
    }.getOrElse { exception ->
        Log.e("!!!", "CurrencyInfoModelData.toModelDomain(), ${exception.stackTraceToString()}")
        null
    }
