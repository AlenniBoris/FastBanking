package com.alenniboris.fastbanking.domain.usecase.implementation.credits

import android.util.Log
import com.alenniboris.fastbanking.domain.model.CustomResultModelDomain
import com.alenniboris.fastbanking.domain.model.credit.CreditModelDomain
import com.alenniboris.fastbanking.domain.model.currency.CurrencyModelDomain
import com.alenniboris.fastbanking.domain.model.exception.CommonExceptionModelDomain
import com.alenniboris.fastbanking.domain.repository.IUserRepository
import com.alenniboris.fastbanking.domain.usecase.logic.credits.IGetAllUserCreditsUseCase
import com.alenniboris.fastbanking.domain.usecase.logic.currency.IGetCurrenciesExchangeRateUseCase
import com.alenniboris.fastbanking.domain.usecase.logic.user.IGetCurrentUserUseCase
import com.alenniboris.fastbanking.domain.utils.IAppDispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.withContext

class GetAllUserCreditsUseCaseImpl(
    private val userRepository: IUserRepository,
    private val getCurrentUserUseCase: IGetCurrentUserUseCase,
    private val getCurrenciesExchangeRateUseCase: IGetCurrenciesExchangeRateUseCase,
    private val dispatchers: IAppDispatchers
) : IGetAllUserCreditsUseCase {

    override suspend fun invoke():
            CustomResultModelDomain<List<CreditModelDomain>, CommonExceptionModelDomain> =
        withContext(dispatchers.IO) {

            val user = getCurrentUserUseCase.userFlow.value
                ?: return@withContext CustomResultModelDomain.Error(
                    CommonExceptionModelDomain.Other
                )

            val creditsResult = userRepository.getAllUserCredits(user = user)

            (creditsResult as? CustomResultModelDomain.Success)?.let { result ->
                val credits = result.result

                val rates = credits.map { credit ->
                    async {
                        getCurrenciesExchangeRateUseCase.invoke(
                            fromCurrency = CurrencyModelDomain(
                                code = credit.currency,
                                fullName = credit.currency
                            ),
                            toCurrency = CurrencyModelDomain(
                                code = credit.reserveCurrency,
                                fullName = credit.reserveCurrency
                            )
                        )
                    }
                }.awaitAll()

                val res = credits.mapIndexed { index, credit ->
                    val rateResult = rates[index]
                    (rateResult as? CustomResultModelDomain.Success)?.let {
                        val rate = rateResult.result
                        Log.e("!!!", rate.toString())
                        credit.copy(
                            amountInReserveCurrency = credit.initialAmount * rate
                        )
                    } ?: credit
                }

                return@withContext CustomResultModelDomain.Success(res)
            } ?: return@withContext creditsResult
        }
}