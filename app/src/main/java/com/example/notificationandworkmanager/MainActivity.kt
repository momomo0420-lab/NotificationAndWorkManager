package com.example.notificationandworkmanager

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequest
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // ボタン押下時の処理登録
        val button = findViewById<Button>(R.id.button)
        button.setOnClickListener(myOnClickListener)

        // テストコメント2
    }

    // ボタン押下時の処理（リスナー）
    private val myOnClickListener = View.OnClickListener {
        // ワークマネージャーの登録
        val workRequest = createWorkerRequest()
        WorkManager
            .getInstance(this)
            .enqueueUniquePeriodicWork(
                "test_notification",
                ExistingPeriodicWorkPolicy.KEEP,
                workRequest
            )
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