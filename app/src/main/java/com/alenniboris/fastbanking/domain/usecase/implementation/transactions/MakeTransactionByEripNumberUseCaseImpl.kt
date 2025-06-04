package com.alenniboris.fastbanking.domain.usecase.implementation.transactions

import android.util.Log
import com.alenniboris.fastbanking.domain.model.CustomResultModelDomain
import com.alenniboris.fastbanking.domain.model.card.CardModelDomain
import com.alenniboris.fastbanking.domain.model.currency.CurrencyModelDomain
import com.alenniboris.fastbanking.domain.model.exception.CommonExceptionModelDomain
import com.alenniboris.fastbanking.domain.model.transaction.TransactionModelDomain
import com.alenniboris.fastbanking.domain.model.transaction.TransactionType
import com.alenniboris.fastbanking.domain.repository.IBankProductsRepository
import com.alenniboris.fastbanking.domain.repository.ICurrencyRepository
import com.alenniboris.fastbanking.domain.usecase.logic.transactions.IMakeTransactionByEripNumberUseCase
import com.alenniboris.fastbanking.domain.utils.IAppDispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import java.util.Calendar

class MakeTransactionByEripNumberUseCaseImpl(
    private val bankProductsRepository: IBankProductsRepository,
    private val currencyRepository: ICurrencyRepository,
    private val dispatchers: IAppDispatchers
) : IMakeTransactionByEripNumberUseCase {

    override suspend fun invoke(
        eripNumber: String,
        usedCard: CardModelDomain,
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

        var transactionType = TransactionType.P2P
        var transactionDetails =
            "From ${usedCard.owner.name} ${usedCard.owner.surname} to $eripNumber"
        var receiverId = ""

        when (
            val cardSearchResult =
                bankProductsRepository.getCardByEripNumber(eripNumber = eripNumber)
        ) {
            is CustomResultModelDomain.Success -> {
                val receiverCard = cardSearchResult.result

                if (receiverCard != null) {

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

                    transactionType = TransactionType.P2P
                    transactionDetails =
                        "From ${usedCard.owner.name} ${usedCard.owner.surname} to ${receiverCard.owner.name} ${receiverCard.owner.surname}"
                    receiverId = receiverCard.id
                } else {

                    when (
                        val creditSearchResult =
                            bankProductsRepository.getCreditByEripNumber(eripNumber = eripNumber)
                    ) {
                        is CustomResultModelDomain.Success -> {

                            val receiverCredit = creditSearchResult.result

                            if (receiverCredit != null) {

                                val receiverCreditMainCurrency = receiverCredit.currency
                                val receiverCardReserveCurrency = receiverCredit.reserveCurrency
                                val usedCardMainCurrency = usedCard.currencyCode

                                val fromUsedCardToReceiverCreditExchangeRateDeferred = async {
                                    currencyRepository.getExchangeRateForCurrencies(
                                        fromCurrency = CurrencyModelDomain(
                                            code = usedCardMainCurrency,
                                            fullName = usedCardMainCurrency
                                        ),
                                        toCurrency = CurrencyModelDomain(
                                            code = receiverCreditMainCurrency,
                                            fullName = receiverCreditMainCurrency
                                        )
                                    )
                                }
                                val fromUsedCardToReceiverCreditExchangeRate = when (
                                    val exchangeRateRes =
                                        fromUsedCardToReceiverCreditExchangeRateDeferred.await()
                                ) {
                                    is CustomResultModelDomain.Success ->
                                        exchangeRateRes.result

                                    is CustomResultModelDomain.Error -> {
                                        return@withContext CustomResultModelDomain.Error(
                                            CommonExceptionModelDomain.Other
                                        )
                                    }
                                }

                                val fromUsedCardToReceiverCreditReserveCurrencyExchangeRateDeferred =
                                    async {
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
                                val fromUsedCardToReceiverCreditReserveCurrencyExchangeRate = when (
                                    val exchangeRateRes =
                                        fromUsedCardToReceiverCreditReserveCurrencyExchangeRateDeferred.await()
                                ) {
                                    is CustomResultModelDomain.Success ->
                                        exchangeRateRes.result

                                    is CustomResultModelDomain.Error -> {
                                        return@withContext CustomResultModelDomain.Error(
                                            CommonExceptionModelDomain.Other
                                        )
                                    }
                                }

                                val updatedReceiverCredit =
                                    receiverCredit.copy(
                                        initialAmount = receiverCredit.initialAmount + amount * fromUsedCardToReceiverCreditExchangeRate,
                                        amountInReserveCurrency = receiverCredit.amountInReserveCurrency + amount * fromUsedCardToReceiverCreditReserveCurrencyExchangeRate
                                    )
                                val receiverCardUpdateDeferred = async {
                                    bankProductsRepository.updateCreditValue(credit = updatedReceiverCredit)
                                }
                                when (
                                    val receiverCreditUpdateRes = receiverCardUpdateDeferred.await()
                                ) {
                                    is CustomResultModelDomain.Success -> {}
                                    is CustomResultModelDomain.Error -> {
                                        return@withContext CustomResultModelDomain.Error(
                                            receiverCreditUpdateRes.exception
                                        )
                                    }
                                }
                            } else {

                                when (
                                    val accountSearchResult =
                                        bankProductsRepository.getAccountByEripNumber(eripNumber = eripNumber)
                                ) {
                                    is CustomResultModelDomain.Success -> {

                                        val receiverAccount = accountSearchResult.result
                                        Log.e("!!!!", receiverAccount.toString())

                                        if (receiverAccount != null) {

                                            val receiverAccountMainCurrency =
                                                receiverAccount.currency
                                            val receiverAccountReserveCurrency =
                                                receiverAccount.reserveCurrency
                                            val usedCardMainCurrency = usedCard.currencyCode

                                            val fromUsedCardToReceiverAccountExchangeRateDeferred =
                                                async {
                                                    currencyRepository.getExchangeRateForCurrencies(
                                                        fromCurrency = CurrencyModelDomain(
                                                            code = usedCardMainCurrency,
                                                            fullName = usedCardMainCurrency
                                                        ),
                                                        toCurrency = CurrencyModelDomain(
                                                            code = receiverAccountMainCurrency,
                                                            fullName = receiverAccountMainCurrency
                                                        )
                                                    )
                                                }
                                            val fromUsedCardToReceiverAccountExchangeRate = when (
                                                val exchangeRateRes =
                                                    fromUsedCardToReceiverAccountExchangeRateDeferred.await()
                                            ) {
                                                is CustomResultModelDomain.Success ->
                                                    exchangeRateRes.result

                                                is CustomResultModelDomain.Error -> {
                                                    return@withContext CustomResultModelDomain.Error(
                                                        CommonExceptionModelDomain.Other
                                                    )
                                                }
                                            }

                                            val fromUsedCardToReceiverAccountReserveCurrencyExchangeRateDeferred =
                                                async {
                                                    currencyRepository.getExchangeRateForCurrencies(
                                                        fromCurrency = CurrencyModelDomain(
                                                            code = usedCardMainCurrency,
                                                            fullName = usedCardMainCurrency
                                                        ),
                                                        toCurrency = CurrencyModelDomain(
                                                            code = receiverAccountReserveCurrency,
                                                            fullName = receiverAccountReserveCurrency
                                                        )
                                                    )
                                                }
                                            val fromUsedCardToReceiverAccountReserveCurrencyExchangeRate =
                                                when (
                                                    val exchangeRateRes =
                                                        fromUsedCardToReceiverAccountReserveCurrencyExchangeRateDeferred.await()
                                                ) {
                                                    is CustomResultModelDomain.Success ->
                                                        exchangeRateRes.result

                                                    is CustomResultModelDomain.Error -> {
                                                        return@withContext CustomResultModelDomain.Error(
                                                            CommonExceptionModelDomain.Other
                                                        )
                                                    }
                                                }

                                            val updatedReceiverAccount =
                                                receiverAccount.copy(
                                                    amount = receiverAccount.amount + amount * fromUsedCardToReceiverAccountExchangeRate,
                                                    amountInReserveCurrency = receiverAccount.amountInReserveCurrency + amount * fromUsedCardToReceiverAccountReserveCurrencyExchangeRate
                                                )
                                            Log.e("!!!!", updatedReceiverAccount.toString())
                                            val receiverCardUpdateDeferred = async {
                                                bankProductsRepository.updateAccountValue(account = updatedReceiverAccount)
                                            }
                                            when (
                                                val receiverCreditUpdateRes =
                                                    receiverCardUpdateDeferred.await()
                                            ) {
                                                is CustomResultModelDomain.Success -> {}
                                                is CustomResultModelDomain.Error -> {
                                                    return@withContext CustomResultModelDomain.Error(
                                                        receiverCreditUpdateRes.exception
                                                    )
                                                }
                                            }
                                        }
                                    }

                                    is CustomResultModelDomain.Error -> {
                                        return@withContext CustomResultModelDomain.Error(
                                            accountSearchResult.exception
                                        )
                                    }
                                }
                            }
                        }

                        is CustomResultModelDomain.Error -> {
                            return@withContext CustomResultModelDomain.Error(
                                creditSearchResult.exception
                            )
                        }
                    }
                }
            }

            is CustomResultModelDomain.Error -> {
                return@withContext CustomResultModelDomain.Error(
                    cardSearchResult.exception
                )
            }
        }


        val transaction = TransactionModelDomain(
            currency = CurrencyModelDomain(
                code = usedCard.currencyCode,
                fullName = usedCard.currencyCode
            ),
            date = Calendar.getInstance().time,
            details = transactionDetails,
            id = "",
            priceAmount = amount,
            senderId = usedCard.id,
            receiverId = receiverId,
            type = transactionType
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