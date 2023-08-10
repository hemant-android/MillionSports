package com.forthpro.millionsport.network

import com.forthpro.millionsport.BuildConfig
import com.forthpro.millionsport.config.PreferenceHelper
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInstance {
    companion object {
        private val retrofit by lazy {
            val logging = HttpLoggingInterceptor()
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)
            val client = OkHttpClient.Builder()
                .addInterceptor(logging)
                .addInterceptor { chain ->
                    val newRequest = chain.request().newBuilder()
                        .addHeader("language", PreferenceHelper.languageHeader)
                        .addHeader("timeFormat", PreferenceHelper.timeFormat)
                        .addHeader("userTimeZone", PreferenceHelper.userTimezone)
                        .addHeader("userTimeZone1", PreferenceHelper.userTimezoneID)
                        .build()
                    chain.proceed(newRequest)
                }
                .build()
            Retrofit.Builder()
                .baseUrl(BuildConfig.SERVER_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
        }
    }

    val retrofitApi: API? by lazy {
        retrofit.create(API::class.java)
    }
}