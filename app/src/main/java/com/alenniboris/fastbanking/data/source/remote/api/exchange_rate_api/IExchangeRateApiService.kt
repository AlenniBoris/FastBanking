package com.alenniboris.fastbanking.data.source.remote.api.exchange_rate_api

import android.app.Application
import com.alenniboris.fastbanking.BuildConfig
import com.alenniboris.fastbanking.data.source.remote.MyRetrofit
import com.alenniboris.fastbanking.data.source.remote.api.exchange_rate_api.response.ConversionRateResponse
import com.alenniboris.fastbanking.data.source.remote.api.exchange_rate_api.response.LatestCurrencyRatesResponse
import com.alenniboris.fastbanking.data.source.remote.api.exchange_rate_api.response.SupportedCodesResponse
import com.andretietz.retrofit.ResponseCache
import retrofit2.http.GET
import retrofit2.http.Path
import java.util.concurrent.TimeUnit

interface IExchangeRateApiService {

    @GET(BuildConfig.EXCHANGE_RATE_API_KEY + ExchangeRateApiValues.REQUEST_CODES)
    @ResponseCache(24, TimeUnit.HOURS)
    suspend fun getAllSupportedCurrencies(): SupportedCodesResponse

    @GET(BuildConfig.EXCHANGE_RATE_API_KEY + ExchangeRateApiValues.REQUEST_PAIR)
    @ResponseCache(24, TimeUnit.HOURS)
    suspend fun getConversionRate(
        @Path(ExchangeRateApiValues.PARAMETER_FROM) fromCurrency: String,
        @Path(ExchangeRateApiValues.PARAMETER_TO) toCurrency: String
    ): ConversionRateResponse

    @GET(BuildConfig.EXCHANGE_RATE_API_KEY + ExchangeRateApiValues.REQUEST_LATEST_COURSE)
    @ResponseCache(24, TimeUnit.HOURS)
    suspend fun getLatestCourse(
        @Path(ExchangeRateApiValues.PARAMETER_CURRENCY) currencyCode: String
    ): LatestCurrencyRatesResponse

    companion object {
        fun create(
            apl: Application
        ) = MyRetrofit()
            .create<IExchangeRateApiService>(
                apl = apl,
                url = ExchangeRateApiValues.BASE_URL
            )
    }
}