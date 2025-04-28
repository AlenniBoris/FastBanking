package com.alenniboris.fastbanking.data.model.currency

import android.util.Log
import com.alenniboris.fastbanking.domain.model.currency.CurrencyRatesModelDomain
import com.alenniboris.fastbanking.domain.model.currency.RateModelDomain
import java.text.SimpleDateFormat
import java.util.Locale

data class CurrencyRatesModelData(
    val lastUpdateDate: String?,
    val nextUpdateDate: String?,
    val baseCode: String?,
    val listOfRates: List<RateModelData>
)

data class RateModelData(
    val code: String?,
    val rate: String?
)

fun CurrencyRatesModelData.toModelDomain(): CurrencyRatesModelDomain? = runCatching {
    val dateFormat = SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z", Locale.ENGLISH)
    val lastDate = this.lastUpdateDate?.let { dateFormat.parse(it) }
    val nextDate = this.nextUpdateDate?.let { dateFormat.parse(it) }

    CurrencyRatesModelDomain(
        lastUpdateDate = lastDate!!,
        nextUpdateDate = nextDate!!,
        baseCode = this.baseCode!!,
        listOfRates = this.listOfRates.mapNotNull { it.toModelDomain() }
    )
}.getOrElse { exception ->
    Log.e("!!!", "CurrencyRatesModelData.toModelDomain(), ${exception.stackTraceToString()}")
    null
}

fun RateModelData.toModelDomain(): RateModelDomain? = runCatching {
    RateModelDomain(
        code = this.code!!,
        rate = this.rate?.toDouble()!!
    )
}.getOrElse {
    Log.e("!!!", "RateModelData.toModelDomain()")
    null
}