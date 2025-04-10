package com.alenniboris.fastbanking.domain.usecase.implementation.accounts

import com.alenniboris.fastbanking.domain.model.CustomResultModelDomain
import com.alenniboris.fastbanking.domain.model.currency.CurrencyModelDomain
import com.alenniboris.fastbanking.domain.model.exception.CommonExceptionModelDomain
import com.alenniboris.fastbanking.domain.usecase.logic.accounts.IGetAllUserAccountsCurrencyAmountUseCase
import com.alenniboris.fastbanking.domain.usecase.logic.accounts.IGetAllUserAccountsUseCase
import com.alenniboris.fastbanking.domain.usecase.logic.currency.IGetCurrenciesExchangeRateUseCase
import com.alenniboris.fastbanking.domain.utils.IAppDispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.withContext

class GetAllUserAccountsCurrencyAmountUseCaseImpl(
    private val getAllUserAccountsUseCase: IGetAllUserAccountsUseCase,
    private val getCurrenciesExchangeRateUseCase: IGetCurrenciesExchangeRateUseCase,
    private val dispatchers: IAppDispatchers
) : IGetAllUserAccountsCurrencyAmountUseCase {

    override suspend fun invoke(
        baseCurrency: CurrencyModelDomain
    ): CustomResultModelDomain<Double, CommonExceptionModelDomain> =
        withContext(dispatchers.IO) {
            val accounts = getAllUserAccountsUseCase.invoke()
            (accounts as? CustomResultModelDomain.Success)?.let {

                val accountsResult = accounts.result

                val rates = accountsResult
                    .map { account ->
                        CurrencyModelDomain(
                            code = account.currency,
                            fullName = account.currency
                        )
                    }
                    .map { currency ->
                        async {
                            getCurrenciesExchangeRateUseCase.invoke(
                                fromCurrency = currency,
                                toCurrency = baseCurrency
                            )
                        }
                    }
                    .awaitAll()
                    .map { result ->
                        (result as? CustomResultModelDomain.Success)?.result
                            ?: return@withContext CustomResultModelDomain.Error(
                                CommonExceptionModelDomain.Other
                            )
                    }

                val result = accountsResult
                    .mapIndexed { index, account ->
                        rates[index] * account.amount
                    }
                    .sum()

                return@withContext CustomResultModelDomain.Success(
                    result
                )
            } ?: return@withContext CustomResultModelDomain.Error(
                accounts.exception!!
            )
        }
}