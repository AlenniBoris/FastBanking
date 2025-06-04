package com.alenniboris.fastbanking.data.repository

import com.alenniboris.fastbanking.data.mappers.toCommonException
import com.alenniboris.fastbanking.data.model.account.AccountModelData
import com.alenniboris.fastbanking.data.model.account.toModelData
import com.alenniboris.fastbanking.data.model.account.toModelDomain
import com.alenniboris.fastbanking.data.model.appliances.CardApplianceModelData
import com.alenniboris.fastbanking.data.model.appliances.CreditApplianceModelData
import com.alenniboris.fastbanking.data.model.appliances.DepositApplianceModelData
import com.alenniboris.fastbanking.data.model.appliances.toModelDomain
import com.alenniboris.fastbanking.data.model.appliances.toSavingModel
import com.alenniboris.fastbanking.data.model.card.CardModelData
import com.alenniboris.fastbanking.data.model.card.toModelData
import com.alenniboris.fastbanking.data.model.card.toModelDomain
import com.alenniboris.fastbanking.data.model.credit.CreditModelData
import com.alenniboris.fastbanking.data.model.credit.toModelData
import com.alenniboris.fastbanking.data.model.credit.toModelDomain
import com.alenniboris.fastbanking.data.model.transaction.TransactionModelData
import com.alenniboris.fastbanking.data.model.transaction.toModelData
import com.alenniboris.fastbanking.data.model.transaction.toModelDomain
import com.alenniboris.fastbanking.domain.model.CustomResultModelDomain
import com.alenniboris.fastbanking.domain.model.account.AccountModelDomain
import com.alenniboris.fastbanking.domain.model.appliances.CardApplianceModelDomain
import com.alenniboris.fastbanking.domain.model.appliances.CreditApplianceModelDomain
import com.alenniboris.fastbanking.domain.model.appliances.DepositApplianceModelDomain
import com.alenniboris.fastbanking.domain.model.card.CardModelDomain
import com.alenniboris.fastbanking.domain.model.credit.CreditModelDomain
import com.alenniboris.fastbanking.domain.model.exception.CommonExceptionModelDomain
import com.alenniboris.fastbanking.domain.model.transaction.TransactionModelDomain
import com.alenniboris.fastbanking.domain.model.user.UserModelDomain
import com.alenniboris.fastbanking.domain.repository.IBankProductsRepository
import com.alenniboris.fastbanking.domain.utils.GsonUtil.fromJson
import com.alenniboris.fastbanking.domain.utils.IAppDispatchers
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.withContext

