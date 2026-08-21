package com.milione.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import app.milionesports.de.R
import com.milione.config.PreferenceHelper
import app.milionesports.de.databinding.ActivitySplashBinding
import com.google.android.gms.ads.MobileAds
import com.milione.app.MyApplication
import com.milione.ui.home.HomeActivity
import com.milione.ui.language.LanguageChooseActivity
import com.milione.util.GoogleMobileAdsConsentManager
import java.util.TimeZone
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicBoolean


class SplashActivity : AppCompatActivity() {
    lateinit var binding: ActivitySplashBinding

    private var mHandler: Handler? = null
    private var mRunnable: Runnable? = null

    private lateinit var googleMobileAdsConsentManager: GoogleMobileAdsConsentManager
    private val isMobileAdsInitializeCalled = AtomicBoolean(false)
    private var secondsRemaining: Long = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        startTimerThread()

        createTimer(COUNTER_TIME_MILLISECONDS)

        var tz = TimeZone.getDefault()
        var timeZone = tz.getDisplayName(true, TimeZone.SHORT)

        PreferenceHelper.userTimezoneID = tz.id
        PreferenceHelper.userTimezone = timeZone


        googleMobileAdsConsentManager = GoogleMobileAdsConsentManager.getInstance(applicationContext)
        googleMobileAdsConsentManager.gatherConsent(this) { consentError ->
            if (consentError != null) {
                // Consent not obtained in current session.
                Log.w(LOG_TAG, String.format("%s: %s", consentError.errorCode, consentError.message))
            }

            if (googleMobileAdsConsentManager.canRequestAds) {
                initializeMobileAdsSdk()
            }

            if (secondsRemaining <= 0) {
                startMainActivity()
            }else{
                startMainActivity()
            }
        }

        // This sample attempts to load ads using consent obtained in the previous session.
        if (googleMobileAdsConsentManager.canRequestAds) {
            initializeMobileAdsSdk()
        }
    }

    /**
     * Create the countdown timer, which counts down to zero and show the app open ad.
     *
     * @param time the number of milliseconds that the timer counts down from
     */
    private fun createTimer(time: Long) {
        val countDownTimer: CountDownTimer =
            object : CountDownTimer(time, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    secondsRemaining = TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) + 1
                }

                override fun onFinish() {
                    secondsRemaining = 0

                    (application as MyApplication).showAdIfAvailable(
                        this@SplashActivity,
                        object : MyApplication.OnShowAdCompleteListener {
                            override fun onShowAdComplete() {
                                // Check if the consent form is currently on screen before moving to the main
                                // activity.
                                if (googleMobileAdsConsentManager.canRequestAds) {
                                    startMainActivity()
                                }else{
                                    startMainActivity()
                                }
                            }
                        }
                    )
                }
            }
        countDownTimer.start()
    }
    private fun initializeMobileAdsSdk() {
        if (isMobileAdsInitializeCalled.getAndSet(true)) {
            return
        }

        // Initialize the Mobile Ads SDK.
        MobileAds.initialize(this) {}

        // Load an ad.
        (application as MyApplication).loadAd(this)
    }
    private fun startMainActivity() {
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

private const val COUNTER_TIME_MILLISECONDS = 5000L
private const val LOG_TAG = "SplashActivity"