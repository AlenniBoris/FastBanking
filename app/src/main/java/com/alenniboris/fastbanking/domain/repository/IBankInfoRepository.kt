package com.alenniboris.fastbanking.domain.repository

import com.alenniboris.fastbanking.domain.model.CustomResultModelDomain
import com.alenniboris.fastbanking.domain.model.bank_info.ApplicationInfoModelDomain
import com.alenniboris.fastbanking.domain.model.bank_info.BankNewsModelDomain
import com.alenniboris.fastbanking.domain.model.exception.CommonInfoExceptionModelDomain

interface IBankInfoRepository {

    suspend fun getApplicationInformation(): CustomResultModelDomain<ApplicationInfoModelDomain, CommonInfoExceptionModelDomain>

    suspend fun getBankNews(): CustomResultModelDomain<List<BankNewsModelDomain>, CommonInfoExceptionModelDomain>
}