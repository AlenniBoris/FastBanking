package com.alenniboris.fastbanking.data.model.bank_info

import android.net.Uri
import android.util.Log
import com.alenniboris.fastbanking.domain.model.bank_info.BankNewsModelDomain
import java.util.Date

data class BankNewsModelData(
    val id: String? = null,
    val author: String? = null,
    val creationDate: String? = null,
    val mainText: String? = null,
    val reference: String? = null,
    val synopsys: String? = null,
    val title: String? = null,
    val imageUrl: String? = null,
    val isRecommended: String? = null
)

fun BankNewsModelData.toModelDomain(): BankNewsModelDomain? = runCatching {
    val date = Date(this.creationDate?.toLong()!!)
    val reference = Uri.parse(this.reference!!)

    BankNewsModelDomain(
        id = this.id!!,
        author = this.author!!,
        creationDate = date,
        mainText = this.mainText!!,
        reference = reference,
        synopsys = this.synopsys!!,
        title = this.title!!,
        image = this.imageUrl!!,
        isRecommended = this.isRecommended.toBoolean()
    )
}.getOrElse { exception ->
    Log.e(
        "!!!", """
            BankNewsModelData.toModelDomain()
            ${exception.stackTraceToString()}
        """.trimIndent()
    )
    null
}
