package com.alenniboris.fastbanking.presentation.model.user

import com.alenniboris.fastbanking.R
import com.alenniboris.fastbanking.domain.model.user.UserGender
import com.alenniboris.fastbanking.domain.model.user.UserModelDomain
import com.alenniboris.fastbanking.presentation.uikit.values.UserValues

data class UserModelUi(
    val domainModel: UserModelDomain
) {

    val userPictureId: Int = when (domainModel.gender) {
        UserGender.Male -> R.drawable.male_user_placeholder
        UserGender.Female -> R.drawable.female_user_placeholder
        UserGender.Undefined -> R.drawable.unknown_gender_user_placeholder
    }

    val userTitleText = domainModel.name + " " + domainModel.surname

    val isCreditsAllowed: Boolean = domainModel.age >= UserValues.CreditMinimumAge
}

fun UserModelDomain.toModelUi(): UserModelUi =
    UserModelUi(
        domainModel = this
    )