package com.forthpro.millionsport.repository

import com.forthpro.millionsport.model.RequestBodies
import com.forthpro.millionsport.network.RetrofitInstance

class AppRepository {
    suspend fun getAllLanguageData() = RetrofitInstance().retrofitApi?.getAllLanguage()
    suspend fun getLanguageLabelData(body: RequestBodies.LanguageLabelBody) =
        RetrofitInstance().retrofitApi?.getLanguageLabel(body)
}