class BankProductsRepositoryImpl(
    private val database: FirebaseDatabase,
    private val dispatchers: IAppDispatchers
) : IBankProductsRepository {

    override suspend fun sendTransactionToDatabase(
        transaction: TransactionModelDomain
    ): CustomResultModelDomain<Unit, CommonExceptionModelDomain> = withContext(dispatchers.IO) {
        return@withContext DatabaseFunctions.addRecordToTheTable(
            dispatcher = dispatchers.IO,
            database = database,
            table = FirebaseDatabaseValues.TABLE_TRANSACTION,
            exceptionMapping = { exception ->
                exception.toCommonException()
            },
            onGeneratingError = {
                CommonExceptionModelDomain.Other
            },
            editingRecord = { id ->
                transaction.copy(id = id).toModelData()
            }
        )
    }

    override suspend fun updateAccountValue(
        account: AccountModelDomain
    ): CustomResultModelDomain<Unit, CommonExceptionModelDomain> = withContext(dispatchers.IO) {
        return@withContext DatabaseFunctions.updateElementValue(
            dispatcher = dispatchers.IO,
            database = database,
            table = FirebaseDatabaseValues.TABLE_ACCOUNTS,
            modelId = account.id,
            model = account.toModelData(),
            exceptionMapping = { exception ->
                exception.toCommonException()
            }
        )
    }

    override suspend fun updateCardValue(
        card: CardModelDomain
    ): CustomResultModelDomain<Unit, CommonExceptionModelDomain> = withContext(dispatchers.IO) {
        return@withContext DatabaseFunctions.updateElementValue(
            dispatcher = dispatchers.IO,
            database = database,
            table = FirebaseDatabaseValues.TABLE_CARDS,
            modelId = card.id,
            model = card.toModelData(),
            exceptionMapping = { exception ->
                exception.toCommonException()
            }
        )
    }

    override suspend fun updateCreditValue(
        credit: CreditModelDomain
    ): CustomResultModelDomain<Unit, CommonExceptionModelDomain> = withContext(dispatchers.IO) {
        return@withContext DatabaseFunctions.updateElementValue(
            dispatcher = dispatchers.IO,
            database = database,
            table = FirebaseDatabaseValues.TABLE_CREDITS,
            modelId = credit.id,
            model = credit.toModelData(),
            exceptionMapping = { exception ->
                exception.toCommonException()
            }
        )
    }

    override suspend fun getCardByEripNumber(
        eripNumber: String
    ): CustomResultModelDomain<CardModelDomain?, CommonExceptionModelDomain> =
        withContext(dispatchers.IO) {
            return@withContext DatabaseFunctions.requestElementByField(
                field = FirebaseDatabaseValues.SEARCHING_FIELD_ERIP_NUMBER,
                fieldValue = eripNumber,
                dispatcher = dispatchers.IO,
                database = database,
                table = FirebaseDatabaseValues.TABLE_CARDS,
                resultMapping = { dataModel: CardModelData? ->
                    dataModel?.toModelDomain()
                },
                exceptionMapping = { exception ->
                    exception.toCommonException()
                }
            )
        }

    override suspend fun getCreditByEripNumber(
        eripNumber: String
    ): CustomResultModelDomain<CreditModelDomain?, CommonExceptionModelDomain> =
        withContext(dispatchers.IO) {
            return@withContext DatabaseFunctions.requestElementByField(
                field = FirebaseDatabaseValues.SEARCHING_FIELD_ERIP_NUMBER,
                fieldValue = eripNumber,
                dispatcher = dispatchers.IO,
                database = database,
                table = FirebaseDatabaseValues.TABLE_CREDITS,
                resultMapping = { dataModel: CreditModelData? ->
                    dataModel?.toModelDomain()
                },
                exceptionMapping = { exception ->
                    exception.toCommonException()
                }
            )
        }

    override suspend fun getAccountByEripNumber(
        eripNumber: String
    ): CustomResultModelDomain<AccountModelDomain?, CommonExceptionModelDomain> =
        withContext(dispatchers.IO) {
            return@withContext DatabaseFunctions.requestElementByField(
                field = FirebaseDatabaseValues.SEARCHING_FIELD_ERIP_NUMBER,
                fieldValue = eripNumber,
                dispatcher = dispatchers.IO,
                database = database,
                table = FirebaseDatabaseValues.TABLE_ACCOUNTS,
                resultMapping = { dataModel: AccountModelData? ->
                    dataModel?.toModelDomain()
                },
                exceptionMapping = { exception ->
                    exception.toCommonException()
                }
            )
        }

    override suspend fun getCardByNumber(
        cardNumber: String
    ): CustomResultModelDomain<CardModelDomain?, CommonExceptionModelDomain> =
        withContext(dispatchers.IO) {
            return@withContext DatabaseFunctions.requestElementByField(
                field = FirebaseDatabaseValues.SEARCHING_FIELD_CARD_NUMBER,
                fieldValue = cardNumber,
                dispatcher = dispatchers.IO,
                database = database,
                table = FirebaseDatabaseValues.TABLE_CARDS,
                resultMapping = { dataModel: CardModelData? ->
                    dataModel?.toModelDomain()
                },
                exceptionMapping = { exception ->
                    exception.toCommonException()
                }
            )
        }

    override suspend fun getCreditByContractNumber(
        contractNumber: String
    ): CustomResultModelDomain<CreditModelDomain?, CommonExceptionModelDomain> =
        withContext(dispatchers.IO) {
            return@withContext DatabaseFunctions.requestElementByField(
                field = FirebaseDatabaseValues.SEARCHING_FIELD_CREDIT_CONTRACT_NUMBER,
                fieldValue = contractNumber,
                dispatcher = dispatchers.IO,
                database = database,
                table = FirebaseDatabaseValues.TABLE_CREDITS,
                resultMapping = { dataModel: CreditModelData? ->
                    dataModel?.toModelDomain()
                },
                exceptionMapping = { exception ->
                    exception.toCommonException()
                }
            )
        }

    override suspend fun getAllUserCards(
        user: UserModelDomain
    ): CustomResultModelDomain<List<CardModelDomain>, CommonExceptionModelDomain> =
        withContext(dispatchers.IO) {
            return@withContext DatabaseFunctions.requestListOfElements(
                dispatcher = dispatchers.IO,
                database = database,
                table = FirebaseDatabaseValues.TABLE_CARDS,
                jsonMapping = { json -> json.fromJson<CardModelData>() },
                modelsMapping = { dataModel -> dataModel.toModelDomain() },
                filterPredicate = { domainModel -> domainModel.owner.id == user.id },
                exceptionMapping = { exception ->
                    exception.toCommonException()
                }
            )
        }

    override suspend fun getAllUserTransactionsByCard(
        user: UserModelDomain,
        cardId: String
    ): CustomResultModelDomain<List<TransactionModelDomain>, CommonExceptionModelDomain> =
        withContext(dispatchers.IO) {
            return@withContext DatabaseFunctions.requestListOfElements(
                dispatcher = dispatchers.IO,
                database = database,
                table = FirebaseDatabaseValues.TABLE_TRANSACTION,
                jsonMapping = { json -> json.fromJson<TransactionModelData>() },
                modelsMapping = { dataModel -> dataModel.toModelDomain() },
                filterPredicate = { domainModel ->
                    domainModel.receiverId == cardId || domainModel.senderId == cardId
                },
                exceptionMapping = { exception ->
                    exception.toCommonException()
                }
            )
        }

    override suspend fun getAllUserAccounts(
        user: UserModelDomain
    ): CustomResultModelDomain<List<AccountModelDomain>, CommonExceptionModelDomain> =
        withContext(dispatchers.IO) {
            return@withContext DatabaseFunctions.requestListOfElements(
                dispatcher = dispatchers.IO,
                database = database,
                table = FirebaseDatabaseValues.TABLE_ACCOUNTS,
                jsonMapping = { json -> json.fromJson<AccountModelData>() },
                modelsMapping = { dataModel -> dataModel.toModelDomain() },
                filterPredicate = { domainModel -> domainModel.owner.id == user.id },
                exceptionMapping = { exception ->
                    exception.toCommonException()
                }
            )
        }

    override suspend fun getAllUserCredits(
        user: UserModelDomain
    ): CustomResultModelDomain<List<CreditModelDomain>, CommonExceptionModelDomain> =
        withContext(dispatchers.IO) {
            return@withContext DatabaseFunctions.requestListOfElements(
                dispatcher = dispatchers.IO,
                database = database,
                table = FirebaseDatabaseValues.TABLE_CREDITS,
                jsonMapping = { json -> json.fromJson<CreditModelData>() },
                modelsMapping = { dataModel -> dataModel.toModelDomain() },
                filterPredicate = { domainModel -> domainModel.ownerId == user.id },
                exceptionMapping = { exception ->
                    exception.toCommonException()
                }
            )
        }

    override suspend fun getUserCardById(
        id: String
    ): CustomResultModelDomain<CardModelDomain?, CommonExceptionModelDomain> =
        withContext(dispatchers.IO) {
            return@withContext DatabaseFunctions.requestElementByField(
                field = FirebaseDatabaseValues.SEARCHING_FIELD_ID,
                fieldValue = id,
                dispatcher = dispatchers.IO,
                database = database,
                table = FirebaseDatabaseValues.TABLE_CARDS,
                resultMapping = { dataModel: CardModelData? ->
                    dataModel?.toModelDomain()
                },
                exceptionMapping = { exception ->
                    exception.toCommonException()
                }
            )
        }

    override suspend fun getUserAccountById(
        id: String
    ): CustomResultModelDomain<AccountModelDomain?, CommonExceptionModelDomain> =
        withContext(dispatchers.IO) {
            return@withContext DatabaseFunctions.requestElementByField(
                field = FirebaseDatabaseValues.SEARCHING_FIELD_ID,
                fieldValue = id,
                dispatcher = dispatchers.IO,
                database = database,
                table = FirebaseDatabaseValues.TABLE_ACCOUNTS,
                resultMapping = { dataModel: AccountModelData? ->
                    dataModel?.toModelDomain()
                },
                exceptionMapping = { exception ->
                    exception.toCommonException()
                }
            )
        }


    override suspend fun getUserCreditById(
        id: String
    ): CustomResultModelDomain<CreditModelDomain?, CommonExceptionModelDomain> =
        withContext(dispatchers.IO) {
            return@withContext DatabaseFunctions.requestElementByField(
                field = FirebaseDatabaseValues.SEARCHING_FIELD_ID,
                fieldValue = id,
                dispatcher = dispatchers.IO,
                database = database,
                table = FirebaseDatabaseValues.TABLE_CREDITS,
                resultMapping = { dataModel: CreditModelData? ->
                    dataModel?.toModelDomain()
                },
                exceptionMapping = { exception ->
                    exception.toCommonException()
                }
            )
        }

    override suspend fun getAllUserAppliancesForCards(
        user: UserModelDomain
    ): CustomResultModelDomain<List<CardApplianceModelDomain>, CommonExceptionModelDomain> =
        withContext(dispatchers.IO) {
            return@withContext DatabaseFunctions.requestListOfElements(
                dispatcher = dispatchers.IO,
                database = database,
                table = FirebaseDatabaseValues.TABLE_APPLIANCES_CARDS,
                jsonMapping = { json -> json.fromJson<CardApplianceModelData>() },
                modelsMapping = { dataModel -> dataModel.toModelDomain() },
                filterPredicate = { domainModel -> domainModel.userId == user.id },
                exceptionMapping = { exception ->
                    exception.toCommonException()
                }
            )
        }

    override suspend fun getAllUserAppliancesForCredits(
        user: UserModelDomain
    ): CustomResultModelDomain<List<CreditApplianceModelDomain>, CommonExceptionModelDomain> =
        withContext(dispatchers.IO) {
            return@withContext DatabaseFunctions.requestListOfElements(
                dispatcher = dispatchers.IO,
                database = database,
                table = FirebaseDatabaseValues.TABLE_APPLIANCES_CREDITS,
                jsonMapping = { json -> json.fromJson<CreditApplianceModelData>() },
                modelsMapping = { dataModel -> dataModel.toModelDomain() },
                filterPredicate = { domainModel -> domainModel.userId == user.id },
                exceptionMapping = { exception ->
                    exception.toCommonException()
                }
            )
        }

    override suspend fun getAllUserAppliancesForDeposits(
        user: UserModelDomain
    ): CustomResultModelDomain<List<DepositApplianceModelDomain>, CommonExceptionModelDomain> =
        withContext(dispatchers.IO) {
            return@withContext DatabaseFunctions.requestListOfElements(
                dispatcher = dispatchers.IO,
                database = database,
                table = FirebaseDatabaseValues.TABLE_APPLIANCES_DEPOSITS,
                jsonMapping = { json -> json.fromJson<DepositApplianceModelData>() },
                modelsMapping = { dataModel -> dataModel.toModelDomain() },
                filterPredicate = { domainModel -> domainModel.userId == user.id },
                exceptionMapping = { exception ->
                    exception.toCommonException()
                }
            )
        }

    override suspend fun getCardApplianceById(
        id: String
    ): CustomResultModelDomain<CardApplianceModelDomain?, CommonExceptionModelDomain> =
        withContext(dispatchers.IO) {
            return@withContext DatabaseFunctions.requestElementByField(
                field = FirebaseDatabaseValues.SEARCHING_FIELD_ID,
                fieldValue = id,
                dispatcher = dispatchers.IO,
                database = database,
                table = FirebaseDatabaseValues.TABLE_APPLIANCES_CARDS,
                resultMapping = { dataModel: CardApplianceModelData? ->
                    dataModel?.toModelDomain()
                },
                exceptionMapping = { exception ->
                    exception.toCommonException()
                }
            )
        }

    override suspend fun getCreditApplianceById(
        id: String
    ): CustomResultModelDomain<CreditApplianceModelDomain?, CommonExceptionModelDomain> =
        withContext(dispatchers.IO) {
            return@withContext DatabaseFunctions.requestElementByField(
                field = FirebaseDatabaseValues.SEARCHING_FIELD_ID,
                fieldValue = id,
                dispatcher = dispatchers.IO,
                database = database,
                table = FirebaseDatabaseValues.TABLE_APPLIANCES_CREDITS,
                resultMapping = { dataModel: CreditApplianceModelData? ->
                    dataModel?.toModelDomain()
                },
                exceptionMapping = { exception ->
                    exception.toCommonException()
                }
            )
        }

    override suspend fun getDepositApplianceById(
        id: String
    ): CustomResultModelDomain<DepositApplianceModelDomain?, CommonExceptionModelDomain> =
        withContext(dispatchers.IO) {
            return@withContext DatabaseFunctions.requestElementByField(
                field = FirebaseDatabaseValues.SEARCHING_FIELD_ID,
                fieldValue = id,
                dispatcher = dispatchers.IO,
                database = database,
                table = FirebaseDatabaseValues.TABLE_APPLIANCES_DEPOSITS,
                resultMapping = { dataModel: DepositApplianceModelData? ->
                    dataModel?.toModelDomain()
                },
                exceptionMapping = { exception ->
                    exception.toCommonException()
                }
            )
        }

    override suspend fun sendApplianceForCard(
        appliance: CardApplianceModelDomain
    ): CustomResultModelDomain<Unit, CommonExceptionModelDomain> = withContext(dispatchers.IO) {
        return@withContext DatabaseFunctions.addRecordToTheTable(
            dispatcher = dispatchers.IO,
            database = database,
            table = FirebaseDatabaseValues.TABLE_APPLIANCES_CARDS,
            exceptionMapping = { exception ->
                exception.toCommonException()
            },
            onGeneratingError = {
                CommonExceptionModelDomain.ErrorGettingData
            },
            editingRecord = { newId ->
                appliance.copy(id = newId).toSavingModel()
            }
        )
    }

    override suspend fun sendApplianceForCredit(
        appliance: CreditApplianceModelDomain
    ): CustomResultModelDomain<Unit, CommonExceptionModelDomain> = withContext(dispatchers.IO) {
        return@withContext DatabaseFunctions.addRecordToTheTable(
            dispatcher = dispatchers.IO,
            database = database,
            table = FirebaseDatabaseValues.TABLE_APPLIANCES_CREDITS,
            exceptionMapping = { exception ->
                exception.toCommonException()
            },
            onGeneratingError = {
                CommonExceptionModelDomain.ErrorGettingData
            },
            editingRecord = { newId ->
                appliance.copy(id = newId).toSavingModel()
            }
        )
    }

    override suspend fun sendApplianceForDeposit(
        appliance: DepositApplianceModelDomain
    ): CustomResultModelDomain<Unit, CommonExceptionModelDomain> = withContext(dispatchers.IO) {
        return@withContext DatabaseFunctions.addRecordToTheTable(
            dispatcher = dispatchers.IO,
            database = database,
            table = FirebaseDatabaseValues.TABLE_APPLIANCES_DEPOSITS,
            exceptionMapping = { exception ->
                exception.toCommonException()
            },
            onGeneratingError = {
                CommonExceptionModelDomain.ErrorGettingData
            },
            editingRecord = { newId ->
                appliance.copy(id = newId).toSavingModel()
            }
        )
    }

    override suspend fun getAllUserTransactions(
        user: UserModelDomain
    ): CustomResultModelDomain<List<TransactionModelDomain>, CommonExceptionModelDomain> =
        withContext(dispatchers.IO) {

            val cards = getAllUserCards(user)
            return@withContext if (cards is CustomResultModelDomain.Success) {
                val list = cards.result
                    .map { card ->
                        async {
                            getAllUserTransactionsByCard(
                                user = user,
                                cardId = card.id
                            )
                        }
                    }
                    .awaitAll()
                    .map { transRes ->
                        if (transRes is CustomResultModelDomain.Success) {
                            transRes.result
                        } else return@withContext CustomResultModelDomain.Error(
                            transRes.exception!!
                        )
                    }
                val res: MutableMap<String, TransactionModelDomain> = mutableMapOf()
                list.forEach { listOfTransactions ->
                    listOfTransactions.forEach { transaction ->
                        res.put(transaction.id, transaction)
                    }
                }

                CustomResultModelDomain.Success(res.values.toList())
            } else {
                CustomResultModelDomain.Error(
                    cards.exception!!
                )
            }
        }

    override suspend fun getAllTransactionsForCreditById(
        creditId: String
    ): CustomResultModelDomain<List<TransactionModelDomain>, CommonExceptionModelDomain> =
        withContext(dispatchers.IO) {
            return@withContext DatabaseFunctions.requestListOfElements(
                dispatcher = dispatchers.IO,
                database = database,
                table = FirebaseDatabaseValues.TABLE_TRANSACTION,
                jsonMapping = { json -> json.fromJson<TransactionModelData>() },
                modelsMapping = { dataModel -> dataModel.toModelDomain() },
                filterPredicate = { domainModel -> domainModel.receiverId == creditId },
                exceptionMapping = { exception ->
                    exception.toCommonException()
                }
            )
        }

    override suspend fun getAllTransactionsForAccountById(
        accountId: String
    ): CustomResultModelDomain<List<TransactionModelDomain>, CommonExceptionModelDomain> =
        withContext(dispatchers.IO) {
            return@withContext DatabaseFunctions.requestListOfElements(
                dispatcher = dispatchers.IO,
                database = database,
                table = FirebaseDatabaseValues.TABLE_TRANSACTION,
                jsonMapping = { json -> json.fromJson<TransactionModelData>() },
                modelsMapping = { dataModel -> dataModel.toModelDomain() },
                filterPredicate = { domainModel ->
                    domainModel.receiverId == accountId || domainModel.senderId == accountId
                },
                exceptionMapping = { exception ->
                    exception.toCommonException()
                }
            )
        }
}