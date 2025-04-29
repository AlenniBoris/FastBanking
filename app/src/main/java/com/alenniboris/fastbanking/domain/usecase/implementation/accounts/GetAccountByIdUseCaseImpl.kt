package com.alenniboris.fastbanking.domain.usecase.implementation.accounts

import com.alenniboris.fastbanking.domain.model.CustomResultModelDomain
import com.alenniboris.fastbanking.domain.model.account.AccountModelDomain
import com.alenniboris.fastbanking.domain.model.currency.CurrencyModelDomain
import com.alenniboris.fastbanking.domain.model.exception.CommonExceptionModelDomain
import com.alenniboris.fastbanking.domain.repository.IBankProductsRepository
import com.alenniboris.fastbanking.domain.usecase.logic.accounts.IGetAccountByIdUseCase
import com.alenniboris.fastbanking.domain.usecase.logic.currency.IGetCurrenciesExchangeRateUseCase
import com.alenniboris.fastbanking.domain.utils.IAppDispatchers
import kotlinx.coroutines.withContext

class GetAccountByIdUseCaseImpl(
    private val bankRepository: IBankProductsRepository,
    private val getCurrenciesExchangeRateUseCase: IGetCurrenciesExchangeRateUseCase,
    private val dispatchers: IAppDispatchers
) : IGetAccountByIdUseCase {

    override suspend fun invoke(
        id: String
    ): CustomResultModelDomain<AccountModelDomain?, CommonExceptionModelDomain> =
        withContext(dispatchers.IO) {

            val accountsResult = bankRepository.getUserAccountById(id = id)

            accountsResult.result?.let { account ->

                val rateResult = getCurrenciesExchangeRateUseCase.invoke(
                    fromCurrency = CurrencyModelDomain(
                        code = account.currency,
                        fullName = account.currency
                    ),
                    toCurrency = CurrencyModelDomain(
                        code = account.reserveCurrency,
                        fullName = account.reserveCurrency
                    )
                )

                val res = (rateResult as? CustomResultModelDomain.Success)?.let { res ->
                    account.copy(
                        amountInReserveCurrency = account.amount * res.result
                    )
                } ?: account

                return@withContext CustomResultModelDomain.Success(res)
            } ?: return@withContext accountsResult
        }
}