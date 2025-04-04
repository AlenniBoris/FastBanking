package com.alenniboris.fastbanking.domain.usecase.logic.bank_info

import com.alenniboris.fastbanking.domain.model.CustomResultModelDomain
import com.alenniboris.fastbanking.domain.model.bank_info.BankNewsModelDomain
import com.alenniboris.fastbanking.domain.model.exception.CommonInfoExceptionModelDomain

interface IGetBankNewsByIdUseCase {

    suspend fun invoke(
        id: String
    ): CustomResultModelDomain<BankNewsModelDomain?, CommonInfoExceptionModelDomain>
}