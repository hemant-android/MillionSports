package com.forthpro.millionsport.repository

import com.forthpro.millionsport.model.RequestBodies
import com.forthpro.millionsport.network.RetrofitInstance

class AppRepository {
    suspend fun getAllLanguageData() = RetrofitInstance().retrofitApi?.getAllLanguage()
    suspend fun getLanguageLabelData(body: RequestBodies.LanguageLabelBody) =RetrofitInstance().retrofitApi?.getLanguageLabel(body)
    suspend fun getAllTimeFormatData() = RetrofitInstance().retrofitApi?.getAllTimeFormat()
    suspend fun getSideMenuData() = RetrofitInstance().retrofitApi?.getSideMenuData()
    suspend fun getNotificationData(body: RequestBodies.GetNotificationBody) =RetrofitInstance().retrofitApi?.getNotificationData(body)
    suspend fun getFavouriteSportData(body: RequestBodies.GetNotificationBody) =RetrofitInstance().retrofitApi?.getFavouriteSPortData(body)
    suspend fun updateSportData(body: RequestBodies.UpdatedSportItemBody) =RetrofitInstance().retrofitApi?.updateSPortItemData(body)
    suspend fun updateNotificationData(body: RequestBodies.UpdatedNotificationBody) =RetrofitInstance().retrofitApi?.updateNotificationData(body)
    suspend fun getDashboardData(body: RequestBodies.DashboardBody) =RetrofitInstance().retrofitApi?.getDashboardData(body)
    suspend fun getMatchData(body: RequestBodies.FavBody) =RetrofitInstance().retrofitApi?.getMatchData(body)
    suspend fun getTeamData(body: RequestBodies.FavBody) =RetrofitInstance().retrofitApi?.getTeamData(body)
    suspend fun getCompetitionData(body: RequestBodies.FavBody) =RetrofitInstance().retrofitApi?.getCompetitionData(body)
    suspend fun getTeamListData(body: RequestBodies.GetTeamListBody) =RetrofitInstance().retrofitApi?.getTeamListData(body)
    suspend fun getCompetitionListData(body: RequestBodies.GetTeamListBody) =RetrofitInstance().retrofitApi?.getCompetitionListData(body)
    suspend fun favAddRemoveData(body: RequestBodies.FavAddRemoveBody) =RetrofitInstance().retrofitApi?.favAddRemoveData(body)
    suspend fun favAddRemoveCompetitionData(body: RequestBodies.FavAddRemoveCompetitionBody) =RetrofitInstance().retrofitApi?.favAddRemoveCompetitionData(body)
    suspend fun getFavData(body: RequestBodies.GetNotificationBody) =RetrofitInstance().retrofitApi?.getFavData(body)
    suspend fun getSoccerPredictionDetailsData(body: RequestBodies.PredictionDetailsBody) =RetrofitInstance().retrofitApi?.getSoccerPredictionDetailsData(body)
    suspend fun getHockeyPredictionDetailsData(body: RequestBodies.PredictionDetailsBody) =RetrofitInstance().retrofitApi?.getHockeyPredictionDetailsData(body)
    suspend fun getBasketPredictionDetailsData(body: RequestBodies.PredictionDetailsBody) =RetrofitInstance().retrofitApi?.getBasketPredictionDetailsData(body)
    suspend fun getHandballPredictionDetailsData(body: RequestBodies.PredictionDetailsBody) =RetrofitInstance().retrofitApi?.getHandballPredictionDetailsData(body)
    suspend fun getFutsalPredictionDetailsData(body: RequestBodies.PredictionDetailsBody) =RetrofitInstance().retrofitApi?.getFutsalPredictionDetailsData(body)
    suspend fun getVolleyballPredictionDetailsData(body: RequestBodies.PredictionDetailsBody) =RetrofitInstance().retrofitApi?.getVolleyballPredictionDetailsData(body)
    suspend fun getRugbyLeaguePredictionDetailsData(body: RequestBodies.PredictionDetailsBody) =RetrofitInstance().retrofitApi?.getRugbyLeaguePredictionDetailsData(body)
    suspend fun getRugbyUnionPredictionDetailsData(body: RequestBodies.PredictionDetailsBody) =RetrofitInstance().retrofitApi?.getRugbyUnionPredictionDetailsData(body)
    suspend fun getTennisPredictionDetailsData(body: RequestBodies.PredictionDetailsBody) =RetrofitInstance().retrofitApi?.getTennisPredictionDetailsData(body)
    suspend fun getAmericanFootballPredictionDetailsData(body: RequestBodies.PredictionDetailsBody) =RetrofitInstance().retrofitApi?.getAmericanFootballPredictionDetailsData(body)
    suspend fun getBaseballPredictionDetailsData(body: RequestBodies.PredictionDetailsBody) =RetrofitInstance().retrofitApi?.getBaseballPredictionDetailsData(body)
    suspend fun getPesapalloPredictionDetailsData(body: RequestBodies.PredictionDetailsBody) =RetrofitInstance().retrofitApi?.getPesapalloPredictionDetailsData(body)
    suspend fun geteSportsDetailsData(body: RequestBodies.PredictionDetailsBody) =RetrofitInstance().retrofitApi?.geteSportsPredictionDetailsData(body)
}