package com.alenniboris.fastbanking.domain.usecase.implementation.accounts

import com.alenniboris.fastbanking.data.source.remote.api.exchange_rate_api.response.ConversionRateResponse
import com.alenniboris.fastbanking.di.DispatchersModule
import com.alenniboris.fastbanking.di.UseCaseModule
import com.alenniboris.fastbanking.domain.model.CustomResultModelDomain
import com.alenniboris.fastbanking.domain.model.OwnerModelDomain
import com.alenniboris.fastbanking.domain.model.account.AccountModelDomain
import com.alenniboris.fastbanking.domain.model.appliances.CardApplianceModelDomain
import com.alenniboris.fastbanking.domain.model.appliances.CreditApplianceModelDomain
import com.alenniboris.fastbanking.domain.model.appliances.DepositApplianceModelDomain
import com.alenniboris.fastbanking.domain.model.card.CardModelDomain
import com.alenniboris.fastbanking.domain.model.card.CardSystem
import com.alenniboris.fastbanking.domain.model.card.SimpleCardModelDomain
import com.alenniboris.fastbanking.domain.model.credit.CreditModelDomain
import com.alenniboris.fastbanking.domain.model.currency.CurrencyModelDomain
import com.alenniboris.fastbanking.domain.model.currency.CurrencyRatesModelDomain
import com.alenniboris.fastbanking.domain.model.exception.CommonExceptionModelDomain
import com.alenniboris.fastbanking.domain.model.exception.CurrencyExceptionModelDomain
import com.alenniboris.fastbanking.domain.model.transaction.TransactionModelDomain
import com.alenniboris.fastbanking.domain.model.user.UserModelDomain
import com.alenniboris.fastbanking.domain.repository.IBankProductsRepository
import com.alenniboris.fastbanking.domain.repository.ICurrencyRepository
import com.alenniboris.fastbanking.domain.usecase.logic.accounts.IGetAccountByIdUseCase
import com.alenniboris.fastbanking.domain.utils.GsonUtil.fromJson
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

class GetAccountByIdUseCaseImplTest : KoinTest {

    private val testAccount: AccountModelDomain = AccountModelDomain(
        id = "21213",
        amount = 11.2,
        currency = "byn",
        amountInReserveCurrency = 3.61088,
        reserveCurrency = "usd",
        attachedCards = mapOf(
            Pair(
                "ksamklsd",
                SimpleCardModelDomain(
                    id = "1",
                    number = "12121221",
                    system = CardSystem.Mir
                )
            ),
            Pair(
                "dscdscdc",
                SimpleCardModelDomain(
                    id = "11",
                    number = "sai23u832y7832yd",
                    system = CardSystem.Mir
                )
            ),
            Pair(
                "dcjndsckjd",
                SimpleCardModelDomain(
                    id = "123",
                    number = "1111",
                    system = CardSystem.Mir
                )
            ),
            Pair(
                "7127198",
                SimpleCardModelDomain(
                    id = "131",
                    number = "3029876235738910",
                    system = CardSystem.Mir
                )
            ),
        ),
        owner = OwnerModelDomain(
            id = "",
            name = "",
            surname = ""
        ),
        name = "odsklmclksd",
        erip = "dshc bsdhcbsdc"
    )
    private val allAccounts = listOf(testAccount)
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

