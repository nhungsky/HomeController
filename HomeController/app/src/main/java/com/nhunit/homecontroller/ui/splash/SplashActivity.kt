package com.nhunit.homecontroller.ui.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.nhunit.homecontroller.R
import com.nhunit.homecontroller.core.firebase.FirebaseDB
import com.nhunit.homecontroller.ui.dashboard.DashboardActivity
import java.util.*

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Timer().schedule(object : TimerTask() {
            override fun run() {
                val intent = Intent(this@SplashActivity, DashboardActivity::class.java)
                startActivity(intent)
                finish()
            }
        }, 2000)

    }
}