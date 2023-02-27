package com.forthpro.millionsport.network

import com.forthpro.millionsport.model.RequestBodies
import com.forthpro.millionsport.model.response.*
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface API {

    @GET("api/getAllLanguage")
    suspend fun getAllLanguage(): Response<GetAllLanguageResponse>

    @POST("api/getLanguageText")
    suspend fun getLanguageLabel(@Body body: RequestBodies.LanguageLabelBody): Response<CommonResponse>

    @GET("api/second_screen")
    suspend fun getAllTimeFormat(): Response<TimeFormatResponse>

    @POST("api/dashboard")
    suspend fun getDashboardData(@Body body: RequestBodies.DashboardBody): Response<DashboardResponse>

    @POST("api/soccerPredictionDetails")
    suspend fun getSoccerPredictionDetailsData(@Body body: RequestBodies.PredictionDetailsBody): Response<PredictionDetailResponse>

    @POST("api/hockeyPredictionDetails")
    suspend fun getHockeyPredictionDetailsData(@Body body: RequestBodies.PredictionDetailsBody): Response<PredictionDetailResponse>

    @POST("api/basketPredictionDetails")
    suspend fun getBasketPredictionDetailsData(@Body body: RequestBodies.PredictionDetailsBody): Response<PredictionDetailResponse>

    @POST("api/basketPredictionDetails")
    suspend fun getHandballPredictionDetailsData(@Body body: RequestBodies.PredictionDetailsBody): Response<PredictionDetailResponse>

    @POST("api/basketPredictionDetails")
    suspend fun getFutsalPredictionDetailsData(@Body body: RequestBodies.PredictionDetailsBody): Response<PredictionDetailResponse>

    @POST("api/basketPredictionDetails")
    suspend fun getVolleyballPredictionDetailsData(@Body body: RequestBodies.PredictionDetailsBody): Response<PredictionDetailResponse>

    @POST("api/basketPredictionDetails")
    suspend fun getRugbyLeaguePredictionDetailsData(@Body body: RequestBodies.PredictionDetailsBody): Response<PredictionDetailResponse>

    @POST("api/basketPredictionDetails")
    suspend fun getRugbyUnionPredictionDetailsData(@Body body: RequestBodies.PredictionDetailsBody): Response<PredictionDetailResponse>

    @POST("api/basketPredictionDetails")
    suspend fun getTennisPredictionDetailsData(@Body body: RequestBodies.PredictionDetailsBody): Response<PredictionDetailResponse>

    @POST("api/americanFootballPredictionDetails")
    suspend fun getAmericanFootballPredictionDetailsData(@Body body: RequestBodies.PredictionDetailsBody): Response<PredictionDetailResponse>

    @POST("api/americanFootballPredictionDetails")
    suspend fun getBaseballPredictionDetailsData(@Body body: RequestBodies.PredictionDetailsBody): Response<PredictionDetailResponse>

    @POST("api/americanFootballPredictionDetails")
    suspend fun getPesapalloPredictionDetailsData(@Body body: RequestBodies.PredictionDetailsBody): Response<PredictionDetailResponse>

    @POST("api/americanFootballPredictionDetails")
    suspend fun geteSportsPredictionDetailsData(@Body body: RequestBodies.PredictionDetailsBody): Response<PredictionDetailResponse>

}