        single<IBankProductsRepository> {

            object : IBankProductsRepository {
                override suspend fun sendTransactionToDatabase(
                    transaction: TransactionModelDomain
                ): CustomResultModelDomain<Unit, CommonExceptionModelDomain> {
                    TODO("Not yet implemented")
                }

                override suspend fun updateCardValue(
                    card: CardModelDomain
                ): CustomResultModelDomain<Unit, CommonExceptionModelDomain> {
                    TODO("Not yet implemented")
                }

                override suspend fun updateCreditValue(
                    credit: CreditModelDomain
                ): CustomResultModelDomain<Unit, CommonExceptionModelDomain> {
                    TODO("Not yet implemented")
                }

                override suspend fun updateAccountValue(
                    account: AccountModelDomain
                ): CustomResultModelDomain<Unit, CommonExceptionModelDomain> {
                    TODO("Not yet implemented")
                }

                override suspend fun getCardByNumber(
                    cardNumber: String
                ): CustomResultModelDomain<CardModelDomain?, CommonExceptionModelDomain> {
                    TODO("Not yet implemented")
                }

                override suspend fun getCreditByContractNumber(contractNumber: String): CustomResultModelDomain<CreditModelDomain?, CommonExceptionModelDomain> {
                    TODO("Not yet implemented")
                }

                override suspend fun getAllUserCards(user: UserModelDomain): CustomResultModelDomain<List<CardModelDomain>, CommonExceptionModelDomain> {
                    TODO("Not yet implemented")
                }

                override suspend fun getAllUserTransactionsByCard(
                    user: UserModelDomain,
                    cardId: String
                ): CustomResultModelDomain<List<TransactionModelDomain>, CommonExceptionModelDomain> {
                    TODO("Not yet implemented")
                }

                override suspend fun getUserCardById(id: String): CustomResultModelDomain<CardModelDomain?, CommonExceptionModelDomain> {
                    TODO("Not yet implemented")
                }

                override suspend fun getAllUserAccounts(user: UserModelDomain): CustomResultModelDomain<List<AccountModelDomain>, CommonExceptionModelDomain> {
                    TODO("Not yet implemented")
                }

                override suspend fun getUserAccountById(id: String): CustomResultModelDomain<AccountModelDomain?, CommonExceptionModelDomain> {
                    return CustomResultModelDomain.Success(allAccounts.firstOrNull { it.id == id })
                }

                override suspend fun getAllUserCredits(user: UserModelDomain): CustomResultModelDomain<List<CreditModelDomain>, CommonExceptionModelDomain> {
                    TODO("Not yet implemented")
                }

                override suspend fun getUserCreditById(id: String): CustomResultModelDomain<CreditModelDomain?, CommonExceptionModelDomain> {
                    TODO("Not yet implemented")
                }

                override suspend fun getAllUserAppliancesForCards(user: UserModelDomain): CustomResultModelDomain<List<CardApplianceModelDomain>, CommonExceptionModelDomain> {
                    TODO("Not yet implemented")
                }

                override suspend fun getAllUserAppliancesForCredits(user: UserModelDomain): CustomResultModelDomain<List<CreditApplianceModelDomain>, CommonExceptionModelDomain> {
                    TODO("Not yet implemented")
                }

                override suspend fun getAllUserAppliancesForDeposits(user: UserModelDomain): CustomResultModelDomain<List<DepositApplianceModelDomain>, CommonExceptionModelDomain> {
                    TODO("Not yet implemented")
                }

                override suspend fun getCardApplianceById(id: String): CustomResultModelDomain<CardApplianceModelDomain?, CommonExceptionModelDomain> {
                    TODO("Not yet implemented")
                }

                override suspend fun getCreditApplianceById(id: String): CustomResultModelDomain<CreditApplianceModelDomain?, CommonExceptionModelDomain> {
                    TODO("Not yet implemented")
                }

                override suspend fun getDepositApplianceById(id: String): CustomResultModelDomain<DepositApplianceModelDomain?, CommonExceptionModelDomain> {
                    TODO("Not yet implemented")
                }

                override suspend fun sendApplianceForCard(appliance: CardApplianceModelDomain): CustomResultModelDomain<Unit, CommonExceptionModelDomain> {
                    TODO("Not yet implemented")
                }

                override suspend fun sendApplianceForCredit(appliance: CreditApplianceModelDomain): CustomResultModelDomain<Unit, CommonExceptionModelDomain> {
                    TODO("Not yet implemented")
                }

                override suspend fun sendApplianceForDeposit(appliance: DepositApplianceModelDomain): CustomResultModelDomain<Unit, CommonExceptionModelDomain> {
                    TODO("Not yet implemented")
                }

                override suspend fun getAllUserTransactions(user: UserModelDomain): CustomResultModelDomain<List<TransactionModelDomain>, CommonExceptionModelDomain> {
                    TODO("Not yet implemented")
                }

                override suspend fun getAllTransactionsForCreditById(creditId: String): CustomResultModelDomain<List<TransactionModelDomain>, CommonExceptionModelDomain> {
                    TODO("Not yet implemented")
                }

                override suspend fun getAllTransactionsForAccountById(accountId: String): CustomResultModelDomain<List<TransactionModelDomain>, CommonExceptionModelDomain> {
                    TODO("Not yet implemented")
                }

                override suspend fun getCardByEripNumber(eripNumber: String): CustomResultModelDomain<CardModelDomain?, CommonExceptionModelDomain> {
                    TODO("Not yet implemented")
                }

                override suspend fun getCreditByEripNumber(eripNumber: String): CustomResultModelDomain<CreditModelDomain?, CommonExceptionModelDomain> {
                    TODO("Not yet implemented")
                }

                override suspend fun getAccountByEripNumber(eripNumber: String): CustomResultModelDomain<AccountModelDomain?, CommonExceptionModelDomain> {
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

    private val useCase: IGetAccountByIdUseCase by inject()

    @Test
    fun `account returns correctly by id`() = runTest {
        val res = useCase.invoke(
            id = testAccount.id
        )
        assert(res is CustomResultModelDomain.Success)
        kotlin.test.assertEquals(
            expected = testAccount,
            actual = res.result
        )
    }

    @Test
    fun `returns null when account not found`() = runTest {
        val res = useCase.invoke(
            id = "testAccount.id"
        )
        assert(res is CustomResultModelDomain.Success)
        kotlin.test.assertEquals(
            expected = null,
            actual = res.result
        )
    }
}