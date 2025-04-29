package com.alenniboris.fastbanking.data.model.user

import android.icu.util.Calendar
import android.util.Log
import com.alenniboris.fastbanking.domain.model.user.UserModelDomain
import com.alenniboris.fastbanking.domain.model.user.toUserGender
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class UserModelData(
    val id: String? = null,
    val name: String? = null,
    val surname: String? = null,
    val email: String? = null,
    val password: String? = null,
    val gender: String? = null,
    val country: String? = null,
    val accountId: String? = null,
    val hasOnlineBanking: String? = null,
    val phoneNumber: String? = null,
    val job: String? = null,
    val dateOfBirth: String? = null
) {
    val hasSomeValueMissing: Boolean
        get() = id == null
                || password == null
                || name == null
                || surname == null
                || email == null
                || gender == null
                || country == null
                || accountId == null
                || hasOnlineBanking == null
                || phoneNumber == null

    fun toUpdatesMap(): Map<String, String?> {
        return mapOf(
            "id" to id,
            "password" to password,
            "name" to name,
            "surname" to surname,
            "email" to email,
            "dateOfBirth" to dateOfBirth,
            "gender" to gender,
            "country" to country,
            "accountId" to accountId,
            "hasOnlineBanking" to hasOnlineBanking,
            "phoneNumber" to phoneNumber
        )
    }
}

fun UserModelData.toModelDomain(): UserModelDomain? = runCatching {

    val dateOfBirth = Date(this.dateOfBirth?.toLong()!!)
    val userBirth = SimpleDateFormat(
        "yyyy",
        Locale.getDefault()
    ).format(dateOfBirth).toInt()
    val age = Calendar.getInstance()[Calendar.YEAR] - userBirth

    UserModelDomain(
        id = this.id!!,
        name = this.name!!,
        surname = this.surname!!,
        email = this.email!!,
        password = this.password!!,
        age = age,
        dateOfBirth = dateOfBirth,
        gender = this.gender?.toUserGender()!!,
        country = this.country!!,
        accountId = this.accountId!!,
        hasOnlineBanking = this.hasOnlineBanking?.toBoolean()!!,
        phoneNumber = this.phoneNumber!!,
        job = this.job!!
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
        dateOfBirth = this.dateOfBirth.time.toString(),
        gender = this.gender.name,
        country = this.country,
        accountId = this.accountId,
        hasOnlineBanking = this.hasOnlineBanking.toString(),
        phoneNumber = this.phoneNumber
    )

