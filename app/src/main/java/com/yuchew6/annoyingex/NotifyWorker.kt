package com.yuchew6.annoyingex

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters

class NotifyWorker(private val context: Context, workParams: WorkerParameters): Worker(context , workParams)  {

    private val apiManager =(context.applicationContext as AEApp).apiManager
    private val aeNotificationManager =(context.applicationContext as AEApp).aeNotificationManager

    override fun doWork(): Result {

        var message: String = apiManager.getMessage()
        if (message.isNotEmpty()) {
            aeNotificationManager.postMessages(message)
        }

        return Result.success()
    }
}