package com.alenniboris.fastbanking.presentation.model

import com.alenniboris.fastbanking.domain.model.credit.CreditModelDomain

fun CreditModelDomain.toModelUi(): CreditModelUi =
    CreditModelUi(
        domainModel = this
    )