package com.example.navigation

import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.google.firebase.messaging.ktx.remoteMessage

class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(p0: RemoteMessage) {
        super.onMessageReceived(p0)
        getfirebasemessage(p0.notification?.title!!, p0.notification!!.body!!)
    }

    fun getfirebasemessage(title:String, msg:String) {

        val builder = NotificationCompat.Builder(this,"firebasechannel")
        builder.setContentTitle(title)
        builder.setContentText(msg)
        builder.setSmallIcon(R.drawable.setting)
        builder.setAutoCancel(true)

        val manager = NotificationManagerCompat.from(this)
        manager.notify(101,builder.build())

    }
}