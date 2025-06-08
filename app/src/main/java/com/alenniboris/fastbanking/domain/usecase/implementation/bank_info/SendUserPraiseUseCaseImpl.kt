package com.alenniboris.fastbanking.domain.usecase.implementation.bank_info

import com.alenniboris.fastbanking.domain.model.CustomResultModelDomain
import com.alenniboris.fastbanking.domain.model.exception.CommonExceptionModelDomain
import com.alenniboris.fastbanking.domain.usecase.logic.bank_info.ISendUserPraiseUseCase
import com.alenniboris.fastbanking.domain.utils.IAppDispatchers
import com.alenniboris.fastbanking.domain.utils.LogPrinter
import kotlinx.coroutines.withContext

class SendUserPraiseUseCaseImpl(
    private val dispatchers: IAppDispatchers
) : ISendUserPraiseUseCase {

    override suspend fun invoke(
        praise: String
    ): CustomResultModelDomain<Unit, CommonExceptionModelDomain> = withContext(dispatchers.IO) {
        LogPrinter.printLog("!!!!", praise)
        return@withContext CustomResultModelDomain.Success(Unit)
    }
}