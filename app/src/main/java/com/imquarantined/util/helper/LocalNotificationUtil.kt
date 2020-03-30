package com.imquarantined.util.helper

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.media.RingtoneManager
import androidx.core.app.NotificationCompat
import com.imquarantined.R

object LocalNotificationUtil {

    fun showNotification(
        context: Context,
        title: String?,
        body: String?,
        intent: Intent,
        channelId: String,
        channelName: String,
        vararg extrasMsgs: String
    ) {
        val notificationManager =
            context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        val notificationId = 1

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val mChannel = NotificationChannel(
                channelId, channelName, NotificationManager.IMPORTANCE_HIGH
            )
            notificationManager.createNotificationChannel(mChannel)
        }
        val message = StringBuilder(body.toString())
        for (item in extrasMsgs){
            message.append('\n').append(item)
        }

        val mBuilder = NotificationCompat.Builder(context, channelId)
            .setContentTitle(title)
            .setStyle(NotificationCompat.BigTextStyle().bigText(message.toString()))
            .setContentText(message.toString())
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setSmallIcon(R.mipmap.ic_launcher)

        val stackBuilder = TaskStackBuilder.create(context)
        stackBuilder.addNextIntent(intent)

        val resultPendingIntent = stackBuilder.getPendingIntent(
            0,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        mBuilder.setContentIntent(resultPendingIntent)
        notificationManager.notify(notificationId, mBuilder.build())

        try {
            val notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
            val r = RingtoneManager.getRingtone(context, notification)
            r.play()
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }
}