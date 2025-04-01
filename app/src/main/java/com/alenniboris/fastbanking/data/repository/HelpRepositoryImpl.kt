package com.alenniboris.fastbanking.data.repository

import android.app.Application
import android.content.Intent
import android.net.Uri
import android.util.Log
import com.alenniboris.fastbanking.data.mappers.toHelpException
import com.alenniboris.fastbanking.domain.model.CustomResultModelDomain
import com.alenniboris.fastbanking.domain.model.exception.HelpExceptionModelDomain
import com.alenniboris.fastbanking.domain.repository.IHelpRepository

class HelpRepositoryImpl(
    private val apl: Application
) : IHelpRepository {

    override fun makePhoneCall(
        number: String
    ): CustomResultModelDomain<Unit, HelpExceptionModelDomain> =
        runCatching {

            val intent = Intent(Intent.ACTION_CALL).apply {
                data = Uri.parse("tel:$number")
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            apl.startActivity(intent)

            CustomResultModelDomain.Success<Unit, HelpExceptionModelDomain>(Unit)
        }.getOrElse { exception ->
            Log.e("!!!", "makePhoneCall ${exception.stackTraceToString()}")
            CustomResultModelDomain.Error(
                exception.toHelpException()
            )
        }


    override fun openMessenger(
        uri: String
    ): CustomResultModelDomain<Unit, HelpExceptionModelDomain> =
        runCatching {

            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
            apl.startActivity(intent)

            CustomResultModelDomain.Success<Unit, HelpExceptionModelDomain>(Unit)
        }.getOrElse { exception ->
            Log.e("!!!", "makePhoneCall ${exception.stackTraceToString()}")
            CustomResultModelDomain.Error(
                exception.toHelpException()
            )
        }
}