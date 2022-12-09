package com.forthpro.millionsport.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.forthpro.millionsport.repository.AppRepository
import com.forthpro.millionsport.ui.language.viewmodel.LanguageChooseViewModel

class ViewModelProviderFactory(
    private val app: Application,
    private val appRepository: AppRepository,
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(LanguageChooseViewModel::class.java)) {
            return LanguageChooseViewModel(app, appRepository) as T
        }

        throw IllegalArgumentException("Unknown class name")
    }

}