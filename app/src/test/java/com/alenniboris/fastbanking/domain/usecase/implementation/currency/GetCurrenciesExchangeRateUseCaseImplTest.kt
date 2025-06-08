package com.alenniboris.fastbanking.domain.usecase.implementation.currency

import com.alenniboris.fastbanking.data.model.currency.CurrencyModelData
import com.alenniboris.fastbanking.data.model.currency.CurrencyRatesModelData
import com.alenniboris.fastbanking.data.model.currency.RateModelData
import com.alenniboris.fastbanking.data.model.currency.toModelDomain
import com.alenniboris.fastbanking.data.source.remote.api.exchange_rate_api.IExchangeRateApiService
import com.alenniboris.fastbanking.data.source.remote.api.exchange_rate_api.response.ConversionRateResponse
import com.alenniboris.fastbanking.data.source.remote.api.exchange_rate_api.response.LatestCurrencyRatesResponse
import com.alenniboris.fastbanking.data.source.remote.api.exchange_rate_api.response.SupportedCodesResponse
import com.alenniboris.fastbanking.di.DispatchersModule
import com.alenniboris.fastbanking.di.RepositoryModule
import com.alenniboris.fastbanking.domain.model.CustomResultModelDomain
import com.alenniboris.fastbanking.domain.model.currency.CurrencyModelDomain
import com.alenniboris.fastbanking.domain.repository.ICurrencyRepository
import com.alenniboris.fastbanking.domain.utils.GsonUtil.fromJson
import com.alenniboris.fastbanking.domain.utils.GsonUtil.toJson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule
import org.koin.test.inject
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class GetCurrenciesExchangeRateUseCaseImplTest : KoinTest {

    val mockedAllCurrenciesResponse = """
        {
        	"result":"success",
        	"documentation":"https://www.exchangerate-api.com/docs",
        	"terms_of_use":"https://www.exchangerate-api.com/terms",
        	"supported_codes":[
        		["AED","UAE Dirham"],
        		["AFN","Afghan Afghani"],
        		["ALL","Albanian Lek"],
        		["AMD","Armenian Dram"]
        	]
        }
    """.trimIndent().fromJson<SupportedCodesResponse>()
    val mockedExchangeRateFromBynToUsdResponse = """
        {
            "result": "success",
            "documentation": "https://www.exchangerate-api.com/docs",
            "terms_of_use": "https://www.exchangerate-api.com/terms",
            "time_last_update_unix": 1749600001,
            "time_last_update_utc": "Wed, 11 Jun 2025 00:00:01 +0000",
            "time_next_update_unix": 1749686401,
            "time_next_update_utc": "Thu, 12 Jun 2025 00:00:01 +0000",
            "base_code": "BYN",
            "target_code": "USD",
            "conversion_rate": 0.3224
        }
    """.trimIndent().fromJson<ConversionRateResponse>()
    val mockedLatestConversionRatesResponse = """
        {
        	"result": "success",
        	"documentation": "https://www.exchangerate-api.com/docs",
        	"terms_of_use": "https://www.exchangerate-api.com/terms",
        	"time_last_update_unix": 1585267200,
        	"time_last_update_utc": "Fri, 27 Mar 2020 00:00:00 +0000",
        	"time_next_update_unix": 1585353700,
        	"time_next_update_utc": "Sat, 28 Mar 2020 00:00:00 +0000",
        	"base_code": "USD",
        	"conversion_rates": {
        		"USD": 1,
        		"AUD": 1.4817,
        		"BGN": 1.7741,
        		"CAD": 1.3168,
        		"CHF": 0.9774,
        		"CNY": 6.9454,
        		"EGP": 15.7361,
        		"EUR": 0.9013,
        		"GBP": 0.7679
        	}
        }
    """.trimIndent().fromJson<LatestCurrencyRatesResponse>()

    val testModule = module {
        single<IExchangeRateApiService> {
            object : IExchangeRateApiService {
                override suspend fun getAllSupportedCurrencies(): SupportedCodesResponse {
                    return mockedAllCurrenciesResponse
                }

                override suspend fun getConversionRate(
                    fromCurrency: String,
                    toCurrency: String
                ): ConversionRateResponse {
                    return mockedExchangeRateFromBynToUsdResponse
                }

                override suspend fun getLatestCourse(currencyCode: String): LatestCurrencyRatesResponse {
                    return mockedLatestConversionRatesResponse
                }

            }
        }
    }
    private val ioDispatcher = StandardTestDispatcher()

    @get:Rule
    val koinTestRule = KoinTestRule.create {
        modules(RepositoryModule + testModule + DispatchersModule)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        Dispatchers.setMain(ioDispatcher)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    private val repository: ICurrencyRepository by inject()

    @Test
    fun `returns all supported currencies`() = runTest {
        val res = repository.getAllCurrencies()
        assert(res is CustomResultModelDomain.Success)
        assertEquals(
            expected = mockedAllCurrenciesResponse.supportedCodes
                ?.mapNotNull { supportedCodes ->
                    CurrencyModelData(
                        code = supportedCodes?.get(0),
                        fullName = supportedCodes?.get(1)
                    )
                }
                ?.mapNotNull { it.toModelDomain() },
            actual = res.result
        )
    }

    @Test
    fun `returns all correct conversion rate from byn to usd`() = runTest {
        val res = repository.getExchangeRateForCurrencies(
            fromCurrency = CurrencyModelDomain("byn", "byn"),
            toCurrency = CurrencyModelDomain("usd", "usd")
        )
        assert(res is CustomResultModelDomain.Success)
        assertEquals(
            expected = mockedExchangeRateFromBynToUsdResponse.conversionRate?.toDouble()!!,
            actual = res.result
        )
    }

    @Test
    fun `returns exception when getting conversion rate for one currency`() = runTest {
        val res = repository.getExchangeRateForCurrencies(
            fromCurrency = null,
            toCurrency = CurrencyModelDomain("usd", "usd")
        )
        assertTrue(res is CustomResultModelDomain.Error)
    }

    @Test
    fun `returns codes for currency`() = runTest {
        val res = repository.getLatestConversionRates(CurrencyModelDomain("usd", "usd"))
        assertTrue(res is CustomResultModelDomain.Success)

        val expRes = mockedLatestConversionRatesResponse.conversionRates?.toJson()
            ?.fromJson<Map<String, String>>()
        val result = CurrencyRatesModelData(
            lastUpdateDate = mockedLatestConversionRatesResponse.timeLastUpdateUtc,
            nextUpdateDate = mockedLatestConversionRatesResponse.timeNextUpdateUtc,
            baseCode = mockedLatestConversionRatesResponse.baseCode,
            listOfRates = expRes?.map {
                RateModelData(
                    code = it.key,
                    rate = it.value
                )
            }!!
        ).toModelDomain()
        assertEquals(
            expected = result,
            actual = res.result
        )
    }
}