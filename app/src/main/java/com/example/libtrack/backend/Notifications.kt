package com.example.libtrack.backend

import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.example.libtrack.errorHandling.logoImage
import java.util.concurrent.TimeUnit


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
            .setContentText("Borrowed $title")
            .setSmallIcon(logoImage)  // Replace with an actual drawable
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText("You have successfully borrowed the book $title. Please return it before $dueDate.")
            )
            .setAutoCancel(true)
            .build()

        notificationManager.notify(1, notification)

        return Result.success()
    }
}

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
            .setContentTitle("Return your Borrowed Book on Time!!")
            .setContentText("Please Return $title")
            .setSmallIcon(logoImage)  // Replace with an actual drawable
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText("Your due date for returning the book $title will be tomorrow ($dueDate), Please return the book on time. Disregard this message if you have already returned the book.")
            )
            .setAutoCancel(true)
            .build()

        notificationManager.notify(1, notification)

        return Result.success()
    }
}


class NotificationLibrary(private val context: Context) {

    fun showNotificationBorrowed(title: String, dueDate: String) {
        val workRequest = OneTimeWorkRequestBuilder<NotificationWorkerBorrowed>() // Renamed
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
        val workRequest = OneTimeWorkRequestBuilder<NotificationWorkerReturned>() // Renamed
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



