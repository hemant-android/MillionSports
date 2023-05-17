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

    @POST("api/getSideBar")
    suspend fun getSideMenuData(): Response<SideMenuResponse>

    @POST("api/dashboard")
    suspend fun getDashboardData(@Body body: RequestBodies.DashboardBody): Response<DashboardResponse>

    @POST("api/favouriteMatch")
    suspend fun getMatchData(@Body body: RequestBodies.FavBody): Response<MatchResponse>

    @POST("api/favouriteTeam")
    suspend fun getTeamData(@Body body: RequestBodies.FavBody): Response<TeamResponse>

    @POST("api/favouriteTeam")
    suspend fun getCompetitionData(@Body body: RequestBodies.FavBody): Response<CompetitionResponse>

    @POST("api/favouriteCommon")
    suspend fun getFavData(): Response<FavouriteCommonResponse>

    @POST("api/soccerPredictionDetails")
    suspend fun getSoccerPredictionDetailsData(@Body body: RequestBodies.PredictionDetailsBody): Response<PredictionDetailResponse>

    @POST("api/hockeyPredictionDetails")
    suspend fun getHockeyPredictionDetailsData(@Body body: RequestBodies.PredictionDetailsBody): Response<PredictionDetailResponse>

    @POST("api/basketPredictionDetails")
    suspend fun getBasketPredictionDetailsData(@Body body: RequestBodies.PredictionDetailsBody): Response<PredictionDetailResponse>

    @POST("api/handballPredictionDetails")
    suspend fun getHandballPredictionDetailsData(@Body body: RequestBodies.PredictionDetailsBody): Response<PredictionDetailResponse>

    @POST("api/futsalPredictionDetails")
    suspend fun getFutsalPredictionDetailsData(@Body body: RequestBodies.PredictionDetailsBody): Response<PredictionDetailResponse>

    @POST("api/volleyBallPredictionDetails")
    suspend fun getVolleyballPredictionDetailsData(@Body body: RequestBodies.PredictionDetailsBody): Response<PredictionDetailResponse>

    @POST("api/rugbyPredictionDetails")
    suspend fun getRugbyLeaguePredictionDetailsData(@Body body: RequestBodies.PredictionDetailsBody): Response<PredictionDetailResponse>

    @POST("api/rugbyUnionPredictionDetails")
    suspend fun getRugbyUnionPredictionDetailsData(@Body body: RequestBodies.PredictionDetailsBody): Response<PredictionDetailResponse>

    @POST("api/tennisPredictionDetails")
    suspend fun getTennisPredictionDetailsData(@Body body: RequestBodies.PredictionDetailsBody): Response<PredictionDetailResponse>

    @POST("api/americanFootballPredictionDetails")
    suspend fun getAmericanFootballPredictionDetailsData(@Body body: RequestBodies.PredictionDetailsBody): Response<PredictionDetailResponse>

    @POST("api/baseballPredictionDetails")
    suspend fun getBaseballPredictionDetailsData(@Body body: RequestBodies.PredictionDetailsBody): Response<PredictionDetailResponse>

    @POST("api/paspalloPredictionDetails")
    suspend fun getPesapalloPredictionDetailsData(@Body body: RequestBodies.PredictionDetailsBody): Response<PredictionDetailResponse>

    @POST("api/americanFootballPredictionDetails")
    suspend fun geteSportsPredictionDetailsData(@Body body: RequestBodies.PredictionDetailsBody): Response<PredictionDetailResponse>

}