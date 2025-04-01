package com.alenniboris.fastbanking.domain.usecase.implementation.help

import com.alenniboris.fastbanking.domain.model.CustomResultModelDomain
import com.alenniboris.fastbanking.domain.model.exception.HelpExceptionModelDomain
import com.alenniboris.fastbanking.domain.repository.IHelpRepository
import com.alenniboris.fastbanking.domain.usecase.logic.help.ICallPhoneNumberUseCase

class CallPhoneNumberUseCaseImpl(
    private val helpRepository: IHelpRepository
) : ICallPhoneNumberUseCase {

    override fun invoke(
        phoneNumber: String
    ): CustomResultModelDomain<Unit, HelpExceptionModelDomain> {
        return helpRepository.makePhoneCall(
            number = phoneNumber
        )
    }
}