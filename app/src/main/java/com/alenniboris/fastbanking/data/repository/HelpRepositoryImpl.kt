package com.alenniboris.fastbanking.data.repository

import com.alenniboris.fastbanking.domain.model.CustomResultModelDomain
import com.alenniboris.fastbanking.domain.model.exception.HelpExceptionModelDomain
import com.alenniboris.fastbanking.domain.repository.IHelpRepository

class HelpRepositoryImpl() : IHelpRepository {

    override fun makePhoneCall(
        number: String
    ): CustomResultModelDomain<Unit, HelpExceptionModelDomain> {
        TODO("Not yet implemented")
    }

    override fun openMessenger(
        uri: String
    ): CustomResultModelDomain<Unit, HelpExceptionModelDomain> {
        TODO("Not yet implemented")
    }
}