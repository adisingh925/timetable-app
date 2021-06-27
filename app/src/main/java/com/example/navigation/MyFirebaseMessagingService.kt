package com.example.navigation

import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService: FirebaseMessagingService() {

    override fun onMessageReceived(p0: RemoteMessage) {
        super.onMessageReceived(p0)
        p0.notification?.title?.let { p0.notification!!.body?.let { it1 -> firebasemsg(it, it1) } }
    }

    fun firebasemsg(title:String, msg:String)
    {
        val builder = NotificationCompat.Builder(this,"firebasemessage")
        builder.setContentText(msg)
        builder.setContentTitle(title)
        builder.setSmallIcon(R.drawable.setting)
        builder.setAutoCancel(true)

        val manager = NotificationManagerCompat.from(this)
        manager.notify(101,builder.build())
    }
}