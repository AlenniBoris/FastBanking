package com.alenniboris.fastbanking.presentation.screens.bank_news

import com.alenniboris.fastbanking.presentation.model.bank_info.BankNewsModelUi

data class BankNewsScreenState(
    val bankNews: List<BankNewsModelUi> = emptyList(),
    val isLoading: Boolean = false
)
