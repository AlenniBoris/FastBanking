package com.alenniboris.fastbanking.data.model

import android.util.Log
import com.alenniboris.fastbanking.domain.model.bank_info.RecommendedNewsModelDomain

data class RecommendedNewsModelData(
    val id: String?,
    val picture: String?,
    val header: String?,
    val text: String?
)

fun RecommendedNewsModelData.toModelDomain(): RecommendedNewsModelDomain? = runCatching {
    RecommendedNewsModelDomain(
        id = this.id!!,
        picture = this.picture!!,
        header = this.header!!,
        text = this.text!!
    )
}.getOrElse { exception ->
    Log.e("!!!", "RecommendedNewsModelData.toModelDomain ${exception.stackTraceToString()}")
    null
}
