package com.alenniboris.fastbanking.data.repository

import android.util.Log
import com.alenniboris.fastbanking.data.mappers.toCurrencyException
import com.alenniboris.fastbanking.data.model.currency.CurrencyModelData
import com.alenniboris.fastbanking.data.model.currency.CurrencyRatesModelData
import com.alenniboris.fastbanking.data.model.currency.RateModelData
import com.alenniboris.fastbanking.data.model.currency.toModelDomain
import com.alenniboris.fastbanking.data.source.remote.api.exchange_rate_api.IExchangeRateApiService
import com.alenniboris.fastbanking.domain.model.CustomResultModelDomain
import com.alenniboris.fastbanking.domain.model.currency.CurrencyModelDomain
import com.alenniboris.fastbanking.domain.model.currency.CurrencyRatesModelDomain
import com.alenniboris.fastbanking.domain.model.exception.CurrencyExceptionModelDomain
import com.alenniboris.fastbanking.domain.repository.ICurrencyRepository
import com.alenniboris.fastbanking.domain.utils.GsonUtil.fromJson
import com.alenniboris.fastbanking.domain.utils.GsonUtil.toJson
import com.alenniboris.fastbanking.domain.utils.IAppDispatchers
import kotlinx.coroutines.withContext

class CurrencyRepositoryImpl(
    private val apiService: IExchangeRateApiService,
    private val dispatchers: IAppDispatchers
) : ICurrencyRepository {

    override suspend fun getLatestConversionRates(
        currency: CurrencyModelDomain
    ): CustomResultModelDomain<CurrencyRatesModelDomain, CurrencyExceptionModelDomain> =
        withContext(dispatchers.IO) {
            runCatching {

                val response = apiService.getLatestCourse(
                    currencyCode = currency.code
                )

                if (response.hasSomeValueMissing || response.errorType != null) {
                    return@withContext CustomResultModelDomain.Error(
                        CurrencyExceptionModelDomain.ServerError
                    )
                }

                val list =
                    response.conversionRates?.toJson()?.fromJson<Map<String, String>>()
                        ?: return@withContext CustomResultModelDomain.Error(
                            CurrencyExceptionModelDomain.ServerError
                        )

                val result = CurrencyRatesModelData(
                    lastUpdateDate = response.timeLastUpdateUtc,
                    nextUpdateDate = response.timeNextUpdateUtc,
                    baseCode = response.baseCode,
                    listOfRates = list.map {
                        RateModelData(
                            code = it.key,
                            rate = it.value
                        )
                    }
                ).toModelDomain()
                    ?: return@withContext CustomResultModelDomain.Error(
                        CurrencyExceptionModelDomain.ErrorMapping
                    )

                return@withContext CustomResultModelDomain.Success(
                    result
                )
            }.getOrElse { exception ->
                Log.e("!!!", exception.stackTraceToString())
                return@withContext CustomResultModelDomain.Error(
                    exception.toCurrencyException()
                )
            }
        }

    override suspend fun getAllCurrencies()
            : CustomResultModelDomain<List<CurrencyModelDomain>, CurrencyExceptionModelDomain> =
        withContext(dispatchers.IO) {
            runCatching {

                val response = apiService.getAllSupportedCurrencies()

                if (response.hasSomeValueMissing || response.errorType != null) {
                    return@withContext CustomResultModelDomain.Error(
                        CurrencyExceptionModelDomain.ServerError
                    )
                }

                val listOfDataModels = response.supportedCodes?.mapNotNull { supportedCodes ->
                    CurrencyModelData(
                        code = supportedCodes?.get(0),
                        fullName = supportedCodes?.get(1)
                    )
                }

                if (listOfDataModels == null) {
                    return@withContext CustomResultModelDomain.Error(
                        CurrencyExceptionModelDomain.ServerError
                    )
                }

                val result = listOfDataModels.mapNotNull { it.toModelDomain() }

                return@withContext CustomResultModelDomain.Success(result)
            }.getOrElse { exception ->
                Log.e("!!!", exception.stackTraceToString())
                return@withContext CustomResultModelDomain.Error(
                    exception.toCurrencyException()
                )
            }
        }

    override suspend fun getExchangeRateForCurrencies(
        fromCurrency: CurrencyModelDomain?,
        toCurrency: CurrencyModelDomain?
    ): CustomResultModelDomain<Double, CurrencyExceptionModelDomain> = withContext(dispatchers.IO) {
        runCatching {

            if (fromCurrency == null || toCurrency == null) {
                return@withContext CustomResultModelDomain.Error(
                    CurrencyExceptionModelDomain.SomeCurrencyNotChosen
                )
            }

            val response = apiService.getConversionRate(
                fromCurrency = fromCurrency.code,
                toCurrency = toCurrency.code
            )

            if (response.hasSomeValueMissing || response.errorType != null) {
                return@withContext CustomResultModelDomain.Error(
                    CurrencyExceptionModelDomain.ServerError
                )
            }

            val result = response.conversionRate?.toDouble()!!

            return@withContext CustomResultModelDomain.Success(result)
        }.getOrElse { exception ->
            Log.e("!!!", exception.stackTraceToString())
            return@withContext CustomResultModelDomain.Error(
                exception.toCurrencyException()
            )
        }
    }
}