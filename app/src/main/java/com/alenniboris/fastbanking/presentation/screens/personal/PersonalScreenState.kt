package com.alenniboris.fastbanking.presentation.screens.personal

import com.alenniboris.fastbanking.presentation.model.user.UserModelUi

data class PersonalScreenState(
    val user: UserModelUi? = null,
    val currentViewedCategory: PersonalScreenCategories = PersonalScreenCategories.PROFILE,
    val allPossibleCategories: List<PersonalScreenCategories> = PersonalScreenCategories.entries.toList()
)
