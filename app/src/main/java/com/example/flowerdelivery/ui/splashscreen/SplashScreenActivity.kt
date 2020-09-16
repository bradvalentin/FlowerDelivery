package com.example.flowerdelivery.ui.splashscreen

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.example.flowerdelivery.R
import com.example.flowerdelivery.ui.MainActivity


private const val SPLASH_DISPLAY_LENGTH = 2000

class SplashScreenActivity : AppCompatActivity() {

    private val handler: Handler by lazy { Handler() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        Handler().postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
        }, SPLASH_DISPLAY_LENGTH.toLong())

    }

    override fun onStop() {
        handler.removeCallbacksAndMessages(null)
        finish()
        super.onStop()
    }
}