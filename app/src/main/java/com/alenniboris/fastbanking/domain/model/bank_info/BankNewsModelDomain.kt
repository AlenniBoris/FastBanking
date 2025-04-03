package com.alenniboris.fastbanking.domain.model.bank_info

import android.net.Uri
import java.util.Date

data class BankNewsModelDomain(
    val id: String,
    val author: String,
    val creationDate: Date,
    val mainText: String,
    val reference: Uri,
    val synopsys: String,
    val title: String
)