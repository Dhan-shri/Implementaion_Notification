package com.dhanshri.notificationimp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

/**
 * TAP ACTION
 */
class SecondActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
    }
}

/***
 * Usually notification responds to a tap by opening an activity or fragment in app that corresponds
 *
 * To do so we need to specify an intent defined with a PendingIntent object and pass it to the setContentIntent() function
 *
 */