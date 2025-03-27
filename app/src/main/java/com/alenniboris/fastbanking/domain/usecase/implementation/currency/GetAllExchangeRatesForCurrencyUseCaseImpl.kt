package com.alenniboris.fastbanking.domain.usecase.implementation.currency

import com.alenniboris.fastbanking.domain.model.CustomResultModelDomain
import com.alenniboris.fastbanking.domain.model.currency.CurrencyModelDomain
import com.alenniboris.fastbanking.domain.model.currency.CurrencyRatesModelDomain
import com.alenniboris.fastbanking.domain.model.exception.CurrencyExceptionModelDomain
import com.alenniboris.fastbanking.domain.repository.ICurrencyRepository
import com.alenniboris.fastbanking.domain.usecase.logic.currency.IGetAllExchangeRatesForCurrencyUseCase
import com.alenniboris.fastbanking.domain.utils.IAppDispatchers
import kotlinx.coroutines.withContext

class GetAllExchangeRatesForCurrencyUseCaseImpl(
    private val currencyRepository: ICurrencyRepository,
    private val dispatchers: IAppDispatchers
) : IGetAllExchangeRatesForCurrencyUseCase {

    override suspend fun invoke(
        currency: CurrencyModelDomain
    ): CustomResultModelDomain<CurrencyRatesModelDomain, CurrencyExceptionModelDomain> =
        withContext(dispatchers.IO) {
            return@withContext currencyRepository.getLatestConversionRates(currency = currency)
        }
}