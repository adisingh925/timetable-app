package com.example.navigation

import android.app.NotificationChannel
import android.app.NotificationManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import java.io.IOException
import java.net.URL


class MyFirebaseMessagingService: FirebaseMessagingService() {

    lateinit var notificationmanager99:NotificationManager

    lateinit var image:Bitmap

    override fun onMessageReceived(p0: RemoteMessage) {
        super.onMessageReceived(p0)
        p0.notification?.body?.let { p0.notification?.title?.let { it1 -> firebasemsg(it1, it) } }
    }

    fun firebasemsg(title:String, msg:String)
    {

        try {
            val url = URL("https://cdn.pixabay.com/photo/2015/04/23/22/00/tree-736885__480.jpg")
            image = BitmapFactory.decodeStream(url.openConnection().getInputStream())
        } catch (e: IOException) {
            System.out.println(e)
        }

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            val channel99 = NotificationChannel("firebasemessage","firebasenotification",NotificationManager.IMPORTANCE_HIGH)
            channel99.description = "This is firebasenotification channel"
            notificationmanager99 = getSystemService(android.content.Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationmanager99.createNotificationChannel(channel99)
        }

        val builder = NotificationCompat.Builder(this,"firebasemessage")
        builder.setContentText(msg)
        builder.setContentTitle(title)
        builder.setSmallIcon(R.drawable.setting)
        builder.setLargeIcon(image)
        builder.setStyle(NotificationCompat.BigPictureStyle()
        .bigPicture(image))
        builder.setAutoCancel(true)


        notificationmanager99.notify(10012,builder.build())
    }
}