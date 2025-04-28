package com.alenniboris.fastbanking.data.model

import android.util.Log
import com.alenniboris.fastbanking.domain.model.card.CardSystem
import com.alenniboris.fastbanking.domain.model.card.SimpleCardModelDomain

data class SimpleCardModelData(
    val id: String?,
    val number: String?,
    val system: String?
)

fun SimpleCardModelData.toModelDomain(): SimpleCardModelDomain? =
    runCatching {
        SimpleCardModelDomain(
            id = this.id!!,
            number = this.number!!,
            system = when (this.system!!) {
                "Visa" -> CardSystem.VISA
                "Mastercard" -> CardSystem.MASTERCARD
                "Mir" -> CardSystem.MIR
                else -> CardSystem.UNDEFINED
            }
        )
    }.getOrElse { exception ->
        Log.e(
            "!!!", """
            SimpleCardModelData.toModelDomain(): SimpleCardModelDomain
            ${exception.stackTraceToString()}
        """.trimIndent()
        )
        null
    }