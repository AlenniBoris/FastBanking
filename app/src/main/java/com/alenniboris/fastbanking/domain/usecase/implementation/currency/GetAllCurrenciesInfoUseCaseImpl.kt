package com.alenniboris.fastbanking.domain.usecase.implementation.currency

import com.alenniboris.fastbanking.domain.model.CustomResultModelDomain
import com.alenniboris.fastbanking.domain.model.currency.CurrencyInfoModelDomain
import com.alenniboris.fastbanking.domain.model.exception.CurrencyExceptionModelDomain
import com.alenniboris.fastbanking.domain.repository.ICurrencyRepository
import com.alenniboris.fastbanking.domain.usecase.logic.currency.IGetAllCurrenciesInfoUseCase
import com.alenniboris.fastbanking.domain.utils.IAppDispatchers
import kotlinx.coroutines.withContext

class GetAllCurrenciesInfoUseCaseImpl(
    private val currencyRepository: ICurrencyRepository,
    private val dispatchers: IAppDispatchers
) : IGetAllCurrenciesInfoUseCase {

    override suspend fun invoke():
            CustomResultModelDomain<List<CurrencyInfoModelDomain>, CurrencyExceptionModelDomain> =
        withContext(dispatchers.IO) {
            return@withContext currencyRepository.getAllCurrencies()
        }
}