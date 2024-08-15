package com.mil.rudebot.presentation.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mil.rudebot.R
import com.mil.rudebot.domain.model.Message
import com.mil.rudebot.presentation.viewmodels.RudeBotVM
import com.mil.rudebot.ui.theme.rudeBotDarkRed
import com.mil.rudebot.ui.theme.rudeBotGrey
import com.mil.rudebot.ui.theme.rudeBotLightGrey

private const val TAG = "ChatPage"

@Composable
fun ChatPage(rudeBotViewModel: RudeBotVM) {
    Column(
        modifier = Modifier
            .background(rudeBotGrey)
            .fillMaxSize()
    ) {
        AppHeader()
        Box(
            modifier = Modifier.weight(1f),
            contentAlignment = Alignment.BottomCenter
        ) {
            MessageList(
                messageList = rudeBotViewModel.messageList
            )
        }
        MessageInput(
            onMessageSend = {
                try {
                    rudeBotViewModel.sendMessage(it)
                } catch (e: Exception) {
                    Log.i(TAG, e.message.toString())
                }
            }
        )
    }
}

@Composable
private fun MessageRow(message: Message) {
    val isModel = message.role == "model"

    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .align(if (isModel) Alignment.BottomStart else Alignment.BottomEnd)
                    .padding(
                        start = if (isModel) 8.dp else 70.dp,
                        end = if (isModel) 70.dp else 8.dp,
                        top = 8.dp,
                        bottom = 8.dp
                    )
                    .clip(RoundedCornerShape(48f))
                    .background(if (isModel) rudeBotDarkRed else rudeBotLightGrey)
                    .padding(10.dp)
            ) {
                Text(
                    text = message.message,
                    fontWeight = FontWeight.W600
                )
            }
        }
    }
}

@Composable
private fun MessageList(messageList: List<Message>) {
    LazyColumn {
        items(messageList) {
            MessageRow(message = it)
        }
    }
}

@Composable
private fun MessageInput(onMessageSend: (String) -> Unit) {
    var message by remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current

    Row(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            value = message,
            onValueChange = { message = it },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.Black,
                cursorColor = Color.Black
            ),
            trailingIcon = {
                IconButton(onClick = {
                    onMessageSend(message)
                    message = ""
                    keyboardController?.hide() // Close the keyboard
                }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.Send,
                        contentDescription = "SendBtn",
                        tint = Color.Black
                    )
                }
            }
        )
    }
}

@Composable
private fun AppHeader() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Gray)
            .padding(14.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "Rude Bot",
            color = rudeBotDarkRed,
            fontSize = 23.sp,
            fontWeight = FontWeight.Bold
        )
        Image(
            modifier = Modifier.size(50.dp),
            painter = painterResource(id = R.drawable.angrybot_back),
            contentDescription = "SendBtn",
        )
    }
}
