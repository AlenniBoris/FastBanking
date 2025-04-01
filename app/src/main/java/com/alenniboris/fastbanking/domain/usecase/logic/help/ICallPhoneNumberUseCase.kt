package com.alenniboris.fastbanking.domain.usecase.logic.help

import com.alenniboris.fastbanking.domain.model.CustomResultModelDomain
import com.alenniboris.fastbanking.domain.model.exception.HelpExceptionModelDomain

interface ICallPhoneNumberUseCase {

    fun invoke(
        phoneNumber: String
    ): CustomResultModelDomain<Unit, HelpExceptionModelDomain>
}