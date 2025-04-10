package com.alenniboris.fastbanking.domain.usecase.implementation.credits

import com.alenniboris.fastbanking.domain.model.CustomResultModelDomain
import com.alenniboris.fastbanking.domain.model.credit.CreditModelDomain
import com.alenniboris.fastbanking.domain.model.currency.CurrencyModelDomain
import com.alenniboris.fastbanking.domain.model.exception.CommonExceptionModelDomain
import com.alenniboris.fastbanking.domain.repository.IUserRepository
import com.alenniboris.fastbanking.domain.usecase.logic.credits.IGetCreditByIdUseCase
import com.alenniboris.fastbanking.domain.usecase.logic.currency.IGetCurrenciesExchangeRateUseCase
import com.alenniboris.fastbanking.domain.utils.IAppDispatchers
import kotlinx.coroutines.withContext

class GetCreditByIdUseCaseImpl(
    private val userRepository: IUserRepository,
    private val getCurrenciesExchangeRateUseCase: IGetCurrenciesExchangeRateUseCase,
    private val dispatchers: IAppDispatchers
) : IGetCreditByIdUseCase {

    override suspend fun invoke(
        id: String
    ): CustomResultModelDomain<CreditModelDomain?, CommonExceptionModelDomain> =
        withContext(dispatchers.IO) {

            val creditResult = userRepository.getUserCreditById(id = id)

            creditResult.result?.let { credit ->

                val rateResult = getCurrenciesExchangeRateUseCase.invoke(
                    fromCurrency = CurrencyModelDomain(
                        code = credit.currency,
                        fullName = credit.currency
                    ),
                    toCurrency = CurrencyModelDomain(
                        code = credit.reserveCurrency,
                        fullName = credit.reserveCurrency
                    )
                )

                val res = (rateResult as? CustomResultModelDomain.Success)?.let { res ->
                    credit.copy(
                        amountInReserveCurrency = credit.initialAmount * res.result
                    )
                } ?: credit

                return@withContext CustomResultModelDomain.Success(res)
            } ?: return@withContext creditResult
        }
}