package com.milione.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.milione.repository.AppRepository
import com.milione.ui.change_language.viewmodel.LanguageChangeViewModel
import com.milione.ui.change_time.viewmodel.ChangeTimeFormatViewModel
import com.milione.ui.details.viewmodel.PredictionDetailViewModel
import com.milione.ui.favourite.fragment.competition.viewmodel.CompetitionViewModel
import com.milione.ui.favourite.fragment.match.viewmodel.MatchViewModel
import com.milione.ui.favourite.fragment.team.viewmodel.TeamViewModel
import com.milione.ui.favourite.viewmodel.FavouriteViewModel
import com.milione.ui.favourite.viewmodel.GetCompetitionListViewModel
import com.milione.ui.favourite.viewmodel.GetTeamListViewModel
import com.milione.ui.favourite_sport.viewmodel.FavouriteSportViewModel
import com.milione.ui.home.viewmodel.DashboardViewModel
import com.milione.ui.home.viewmodel.HomeViewModel
import com.milione.ui.language.viewmodel.LanguageChooseViewModel
import com.milione.ui.notification.viewmodel.NotificationViewModel
import com.milione.ui.timing.viewmodel.TimeFormatViewModel

class ViewModelProviderFactory(
    private val app: Application,
    private val appRepository: AppRepository,
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(LanguageChooseViewModel::class.java)) {
            return LanguageChooseViewModel(app, appRepository) as T
        }

        if (modelClass.isAssignableFrom(TimeFormatViewModel::class.java)) {
            return TimeFormatViewModel(app, appRepository) as T
        }

        if (modelClass.isAssignableFrom(DashboardViewModel::class.java)) {
            return DashboardViewModel(app, appRepository) as T
        }

        if (modelClass.isAssignableFrom(PredictionDetailViewModel::class.java)) {
            return PredictionDetailViewModel(app, appRepository) as T
        }

        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(app, appRepository) as T
        }

        if (modelClass.isAssignableFrom(FavouriteViewModel::class.java)) {
            return FavouriteViewModel(app, appRepository) as T
        }

        if (modelClass.isAssignableFrom(LanguageChangeViewModel::class.java)) {
            return LanguageChangeViewModel(app, appRepository) as T
        }

        if (modelClass.isAssignableFrom(ChangeTimeFormatViewModel::class.java)) {
            return ChangeTimeFormatViewModel(app, appRepository) as T
        }

        if (modelClass.isAssignableFrom(MatchViewModel::class.java)) {
            return MatchViewModel(app, appRepository) as T
        }

        if (modelClass.isAssignableFrom(TeamViewModel::class.java)) {
            return TeamViewModel(app, appRepository) as T
        }

        if (modelClass.isAssignableFrom(CompetitionViewModel::class.java)) {
            return CompetitionViewModel(app, appRepository) as T
        }

        if (modelClass.isAssignableFrom(GetTeamListViewModel::class.java)) {
            return GetTeamListViewModel(app, appRepository) as T
        }

        if (modelClass.isAssignableFrom(GetCompetitionListViewModel::class.java)) {
            return GetCompetitionListViewModel(app, appRepository) as T
        }

        if (modelClass.isAssignableFrom(NotificationViewModel::class.java)) {
            return NotificationViewModel(app, appRepository) as T
        }

        if (modelClass.isAssignableFrom(FavouriteSportViewModel::class.java)) {
            return FavouriteSportViewModel(app, appRepository) as T
        }

        throw IllegalArgumentException("Unknown class name")
    }

}