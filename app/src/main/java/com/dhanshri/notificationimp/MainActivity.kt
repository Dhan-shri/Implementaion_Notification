package com.dhanshri.notificationimp

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.NotificationCompat
import androidx.core.app.RemoteInput
import androidx.databinding.DataBindingUtil
import com.dhanshri.notificationimp.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    /**
     * Before write codes to send a notification, we have to first write codes to create a notification channels.\
     * Define the channel id to notification for channel
     *
     */

    private val channelID = "com.dhanshri.notificationimp.channel1"    // notification manager Instance which is required to cre4ate channel
    private var notificationManager: NotificationManager? = null   // Define this as var, becaue we are get this using system server, there is chance for it to be reassigned
    private val KEY_REPLY = "key_reply"       // Key for the reply text

    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        createNotificationChannel(channelID, "myChannel", "This is a demo of implementation of notification ")
        /***
         * / Notification channel has an ID a channel name and channel description
         * We create a Notification channel instance and then passing that instance to the createNotificationChannel() of the NotificationManager class
         */



        binding.button.setOnClickListener {
            displayNotification()
        }
    }

    @SuppressLint("UnspecifiedImmutableFlag")
    fun displayNotification(){
        val notificationId = 45

        /***
         *  Lets create an Intent configured to launch the SECONDACTIVITY
         *  create a PendingIntent instance, including this intent instance.
         *  We use PendingIntent object when we want to use the intent at some point in the future
         */

        val tapResultIntent = Intent(this, SecondActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_FORWARD_RESULT
        }
        val pendingIntent: PendingIntent = PendingIntent.getActivity(
            this,
            0,
            tapResultIntent,
            PendingIntent.FLAG_IMMUTABLE     /*** what flag does is, when the system create a new intent, if pendingIntent is already exist in the memory system. Keep it but replace its extra data with what is in this new intent  */
        )

        // ACTION BUTTON to display details activity
        val intent2 = Intent(this, DetailsActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_FORWARD_RESULT
        }
        val pendingIntent2: PendingIntent = PendingIntent.getActivity(
            this,
            0,
            intent2,
            PendingIntent.FLAG_IMMUTABLE
        )
        val action2 : NotificationCompat.Action = NotificationCompat.Action.Builder(0, "Details", pendingIntent2).build() // action to open the details activity


        //Action
        val intent3 = Intent(this, SettingsActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_FORWARD_RESULT
        }
        val pendingIntent3: PendingIntent = PendingIntent.getActivity(
            this,
            0,
            intent3,
            PendingIntent.FLAG_IMMUTABLE
        )
        val action3 : NotificationCompat.Action = NotificationCompat.Action.Builder(0, "Settings", pendingIntent3).build()


        // Reply action

        val intent4 = Intent(this, ReplyActionActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_FORWARD_RESULT
        }
        val pendingIntent4: PendingIntent = PendingIntent.getActivity(
            this,
            0,
            intent4,
            PendingIntent.FLAG_MUTABLE
        )
        // REPLY ACTION
        val remoteInput : RemoteInput = RemoteInput.Builder(KEY_REPLY).run {
            setLabel("Insert your name here")   // reply label hint
            build()
        }
        // create action for remoteInput
        val replyAction : NotificationCompat.Action = NotificationCompat.Action.Builder(
            0,
            "REPLY",
            pendingIntent4,
        ).addRemoteInput(remoteInput)
            .build()


        // NOTIFICATION OBJECT
        val notification = NotificationCompat.Builder(this@MainActivity, channelID)
            .setContentTitle("Demo Notification")
            .setContentText("This is the demo for implementation of notification in application")
            .setSmallIcon(R.drawable.info_24)
            .setAutoCancel(true)  // Auto cancel true, so that notification will be automatically canceled, when user click on the panel
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent) // tap action
            .addAction(action2)
            .addAction(action3)
            .addAction(replyAction)
            .build()

        notificationManager?.notify(notificationId, notification)
    }

    /***
     * This function should have 3 parameters, id, name, channelDescription
     */
    private fun createNotificationChannel(id : String, name : String, channelDescription : String){
        // SDK version should be android oreo or above

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            // This parameter determines how to interrupt the user for any notification belong to this channel
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(id, name, importance).apply {
                description = channelDescription
            }
            notificationManager?.createNotificationChannel(channel)
        }

    }
}


/***
 * Notification comes under behaviour components of JetPack.
 * Notification is message  that our app displays outside of its user interface
 *
 */


//Note

/***
 * Add direct reply to action to a notification,
 * This help allows us to facillitate the user to reply to a message without opening an activity or a fragment
 *
 */


/***
 * To update the notification message we have to use the same channel Id and notification id we used in the mainActivity
 */