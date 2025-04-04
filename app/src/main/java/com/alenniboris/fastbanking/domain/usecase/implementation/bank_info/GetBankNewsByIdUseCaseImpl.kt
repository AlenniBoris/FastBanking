package com.alenniboris.fastbanking.domain.usecase.implementation.bank_info

import com.alenniboris.fastbanking.domain.model.CustomResultModelDomain
import com.alenniboris.fastbanking.domain.model.bank_info.BankNewsModelDomain
import com.alenniboris.fastbanking.domain.model.exception.CommonInfoExceptionModelDomain
import com.alenniboris.fastbanking.domain.repository.IBankInfoRepository
import com.alenniboris.fastbanking.domain.usecase.logic.bank_info.IGetBankNewsByIdUseCase
import com.alenniboris.fastbanking.domain.utils.IAppDispatchers
import kotlinx.coroutines.withContext

class GetBankNewsByIdUseCaseImpl(
    private val infoRepository: IBankInfoRepository,
    private val dispatchers: IAppDispatchers
) : IGetBankNewsByIdUseCase {

    override suspend fun invoke(
        id: String
    ): CustomResultModelDomain<BankNewsModelDomain, CommonInfoExceptionModelDomain> =
        withContext(dispatchers.IO) {
            return@withContext infoRepository.getBankNewsById(id = id)
        }
}