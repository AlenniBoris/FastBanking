package com.alenniboris.fastbanking.data.model

import com.alenniboris.fastbanking.domain.model.OwnerModelDomain

data class OwnerModelData(
    val id: String?,
    val name: String?,
    val surname: String?
)

fun OwnerModelData.toModelDomain() =
    OwnerModelDomain(
        id = this.id!!,
        name = this.name!!,
        surname = this.surname!!
    )