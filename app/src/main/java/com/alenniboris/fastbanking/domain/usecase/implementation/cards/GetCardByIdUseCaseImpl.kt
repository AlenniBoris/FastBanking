package com.alenniboris.fastbanking.domain.usecase.implementation.cards

import com.alenniboris.fastbanking.domain.model.CustomResultModelDomain
import com.alenniboris.fastbanking.domain.model.card.CardModelDomain
import com.alenniboris.fastbanking.domain.model.currency.CurrencyModelDomain
import com.alenniboris.fastbanking.domain.model.exception.CommonExceptionModelDomain
import com.alenniboris.fastbanking.domain.repository.IBankProductsRepository
import com.alenniboris.fastbanking.domain.usecase.logic.cards.IGetCardByIdUseCase
import com.alenniboris.fastbanking.domain.usecase.logic.currency.IGetCurrenciesExchangeRateUseCase
import com.alenniboris.fastbanking.domain.utils.IAppDispatchers
import kotlinx.coroutines.withContext

class GetCardByIdUseCaseImpl(
    private val bankRepository: IBankProductsRepository,
    private val getCurrenciesExchangeRateUseCase: IGetCurrenciesExchangeRateUseCase,
    private val dispatchers: IAppDispatchers
) : IGetCardByIdUseCase {

    override suspend fun invoke(
        id: String
    ): CustomResultModelDomain<CardModelDomain?, CommonExceptionModelDomain> =
        withContext(dispatchers.IO) {

            val cardResult = bankRepository.getUserCardById(id = id)

            cardResult.result?.let { card ->

                val rateResult = getCurrenciesExchangeRateUseCase.invoke(
                    fromCurrency = CurrencyModelDomain(
                        code = card.currencyCode,
                        fullName = card.currencyCode
                    ),
                    toCurrency = CurrencyModelDomain(
                        code = card.reserveCurrencyCode,
                        fullName = card.reserveCurrencyCode
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