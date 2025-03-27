package com.alenniboris.fastbanking.domain.usecase.implementation.currency

import com.alenniboris.fastbanking.domain.model.CustomResultModelDomain
import com.alenniboris.fastbanking.domain.model.currency.CurrencyModelDomain
import com.alenniboris.fastbanking.domain.model.exception.CurrencyExceptionModelDomain
import com.alenniboris.fastbanking.domain.repository.ICurrencyRepository
import com.alenniboris.fastbanking.domain.usecase.logic.currency.IGetCurrenciesExchangeRateUseCase
import com.alenniboris.fastbanking.domain.utils.IAppDispatchers
import kotlinx.coroutines.withContext

class GetCurrenciesExchangeRateUseCaseImpl(
    private val currencyRepository: ICurrencyRepository,
    private val dispatchers: IAppDispatchers
) : IGetCurrenciesExchangeRateUseCase {

    override suspend fun invoke(
        fromCurrency: CurrencyModelDomain?,
        toCurrency: CurrencyModelDomain?
    ): CustomResultModelDomain<Double, CurrencyExceptionModelDomain> = withContext(dispatchers.IO) {
        return@withContext currencyRepository.getExchangeRateForCurrencies(
            fromCurrency = fromCurrency,
            toCurrency = toCurrency
        )
    }
}