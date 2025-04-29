package com.alenniboris.fastbanking.domain.usecase.implementation.cards

import com.alenniboris.fastbanking.domain.model.CustomResultModelDomain
import com.alenniboris.fastbanking.domain.model.card.CardModelDomain
import com.alenniboris.fastbanking.domain.model.currency.CurrencyModelDomain
import com.alenniboris.fastbanking.domain.model.exception.CommonExceptionModelDomain
import com.alenniboris.fastbanking.domain.repository.IBankProductsRepository
import com.alenniboris.fastbanking.domain.usecase.logic.cards.IGetAllUserCardsUseCase
import com.alenniboris.fastbanking.domain.usecase.logic.currency.IGetCurrenciesExchangeRateUseCase
import com.alenniboris.fastbanking.domain.usecase.logic.authorization.IGetCurrentUserUseCase
import com.alenniboris.fastbanking.domain.utils.IAppDispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.withContext

class GetAllUserCardsUseCaseImpl(
    private val bankRepository: IBankProductsRepository,
    private val getCurrentUserUseCase: IGetCurrentUserUseCase,
    private val getCurrenciesExchangeRateUseCase: IGetCurrenciesExchangeRateUseCase,
    private val dispatchers: IAppDispatchers
) : IGetAllUserCardsUseCase {

    override suspend fun invoke():
            CustomResultModelDomain<List<CardModelDomain>, CommonExceptionModelDomain> =
        withContext(dispatchers.IO) {

            val user = getCurrentUserUseCase.userFlow.value
                ?: return@withContext CustomResultModelDomain.Error(
                    CommonExceptionModelDomain.Other
                )

            val cardsResult = bankRepository.getAllUserCards(user = user)

            cardsResult.result?.let { cards ->

                val rates = cards
                    .map { card ->
                        async {
                            getCurrenciesExchangeRateUseCase.invoke(
                                fromCurrency = CurrencyModelDomain(
                                    code = card.currencyCode,
                                    fullName = card.currencyCode
                                ),
                                toCurrency = CurrencyModelDomain(
                                    code = card.reserveCurrencyCode,
                                    fullName = card.reserveCurrencyCode
                                )
                            )
                        }
                    }.awaitAll()

                val res = cards.mapIndexed { index, card ->
                    val rateResult = rates[index]
                    (rateResult as? CustomResultModelDomain.Success)?.let {
                        val rate = rateResult.result
                        card.copy(
                            amountInReserveCurrency = card.amount * rate
                        )
                    } ?: card
                }

                return@withContext CustomResultModelDomain.Success(
                    res
                )
            } ?: return@withContext cardsResult
        }
}