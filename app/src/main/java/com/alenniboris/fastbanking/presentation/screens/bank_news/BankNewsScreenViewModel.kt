package com.alenniboris.fastbanking.presentation.screens.bank_news

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alenniboris.fastbanking.domain.model.CustomResultModelDomain
import com.alenniboris.fastbanking.domain.usecase.logic.bank_info.IGetBankNewsUseCase
import com.alenniboris.fastbanking.domain.utils.SingleFlowEvent
import com.alenniboris.fastbanking.presentation.mappers.toUiMessageString
import com.alenniboris.fastbanking.presentation.model.bank_info.toModelUi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class BankNewsScreenViewModel(
    private val getBankNewsUseCase: IGetBankNewsUseCase
) : ViewModel() {

    private val _screenState = MutableStateFlow(BankNewsScreenState())
    val state = _screenState.asStateFlow()

    private val _event = SingleFlowEvent<IBankNewsScreenEvent>(viewModelScope)
    val event = _event.flow

    init {
        loadNews()
    }

    private fun loadNews() {
        viewModelScope.launch {
            _screenState.update { it.copy(isLoading = true) }
            when (
                val res = getBankNewsUseCase.invoke()
            ) {
                is CustomResultModelDomain.Success -> {
                    _screenState.update { state ->
                        state.copy(
                            bankNews = res.result.map { it.toModelUi() }
                        )
                    }
                }

                is CustomResultModelDomain.Error -> {
                    _event.emit(
                        IBankNewsScreenEvent.ShowToastMessage(
                            res.exception.toUiMessageString()
                        )
                    )
                }
            }
            _screenState.update { it.copy(isLoading = false) }
        }
    }

    fun proceedIntent(intent: IBankNewsScreenIntent) {
        when (intent) {
            is IBankNewsScreenIntent.NavigateBack -> navigateBack()
            is IBankNewsScreenIntent.OpenNewsDetailsPage -> openNewsDetailsPage(intent.newsId)
        }
    }

    private fun openNewsDetailsPage(newsId: String) {
        _event.emit(
            IBankNewsScreenEvent.OpenNewsDetailsScreen(newsId)
        )
    }

    private fun navigateBack() {
        _event.emit(IBankNewsScreenEvent.NavigateBack)
    }
}