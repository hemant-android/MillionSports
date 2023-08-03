package com.forthpro.millionsport.config

import com.forthpro.millionsport.app.MyApplication
import com.forthpro.millionsport.app.getPref
import com.forthpro.millionsport.constant.PrefKey

object PreferenceHelper {
    private val preferences: SharedPreferenceUtils by lazy { MyApplication.getPref() }

    var loggedIn: Boolean
        get() = preferences.get(PrefKey.IsLoggedIn, false)
        set(loggedIn) = preferences.set(PrefKey.IsLoggedIn, loggedIn)

    var isFavSport: Boolean
        get() = preferences.get(PrefKey.isFavSport, false)
        set(isFavSport) = preferences.set(PrefKey.isFavSport, isFavSport)

    var userId: String
        get() = preferences.get(PrefKey.userId, "")
        set(userId) = preferences.set(PrefKey.userId, userId)

    var languageHeader: String
        get() = preferences.get(PrefKey.languageHeader, "")
        set(languageHeader) = preferences.set(PrefKey.languageHeader, languageHeader)

    var timeFormat: String
        get() = preferences.get(PrefKey.timeFormat, "")
        set(timeFormat) = preferences.set(PrefKey.timeFormat, timeFormat)

    var token: String
        get() = preferences.get(PrefKey.token, "")
        set(token) = preferences.set(PrefKey.token, token)

    var deviceId: String
        get() = preferences.get(PrefKey.deviceId, "")
        set(deviceId) = preferences.set(PrefKey.deviceId, deviceId)

    var deviceToken: String
        get() = preferences.get(PrefKey.deviceToken, "")
        set(deviceToken) = preferences.set(PrefKey.deviceToken, deviceToken)
}