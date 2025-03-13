package com.alenniboris.fastbanking.data.receiver

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.alenniboris.fastbanking.R

class MessageReceiver : BroadcastReceiver() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onReceive(context: Context, intent: Intent) {
        val verificationCode = intent.getStringExtra(VERIFICATION_CODE)

        val notification = createNotification(context, verificationCode)
        val nManager = context.getSystemService(NotificationManager::class.java)

        val channel = NotificationChannel(
            NOTIFICATION_CHANNEL,
            NOTIFICATION_CHANNEL_NAME,
            NotificationManager.IMPORTANCE_DEFAULT
        )
        nManager.createNotificationChannel(channel)


        nManager.notify(NOTIFICATION_ID, notification)
    }

    private fun createNotification(context: Context, verificationCode: String?): Notification {
        return NotificationCompat.Builder(context, NOTIFICATION_CHANNEL)
            .setContentTitle("BBank")
            .setContentText("Your verification code is $verificationCode")
            .setSmallIcon(R.drawable.notification_icon)
            .build()
    }

    companion object {
        val SEND_MESSAGE_WITH_VERIFICATION_CODE_ACTION =
            "com.alenniboris.action.MESSAGE_WITH_VERIFICATION_CODE"
        val VERIFICATION_CODE = "verification_code"
        val NOTIFICATION_CHANNEL = "notification_channel"
        val NOTIFICATION_CHANNEL_NAME = "BBank notification"
        val NOTIFICATION_ID = 1
    }
}