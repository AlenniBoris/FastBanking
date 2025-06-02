package com.alenniboris.fastbanking.data.model.card

import android.util.Log
import com.alenniboris.fastbanking.domain.model.card.CardSystem
import com.alenniboris.fastbanking.domain.model.card.SimpleCardModelDomain

data class SimpleCardModelData(
    val id: String? = null,
    val number: String? = null,
    val system: String? = null
)

fun SimpleCardModelData.toModelDomain(): SimpleCardModelDomain? =
    runCatching {
        SimpleCardModelDomain(
            id = this.id!!,
            number = this.number!!,
            system = when (this.system!!) {
                "Visa" -> CardSystem.Visa
                "Mastercard" -> CardSystem.Mastercard
                "Mir" -> CardSystem.Mir
                else -> CardSystem.Undefined
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