package com.forthpro.millionsport.ui.home.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.forthpro.millionsport.R
import com.forthpro.millionsport.app.MyApplication
import com.forthpro.millionsport.model.RequestBodies
import com.forthpro.millionsport.model.response.DashboardResponse
import com.forthpro.millionsport.model.response.SideMenuResponse
import com.forthpro.millionsport.repository.AppRepository
import com.forthpro.millionsport.util.Event
import com.forthpro.millionsport.util.Resource
import com.forthpro.millionsport.util.Utils
import kotlinx.coroutines.launch
import java.io.IOException

class HomeViewModel(app: Application, private val appRepository: AppRepository) :
    AndroidViewModel(app) {

    private val _getSideMenuResponse = MutableLiveData<Event<Resource<SideMenuResponse>>>()
    val getSideMenuResponse: LiveData<Event<Resource<SideMenuResponse>>> = _getSideMenuResponse

    fun getSideMenuItem() = viewModelScope.launch {
        getSideMenuData()
    }

    private suspend fun getSideMenuData() {
        _getSideMenuResponse.postValue(Event(Resource.Loading()))
        try {
            if (Utils.hasInternetConnection(getApplication<MyApplication>())) {
                val response = appRepository.getSideMenuData()
                _getSideMenuResponse.postValue(response?.let { handleLanguageLabelResponse(it) })
            } else {
                _getSideMenuResponse.postValue(
                    Event(
                        Resource.Error(
                            getApplication<MyApplication>().getString(
                                R.string.no_internet_connection
                            )
                        )
                    )
                )
            }
        } catch (t: Throwable) {
            when (t) {
                is IOException -> {
                    _getSideMenuResponse.postValue(
                        Event(
                            Resource.Error(
                                getApplication<MyApplication>().getString(
                                    R.string.network_failure
                                )
                            )
                        )
                    )
                }
                else -> {
                    _getSideMenuResponse.postValue(
                        Event(
                            Resource.Error(t.localizedMessage)
                        )
                    )
                }
            }
        }
    }


    private fun handleLanguageLabelResponse(response: retrofit2.Response<SideMenuResponse>): Event<Resource<SideMenuResponse>>? {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Event(Resource.Success(resultResponse))
            }
        }
        return Event(Resource.Error(response.message()))
    }
}