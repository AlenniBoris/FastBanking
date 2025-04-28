package com.alenniboris.fastbanking.data.model

import android.util.Log
import com.alenniboris.fastbanking.domain.model.OwnerModelDomain

data class OwnerModelData(
    val id: String? = null,
    val name: String? = null,
    val surname: String? = null
)

fun OwnerModelData.toModelDomain(): OwnerModelDomain? =
    runCatching {
        OwnerModelDomain(
            id = this.id!!,
            name = this.name!!,
            surname = this.surname!!
        )
    }.getOrElse { exception ->
        Log.e(
            "!!!", """
            OwnerModelData.toModelDomain(): OwnerModelDomain
            ${exception.stackTraceToString()}
        """.trimIndent()
        )
        null
    }
