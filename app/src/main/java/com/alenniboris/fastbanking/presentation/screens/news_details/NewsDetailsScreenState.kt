package com.alenniboris.fastbanking.presentation.screens.news_details

import com.alenniboris.fastbanking.presentation.model.bank_info.BankNewsModelUi

data class NewsDetailsScreenState(
    val news: BankNewsModelUi? = null,
    val isLoading: Boolean = false
)
