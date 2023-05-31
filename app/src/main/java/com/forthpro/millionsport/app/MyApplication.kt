package com.forthpro.millionsport.app

import android.app.Activity
import android.app.Application
import android.provider.Settings
import com.forthpro.millionsport.config.PreferenceHelper
import com.forthpro.millionsport.config.SharedPreferenceUtils

class MyApplication : Application() {
    private var mCurrentActivity: Activity? = null
    val preference by lazy { SharedPreferenceUtils(this) }

    override fun onCreate() {
        super.onCreate()
        application = this

        val deviceId: String = Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID)

        PreferenceHelper.deviceId = deviceId

    }

    companion object {
        lateinit var application: MyApplication

        @JvmStatic
        fun getApp() = application
    }

    fun getCurrentActivity(): Activity? {
        return mCurrentActivity
    }

    fun setCurrentActivity(mCurrentActivity: Activity?) {
        this.mCurrentActivity = mCurrentActivity
    }
}

fun Any.getPref(): SharedPreferenceUtils {
    return MyApplication.getApp().preference
}