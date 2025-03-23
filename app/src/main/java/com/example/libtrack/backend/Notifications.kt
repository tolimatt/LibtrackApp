package com.example.libtrack.backend

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.example.libtrack.errorHandling.logoImage


class Notification: Application(){

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate() {
        super.onCreate()

        // Create the NotificationChannel
        val channel = NotificationChannel(
            "download_channel",  // Channel ID should match the one used in NotificationCompat
            "File download",
            NotificationManager.IMPORTANCE_HIGH
        )

        channel.description = "Library"

        // Create and register the NotificationChannel
        val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        manager.createNotificationChannel(channel)
    }
}

class NotificationLibrary(
    private val context: Context
){
    private val notificationManager = context.getSystemService(NotificationManager::class.java)

    fun showNotificationBorrowed(title: String, dueDate: String) {
        // Create the notification using the correct channel ID
        val notification = NotificationCompat.Builder(context, "download_channel")
            .setContentTitle("Book Borrowed Successfully")
            .setContentText("Borrowed $title")
            .setSmallIcon(logoImage)  // Ensure you have this drawable in your resources
            .setPriority(NotificationManager.IMPORTANCE_HIGH)
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText("You have successfully borrowed the book $title. Please Remember to return it before $dueDate.")
            )
            .setAutoCancel(true)
            .build()

        // Notify using the NotificationManager
        notificationManager?.notify(1, notification)
    }
}