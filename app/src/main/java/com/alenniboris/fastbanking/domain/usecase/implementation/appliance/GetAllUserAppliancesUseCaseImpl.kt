package com.alenniboris.fastbanking.domain.usecase.implementation.appliance

import com.alenniboris.fastbanking.domain.model.CustomResultModelDomain
import com.alenniboris.fastbanking.domain.model.appliances.IProductAppliance
import com.alenniboris.fastbanking.domain.model.exception.CommonExceptionModelDomain
import com.alenniboris.fastbanking.domain.repository.IBankProductsRepository
import com.alenniboris.fastbanking.domain.usecase.logic.appliance.IGetAllUserAppliancesUseCase
import com.alenniboris.fastbanking.domain.usecase.logic.authorization.IGetCurrentUserUseCase
import com.alenniboris.fastbanking.domain.utils.IAppDispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

class GetAllUserAppliancesUseCaseImpl(
    private val getCurrentUserUseCase: IGetCurrentUserUseCase,
    private val bankRepository: IBankProductsRepository,
    private val dispatchers: IAppDispatchers
) : IGetAllUserAppliancesUseCase {

    override suspend fun invoke()
            : CustomResultModelDomain<List<IProductAppliance>, CommonExceptionModelDomain> =
        withContext(dispatchers.IO) {
            val user = getCurrentUserUseCase.userFlow.value
                ?: return@withContext CustomResultModelDomain.Error(
                    CommonExceptionModelDomain.Other
                )

            val _cards = async {
                bankRepository.getAllUserAppliancesForCards(
                    user = user
                )
            }

            val _credits = async {
                bankRepository.getAllUserAppliancesForCredits(
                    user = user
                )
            }

            val _deposits = async {
                bankRepository.getAllUserAppliancesForDeposits(
                    user = user
                )
            }

            val cardsRes = _cards.await()
            val creditsRes = _credits.await()
            val depositsRes = _deposits.await()

            if (
                cardsRes is CustomResultModelDomain.Success &&
                creditsRes is CustomResultModelDomain.Success &&
                depositsRes is CustomResultModelDomain.Success
            ) {

                val cards = cardsRes.result
                val credits = creditsRes.result
                val deposits = depositsRes.result

                return@withContext CustomResultModelDomain.Success(
                    cards + credits + deposits
                )
            }

            return@withContext CustomResultModelDomain.Error(
                CommonExceptionModelDomain.ErrorGettingData
            )
        }
}