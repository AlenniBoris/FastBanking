package com.alenniboris.fastbanking.presentation.screens.register

import android.util.Patterns

object RegistrationFunctions {

    fun checkIdentificationNumberFormat(number: String): Boolean {
        val regex = "^(?=.*\\d)[A-Za-z0-9]+$".toRegex()
        return regex.matches(number)
    }

    fun checkPhoneNumberFormat(number: String): Boolean {
        return Patterns.PHONE.matcher(number).matches()
    }

    fun checkIfSomePasswordIsEmpty(first: String, second: String): Boolean {
        return first.isEmpty() || second.isEmpty()
    }

    fun checkIfPasswordsAreSame(first: String, second: String): Boolean {
        return first == second
    }

    fun checkIfPasswordIsWeak(password: String): Boolean {
        return password.length < 6
    }
}