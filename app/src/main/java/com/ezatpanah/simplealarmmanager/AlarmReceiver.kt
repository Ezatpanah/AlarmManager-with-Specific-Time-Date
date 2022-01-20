package com.ezatpanah.simplealarmmanager

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.app.NotificationChannel
import android.os.Build
import android.R
import android.app.Notification
import android.app.NotificationManager
import android.app.NotificationManager.IMPORTANCE_DEFAULT
import android.app.PendingIntent
import android.widget.Toast
import androidx.core.app.TaskStackBuilder


class AlarmReceiver : BroadcastReceiver() {

    private val CHANNEL_ID = "com.ezatpanah.simplealarmmanager"

    override fun onReceive(context: Context?, p1: Intent?) {



        val notificationIntent = Intent(context, NotificationActivity::class.java)

        val stackBuilder: TaskStackBuilder? = context?.let { TaskStackBuilder.create(it) }
        stackBuilder?.addParentStack(NotificationActivity::class.java)
        stackBuilder?.addNextIntent(notificationIntent)

        val pendingIntent: PendingIntent? = stackBuilder?.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)

        val builder: Notification.Builder = Notification.Builder(context)

        val notification: Notification = builder
            .setContentTitle("Your Title Notification Here")
            .setContentText("Your Text Notification Here\nClick on Notification to going to specific Activity ")
            .setTicker("New Message Alert!")
            .setSmallIcon(R.drawable.ic_lock_idle_alarm)
            .setContentIntent(pendingIntent).build()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder.setChannelId(CHANNEL_ID)
        }

        val notificationManager: NotificationManager = (context!!.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "NotificationDemo",
                IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel);
        }

        notificationManager.notify(0, notification)
    }
}