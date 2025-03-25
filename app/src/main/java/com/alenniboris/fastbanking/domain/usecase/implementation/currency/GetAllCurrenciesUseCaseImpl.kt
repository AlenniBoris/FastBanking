package com.alenniboris.fastbanking.domain.usecase.implementation.currency

import com.alenniboris.fastbanking.domain.model.CustomResultModelDomain
import com.alenniboris.fastbanking.domain.model.country.CountryInfoModelDomain
import com.alenniboris.fastbanking.domain.model.currency.CurrencyModelDomain
import com.alenniboris.fastbanking.domain.model.exception.CurrencyExceptionModelDomain
import com.alenniboris.fastbanking.domain.usecase.logic.currency.IGetAllCurrenciesInfoUseCase
import com.alenniboris.fastbanking.domain.usecase.logic.currency.IGetAllCurrenciesUseCase
import com.alenniboris.fastbanking.domain.usecase.logic.currency.IGetCountryFlagByCurrencyCodeUseCase
import com.alenniboris.fastbanking.domain.utils.IAppDispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.withContext

class GetAllCurrenciesUseCaseImpl(
    private val getAllCurrenciesInfoUseCase: IGetAllCurrenciesInfoUseCase,
    private val getCountryFlagByCurrencyCodeUseCase: IGetCountryFlagByCurrencyCodeUseCase,
    private val dispatchers: IAppDispatchers
) : IGetAllCurrenciesUseCase {

    override suspend fun invoke()
            : CustomResultModelDomain<List<CurrencyModelDomain>, CurrencyExceptionModelDomain> =
        withContext(dispatchers.IO) {
            return@withContext when (
                val allCurrenciesInfo = getAllCurrenciesInfoUseCase.invoke()
            ) {
                is CustomResultModelDomain.Success -> {

                    val currenciesInfoList = allCurrenciesInfo.result

                    val countriesInfo: MutableList<CountryInfoModelDomain> = mutableListOf()
                    currenciesInfoList
                        .map { info ->
                            async {
                                getCountryFlagByCurrencyCodeUseCase.invoke(
                                    currencyCode = info.code
                                )
                            }
                        }
                        .awaitAll()
                        .forEach { infoResult ->
                            when (infoResult) {
                                is CustomResultModelDomain.Success -> {
                                    countriesInfo.add(infoResult.result)
                                }

                                is CustomResultModelDomain.Error -> {
                                    return@withContext CustomResultModelDomain.Error(
                                        infoResult.exception
                                    )
                                }
                            }
                        }

                    val result: MutableList<CurrencyModelDomain> = mutableListOf()
                    currenciesInfoList.forEachIndexed { index, currencyInfo ->
                        result.add(
                            CurrencyModelDomain(
                                currencyInfo = currencyInfo,
                                countryInfo = countriesInfo[index]
                            )
                        )
                    }


                    CustomResultModelDomain.Success(result)
                }

                is CustomResultModelDomain.Error -> {
                    CustomResultModelDomain.Error(
                        allCurrenciesInfo.exception
                    )
                }
            }
        }
}