package com.dhanshri.notificationimp

import android.app.NotificationManager
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.NotificationCompat
import androidx.core.app.RemoteInput
import androidx.databinding.DataBindingUtil
import com.dhanshri.notificationimp.databinding.ActivityReplyActionBinding

class ReplyActionActivity : AppCompatActivity() {

    private lateinit var binding : ActivityReplyActionBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_reply_action)
        receiveInput()
    }

    private fun receiveInput(){
        val KEY_REPLY = "key_reply"
        val intent = this.intent
        val remoteInput = RemoteInput.getResultsFromIntent(intent)
        if (remoteInput!= null){
            val inputString = remoteInput.getCharSequence(KEY_REPLY).toString()
            binding.replyText.text = inputString


            // To update the notification message

            val channelID = "com.dhanshri.notification.channel1"
            val notificationId = 45

            val repliedNotification = NotificationCompat.Builder(this, channelID)
                .setSmallIcon(R.drawable.info_24)
                .setContentText("Your reply recieved")
                .build()

            // GET NOTIFICATION MANAGER INSTANCE
            val notificationManger : NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManger.notify(notificationId, repliedNotification)
        }
    }
}