package com.alenniboris.fastbanking.domain.model.card

data class SimpleCardModelDomain(
    val id: String,
    val number: String,
    val system: CardSystem
)