package com.alenniboris.fastbanking.data.model

import android.util.Log
import com.alenniboris.fastbanking.domain.model.UserModelDomain
import com.alenniboris.fastbanking.domain.model.toUserGender

data class UserModelData(
    val id: String? = null,
    val name: String? = null,
    val surname: String? = null,
    val email: String? = null,
    val password: String? = null,
    val age: String? = null,
    val gender: String? = null,
    val country: String? = null,
    val accountId: String? = null,
    val hasOnlineBanking: String? = null
) {
    val hasSomeValueMissing: Boolean
        get() = id == null
                || password == null
                || name == null
                || surname == null
                || email == null
                || age == null
                || gender == null
                || country == null
                || accountId == null
                || hasOnlineBanking == null

    fun toUpdatesMap(): Map<String, String?> {
        return mapOf(
            "id" to id,
            "password" to password,
            "name" to name,
            "surname" to surname,
            "email" to email,
            "age" to age,
            "gender" to gender,
            "country" to country,
            "accountId" to accountId,
            "hasOnlineBanking" to hasOnlineBanking
        )
    }
}

fun UserModelData.toModelDomain(): UserModelDomain? = runCatching {
    UserModelDomain(
        id = this.id!!,
        name = this.name!!,
        surname = this.surname!!,
        email = this.email!!,
        password = this.password!!,
        age = this.age?.toInt()!!,
        gender = this.gender?.toUserGender()!!,
        country = this.country!!,
        accountId = this.accountId!!,
        hasOnlineBanking = this.hasOnlineBanking?.toBoolean()!!
    )
}.getOrElse {
    Log.e("!!!", "UserModelData.toModelDomain")
    null
}

fun UserModelDomain.toModelData(): UserModelData =
    UserModelData(
        id = this.id,
        name = this.name,
        surname = this.surname,
        email = this.email,
        password = this.password,
        age = this.age.toString(),
        gender = this.gender.name,
        country = this.country,
        accountId = this.accountId,
        hasOnlineBanking = this.hasOnlineBanking.toString()
    )

