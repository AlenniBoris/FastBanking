package com.alenniboris.fastbanking.presentation.screens.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alenniboris.fastbanking.domain.model.CustomResultModelDomain
import com.alenniboris.fastbanking.domain.model.currency.CurrencyModelDomain
import com.alenniboris.fastbanking.domain.usecase.logic.accounts.IGetAllUserAccountsCurrencyAmountUseCase
import com.alenniboris.fastbanking.domain.usecase.logic.accounts.IGetAllUserAccountsUseCase
import com.alenniboris.fastbanking.domain.usecase.logic.bank_info.IGetBankRecommendedNewsUseCase
import com.alenniboris.fastbanking.domain.usecase.logic.cards.IGetAllUserCardsUseCase
import com.alenniboris.fastbanking.domain.usecase.logic.credits.IGetAllUserCreditsUseCase
import com.alenniboris.fastbanking.domain.usecase.logic.currency.IGetCurrenciesExchangeRateUseCase
import com.alenniboris.fastbanking.domain.usecase.logic.transactions.IGetAllUserTransactionsByCardUseCase
import com.alenniboris.fastbanking.domain.utils.SingleFlowEvent
import com.alenniboris.fastbanking.presentation.mappers.toUiMessageString
import com.alenniboris.fastbanking.presentation.model.bank_product.AccountModelUi
import com.alenniboris.fastbanking.presentation.model.bank_product.CardModelUi
import com.alenniboris.fastbanking.presentation.model.bank_product.CreditModelUi
import com.alenniboris.fastbanking.presentation.model.bank_product.IBankProductModelUi
import com.alenniboris.fastbanking.presentation.model.bank_product.TransactionModelUi
import com.alenniboris.fastbanking.presentation.model.bank_product.toModelUi
import com.alenniboris.fastbanking.presentation.model.bank_product.toUiModel
import com.alenniboris.fastbanking.presentation.uikit.utils.BaseCurrencyMode
import com.alenniboris.fastbanking.presentation.uikit.utils.baseCurrencyMode
import com.alenniboris.fastbanking.presentation.uikit.values.BankProduct
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainScreenViewModel(
    private val getBankRecommendedNewsUseCase: IGetBankRecommendedNewsUseCase,
    private val getAllUserCardsUseCase: IGetAllUserCardsUseCase,
    private val getAllUserCreditsUseCase: IGetAllUserCreditsUseCase,
    private val getAllUserAccountsUseCase: IGetAllUserAccountsUseCase,
    private val getAllUserTransactionsByCardUseCase: IGetAllUserTransactionsByCardUseCase,
    private val getCurrenciesExchangeRateUseCase: IGetCurrenciesExchangeRateUseCase,
    private val getAllUserAccountsCurrencyAmountUseCase: IGetAllUserAccountsCurrencyAmountUseCase
) : ViewModel() {

    private val _screenState = MutableStateFlow(MainScreenState())
    val state = _screenState.asStateFlow()

    private val _event = SingleFlowEvent<IMainScreenEvent>(viewModelScope)
    val event = _event.flow

    private var _recommendedNewsJob: Job? = null
    private var _userBankProductsJob: Job? = null

    init {
        loadRecommendedNews()
    }

    init {
        viewModelScope.launch {
            _screenState
                .map {
                    it.currentBankProduct
                }
                .buffer(onBufferOverflow = BufferOverflow.DROP_OLDEST)
                .distinctUntilChanged()
                .collect { product ->
                    when (product) {
                        BankProduct.CARD -> loadUserCards()
                        BankProduct.CREDIT -> loadUserCredits()
                        BankProduct.DEPOSITS_AND_ACCOUNTS -> loadUserAccounts()
                    }
                }
        }
    }

    init {
        viewModelScope.launch {
            _screenState
                .map {
                    it.currentViewedUserProduct
                }
                .buffer(onBufferOverflow = BufferOverflow.DROP_OLDEST)
                .distinctUntilChanged()
                .collect { product ->
                    (product as? CardModelUi)?.let { card ->
                        loadCardTransactionsHistory(card)
                    }
                }
        }
    }

    init {
        viewModelScope.launch {
            baseCurrencyMode
                .buffer(onBufferOverflow = BufferOverflow.DROP_OLDEST)
                .distinctUntilChanged()
                .collect { baseCurrency ->
                    loadBaseExchangeRate(baseCurrency = baseCurrency)
                    loadAllAccountsSummaryValue(baseCurrency = baseCurrency)
                }
        }
    }

    private fun loadBaseExchangeRate(baseCurrency: BaseCurrencyMode) {
        viewModelScope.launch {
            _screenState.update { it.copy(isBaseCurrencyExchangeLoading = true) }

            when (
                val res = getCurrenciesExchangeRateUseCase.invoke(
                    fromCurrency = CurrencyModelDomain(
                        code = baseCurrency.currencyCode,
                        fullName = baseCurrency.currencyFullName
                    ),
                    toCurrency = MainScreenValues.BaseExchangeCurrency
                )
            ) {
                is CustomResultModelDomain.Success -> {
                    val rate = res.result

                    _screenState.update {
                        it.copy(
                            baseCurrencyAmount = baseCurrency.baseAmount,
                            exchangeCurrencyAmount = baseCurrency.baseAmount * rate,
                            exchangeCurrencyCode = MainScreenValues.BaseExchangeCurrency.code
                        )
                    }
                }

                is CustomResultModelDomain.Error -> {
                    _event.emit(
                        IMainScreenEvent.ShowToastMessage(
                            res.exception.toUiMessageString()
                        )
                    )
                }
            }

            _screenState.update { it.copy(isBaseCurrencyExchangeLoading = false) }
        }
    }

    private fun loadAllAccountsSummaryValue(baseCurrency: BaseCurrencyMode) {
        viewModelScope.launch {
            _screenState.update { it.copy(isUserAccountsSumLoading = true) }

            when (
                val res = getAllUserAccountsCurrencyAmountUseCase.invoke(
                    baseCurrency = CurrencyModelDomain(
                        code = baseCurrency.currencyCode,
                        fullName = baseCurrency.currencyFullName
                    )
                )
            ) {
                is CustomResultModelDomain.Success -> {
                    _screenState.update {
                        it.copy(
                            userAccountsSum = res.result
                        )
                    }
                }

                is CustomResultModelDomain.Error -> {
                    _event.emit(
                        IMainScreenEvent.ShowToastMessage(
                            res.exception.toUiMessageString()
                        )
                    )
                }
            }

            _screenState.update { it.copy(isUserAccountsSumLoading = false) }
        }
    }

    private fun loadCardTransactionsHistory(card: CardModelUi) {
        viewModelScope.launch {
            _screenState.update { it.copy(isUserTransactionsHistoryLoading = true) }

            when (
                val result =
                    getAllUserTransactionsByCardUseCase.invoke(card = card.domainModel)
            ) {
                is CustomResultModelDomain.Success -> {

                    val list = if (result.result.size < 3) {
                        result.result
                    } else
                        result.result.subList(0, MainScreenValues.NumberOfVisibleTransactions)

                    val res = list.map { it.toUiModel(usedCard = card.domainModel) }

                    _screenState.update {
                        it.copy(
                            userTransactions = res
                        )
                    }
                }

                is CustomResultModelDomain.Error -> {
                    _event.emit(
                        IMainScreenEvent.ShowToastMessage(
                            result.exception.toUiMessageString()
                        )
                    )
                }
            }

            _screenState.update { it.copy(isUserTransactionsHistoryLoading = false) }
        }
    }

    private fun loadUserCards() {
        _userBankProductsJob?.cancel()
        viewModelScope.launch {
            _screenState.update { it.copy(isUserBankProductsLoading = true) }

            when (
                val result = getAllUserCardsUseCase.invoke()
            ) {
                is CustomResultModelDomain.Success -> {
                    val list = result.result.map { it.toModelUi() }
                    _screenState.update {
                        it.copy(
                            listOfUserProducts = list,
                            currentViewedUserProduct = list.firstOrNull()
                        )
                    }
                }

                is CustomResultModelDomain.Error -> {
                    _event.emit(
                        IMainScreenEvent.ShowToastMessage(
                            result.exception.toUiMessageString()
                        )
                    )
                }
            }

            _screenState.update { it.copy(isUserBankProductsLoading = false) }
        }
    }

    private fun loadUserCredits() {
        _userBankProductsJob?.cancel()
        viewModelScope.launch {
            _screenState.update { it.copy(isUserBankProductsLoading = true) }

            when (
                val result = getAllUserCreditsUseCase.invoke()
            ) {
                is CustomResultModelDomain.Success -> {
                    val list = result.result.map { it.toModelUi() }
                    _screenState.update {
                        it.copy(
                            listOfUserProducts = list,
                            currentViewedUserProduct = list.firstOrNull()
                        )
                    }
                }

                is CustomResultModelDomain.Error -> {
                    _event.emit(
                        IMainScreenEvent.ShowToastMessage(
                            result.exception.toUiMessageString()
                        )
                    )
                }
            }

            _screenState.update { it.copy(isUserBankProductsLoading = false) }
        }
    }

    private fun loadUserAccounts() {
        _userBankProductsJob?.cancel()
        viewModelScope.launch {
            _screenState.update { it.copy(isUserBankProductsLoading = true) }

            when (
                val result = getAllUserAccountsUseCase.invoke()
            ) {
                is CustomResultModelDomain.Success -> {
                    val list = result.result.map { it.toModelUi() }
                    _screenState.update {
                        it.copy(
                            listOfUserProducts = list,
                            currentViewedUserProduct = list.firstOrNull()
                        )
                    }
                }

                is CustomResultModelDomain.Error -> {
                    _event.emit(
                        IMainScreenEvent.ShowToastMessage(
                            result.exception.toUiMessageString()
                        )
                    )
                }
            }

            _screenState.update { it.copy(isUserBankProductsLoading = false) }
        }
    }

    private fun loadRecommendedNews() {
        _recommendedNewsJob?.cancel()
        _recommendedNewsJob = viewModelScope.launch {
            _screenState.update { it.copy(isRecommendedNewsLoading = true) }
            when (
                val res = getBankRecommendedNewsUseCase.invoke()
            ) {
                is CustomResultModelDomain.Success -> {
                    _screenState.update { it.copy(recommendedNews = res.result) }
                }

                is CustomResultModelDomain.Error -> {
                    _event.emit(
                        IMainScreenEvent.ShowToastMessage(res.exception.toUiMessageString())
                    )
                }
            }
            _screenState.update { it.copy(isRecommendedNewsLoading = false) }
        }
    }

    fun proceedIntent(intent: IMainScreenIntent) {
        when (intent) {
            is IMainScreenIntent.UpdateRecommendedNewsVisibility -> updateRecommendedNewsVisibility()
            is IMainScreenIntent.OpenHelpScreen -> openHelpScreen()
            is IMainScreenIntent.OpenPersonalDetailsScreen -> openPersonalDetailsScreen()
            is IMainScreenIntent.OpenRecommendedNewsDetails -> openRecommendedNewsDetails(intent.newsId)
            is IMainScreenIntent.UpdateActionsWithProductsSheetVisibility -> updateProductsSheetVisibility()
            is IMainScreenIntent.UpdateCurrentlyViewedProductsType ->
                updateCurrentlyViewedProductsType(intent.productType)

            is IMainScreenIntent.UpdateUserBankProductsSheetVisibility ->
                updateUserBankProductsSheetVisibility()

            is IMainScreenIntent.ProceedProductAction -> proceedProductAction(intent.action)
            is IMainScreenIntent.UpdateCurrentViewedUserProduct ->
                updateCurrentViewedUserProduct(intent.product)

            is IMainScreenIntent.UpdateSelectedTransaction ->
                updateSelectedTransaction(intent.transaction)

            is IMainScreenIntent.OpenProductDetailsScreen -> openProductDetailsScreen(intent.product)
        }
    }

    private fun openProductDetailsScreen(product: IBankProductModelUi) {
        _event.emit(
            when (product) {
                is AccountModelUi -> IMainScreenEvent.OpenAccountDetailsScreen(product.domainModel.id)
                is CardModelUi -> IMainScreenEvent.OpenCardDetailsScreen(product.domainModel.id)
                is CreditModelUi -> IMainScreenEvent.OpenCreditDetailsScreen(product.domainModel.id)
            }
        )
    }

    private fun updateSelectedTransaction(transaction: TransactionModelUi?) {
        _screenState.update { it.copy(selectedTransaction = transaction) }
    }

    private fun updateCurrentViewedUserProduct(product: IBankProductModelUi) {
        _screenState.update {
            it.copy(
                currentViewedUserProduct = if (it.currentViewedUserProduct == product) null else product
            )
        }
    }

    private fun proceedProductAction(action: ActionsWithProducts) {
        updateProductsSheetVisibility()
        when (action) {
            ActionsWithProducts.GET_OWN_CARD -> {
                _event.emit(
                    IMainScreenEvent.OpenProductApplianceChoosingScreen(
                        BankProduct.CARD
                    )
                )
            }

            ActionsWithProducts.SEND_APPLY_FOR_CREDIT -> {
                _event.emit(
                    IMainScreenEvent.OpenProductApplianceChoosingScreen(
                        BankProduct.CREDIT
                    )
                )
            }

            ActionsWithProducts.OPEN_ONLINE_DEPOSIT -> {
                _event.emit(
                    IMainScreenEvent.OpenProductApplianceChoosingScreen(
                        BankProduct.DEPOSITS_AND_ACCOUNTS
                    )
                )
            }
        }
    }

    private fun updateUserBankProductsSheetVisibility() {
        _screenState.update { it.copy(isUserBankProductsSheetVisible = !it.isUserBankProductsSheetVisible) }
    }

    private fun updateCurrentlyViewedProductsType(productType: BankProduct) {
        _screenState.update { it.copy(currentBankProduct = productType) }
    }

    private fun updateProductsSheetVisibility() {
        _screenState.update { it.copy(isProductsSheetVisible = !it.isProductsSheetVisible) }
    }

    private fun openRecommendedNewsDetails(newsId: String) {
        _event.emit(
            IMainScreenEvent.OpenRecommendedNewsDetails(newsId)
        )
    }

    private fun openHelpScreen() {
        _event.emit(IMainScreenEvent.OpenHelpScreen)
    }

    private fun openPersonalDetailsScreen() {
        _event.emit(IMainScreenEvent.OpenPersonalDetailsScreen)
    }

    private fun updateRecommendedNewsVisibility() {
        _screenState.update { it.copy(isRecommendedNewsVisible = !it.isRecommendedNewsVisible) }
    }
}