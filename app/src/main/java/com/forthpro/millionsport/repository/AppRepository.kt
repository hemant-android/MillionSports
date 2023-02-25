package com.forthpro.millionsport.repository

import com.forthpro.millionsport.model.RequestBodies
import com.forthpro.millionsport.network.RetrofitInstance

class AppRepository {
    suspend fun getAllLanguageData() = RetrofitInstance().retrofitApi?.getAllLanguage()
    suspend fun getLanguageLabelData(body: RequestBodies.LanguageLabelBody) =RetrofitInstance().retrofitApi?.getLanguageLabel(body)
    suspend fun getAllTimeFormatData() = RetrofitInstance().retrofitApi?.getAllTimeFormat()
    suspend fun getDashboardData(body: RequestBodies.DashboardBody) =RetrofitInstance().retrofitApi?.getDashboardData(body)
    suspend fun getSoccerPredictionDetailsData(body: RequestBodies.PredictionDetailsBody) =RetrofitInstance().retrofitApi?.getSoccerPredictionDetailsData(body)
    suspend fun getHockeyPredictionDetailsData(body: RequestBodies.PredictionDetailsBody) =RetrofitInstance().retrofitApi?.getHockeyPredictionDetailsData(body)
    suspend fun getBasketPredictionDetailsData(body: RequestBodies.PredictionDetailsBody) =RetrofitInstance().retrofitApi?.getBasketPredictionDetailsData(body)
}