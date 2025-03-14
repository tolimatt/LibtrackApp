package com.example.libtrack.backend

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.content.ContextCompat
import com.example.libtrack.R
import androidx.core.app.NotificationCompat

// Function to show notification
fun Context.showNotification() {
    val notificationManager = ContextCompat.getSystemService(this, NotificationManager::class.java)

// Create a Notification Channel for Android 8.0 and above
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val channelId = "default_channel"
        val channelName = "Default Notifications"
        val channel = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT)
        notificationManager?.createNotificationChannel(channel)
    }

    val notification: Notification = NotificationCompat.Builder(this, "default_channel")
        .setSmallIcon(R.drawable.logo) // Example icon
        .setContentTitle("Book Borrowed Successfully")
        .setContentText("Your book has been successfully borrowed. Please return it on time.")
        .setPriority(NotificationCompat.PRIORITY_HIGH) // Heads-up notification priority
        .setCategory(NotificationCompat.CATEGORY_MESSAGE) // Category for message notifications
        .setAutoCancel(true) // Dismiss notification when clicked
        .build()

    notificationManager?.notify(1, notification) // 1 is the notification ID
}

