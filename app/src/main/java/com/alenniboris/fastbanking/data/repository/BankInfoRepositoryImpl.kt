package com.alenniboris.fastbanking.data.repository

import com.alenniboris.fastbanking.domain.model.CustomResultModelDomain
import com.alenniboris.fastbanking.domain.model.bank_info.ApplicationInfoModelDomain
import com.alenniboris.fastbanking.domain.model.bank_info.BankNewsModelDomain
import com.alenniboris.fastbanking.domain.model.exception.CommonInfoExceptionModelDomain
import com.alenniboris.fastbanking.domain.repository.IBankInfoRepository
import com.alenniboris.fastbanking.domain.utils.IAppDispatchers
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.withContext

class BankInfoRepositoryImpl(
    private val database: FirebaseDatabase,
    private val dispatchers: IAppDispatchers
) : IBankInfoRepository {

    override suspend fun getApplicationInformation():
            CustomResultModelDomain<ApplicationInfoModelDomain, CommonInfoExceptionModelDomain> =
        withContext(dispatchers.IO) {
            TODO("Not yet implemented")
        }

    override suspend fun getBankNews():
            CustomResultModelDomain<List<BankNewsModelDomain>, CommonInfoExceptionModelDomain> =
        withContext(dispatchers.IO) {
            TODO("Not yet implemented")
        }
}