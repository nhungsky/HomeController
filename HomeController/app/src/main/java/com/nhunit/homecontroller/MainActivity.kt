package com.nhunit.homecontroller

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.nhunit.homecontroller.ui.splash.SplashActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        goToSplash()
    }

    private fun goToSplash() {
        // Tạo thể hiện chuyển đổi ngữ cảnh sang màn hình mời
        val intent = Intent(this, SplashActivity::class.java)
        // Bắt đầu activity mới
        startActivity(intent)
        // kết thúc activity hiện tại
        finish()
    }
}