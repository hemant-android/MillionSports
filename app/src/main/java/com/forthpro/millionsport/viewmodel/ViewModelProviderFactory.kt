package com.forthpro.millionsport.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.forthpro.millionsport.repository.AppRepository
import com.forthpro.millionsport.ui.details.viewmodel.PredictionDetailViewModel
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

        throw IllegalArgumentException("Unknown class name")
    }

}