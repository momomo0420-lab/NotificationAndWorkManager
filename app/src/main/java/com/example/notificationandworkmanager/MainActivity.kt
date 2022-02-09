package com.example.notificationandworkmanager

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    companion object {
        private const val CHANNEL_ID = "NOTIFICATION_CHANNEL_TEST_ID"
    }

    private lateinit var myNotification: Notification

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        myNotification = createNotification()

        // 通知用チャンネルを登録
        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channel = createMyNotificationChannel()
        manager.createNotificationChannel(channel)

        // ボタン押下時の処理登録
        val button = findViewById<Button>(R.id.button)
        button.setOnClickListener(myOnClickListener)

        // ワークマネージャーの登録
        val workRequest = createWorkerRequest()
        val workManager = WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            "test_notification",
            ExistingPeriodicWorkPolicy.KEEP,
            workRequest
        )
    }

    // ボタン押下時の処理（リスナー）
    private val myOnClickListener = View.OnClickListener {
        NotificationManagerCompat
            .from(this)
            .notify(100, myNotification)
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
            .Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle("My Notification")
            .setContentText("Notification Practice")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()
    }

    /**
     * ワーカーリクエストの作成
     *
     * @return ワーカーリクエスト
     */
    private fun createWorkerRequest() : PeriodicWorkRequest {
        return PeriodicWorkRequestBuilder<MyWorker>(
            15, TimeUnit.MINUTES
        ).build()
    }
}