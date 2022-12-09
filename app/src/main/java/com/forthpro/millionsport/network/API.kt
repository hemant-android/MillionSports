package com.forthpro.millionsport.network

import com.forthpro.millionsport.model.RequestBodies
import com.forthpro.millionsport.model.response.CommonResponse
import com.forthpro.millionsport.model.response.GetAllLanguageResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface API {

    @GET("api/getAllLanguage")
    suspend fun getAllLanguage(): Response<GetAllLanguageResponse>

    @POST("api/getLanguageText")
    suspend fun getLanguageLabel(@Body body: RequestBodies.LanguageLabelBody): Response<CommonResponse>


}