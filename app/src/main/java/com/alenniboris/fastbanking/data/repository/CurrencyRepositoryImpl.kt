package com.alenniboris.fastbanking.data.repository

import android.util.Log
import com.alenniboris.fastbanking.data.mappers.toCurrencyException
import com.alenniboris.fastbanking.data.model.CurrencyInfoModelData
import com.alenniboris.fastbanking.data.model.toModelDomain
import com.alenniboris.fastbanking.data.source.remote.api.exchange_rate_api.IExchangeRateApiService
import com.alenniboris.fastbanking.domain.model.CustomResultModelDomain
import com.alenniboris.fastbanking.domain.model.currency.CurrencyInfoModelDomain
import com.alenniboris.fastbanking.domain.model.exception.CurrencyExceptionModelDomain
import com.alenniboris.fastbanking.domain.repository.ICurrencyRepository
import com.alenniboris.fastbanking.domain.utils.IAppDispatchers
import kotlinx.coroutines.withContext

class CurrencyRepositoryImpl(
    private val apiService: IExchangeRateApiService,
    private val dispatchers: IAppDispatchers
) : ICurrencyRepository {

    override suspend fun getAllCurrencies()
            : CustomResultModelDomain<List<CurrencyInfoModelDomain>, CurrencyExceptionModelDomain> =
        withContext(dispatchers.IO) {
            runCatching {

                val response = apiService.getAllSupportedCurrencies()

                if (response.hasSomeValueMissing || response.errorType != null) {
                    return@withContext CustomResultModelDomain.Error(
                        CurrencyExceptionModelDomain.ServerError
                    )
                }

                val listOfDataModels = response.supportedCodes?.mapNotNull { supportedCodes ->
                    CurrencyInfoModelData(
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
        fromCurrency: CurrencyInfoModelDomain,
        toCurrency: CurrencyInfoModelDomain
    ): CustomResultModelDomain<Double, CurrencyExceptionModelDomain> = withContext(dispatchers.IO) {
        runCatching {

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