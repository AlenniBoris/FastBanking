package com.alenniboris.fastbanking.domain.usecase.implementation.help

import com.alenniboris.fastbanking.domain.model.CustomResultModelDomain
import com.alenniboris.fastbanking.domain.model.exception.HelpExceptionModelDomain
import com.alenniboris.fastbanking.domain.repository.IHelpRepository
import com.alenniboris.fastbanking.domain.usecase.logic.help.IOpenMessengerUseCase

class OpenMessengerUseCaseImpl(
    private val helpRepository: IHelpRepository
): IOpenMessengerUseCase {

    override fun invoke(
        uri: String
    ): CustomResultModelDomain<Unit, HelpExceptionModelDomain> {
        return helpRepository.openMessenger(
            uri = uri
        )
    }
}