package com.alenniboris.fastbanking.domain.utils

import android.util.Log
import com.alenniboris.fastbanking.BuildConfig

object LogPrinter {
    fun printLog(tag: String, message: String) {
        if (BuildConfig.DEBUG) {
            runCatching {
                Log.e(tag, message)
            }.getOrElse {
                println(
                    """
                        $tag
                        --------------
                        $message
                    """.trimIndent()
                )
            }
        }
    }
}