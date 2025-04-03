package com.alenniboris.fastbanking.presentation.screens.application_information

import com.alenniboris.fastbanking.domain.model.bank_info.ApplicationInfoModelDomain
import java.text.SimpleDateFormat
import java.util.Locale

data class ApplicationInformationScreenState(
    val appInfo: ApplicationInfoModelDomain? = null,
    val isLoading: Boolean = false
) {

    val dateText: String? = appInfo?.updateDate?.let { date ->
        SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(date)
    }

    val updatesText: String? = appInfo?.versionUpdates?.replace("|", "\n")

    val versionText: String? = appInfo?.applicationVersion
}
