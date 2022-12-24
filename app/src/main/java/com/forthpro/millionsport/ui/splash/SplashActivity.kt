package com.forthpro.millionsport.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.forthpro.millionsport.config.PreferenceHelper
import com.forthpro.millionsport.databinding.ActivitySplashBinding
import com.forthpro.millionsport.ui.home.HomeActivity
import com.forthpro.millionsport.ui.language.LanguageChooseActivity

class SplashActivity : AppCompatActivity() {
    lateinit var binding: ActivitySplashBinding

    private var mHandler: Handler? = null
    private var mRunnable: Runnable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        startTimerThread()
    }

    private fun startTimerThread() {
        mHandler = Handler()
        mRunnable = Runnable {
            jumpToNextScreen()
        }
        /// Schedule the task to repeat after 0 second
        mHandler!!.postDelayed(mRunnable!!, // Runnable
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