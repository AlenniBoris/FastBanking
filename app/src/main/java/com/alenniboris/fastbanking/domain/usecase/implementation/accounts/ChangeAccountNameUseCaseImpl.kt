package com.alenniboris.fastbanking.domain.usecase.implementation.accounts

import com.alenniboris.fastbanking.domain.model.CustomResultModelDomain
import com.alenniboris.fastbanking.domain.model.account.AccountModelDomain
import com.alenniboris.fastbanking.domain.model.exception.CommonExceptionModelDomain
import com.alenniboris.fastbanking.domain.repository.IBankProductsRepository
import com.alenniboris.fastbanking.domain.usecase.logic.accounts.IChangeAccountNameUseCase
import com.alenniboris.fastbanking.domain.utils.IAppDispatchers
import kotlinx.coroutines.withContext

class ChangeAccountNameUseCaseImpl(
    private val bankProductsRepository: IBankProductsRepository,
    private val dispatchers: IAppDispatchers
) : IChangeAccountNameUseCase {

    override suspend fun invoke(
        account: AccountModelDomain,
        newName: String
    ): CustomResultModelDomain<Unit, CommonExceptionModelDomain> = withContext(dispatchers.IO) {
        return@withContext bankProductsRepository.updateAccountValue(account = account.copy(name = newName))
    }
}