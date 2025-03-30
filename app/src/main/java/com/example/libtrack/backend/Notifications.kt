package com.example.libtrack.backend

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.work.*
import com.example.libtrack.errorHandling.logoImage
import java.util.concurrent.TimeUnit

// ✅ Create Notification Channels (Call this in Application class or before scheduling notifications)
fun createNotificationChannels(context: Context) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val channel = NotificationChannel(
            "download_channel",
            "Library Notifications",
            NotificationManager.IMPORTANCE_HIGH
        ).apply {
            description = "Notifications for borrowed and returned books"
        }

        val notificationManager = context.getSystemService(NotificationManager::class.java)
        notificationManager.createNotificationChannel(channel)
    }
}

// ✅ Worker for Borrowed Book Notification
class NotificationWorkerBorrowed(
    context: Context,
    workerParams: WorkerParameters
) : Worker(context, workerParams) {

    override fun doWork(): Result {
        val context = applicationContext
        val title = inputData.getString("title") ?: "Book Borrowed"
        val dueDate = inputData.getString("dueDate") ?: "Unknown Date"

        val notificationManager = context.getSystemService(NotificationManager::class.java)

        val notification = NotificationCompat.Builder(context, "download_channel")
            .setContentTitle("Book Borrowed Successfully")
            .setContentText("Borrowed: $title")
            .setSmallIcon(logoImage) // ✅ Replace with a valid drawable
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText("You have successfully borrowed the book \"$title\". Please return it before $dueDate.")
            )
            .setAutoCancel(true)
            .build()

        notificationManager.notify(1, notification)

        return Result.success()
    }
}

// ✅ Worker for Return Reminder Notification
class NotificationWorkerReturned(
    context: Context,
    workerParams: WorkerParameters
) : Worker(context, workerParams) {

    override fun doWork(): Result {
        val context = applicationContext
        val title = inputData.getString("title") ?: "Book Borrowed"
        val dueDate = inputData.getString("dueDate") ?: "Unknown Date"

        val notificationManager = context.getSystemService(NotificationManager::class.java)

        val notification = NotificationCompat.Builder(context, "download_channel")
            .setContentTitle("Return Reminder: $title")
            .setContentText("Return \"$title\" by $dueDate")
            .setSmallIcon(logoImage) // ✅ Replace with a valid drawable
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText("Your due date for returning \"$title\" is tomorrow ($dueDate). Please return it on time.")
            )
            .setAutoCancel(true)
            .build()

        notificationManager.notify(2, notification)

        return Result.success()
    }
}

// ✅ Notification Library (Schedules Notifications)
class NotificationLibrary(private val context: Context) {

    fun showNotificationBorrowed(title: String, dueDate: String) {
        createNotificationChannels(context) // ✅ Ensure channels exist before scheduling notifications

        val workRequest = OneTimeWorkRequestBuilder<NotificationWorkerBorrowed>()
            .setInitialDelay(1, TimeUnit.SECONDS)
            .setInputData(
                workDataOf(
                    "title" to title,
                    "dueDate" to dueDate
                )
            )
            .build()
        WorkManager.getInstance(context).enqueue(workRequest)
    }

    fun showNotificationReturned(title: String, dueDate: String) {
        createNotificationChannels(context) // ✅ Ensure channels exist before scheduling notifications

        val workRequest = OneTimeWorkRequestBuilder<NotificationWorkerReturned>()
            .setInitialDelay(6, TimeUnit.DAYS)
            .setInputData(
                workDataOf(
                    "title" to title,
                    "dueDate" to dueDate
                )
            )
            .build()
        WorkManager.getInstance(context).enqueue(workRequest)
    }
}
