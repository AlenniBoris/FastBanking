package com.alenniboris.fastbanking.domain.usecase.logic.help

import com.alenniboris.fastbanking.domain.model.CustomResultModelDomain
import com.alenniboris.fastbanking.domain.model.exception.HelpExceptionModelDomain

interface IOpenMessengerUseCase {

    fun invoke(
        uri: String
    ): CustomResultModelDomain<Unit, HelpExceptionModelDomain>
}