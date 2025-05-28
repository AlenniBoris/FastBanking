package com.alenniboris.fastbanking.presentation.screens.product_information

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alenniboris.fastbanking.domain.utils.SingleFlowEvent

class ProductInformationScreenViewModel : ViewModel() {

    private val _event = SingleFlowEvent<IProductInformationScreenEvent>(viewModelScope)
    val event = _event.flow

    fun proceedIntent(intent: IProductInformationScreenIntent) {
        when (intent) {
            is IProductInformationScreenIntent.NavigateBack ->
                navigateBack()

            is IProductInformationScreenIntent.CopyTextToClipboard ->
                copyTextToClipboard(intent.text)
        }
    }

    private fun navigateBack() {
        _event.emit(IProductInformationScreenEvent.NavigateBack)
    }

    private fun copyTextToClipboard(text: String) {
        _event.emit(
            IProductInformationScreenEvent.CopyTextToClipboard(text)
        )
    }
}