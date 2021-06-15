package com.example.navigation

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class thursdayalarmmanager: BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {

        val i = Intent(context, thursday::class.java)
        intent!!.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

        val pendingintent = PendingIntent.getActivity(context, 0, i, PendingIntent.FLAG_UPDATE_CURRENT)

        val builder = NotificationCompat.Builder(context!!, "23")
            .setAutoCancel(true)
            .setSmallIcon(R.drawable.setting)
            .setContentTitle(intent.getStringExtra("content title"))
            .setContentText(intent.getStringExtra("content text"))
            .setContentIntent(pendingintent)
            .setDefaults(NotificationCompat.DEFAULT_ALL)
            .setPriority(NotificationCompat.PRIORITY_HIGH)

        val notificationManager = NotificationManagerCompat.from(context)

        val x = intent.getIntExtra("uniqueno", 0)

        notificationManager.notify(x, builder.build())

    }
}