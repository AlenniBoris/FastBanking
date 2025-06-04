package com.alenniboris.fastbanking.domain.usecase.implementation.transactions

import com.alenniboris.fastbanking.domain.model.CustomResultModelDomain
import com.alenniboris.fastbanking.domain.model.card.CardModelDomain
import com.alenniboris.fastbanking.domain.model.currency.CurrencyModelDomain
import com.alenniboris.fastbanking.domain.model.exception.CommonExceptionModelDomain
import com.alenniboris.fastbanking.domain.model.transaction.TransactionModelDomain
import com.alenniboris.fastbanking.domain.model.transaction.TransactionType
import com.alenniboris.fastbanking.domain.repository.IBankProductsRepository
import com.alenniboris.fastbanking.domain.repository.ICurrencyRepository
import com.alenniboris.fastbanking.domain.usecase.logic.transactions.IMakeTransactionByCardNumberUseCase
import com.alenniboris.fastbanking.domain.utils.IAppDispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import java.util.Calendar

class MakeTransactionByCardNumberUseCaseImpl(
    private val bankProductsRepository: IBankProductsRepository,
    private val currencyRepository: ICurrencyRepository,
    private val dispatchers: IAppDispatchers
) : IMakeTransactionByCardNumberUseCase {

    override suspend fun invoke(
        usedCard: CardModelDomain,
        cardNumber: String,
        amount: Double
    ): CustomResultModelDomain<Unit, CommonExceptionModelDomain> = withContext(dispatchers.IO) {

        if (usedCard.amount < amount) {
            return@withContext CustomResultModelDomain.Error(
                CommonExceptionModelDomain.NoEnoughMoneyAmount
            )
        }
        val usedCardExchangeRateFromBaseToReserveDeferred = async {
            currencyRepository.getExchangeRateForCurrencies(
                fromCurrency = CurrencyModelDomain(
                    code = usedCard.currencyCode,
                    fullName = usedCard.currencyCode
                ),
                toCurrency = CurrencyModelDomain(
                    code = usedCard.reserveCurrencyCode,
                    fullName = usedCard.reserveCurrencyCode
                )
            )
        }
        val usedCardExchangeRateFromBaseToReserve = when (
            val exchangeRateRes = usedCardExchangeRateFromBaseToReserveDeferred.await()
        ) {
            is CustomResultModelDomain.Success ->
                exchangeRateRes.result

            is CustomResultModelDomain.Error -> {
                return@withContext CustomResultModelDomain.Error(
                    CommonExceptionModelDomain.Other
                )
            }
        }
        val updatedUsedCard = usedCard.copy(
            amount = usedCard.amount - amount,
            amountInReserveCurrency = usedCard.amountInReserveCurrency - amount * usedCardExchangeRateFromBaseToReserve
        )
        val usedCardUpdateDeferred = async {
            bankProductsRepository.updateCardValue(card = updatedUsedCard)
        }

        val receiverCardDeferred = async {
            bankProductsRepository.getCardByNumber(cardNumber = cardNumber)
        }

        when (
            val usedCardUpdateResult = usedCardUpdateDeferred.await()
        ) {
            is CustomResultModelDomain.Success -> {}
            is CustomResultModelDomain.Error -> {
                return@withContext CustomResultModelDomain.Error(
                    usedCardUpdateResult.exception
                )
            }
        }
        val receiverCard = when (
            val receivedCardResult = receiverCardDeferred.await()
        ) {
            is CustomResultModelDomain.Success -> {
                receivedCardResult.result
            }

            is CustomResultModelDomain.Error -> {
                return@withContext CustomResultModelDomain.Error(
                    receivedCardResult.exception
                )
            }
        }
        if (
            receiverCard != null
        ) {

            val receiverCardMainCurrency = receiverCard.currencyCode
            val receiverCardReserveCurrency = receiverCard.reserveCurrencyCode
            val usedCardMainCurrency = usedCard.currencyCode

            val fromUsedCardToReceiverCardExchangeRateDeferred = async {
                currencyRepository.getExchangeRateForCurrencies(
                    fromCurrency = CurrencyModelDomain(
                        code = usedCardMainCurrency,
                        fullName = usedCardMainCurrency
                    ),
                    toCurrency = CurrencyModelDomain(
                        code = receiverCardMainCurrency,
                        fullName = receiverCardMainCurrency
                    )
                )
            }
            val fromUsedCardToReceiverCardExchangeRate = when (
                val exchangeRateRes = fromUsedCardToReceiverCardExchangeRateDeferred.await()
            ) {
                is CustomResultModelDomain.Success ->
                    exchangeRateRes.result

                is CustomResultModelDomain.Error -> {
                    return@withContext CustomResultModelDomain.Error(
                        CommonExceptionModelDomain.Other
                    )
                }
            }

            val fromUsedCardToReceiverCardReserveCurrencyExchangeRateDeferred = async {
                currencyRepository.getExchangeRateForCurrencies(
                    fromCurrency = CurrencyModelDomain(
                        code = usedCardMainCurrency,
                        fullName = usedCardMainCurrency
                    ),
                    toCurrency = CurrencyModelDomain(
                        code = receiverCardReserveCurrency,
                        fullName = receiverCardReserveCurrency
                    )
                )
            }
            val fromUsedCardToReceiverCardReserveCurrencyExchangeRate = when (
                val exchangeRateRes =
                    fromUsedCardToReceiverCardReserveCurrencyExchangeRateDeferred.await()
            ) {
                is CustomResultModelDomain.Success ->
                    exchangeRateRes.result

                is CustomResultModelDomain.Error -> {
                    return@withContext CustomResultModelDomain.Error(
                        CommonExceptionModelDomain.Other
                    )
                }
            }

            val updatedReceiverCard =
                receiverCard.copy(
                    amount = receiverCard.amount + amount * fromUsedCardToReceiverCardExchangeRate,
                    amountInReserveCurrency = receiverCard.amountInReserveCurrency + amount * fromUsedCardToReceiverCardReserveCurrencyExchangeRate
                )
            val receiverCardUpdateDeferred = async {
                bankProductsRepository.updateCardValue(card = updatedReceiverCard)
            }
            when (
                val receiverCardUpdateRes = receiverCardUpdateDeferred.await()
            ) {
                is CustomResultModelDomain.Success -> {}
                is CustomResultModelDomain.Error -> {
                    return@withContext CustomResultModelDomain.Error(
                        receiverCardUpdateRes.exception
                    )
                }
            }
        }

        val transaction = TransactionModelDomain(
            currency = CurrencyModelDomain(
                code = usedCard.currencyCode,
                fullName = usedCard.currencyCode
            ),
            date = Calendar.getInstance().time,
            details = receiverCard?.owner?.let {
                "From ${usedCard.owner.name} ${usedCard.owner.surname} to ${receiverCard.owner.name} ${receiverCard.owner.surname}"
            } ?: "From ${usedCard.owner.name} ${usedCard.owner.surname} to $cardNumber",
            id = "",
            priceAmount = amount,
            senderId = usedCard.id,
            receiverId = receiverCard?.id ?: "",
            type = TransactionType.P2P
        )

        return@withContext when (
            val transactionSendingResult = bankProductsRepository.sendTransactionToDatabase(
                transaction = transaction
            )
        ) {
            is CustomResultModelDomain.Success -> {
                CustomResultModelDomain.Success(Unit)
            }

            is CustomResultModelDomain.Error -> {
                CustomResultModelDomain.Error(
                    transactionSendingResult.exception
                )
            }
        }
    }
}