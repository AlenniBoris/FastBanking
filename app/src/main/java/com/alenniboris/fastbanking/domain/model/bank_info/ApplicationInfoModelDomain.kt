package com.alenniboris.fastbanking.domain.model.bank_info

import java.util.Date

data class ApplicationInfoModelDomain(
    val applicationVersion: String,
    val updateDate: Date,
    val versionUpdates: String
)
