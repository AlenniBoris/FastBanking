package com.alenniboris.fastbanking.presentation.uikit.functions

import android.util.Patterns
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object CommonFunctions {

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

    fun formatAmount(amount: Double): String = String.format(
        "%.2f",
        amount
    )

    fun formatDateToMonthYearText(date: Date): String =
        SimpleDateFormat("MM/yyyy", Locale.getDefault()).format(date)

    fun formatDateToDateText(date: Date): String =
        SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(date)

    fun formatDateToDateAndTimeText(date: Date): String =
        SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault()).format(date)
}