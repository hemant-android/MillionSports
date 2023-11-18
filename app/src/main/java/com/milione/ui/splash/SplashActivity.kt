package com.milione.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.milione.config.PreferenceHelper
import app.milionesports.de.databinding.ActivitySplashBinding
import com.milione.ui.home.HomeActivity
import com.milione.ui.language.LanguageChooseActivity
import java.util.TimeZone


class SplashActivity : AppCompatActivity() {
    lateinit var binding: ActivitySplashBinding

    private var mHandler: Handler? = null
    private var mRunnable: Runnable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        startTimerThread()

        var tz = TimeZone.getDefault()
        var timeZone = tz.getDisplayName(true, TimeZone.SHORT)

        PreferenceHelper.userTimezoneID = tz.id
        PreferenceHelper.userTimezone = timeZone

    }

    private fun startTimerThread() {
        mHandler = Handler()
        mRunnable = Runnable {
            jumpToNextScreen()
        }
        /// Schedule the task to repeat after 0 second
        mHandler!!.postDelayed(
            mRunnable!!, // Runnable
            2000 // Delay in milliseconds
        )
    }

    private fun jumpToNextScreen() {
        if (PreferenceHelper.loggedIn) {
            Intent(this, HomeActivity::class.java).also {
                startActivity(it)
                finish()
            }
        } else {
            Intent(this, LanguageChooseActivity::class.java).also {
                startActivity(it)
                finish()
            }
        }

    }
}