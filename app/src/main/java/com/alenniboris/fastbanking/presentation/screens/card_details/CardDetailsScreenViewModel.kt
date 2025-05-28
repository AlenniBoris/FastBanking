package com.alenniboris.fastbanking.presentation.screens.card_details

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alenniboris.fastbanking.domain.model.CustomResultModelDomain
import com.alenniboris.fastbanking.domain.usecase.logic.cards.IGetCardByIdUseCase
import com.alenniboris.fastbanking.domain.utils.SingleFlowEvent
import com.alenniboris.fastbanking.presentation.mappers.toUiMessageString
import com.alenniboris.fastbanking.presentation.model.bank_product.toModelUi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CardDetailsScreenViewModel(
    private val cardId: String,
    private val getCardByIdUseCase: IGetCardByIdUseCase
) : ViewModel() {

    private val _screenState = MutableStateFlow(CardDetailsScreenState())
    val state = _screenState.asStateFlow()

    private val _event = SingleFlowEvent<ICardDetailsScreenEvent>(viewModelScope)
    val event = _event.flow

    init {
        loadCard()
    }

    private fun loadCard() {
        viewModelScope.launch {
            _screenState.update { it.copy(isLoading = true) }

            when (val res = getCardByIdUseCase.invoke(cardId)) {
                is CustomResultModelDomain.Success -> {
                    _screenState.update { it.copy(card = res.result?.toModelUi()) }
                }

                is CustomResultModelDomain.Error -> {
                    _event.emit(
                        ICardDetailsScreenEvent.ShowToastMessage(
                            res.exception.toUiMessageString()
                        )
                    )
                }
            }

            _screenState.update { it.copy(isLoading = false) }
        }
    }

    fun proceedIntent(intent: ICardDetailsScreenIntent) {
        when (intent) {
            is ICardDetailsScreenIntent.NavigateBack -> navigateBack()
            is ICardDetailsScreenIntent.ProceedDetailsAction -> proceedDetailsAction(intent.action)
        }
    }

    private fun navigateBack() {
        _event.emit(ICardDetailsScreenEvent.NavigateBack)
    }

    private fun proceedDetailsAction(action: CardDetailsScreenActions) {
        when (action) {
            CardDetailsScreenActions.History -> openCardHistoryScreen()
            CardDetailsScreenActions.Details -> openCardDetailedInformationScreen()
        }
    }

    private fun openCardDetailedInformationScreen() {
        _screenState.value.card?.let { card ->
            _event.emit(
                ICardDetailsScreenEvent.NavigateToProductInfoScreen(card)
            )
        }
    }

    private fun openCardHistoryScreen() {
        _screenState.value.card?.let { card ->
            _event.emit(
                ICardDetailsScreenEvent.NavigateToProductHistoryScreen(card)
            )
        }
    }
}