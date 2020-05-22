package com.yuchew6.annoyingex

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.yuchew6.annoyingex.manager.AENotificationManager
import com.yuchew6.annoyingex.manager.AEWorkingManager
import com.yuchew6.annoyingex.manager.ApiManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var apiManager: ApiManager
    private lateinit var  aeWorkingManager: AEWorkingManager
    private lateinit var aeNotificationManager: AENotificationManager

    companion object {
        const val TEXT_TAG = "TEXT_TAG"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //initialize App and manager
        val aeApp = application as AEApp
        apiManager = aeApp.apiManager
        aeWorkingManager = aeApp.aeWorkingManager
        aeNotificationManager = aeApp.aeNotificationManager

        // set text
        val textFromEX = intent.getStringExtra(TEXT_TAG)
        if (textFromEX != null) {
            textContent.text = textFromEX
        } else {
            textContent.text = "Messages from EX: ..."
        }

        // buttons
        btnStart.setOnClickListener {
            val messages = apiManager.getAllMessages()
            if (messages == null) {
                apiManager.getListOfMessages({
                    Toast.makeText(this, "She is texting you.", Toast.LENGTH_SHORT).show()
                }, {
                    Toast.makeText(this, "Unable to retrieve message", Toast.LENGTH_SHORT).show()
                })
            }
            aeWorkingManager.startNotification()
        }

        btnStop.setOnClickListener {
            aeWorkingManager.stopWork()
        }
    }
}


