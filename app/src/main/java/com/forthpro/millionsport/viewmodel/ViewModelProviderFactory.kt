package com.forthpro.millionsport.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.demoapp.ui.viewmodel.LoginViewModel
import com.forthpro.millionsport.repository.AppRepository
import com.forthpro.millionsport.ui.main.viewmodel.MainViewModel

class ViewModelProviderFactory(
    private val app: Application,
    private val appRepository: AppRepository,
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(app, appRepository) as T
        }

        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(app, appRepository) as T
        }

        throw IllegalArgumentException("Unknown class name")
    }

}