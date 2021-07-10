package com.app1.navigation

import android.app.NotificationChannel
import android.app.NotificationManager
import android.graphics.Bitmap
import android.os.Build
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage


class MyFirebaseMessagingService: FirebaseMessagingService() {

    lateinit var notificationmanager99:NotificationManager

    lateinit var image:Bitmap

    override fun onMessageReceived(p0: RemoteMessage) {
        super.onMessageReceived(p0)
        p0.notification?.body?.let { p0.notification?.title?.let { it1 -> firebasemsg(it1, it) } }
    }

    fun firebasemsg(title:String, msg:String)
    {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            val channel99 = NotificationChannel("firebasemessage","Cloud_Notification",NotificationManager.IMPORTANCE_HIGH)
            channel99.description = "This is firebasenotification channel"
            notificationmanager99 = getSystemService(android.content.Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationmanager99.createNotificationChannel(channel99)
        }

        val builder = NotificationCompat.Builder(this,"firebasemessage")
        builder.setContentText(msg)
        builder.setContentTitle(title)
        builder.setSmallIcon(R.drawable.setting)
        builder.setAutoCancel(true)

        notificationmanager99.notify(10012,builder.build())
    }
}