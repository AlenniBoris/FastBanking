package com.alenniboris.fastbanking.domain.usecase.implementation.accounts

import com.alenniboris.fastbanking.domain.model.CustomResultModelDomain
import com.alenniboris.fastbanking.domain.model.account.AccountModelDomain
import com.alenniboris.fastbanking.domain.model.currency.CurrencyModelDomain
import com.alenniboris.fastbanking.domain.model.exception.CommonExceptionModelDomain
import com.alenniboris.fastbanking.domain.repository.IUserRepository
import com.alenniboris.fastbanking.domain.usecase.logic.accounts.IGetAllUserAccountsUseCase
import com.alenniboris.fastbanking.domain.usecase.logic.currency.IGetCurrenciesExchangeRateUseCase
import com.alenniboris.fastbanking.domain.usecase.logic.user.IGetCurrentUserUseCase
import com.alenniboris.fastbanking.domain.utils.IAppDispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.withContext

class GetAllUserAccountsUseCaseImpl(
    private val userRepository: IUserRepository,
    private val dispatchers: IAppDispatchers,
    private val getCurrenciesExchangeRateUseCase: IGetCurrenciesExchangeRateUseCase,
    private val getCurrentUserUseCase: IGetCurrentUserUseCase
) : IGetAllUserAccountsUseCase {

    override suspend fun invoke():
            CustomResultModelDomain<List<AccountModelDomain>, CommonExceptionModelDomain> =
        withContext(dispatchers.IO) {

            val user = getCurrentUserUseCase.userFlow.value
                ?: return@withContext CustomResultModelDomain.Error(
                    CommonExceptionModelDomain.Other
                )

            val accountsResult = userRepository.getAllUserAccounts(user = user)

            accountsResult.result?.let { account ->

                val rates = account
                    .map { card ->
                        async {
                            getCurrenciesExchangeRateUseCase.invoke(
                                fromCurrency = CurrencyModelDomain(
                                    code = card.currency,
                                    fullName = card.currency
                                ),
                                toCurrency = CurrencyModelDomain(
                                    code = card.reserveCurrency,
                                    fullName = card.reserveCurrency
                                )
                            )
                        }
                    }.awaitAll()

                val res = account.mapIndexed { index, acc ->
                    val rateResult = rates[index]
                    (rateResult as? CustomResultModelDomain.Success)?.let {
                        val rate = rateResult.result
                        acc.copy(
                            amountInReserveCurrency = acc.amount * rate
                        )
                    } ?: acc
                }

                return@withContext CustomResultModelDomain.Success(
                    res
                )
            } ?: return@withContext accountsResult
        }
}