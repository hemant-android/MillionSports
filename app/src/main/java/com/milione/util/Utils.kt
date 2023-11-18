@file:Suppress("UNREACHABLE_CODE")

package com.milione.util

import android.content.Context
import android.icu.text.SimpleDateFormat
import android.icu.util.TimeZone
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.provider.Settings
import android.telephony.TelephonyManager
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.milione.app.MyApplication
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*


object Utils {
    /**
     * Check network connectivity
     */
    fun hasInternetConnection(application: MyApplication): Boolean {
        val connectivityManager = application.getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val activeNetwork = connectivityManager.activeNetwork ?: return false
            val capabilities =
                connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
            return when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } else {
            connectivityManager.activeNetworkInfo?.run {
                return when (type) {
                    ConnectivityManager.TYPE_WIFI -> true
                    ConnectivityManager.TYPE_MOBILE -> true
                    ConnectivityManager.TYPE_ETHERNET -> true
                    else -> false
                }
            }
        }
        return false
    }

    private fun customToast(context: Context?, string: String?) {
        Toast.makeText(context, string, Toast.LENGTH_LONG).show()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun convertPredictionTimeToHHMM(getDate: String?, timeFormat: String): String? {
        return if (timeFormat == "1") {
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
            val date = LocalDateTime.parse(getDate, formatter)
            val formatter2 = DateTimeFormatter.ofPattern("hh:mm a")
            formatter2.format(date)
        } else {
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
            val date = LocalDateTime.parse(getDate, formatter)
            val formatter2 = DateTimeFormatter.ofPattern("HH:mm")
            formatter2.format(date)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun convertPredictionTimeCurrentTimeZone(getDate: String?, timeFormat: String): String? {
        /*return if (timeFormat == "1") {
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
            val date = LocalDateTime.parse(getDate, formatter)
            val formatter2 = DateTimeFormatter.ofPattern("hh:mm a")
            formatter2.format(date)
        } else {
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
            val date = LocalDateTime.parse(getDate, formatter)
            val formatter2 = DateTimeFormatter.ofPattern("HH:mm")
            formatter2.format(date)
        }*/

        var newDate = getDate
        return if (timeFormat == "1") {
            try {
                val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm")
                formatter.timeZone = TimeZone.getTimeZone("GMT")
                val value = formatter.parse(newDate.toString())
                val dateFormatter = SimpleDateFormat("hh:mm a")
                dateFormatter.timeZone = TimeZone.getDefault()
                newDate = dateFormatter.format(value)
            } catch (e: Exception) {
                newDate = "0000-00-00 00:00"
            }
            return newDate
        } else {
            try {
                val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm")
                formatter.timeZone = TimeZone.getTimeZone("GMT")
                val value = formatter.parse(newDate.toString())
                val dateFormatter = SimpleDateFormat("HH:mm")
                dateFormatter.timeZone = TimeZone.getDefault()
                newDate = dateFormatter.format(value)
            } catch (e: Exception) {
                newDate = "0000-00-00 00:00"
            }
            return newDate
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getCurrentDate(): String? {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        return LocalDateTime.now().format(formatter)
    }

    fun getDeviceId(context: Context): String? {
        val deviceId: String = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            Settings.Secure.getString(
                context.contentResolver,
                Settings.Secure.ANDROID_ID
            )
        } else {
            val mTelephony = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            if (mTelephony.deviceId != null) {
                mTelephony.deviceId
            } else {
                Settings.Secure.getString(
                    context.contentResolver,
                    Settings.Secure.ANDROID_ID
                )
            }
        }
        return deviceId
    }

}