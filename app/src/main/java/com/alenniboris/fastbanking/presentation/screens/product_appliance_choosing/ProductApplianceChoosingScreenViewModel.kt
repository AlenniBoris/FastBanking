package com.alenniboris.fastbanking.presentation.screens.product_appliance_choosing

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alenniboris.fastbanking.R
import com.alenniboris.fastbanking.domain.model.appliances.CardDetailedApplianceType
import com.alenniboris.fastbanking.domain.model.appliances.CreditDetailedApplianceType
import com.alenniboris.fastbanking.domain.model.appliances.DepositDetailedApplianceType
import com.alenniboris.fastbanking.domain.utils.SingleFlowEvent
import com.alenniboris.fastbanking.presentation.uikit.values.BankProduct
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ProductApplianceChoosingScreenViewModel(
    currentProduct: BankProduct
) : ViewModel() {

    private val _screenState = MutableStateFlow(
        ProductApplianceChoosingScreenState(
            currentProduct = currentProduct
        )
    )
    val state = _screenState.asStateFlow()

    private val _event = SingleFlowEvent<IProductApplianceChoosingScreenEvent>(viewModelScope)
    val event = _event.flow

    fun proceedIntent(intent: IProductApplianceChoosingScreenIntent) {
        when (intent) {
            is IProductApplianceChoosingScreenIntent.ProceedCardOption -> proceedCardOption(intent.option)
            is IProductApplianceChoosingScreenIntent.ProceedCreditOption -> proceedCreditOption(
                intent.option
            )

            is IProductApplianceChoosingScreenIntent.ProceedDepositOption -> proceedDepositOption(
                intent.option
            )

            is IProductApplianceChoosingScreenIntent.NavigateBack -> navigateBack()
        }
    }

    private fun navigateBack() {
        _event.emit(
            IProductApplianceChoosingScreenEvent.NavigateBack
        )
    }

    private fun proceedCardOption(option: CardDetailedApplianceType) {
        when (option) {
            CardDetailedApplianceType.ISSUE_PAYMENT_CARD,
            CardDetailedApplianceType.ISSUE_VIRTUAL_CARD,
            CardDetailedApplianceType.REISSUE_PAYMENT_CARD,
            CardDetailedApplianceType.REISSUE_VIRTUAL_CARD -> {
                _event.emit(
                    IProductApplianceChoosingScreenEvent.OpenCardApplianceScreenForm(
                        option
                    )
                )
            }

            CardDetailedApplianceType.UNDEFINED -> {
                _event.emit(
                    IProductApplianceChoosingScreenEvent.ShowToastMessage(
                        R.string.exception_unknown
                    )
                )
            }
        }
    }

    private fun proceedCreditOption(option: CreditDetailedApplianceType) {
        when (option) {
            CreditDetailedApplianceType.BBANK_CREDIT,
            CreditDetailedApplianceType.SOCIAL_CREDIT -> {
                _event.emit(
                    IProductApplianceChoosingScreenEvent.OpenCreditApplianceScreenForm(
                        option
                    )
                )
            }

            CreditDetailedApplianceType.UNDEFINED -> {
                _event.emit(
                    IProductApplianceChoosingScreenEvent.ShowToastMessage(
                        R.string.exception_unknown
                    )
                )
            }
        }
    }

    private fun proceedDepositOption(option: DepositDetailedApplianceType) {
        when (option) {
            DepositDetailedApplianceType.URGENT_DEPOSIT -> {
                _event.emit(
                    IProductApplianceChoosingScreenEvent.OpenDepositApplianceScreenForm(
                        option
                    )
                )
            }

            DepositDetailedApplianceType.UNDEFINED -> {
                _event.emit(
                    IProductApplianceChoosingScreenEvent.ShowToastMessage(
                        R.string.exception_unknown
                    )
                )
            }
        }
    }
}