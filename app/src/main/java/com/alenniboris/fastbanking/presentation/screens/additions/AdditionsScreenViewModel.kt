package com.alenniboris.fastbanking.presentation.screens.additions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alenniboris.fastbanking.R
import com.alenniboris.fastbanking.domain.model.CustomResultModelDomain
import com.alenniboris.fastbanking.domain.usecase.logic.bank_info.ISendUserPraiseUseCase
import com.alenniboris.fastbanking.domain.utils.SingleFlowEvent
import com.alenniboris.fastbanking.presentation.mappers.toUiMessageString
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AdditionsScreenViewModel(
    private val sendUserPraiseUseCase: ISendUserPraiseUseCase
) : ViewModel() {

    private val _screenState = MutableStateFlow(AdditionsScreenState())
    val state = _screenState.asStateFlow()

    private val _event = SingleFlowEvent<IAdditionsScreenEvent>(viewModelScope)
    val event = _event.flow

    fun proceedIntent(intent: IAdditionsScreenIntent) {
        when (intent) {
            is IAdditionsScreenIntent.ProceedAccordingToAction -> proceedAccordingToAction(
                intent.action
            )

            is IAdditionsScreenIntent.ChangePraiseText -> changePraiseText(intent.text)
            is IAdditionsScreenIntent.UpdatePraiseDialogVisibility -> updatePraiseDialogVisibility()
            is IAdditionsScreenIntent.SendUserPraise -> sendUserPraise()
        }
    }

    private fun sendUserPraise() {
        viewModelScope.launch {
            when (
                val res = sendUserPraiseUseCase.invoke(praise = _screenState.value.praiseText)
            ) {
                is CustomResultModelDomain.Success -> {
                    _event.emit(
                        IAdditionsScreenEvent.ShowToastMessage(
                            R.string.praise_send_successfully_text
                        )
                    )
                    updatePraiseDialogVisibility()
                }

                is CustomResultModelDomain.Error -> {
                    _event.emit(
                        IAdditionsScreenEvent.ShowToastMessage(
                            res.exception.toUiMessageString()
                        )
                    )
                }
            }
        }
    }

    private fun updatePraiseDialogVisibility() {
        _screenState.update {
            it.copy(
                isPraiseDialogVisible = !it.isPraiseDialogVisible,
                praiseText = ""
            )
        }
    }

    private fun changePraiseText(text: String) {
        _screenState.update { it.copy(praiseText = text) }
    }

    private fun proceedAccordingToAction(action: AdditionsCategoriesAction) {
        when (action) {
            AdditionsCategoriesAction.REGISTRATION_IN_SYSTEM ->
                _event.emit(
                    IAdditionsScreenEvent.OpenRegistrationOptionsPage
                )

            AdditionsCategoriesAction.PASSWORD_RECOVERY ->
                _event.emit(
                    IAdditionsScreenEvent.OpenPasswordRecoveryPage
                )

            AdditionsCategoriesAction.CURRENCY_EXCHANGER ->
                _event.emit(
                    IAdditionsScreenEvent.OpenCurrencyExchangePage
                )

            AdditionsCategoriesAction.BANK_NEWS ->
                _event.emit(
                    IAdditionsScreenEvent.OpenBankNewsPage
                )

            AdditionsCategoriesAction.APP_VERSION ->
                _event.emit(
                    IAdditionsScreenEvent.OpenApplicationInformationPage
                )

            AdditionsCategoriesAction.ATMS_AND_OFFICES ->
                _event.emit(
                    IAdditionsScreenEvent.OpenMapPage
                )

            AdditionsCategoriesAction.PRAISE ->
                updatePraiseDialogVisibility()
        }
    }
}