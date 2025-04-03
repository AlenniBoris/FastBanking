package com.alenniboris.fastbanking.presentation.screens.bank_news

import com.alenniboris.fastbanking.presentation.model.BankNewsModelUi

data class BankNewsScreenState(
    val bankNews: List<BankNewsModelUi> = emptyList(),
    val isLoading: Boolean = false
)
