package com.alenniboris.fastbanking.presentation.screens.main

import com.alenniboris.fastbanking.domain.model.bank_info.RecommendedNewsModelDomain
import com.alenniboris.fastbanking.presentation.model.bank_product.IBankProductModelUi
import com.alenniboris.fastbanking.presentation.model.bank_product.TransactionModelUi
import com.alenniboris.fastbanking.presentation.uikit.values.BankProduct

data class MainScreenState(
    val isRecommendedNewsLoading: Boolean = false,
    val recommendedNews: List<RecommendedNewsModelDomain> = emptyList(),
    val isRecommendedNewsVisible: Boolean = false,
    val isProductsSheetVisible: Boolean = false,
    val bankProducts: List<BankProduct> = BankProduct.entries.toList(),
    val currentBankProduct: BankProduct = BankProduct.CARD,
    val currentViewedUserProduct: IBankProductModelUi? = null,
    val listOfUserProducts: List<IBankProductModelUi> = emptyList(),
    val isUserBankProductsLoading: Boolean = false,
    val isUserBankProductsSheetVisible: Boolean = false,
    val userTransactions: List<TransactionModelUi> = emptyList(),
    val isUserTransactionsHistoryLoading: Boolean = false,
    val isBaseCurrencyExchangeLoading: Boolean = false,
    val baseCurrencyAmount: Double? = null,
    val exchangedCurrencyAmount: Double? = null,
    val isUserAccountsSumLoading: Boolean = false,
    val userAccountsSum: Double? = null,
    val actionsWithProducts: List<ActionsWithProducts> = ActionsWithProducts.entries.toList()
)