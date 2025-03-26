package com.example.libtrack.backend

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.R
import androidx.core.app.NotificationCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.ListenableWorker.Result
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.libtrack.errorHandling.logoImage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit


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

class NotificationWorker(
    context: Context,
    workerParams: WorkerParameters
) : Worker(context, workerParams) {

    override fun doWork(): Result {
        showNotification()
        return Result.success()
    }

    private fun showNotification() {
        val channelId = "reminder_channel"
        val notificationManager =
            applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val notification = NotificationCompat.Builder(applicationContext, channelId)
            .setContentTitle("The Book You Borrowed Is Due Soon")
            .setContentText("Ignore this message if you already returned the book.")
            .setSmallIcon(logoImage)  // Ensure you have this drawable in your resources
            .setPriority(NotificationManager.IMPORTANCE_HIGH)
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText("Make sure to return the book before the deadline which is tomorrow!!!")
            )
            .setAutoCancel(true)
            .build()

        notificationManager.notify(1, notification)
    }
}

fun scheduleNotification(context: Context) {
    val workManager = WorkManager.getInstance(context)

    val notificationWork = OneTimeWorkRequestBuilder<NotificationWorker>()
        .setInitialDelay(6, TimeUnit.DAYS) // Delay Deadline
        .build()

    workManager.enqueue(notificationWork)
}

