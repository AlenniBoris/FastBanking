package com.alenniboris.fastbanking.presentation.screens.transactions_history

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alenniboris.fastbanking.domain.model.CustomResultModelDomain
import com.alenniboris.fastbanking.domain.usecase.logic.transactions.IGetAllUserTransactionsUseCase
import com.alenniboris.fastbanking.domain.utils.SingleFlowEvent
import com.alenniboris.fastbanking.presentation.mappers.toUiMessageString
import com.alenniboris.fastbanking.presentation.model.bank_product.TransactionModelUi
import com.alenniboris.fastbanking.presentation.model.bank_product.toUiModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TransactionsHistoryScreenViewModel(
    private val getAllUserTransactionsUseCase: IGetAllUserTransactionsUseCase
) : ViewModel() {

    private val _screenState = MutableStateFlow(TransactionsHistoryScreenState())
    val state = _screenState.asStateFlow()

    private val _event = SingleFlowEvent<ITransactionsHistoryScreenEvent>(viewModelScope)
    val event = _event.flow

    init {
        loadData()
    }

    fun proceedIntent(intent: ITransactionsHistoryScreenIntent) {
        when (intent) {
            is ITransactionsHistoryScreenIntent.ReloadData -> reloadData()
            is ITransactionsHistoryScreenIntent.UpdateSelectedTransaction ->
                updateSelectedTransaction(intent.transaction)
        }
    }

    private fun loadData() {
        viewModelScope.launch {
            _screenState.update { it.copy(isLoading = true) }

            when (
                val res = getAllUserTransactionsUseCase.invoke()
            ) {
                is CustomResultModelDomain.Success -> {
                    Log.e("!!!", res.result.toString())
                    _screenState.update {
                        it.copy(
                            transactions = res.result.map { it.toUiModel() }
                        )
                    }
                }

                is CustomResultModelDomain.Error -> {
                    _screenState.update {
                        it.copy(isError = true)
                    }
                    _event.emit(
                        ITransactionsHistoryScreenEvent.ShowToastMessage(
                            res.exception.toUiMessageString()
                        )
                    )
                }
            }

            _screenState.update { it.copy(isLoading = false) }
        }
    }

    private fun reloadData() {
        _screenState.update {
            it.copy(isError = false)
        }
        loadData()
    }

    private fun updateSelectedTransaction(transaction: TransactionModelUi?) {
        _screenState.update { it.copy(selectedTransaction = transaction) }
    }
}