package com.alenniboris.fastbanking.presentation.model

import com.alenniboris.fastbanking.domain.model.bank_info.BankNewsModelDomain
import java.text.SimpleDateFormat
import java.util.Locale

data class BankNewsModelUi(
    val domainModel: BankNewsModelDomain,
) {

    val dateText =
        SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(domainModel.creationDate)

    val formattedMainText = domainModel.mainText.replace("|", "\n")
}

fun BankNewsModelDomain.toModelUi(): BankNewsModelUi =
    BankNewsModelUi(
        domainModel = this
    )