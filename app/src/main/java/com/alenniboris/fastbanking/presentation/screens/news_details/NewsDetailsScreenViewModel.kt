package com.alenniboris.fastbanking.presentation.screens.news_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alenniboris.fastbanking.domain.model.CustomResultModelDomain
import com.alenniboris.fastbanking.domain.usecase.logic.bank_info.IGetBankNewsByIdUseCase
import com.alenniboris.fastbanking.domain.utils.SingleFlowEvent
import com.alenniboris.fastbanking.presentation.mappers.toUiMessageString
import com.alenniboris.fastbanking.presentation.model.toModelUi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class NewsDetailsScreenViewModel(
    private val newsId: String,
    private val getNewsByIdUseCase: IGetBankNewsByIdUseCase
) : ViewModel() {

    private val _screenState = MutableStateFlow(NewsDetailsScreenState())
    val state = _screenState.asStateFlow()

    private val _event = SingleFlowEvent<INewsDetailsScreenEvent>(viewModelScope)
    val event = _event.flow

    init {
        loadNewsById(id = newsId)
    }

    private fun loadNewsById(id: String) {
        viewModelScope.launch {
            _screenState.update { it.copy(isLoading = true) }

            when (
                val res = getNewsByIdUseCase.invoke(id = id)
            ) {
                is CustomResultModelDomain.Success -> {
                    _screenState.update {
                        it.copy(
                            news = res.result?.toModelUi()
                        )
                    }
                }

                is CustomResultModelDomain.Error -> {
                    _event.emit(
                        INewsDetailsScreenEvent.ShowToastMessage(
                            res.exception.toUiMessageString()
                        )
                    )
                }
            }

            _screenState.update { it.copy(isLoading = false) }
        }
    }

    fun proceedIntent(intent: INewsDetailsScreenIntent) {
        when (intent) {
            is INewsDetailsScreenIntent.NavigateBack ->
                navigateBack()
        }
    }

    private fun navigateBack() {
        _event.emit(INewsDetailsScreenEvent.NavigateBack)
    }
}