package com.forthpro.millionsport.util

import android.content.Context
import android.icu.text.SimpleDateFormat
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.forthpro.millionsport.app.MyApplication
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
    fun getWeekName(getDate: String?): String? {
        val sdfWeekName = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
        val dateWeekName = sdfWeekName.parse(getDate)
        sdfWeekName.applyPattern("EEE")
        return sdfWeekName.format(dateWeekName)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getDayMonth(getDate: String?): String? {
        val sdfWeekName = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
        val dateWeekName = sdfWeekName.parse(getDate)
        sdfWeekName.applyPattern("d MMM")
        return sdfWeekName.format(dateWeekName)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getCurrentDate(): String? {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        return LocalDateTime.now().format(formatter)
    }

}