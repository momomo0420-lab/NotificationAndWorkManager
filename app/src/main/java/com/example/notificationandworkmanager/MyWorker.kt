package com.example.notificationandworkmanager

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.Worker
import androidx.work.WorkerParameters

class MyWorker(
    context: Context,
    params: WorkerParameters
) : Worker(context, params) {

    companion object {
        private const val CHANNEL_ID = "NOTIFICATION_CHANNEL_TEST_ID"
    }

    private val context = applicationContext

    private lateinit var myNotification: Notification

    override fun doWork(): Result {
        myNotification = createNotification()

        // 通知用チャンネルを登録
        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channel = createMyNotificationChannel()
        manager.createNotificationChannel(channel)

        NotificationManagerCompat
            .from(context)
            .notify(100, myNotification)

        return Result.success()
    }

    /**
     * 通知用のチャンネルを作成
     *
     * @return 通知用のチャンネル
     */
    private fun createMyNotificationChannel(): NotificationChannel {
        return NotificationChannel(
            CHANNEL_ID,
            "Notification channel test",
            NotificationManager.IMPORTANCE_DEFAULT
        ).apply {
            description = "AAAAAAA"
        }
    }

    /**
     * 通知の作成
     *
     * @return 通知
     */
    private fun createNotification() : Notification {
        return NotificationCompat
            .Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle("My Notification")
            .setContentText("Notification Practice")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()
    }
}