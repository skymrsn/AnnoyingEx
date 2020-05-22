package com.yuchew6.annoyingex.manager

import android.content.Context
import androidx.work.*
import com.yuchew6.annoyingex.NotifyWorker
import java.util.concurrent.TimeUnit

class AEWorkingManager(context: Context) {

    private var workManager = WorkManager.getInstance(context)

    fun startNotification() {
        if (isAERunning()) {
            stopWork()
        }

        val constraints = Constraints.Builder()
            .setRequiresCharging(true)
            .build()

        val constraintsExtraCredit = Constraints.Builder()
            .setRequiresBatteryNotLow(true)
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val workRequest = PeriodicWorkRequestBuilder<NotifyWorker>(20, TimeUnit.MINUTES)
            .setInitialDelay(5000, TimeUnit.MILLISECONDS)
            .setConstraints(constraints)
            .addTag(AE_WORK_REQUEST_TAG)
            .build()

        val workRequestExtraCredit = PeriodicWorkRequestBuilder<NotifyWorker>(2, TimeUnit.DAYS)
            .setInitialDelay(5000, TimeUnit.MILLISECONDS)
            .setConstraints(constraintsExtraCredit)
            .addTag(AE_WORK_REQUEST_TAG)
            .build()


        workManager.enqueue(workRequest)

    }

    private fun isAERunning(): Boolean {
        return when (workManager.getWorkInfosByTag(AE_WORK_REQUEST_TAG).get().firstOrNull()?.state) {
            WorkInfo.State.RUNNING,
            WorkInfo.State.ENQUEUED -> true
            else -> false
        }
    }

     fun stopWork() {
        workManager.cancelAllWorkByTag(AE_WORK_REQUEST_TAG)
    }

    companion object {
        const val AE_WORK_REQUEST_TAG = "AE_WORK_REQUEST_TAG"
    }
}