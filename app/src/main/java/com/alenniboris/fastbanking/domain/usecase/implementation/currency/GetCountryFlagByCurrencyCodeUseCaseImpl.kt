package com.alenniboris.fastbanking.domain.usecase.implementation.currency

import com.alenniboris.fastbanking.domain.model.CustomResultModelDomain
import com.alenniboris.fastbanking.domain.model.country.CountryInfoModelDomain
import com.alenniboris.fastbanking.domain.model.exception.CurrencyExceptionModelDomain
import com.alenniboris.fastbanking.domain.repository.ICountriesRepository
import com.alenniboris.fastbanking.domain.usecase.logic.currency.IGetCountryFlagByCurrencyCodeUseCase
import com.alenniboris.fastbanking.domain.utils.IAppDispatchers
import kotlinx.coroutines.withContext

class GetCountryFlagByCurrencyCodeUseCaseImpl(
    private val countriesRepository: ICountriesRepository,
    private val dispatchers: IAppDispatchers
) : IGetCountryFlagByCurrencyCodeUseCase {

    override suspend fun invoke(
        currencyCode: String
    ): CustomResultModelDomain<CountryInfoModelDomain, CurrencyExceptionModelDomain> =
        withContext(dispatchers.IO) {
            return@withContext countriesRepository.GetCountryFlagByCurrencyCode(
                currencyCode = currencyCode
            )
        }
}