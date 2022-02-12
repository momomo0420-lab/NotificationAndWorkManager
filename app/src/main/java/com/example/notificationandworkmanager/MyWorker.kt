package com.example.notificationandworkmanager

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Context
import android.content.Intent
import android.text.format.DateFormat
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

    override fun doWork(): Result {
        // 通知を作成
        val myNotification = createNotification()
        val now = getCurrentDateAndTime()
        myNotification.setContentText(now)

        // PendingIntentを作成
        val intent = Intent(context, MainActivity::class.java)
        val pendingIntent: PendingIntent? = TaskStackBuilder.create(context).run {
            addNextIntentWithParentStack(intent)
            getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)
        }
        myNotification.setContentIntent(pendingIntent)


        // 通知用チャンネルを登録
        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channel = createMyNotificationChannel()
        manager.createNotificationChannel(channel)

        // 通知を表示する
        NotificationManagerCompat
            .from(context)
            .notify(100, myNotification.build())

        return Result.success()
    }

    /**
     * 現在時刻（文字列）を取得
     *
     * @return 現在時刻（文字列）
     */
    private fun getCurrentDateAndTime(): String {
        val now = System.currentTimeMillis()
        return DateFormat.format(
            "yyyy-MM-dd hh:mm:ss",
            now
        ).toString()
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
    private fun createNotification() : NotificationCompat.Builder {
        return NotificationCompat
            .Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle("My Notification")
            .setContentText("Notification Practice")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
    }
}