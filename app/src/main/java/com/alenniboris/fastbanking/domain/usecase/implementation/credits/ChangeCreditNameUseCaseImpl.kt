package com.alenniboris.fastbanking.domain.usecase.implementation.credits

import com.alenniboris.fastbanking.domain.model.CustomResultModelDomain
import com.alenniboris.fastbanking.domain.model.credit.CreditModelDomain
import com.alenniboris.fastbanking.domain.model.exception.CommonExceptionModelDomain
import com.alenniboris.fastbanking.domain.repository.IBankProductsRepository
import com.alenniboris.fastbanking.domain.usecase.logic.credits.IChangeCreditNameUseCase
import com.alenniboris.fastbanking.domain.utils.IAppDispatchers
import kotlinx.coroutines.withContext

class ChangeCreditNameUseCaseImpl(
    private val bankProductsRepository: IBankProductsRepository,
    private val dispatchers: IAppDispatchers
) : IChangeCreditNameUseCase {

    override suspend fun invoke(
        credit: CreditModelDomain,
        newName: String
    ): CustomResultModelDomain<Unit, CommonExceptionModelDomain> = withContext(dispatchers.IO) {
        return@withContext bankProductsRepository.updateCreditValue(credit = credit.copy(name = newName))
    }
}