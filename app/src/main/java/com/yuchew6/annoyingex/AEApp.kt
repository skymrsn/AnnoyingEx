package com.yuchew6.annoyingex

import android.app.Application
import com.yuchew6.annoyingex.manager.AENotificationManager
import com.yuchew6.annoyingex.manager.AEWorkingManager
import com.yuchew6.annoyingex.manager.ApiManager

class AEApp: Application() {
    lateinit var apiManager: ApiManager
        private set
    lateinit var aeNotificationManager: AENotificationManager
        private set
    lateinit var aeWorkingManager: AEWorkingManager
        private set

    override fun onCreate() {
        super.onCreate()

        apiManager = ApiManager(this)
        aeWorkingManager = AEWorkingManager(this)
        aeNotificationManager = AENotificationManager(this)
    }
}