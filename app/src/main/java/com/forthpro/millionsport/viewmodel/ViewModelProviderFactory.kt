package com.forthpro.millionsport.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.forthpro.millionsport.repository.AppRepository
import com.forthpro.millionsport.ui.change_language.viewmodel.LanguageChangeViewModel
import com.forthpro.millionsport.ui.change_time.viewmodel.ChangeTimeFormatViewModel
import com.forthpro.millionsport.ui.details.viewmodel.PredictionDetailViewModel
import com.forthpro.millionsport.ui.favourite.fragment.competition.viewmodel.CompetitionViewModel
import com.forthpro.millionsport.ui.favourite.fragment.match.viewmodel.MatchViewModel
import com.forthpro.millionsport.ui.favourite.fragment.team.viewmodel.TeamViewModel
import com.forthpro.millionsport.ui.favourite.viewmodel.FavouriteViewModel
import com.forthpro.millionsport.ui.home.viewmodel.DashboardViewModel
import com.forthpro.millionsport.ui.home.viewmodel.HomeViewModel
import com.forthpro.millionsport.ui.language.viewmodel.LanguageChooseViewModel
import com.forthpro.millionsport.ui.timing.viewmodel.TimeFormatViewModel

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

        throw IllegalArgumentException("Unknown class name")
    }

}