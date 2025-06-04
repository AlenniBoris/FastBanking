package com.alenniboris.fastbanking.domain.usecase.implementation.transactions

import com.alenniboris.fastbanking.domain.model.CustomResultModelDomain
import com.alenniboris.fastbanking.domain.model.card.CardModelDomain
import com.alenniboris.fastbanking.domain.model.currency.CurrencyModelDomain
import com.alenniboris.fastbanking.domain.model.exception.CommonExceptionModelDomain
import com.alenniboris.fastbanking.domain.model.transaction.TransactionModelDomain
import com.alenniboris.fastbanking.domain.model.transaction.TransactionType
import com.alenniboris.fastbanking.domain.repository.IBankProductsRepository
import com.alenniboris.fastbanking.domain.repository.ICurrencyRepository
import com.alenniboris.fastbanking.domain.usecase.logic.transactions.IMakeTransactionForCreditByContractNumberUseCase
import com.alenniboris.fastbanking.domain.utils.IAppDispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import java.util.Calendar

class MakeTransactionForCreditByContractNumberUseCaseImpl(
    private val bankProductsRepository: IBankProductsRepository,
    private val currencyRepository: ICurrencyRepository,
    private val dispatchers: IAppDispatchers
) : IMakeTransactionForCreditByContractNumberUseCase {

    override suspend fun invoke(
        usedCard: CardModelDomain,
        contractNumber: String,
        amount: Double
    ): CustomResultModelDomain<Unit, CommonExceptionModelDomain> = withContext(dispatchers.IO) {
        return@withContext when (
            val creditResult =
                bankProductsRepository.getCreditByContractNumber(contractNumber = contractNumber)
        ) {
            is CustomResultModelDomain.Success -> {

                creditResult.result?.let { credit ->

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

                    val exchangeRateFromBaseUsedCardCurrencyToBaseCreditCurrencyDeffered = async {
                        currencyRepository.getExchangeRateForCurrencies(
                            fromCurrency = CurrencyModelDomain(
                                code = usedCard.currencyCode,
                                fullName = usedCard.currencyCode
                            ),
                            toCurrency = CurrencyModelDomain(
                                code = credit.currency,
                                fullName = credit.currency
                            )
                        )
                    }
                    val exchangeRateFromBaseUsedCardCurrencyToBaseCreditCurrency = when (
                        val exchangeRateRes =
                            exchangeRateFromBaseUsedCardCurrencyToBaseCreditCurrencyDeffered.await()
                    ) {
                        is CustomResultModelDomain.Success ->
                            exchangeRateRes.result

                        is CustomResultModelDomain.Error -> {
                            return@withContext CustomResultModelDomain.Error(
                                CommonExceptionModelDomain.Other
                            )
                        }
                    }

                    val exchangeRateFromBaseUsedCardCurrencyToReserveCreditCurrencyDeffered =
                        async {
                            currencyRepository.getExchangeRateForCurrencies(
                                fromCurrency = CurrencyModelDomain(
                                    code = usedCard.currencyCode,
                                    fullName = usedCard.currencyCode
                                ),
                                toCurrency = CurrencyModelDomain(
                                    code = credit.reserveCurrency,
                                    fullName = credit.reserveCurrency
                                )
                            )
                        }
                    val exchangeRateFromBaseUsedCardCurrencyToReserveCreditCurrency = when (
                        val exchangeRateRes =
                            exchangeRateFromBaseUsedCardCurrencyToReserveCreditCurrencyDeffered.await()
                    ) {
                        is CustomResultModelDomain.Success ->
                            exchangeRateRes.result

                        is CustomResultModelDomain.Error -> {
                            return@withContext CustomResultModelDomain.Error(
                                CommonExceptionModelDomain.Other
                            )
                        }
                    }
                    val updatedCredit = credit.copy(
                        initialAmount = credit.initialAmount - amount * exchangeRateFromBaseUsedCardCurrencyToBaseCreditCurrency,
                        amountInReserveCurrency = usedCard.amountInReserveCurrency - amount * exchangeRateFromBaseUsedCardCurrencyToReserveCreditCurrency
                    )
                    val usedCreditUpdateDeferred = async {
                        bankProductsRepository.updateCreditValue(credit = updatedCredit)
                    }
                    when (
                        val creditUpdateResult = usedCreditUpdateDeferred.await()
                    ) {
                        is CustomResultModelDomain.Success -> {}
                        is CustomResultModelDomain.Error -> {
                            return@withContext CustomResultModelDomain.Error(
                                creditUpdateResult.exception
                            )
                        }
                    }

                    val transaction = TransactionModelDomain(
                        currency = CurrencyModelDomain(
                            code = usedCard.currencyCode,
                            fullName = usedCard.currencyCode
                        ),
                        date = Calendar.getInstance().time,
                        details = "From ${usedCard.owner.name} ${usedCard.owner.surname} to credit ${credit.contractNumber}",
                        id = "",
                        priceAmount = amount,
                        senderId = usedCard.id,
                        receiverId = credit.id,
                        type = TransactionType.Credit
                    )

                    return@withContext when (
                        val transactionSendingResult =
                            bankProductsRepository.sendTransactionToDatabase(
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
                } ?: return@withContext CustomResultModelDomain.Error(
                    CommonExceptionModelDomain.NotOurBankCredit
                )
            }

            is CustomResultModelDomain.Error -> {
                CustomResultModelDomain.Error(creditResult.exception)
            }
        }
    }
}