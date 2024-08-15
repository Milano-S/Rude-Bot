package com.mil.rudebot.presentation.viewmodels

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.ai.client.generativeai.GenerativeModel
import com.mil.rudebot.domain.model.Message
import com.mil.rudebot.util.Consts
import kotlinx.coroutines.launch
import kotlin.random.Random

private const val TAG = "RudeBotVM"

class RudeBotVM : ViewModel() {

    private val generativeModel: GenerativeModel = GenerativeModel(
        modelName = "gemini-pro",
        apiKey = Consts.GEMINI_KEY
    )

    val messageList by lazy {
        mutableStateListOf<Message>()
    }

    fun sendMessage(message: String) {
        try {
            viewModelScope.launch {
                val chat = generativeModel.startChat()
                messageList.add(Message(message, "user"))

                // Generate an insult and a response
                val insult = Consts.insults[Random.nextInt(Consts.insults.size)]
                val response = chat.sendMessage(message)

                // Combine insult and response into one message
                val combinedMessage = "$insult\n${response.text}"

                messageList.add(Message(combinedMessage, "model"))
                Log.i(TAG, "Response: $combinedMessage")
            }
        } catch (e: Exception) {
            Log.i(TAG, e.message.toString())
        }
    }



}