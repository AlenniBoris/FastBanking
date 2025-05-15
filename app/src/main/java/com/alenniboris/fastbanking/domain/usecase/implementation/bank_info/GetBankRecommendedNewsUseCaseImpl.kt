package com.alenniboris.fastbanking.domain.usecase.implementation.bank_info

import com.alenniboris.fastbanking.domain.model.CustomResultModelDomain
import com.alenniboris.fastbanking.domain.model.bank_info.BankNewsModelDomain
import com.alenniboris.fastbanking.domain.model.exception.CommonInfoExceptionModelDomain
import com.alenniboris.fastbanking.domain.repository.IBankInfoRepository
import com.alenniboris.fastbanking.domain.usecase.logic.bank_info.IGetBankRecommendedNewsUseCase
import com.alenniboris.fastbanking.domain.utils.IAppDispatchers
import kotlinx.coroutines.withContext

class GetBankRecommendedNewsUseCaseImpl(
    private val bankInfoRepository: IBankInfoRepository,
    private val dispatchers: IAppDispatchers
) : IGetBankRecommendedNewsUseCase {

    override suspend fun invoke():
            CustomResultModelDomain<List<BankNewsModelDomain>, CommonInfoExceptionModelDomain> =
        withContext(dispatchers.IO) {
            return@withContext bankInfoRepository.getBankRecommendedNews()
        }
}