package com.alenniboris.fastbanking.data.model

import android.net.Uri
import android.util.Log
import com.alenniboris.fastbanking.domain.model.bank_info.BankNewsModelDomain
import java.util.Date

data class BankNewsModelData(
    val id: String?,
    val author: String?,
    val creationDate: String?,
    val mainText: String?,
    val reference: String?,
    val synopsys: String?,
    val title: String?
) {

    val hasSomeValueMissing: Boolean
        get() = id == null
                || author == null
                || creationDate == null
                || mainText == null
                || reference == null
                || synopsys == null
                || title == null
}

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
        title = this.title!!
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
