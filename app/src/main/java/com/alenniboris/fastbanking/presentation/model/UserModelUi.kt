package com.alenniboris.fastbanking.presentation.model

import com.alenniboris.fastbanking.R
import com.alenniboris.fastbanking.domain.model.user.UserGender
import com.alenniboris.fastbanking.domain.model.user.UserModelDomain

data class UserModelUi(
    val domainModel: UserModelDomain
){

    val userPictureId: Int = when(domainModel.gender){
        UserGender.Male -> R.drawable.male_user_placeholder
        UserGender.Female -> R.drawable.female_user_placeholder
        UserGender.Undefined -> R.drawable.unknown_gender_user_placeholder
    }

    val userTitleText = domainModel.name + " " + domainModel.surname
}

fun UserModelDomain.toModelUi(): UserModelUi =
    UserModelUi(
        domainModel = this
    )