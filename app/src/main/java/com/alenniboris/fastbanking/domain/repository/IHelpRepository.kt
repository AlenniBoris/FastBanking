package com.alenniboris.fastbanking.domain.repository

import com.alenniboris.fastbanking.domain.model.CustomResultModelDomain
import com.alenniboris.fastbanking.domain.model.exception.HelpExceptionModelDomain

interface IHelpRepository {

    fun makePhoneCall(
        number: String
    ): CustomResultModelDomain<Unit, HelpExceptionModelDomain>

    fun openMessenger(
        uri: String
    ): CustomResultModelDomain<Unit, HelpExceptionModelDomain>
}