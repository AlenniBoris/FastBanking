package com.alenniboris.fastbanking.domain.usecase.implementation.cards

import com.alenniboris.fastbanking.domain.model.CustomResultModelDomain
import com.alenniboris.fastbanking.domain.model.card.CardModelDomain
import com.alenniboris.fastbanking.domain.model.currency.CurrencyModelDomain
import com.alenniboris.fastbanking.domain.model.exception.CommonExceptionModelDomain
import com.alenniboris.fastbanking.domain.repository.IUserRepository
import com.alenniboris.fastbanking.domain.usecase.logic.cards.IGetCardByIdUseCase
import com.alenniboris.fastbanking.domain.usecase.logic.currency.IGetCurrenciesExchangeRateUseCase
import com.alenniboris.fastbanking.domain.utils.IAppDispatchers
import kotlinx.coroutines.withContext

class GetCardByIdUseCaseImpl(
    private val userRepository: IUserRepository,
    private val getCurrenciesExchangeRateUseCase: IGetCurrenciesExchangeRateUseCase,
    private val dispatchers: IAppDispatchers
) : IGetCardByIdUseCase {

    override suspend fun invoke(
        id: String
    ): CustomResultModelDomain<CardModelDomain?, CommonExceptionModelDomain> =
        withContext(dispatchers.IO) {

            val cardResult = userRepository.getUserCardById(id = id)

            cardResult.result?.let { card ->

                val rateResult = getCurrenciesExchangeRateUseCase.invoke(
                    fromCurrency = CurrencyModelDomain(
                        code = card.currency,
                        fullName = card.currency
                    ),
                    toCurrency = CurrencyModelDomain(
                        code = card.reserveCurrency,
                        fullName = card.reserveCurrency
                    )
                )

                val res = (rateResult as? CustomResultModelDomain.Success)?.let { res ->
                    card.copy(
                        amountInReserveCurrency = card.amount * res.result
                    )
                } ?: card

                return@withContext CustomResultModelDomain.Success(res)
            } ?: return@withContext cardResult
        }
}