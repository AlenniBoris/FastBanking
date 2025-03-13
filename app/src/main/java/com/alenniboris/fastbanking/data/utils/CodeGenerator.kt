package com.alenniboris.fastbanking.data.utils

import kotlin.random.Random

object CodeGenerator {

    fun generate(): String {
        return (Random.nextInt(999999) + 100000).toString()
    }
}