package com.alenniboris.fastbanking.domain.usecase.logic.accounts

import com.alenniboris.fastbanking.domain.model.CustomResultModelDomain
import com.alenniboris.fastbanking.domain.model.account.AccountModelDomain
import com.alenniboris.fastbanking.domain.model.exception.CommonExceptionModelDomain

interface IGetAllUserAccountsUseCase {

    suspend fun invoke(): CustomResultModelDomain<List<AccountModelDomain>, CommonExceptionModelDomain>
}