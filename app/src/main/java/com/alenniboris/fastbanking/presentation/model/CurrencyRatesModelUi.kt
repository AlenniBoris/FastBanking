package com.alenniboris.fastbanking.presentation.model

import com.alenniboris.fastbanking.domain.model.currency.CurrencyRatesModelDomain
import com.alenniboris.fastbanking.domain.model.currency.RateModelDomain
import java.text.SimpleDateFormat
import java.util.Locale

data class CurrencyRatesModelUi(
    val lastUpdateDateText: String = "",
    val nextUpdateDateText: String = "",
    val baseCodeText: String = "",
    val listOfRates: List<RateModelDomain> = emptyList()
)

fun CurrencyRatesModelDomain.toModelUi() =
    CurrencyRatesModelUi(
        lastUpdateDateText = SimpleDateFormat("dd-MM-yyyy, HH:mm:ss", Locale.getDefault()).format(
            this.lastUpdateDate
        ),
        nextUpdateDateText = SimpleDateFormat("dd-MM-yyyy, HH:mm:ss", Locale.getDefault()).format(
            this.nextUpdateDate
        ),
        baseCodeText = "1 ${this.baseCode}",
        listOfRates = this.listOfRates
    )