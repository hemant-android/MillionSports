package com.forthpro.millionsport.config

import com.forthpro.millionsport.app.MyApplication
import com.forthpro.millionsport.app.getPref
import com.forthpro.millionsport.constant.PrefKey

object PreferenceHelper {
    private val preferences: SharedPreferenceUtils by lazy { MyApplication.getPref() }
    var userId: String
        get() = preferences.get(PrefKey.userId, "")
        set(userId) = preferences.set(PrefKey.userId, userId)
}