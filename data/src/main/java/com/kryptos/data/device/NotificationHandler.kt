package com.kryptos.data.device

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.kryptos.data.R
import com.kryptos.data.util.Constants

object NotificationHandler {

    fun makePushNotification(title: String, message: String?, context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name: CharSequence = Constants.NOTIFICATION_CHANNEL_NAME
            val description: String = Constants.NOTIFICATION_CHANNEL_DESCRIPTION
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(Constants.NOTIFICATION_CHANNEL_ID, name, importance)
            channel.description = description
            channel.enableVibration(true)
            channel.vibrationPattern = longArrayOf(100, 200, 300, 400, 500)
            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }

        val builder: NotificationCompat.Builder = NotificationCompat.Builder(context, Constants.NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle(title)
            .setContentText(message)
            .setVibrate(longArrayOf(100, 200, 300, 400, 500))
            .setPriority(NotificationCompat.PRIORITY_HIGH) // Set the intent that will fire when the user taps the notification
            .setAutoCancel(true)
        NotificationManagerCompat.from(context).notify(Constants.NOTIFICATION_ID, builder.build())
    }

}