package com.yuchew6.annoyingex.manager

import android.content.Context
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.yuchew6.annoyingex.model.Messages
import kotlin.random.Random

class ApiManager(context: Context) {
    private val queue: RequestQueue = Volley.newRequestQueue(context)
    private var exMessages: List<String> ?= null
    private var index: Int = 0

    fun getListOfMessages(onMessageReady: () -> Unit, onError: (() -> Unit)? = null) {
        val songURL = "https://raw.githubusercontent.com/echeeUW/codesnippets/master/ex_messages.json"

        val request = StringRequest (
            Request.Method.GET, songURL,
            { response ->
                // Success
                val gson = Gson()
                var listMessages = gson.fromJson(response, Messages::class.java)
                exMessages = listMessages.messages
                onMessageReady?.invoke()
            },
            {
                onError?.invoke()
            }
        )

        queue.add(request)
    }

    fun getAllMessages(): List<String>? {
        return exMessages
    }

    fun getMessage(): String {
        if (exMessages !== null) {
            index = Random.nextInt(0, exMessages!!.size)
            return exMessages!![index]
        }
        return "Unable to retrieve message"
    }

}