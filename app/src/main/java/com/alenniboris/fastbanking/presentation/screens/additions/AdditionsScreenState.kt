package com.alenniboris.fastbanking.presentation.screens.additions

data class AdditionsScreenState(
    val authorizedCategories: List<AdditionsScreenCategory> = AuthorizedAdditionsCategories,
    val notAuthorizedCategories: List<AdditionsScreenCategory> = NotAuthorizedAdditionsCategories
)
