package com.alenniboris.fastbanking.domain.usecase.implementation.accounts

import com.alenniboris.fastbanking.data.source.remote.api.exchange_rate_api.response.ConversionRateResponse
import com.alenniboris.fastbanking.di.DispatchersModule
import com.alenniboris.fastbanking.di.UseCaseModule
import com.alenniboris.fastbanking.domain.model.CustomResultModelDomain
import com.alenniboris.fastbanking.domain.model.OwnerModelDomain
import com.alenniboris.fastbanking.domain.model.account.AccountModelDomain
import com.alenniboris.fastbanking.domain.model.currency.CurrencyModelDomain
import com.alenniboris.fastbanking.domain.model.currency.CurrencyRatesModelDomain
import com.alenniboris.fastbanking.domain.model.exception.CommonExceptionModelDomain
import com.alenniboris.fastbanking.domain.model.exception.CurrencyExceptionModelDomain
import com.alenniboris.fastbanking.domain.model.user.UserGender
import com.alenniboris.fastbanking.domain.model.user.UserModelDomain
import com.alenniboris.fastbanking.domain.repository.ICurrencyRepository
import com.alenniboris.fastbanking.domain.usecase.logic.accounts.IGetAllUserAccountsCurrencyAmountUseCase
import com.alenniboris.fastbanking.domain.usecase.logic.accounts.IGetAllUserAccountsUseCase
import com.alenniboris.fastbanking.domain.usecase.logic.authorization.IGetCurrentUserUseCase
import com.alenniboris.fastbanking.domain.utils.GsonUtil.fromJson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
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
import java.util.Calendar
import kotlin.test.assertEquals

class GetAllUserAccountsCurrencyAmountUseCaseImplTest : KoinTest {


    private val firstAccount: AccountModelDomain = AccountModelDomain(
        id = "21213",
        amount = 11.2,
        currency = "byn",
        amountInReserveCurrency = 3.61088,
        reserveCurrency = "usd",
        attachedCards = emptyMap(),
        owner = OwnerModelDomain(
            id = "",
            name = "",
            surname = ""
        ),
        name = "odsklmclksd",
        erip = "dshc bsdhcbsdc"
    )
    private val secondAccount: AccountModelDomain = AccountModelDomain(
        id = "21213",
        amount = 10.0,
        currency = "byn",
        amountInReserveCurrency = 3.2240,
        reserveCurrency = "usd",
        attachedCards = emptyMap(),
        owner = OwnerModelDomain(
            id = "",
            name = "",
            surname = ""
        ),
        name = "odsklmclksd",
        erip = "dshc bsdhcbsdc"
    )
    private val baseCurrency = CurrencyModelDomain("usd", "usd")
    private val allAccounts = listOf(firstAccount, secondAccount)
    private val mockedCurrenciesExchangeRateResponse = """
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

    private val testModule = module {

        single<IGetAllUserAccountsUseCase> {
            object : IGetAllUserAccountsUseCase {
                override suspend fun invoke(): CustomResultModelDomain<List<AccountModelDomain>, CommonExceptionModelDomain> {
                    return CustomResultModelDomain.Success(allAccounts)
                }

            }
        }

        factory<IGetCurrentUserUseCase> {
            object : IGetCurrentUserUseCase {
                override val userFlow: StateFlow<UserModelDomain?>
                    get() = MutableStateFlow(
                        UserModelDomain(
                            id = "",
                            password = "111",
                            name = "",
                            surname = "",
                            email = "sddsd@dsdsds",
                            age = 1,
                            gender = UserGender.Male,
                            country = "bel",
                            accountId = "aaa",
                            hasOnlineBanking = true,
                            phoneNumber = "1111111",
                            job = "asasas",
                            dateOfBirth = Calendar.getInstance().time
                        )
                    ).asStateFlow()
            }
        }

        single<ICurrencyRepository> {
            object : ICurrencyRepository {
                override suspend fun getAllCurrencies(): CustomResultModelDomain<List<CurrencyModelDomain>, CurrencyExceptionModelDomain> {
                    TODO("Not yet implemented")
                }

                override suspend fun getExchangeRateForCurrencies(
                    fromCurrency: CurrencyModelDomain?,
                    toCurrency: CurrencyModelDomain?
                ): CustomResultModelDomain<Double, CurrencyExceptionModelDomain> {
                    return CustomResultModelDomain.Success(
                        mockedCurrenciesExchangeRateResponse.conversionRate?.toDouble()!!
                    )
                }

                override suspend fun getLatestConversionRates(currency: CurrencyModelDomain): CustomResultModelDomain<CurrencyRatesModelDomain, CurrencyExceptionModelDomain> {
                    TODO("Not yet implemented")
                }
            }
        }


    }
    private val ioDispatcher = StandardTestDispatcher()

    @get:Rule
    val koinTestRule = KoinTestRule.create {
        modules(UseCaseModule + testModule + DispatchersModule)
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

    private val useCase: IGetAllUserAccountsCurrencyAmountUseCase by inject()

    @Test
    fun `returns exact amount`() = runTest {
        val res = useCase.invoke(baseCurrency = baseCurrency)
        assert(res is CustomResultModelDomain.Success)
        assertEquals(
            expected = 6.83488,
            actual = res.result
        )
    }
}