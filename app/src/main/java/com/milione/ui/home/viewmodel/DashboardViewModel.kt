package com.milione.ui.home.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import app.milionesports.de.R
import com.milione.app.MyApplication
import com.milione.model.RequestBodies
import com.milione.model.response.DashboardResponse
import com.milione.repository.AppRepository
import com.milione.util.Event
import com.milione.util.Resource
import com.milione.util.Utils
import kotlinx.coroutines.launch
import java.io.IOException

class DashboardViewModel(app: Application, private val appRepository: AppRepository) :
    AndroidViewModel(app) {

    private val _getDashboardResponse = MutableLiveData<Event<Resource<DashboardResponse>>>()
    val getDashboardResponse: LiveData<Event<Resource<DashboardResponse>>> = _getDashboardResponse

    private val _getDashboardResponseFilter = MutableLiveData<Event<Resource<DashboardResponse>>>()
    val getDashboardResponseFilter: LiveData<Event<Resource<DashboardResponse>>> = _getDashboardResponseFilter

    fun getDashboard(body: RequestBodies.DashboardBody) = viewModelScope.launch {
        getDashboardData(body)
    }
    fun getDashboardFilter(body: RequestBodies.DashboardBody) = viewModelScope.launch {
        getDashboardDataFilter(body)
    }

    private suspend fun getDashboardData(body: RequestBodies.DashboardBody) {
        _getDashboardResponse.postValue(Event(Resource.Loading()))
        try {
            if (Utils.hasInternetConnection(getApplication<MyApplication>())) {
                val response = appRepository.getDashboardData(body)
                _getDashboardResponse.postValue(response?.let { handleLanguageLabelResponse(it) })
            } else {
                _getDashboardResponse.postValue(
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
                    _getDashboardResponse.postValue(
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
                    _getDashboardResponse.postValue(
                        Event(
                            Resource.Error(t.localizedMessage)
                        )
                    )
                }
            }
        }
    }
    private suspend fun getDashboardDataFilter(body: RequestBodies.DashboardBody) {
        _getDashboardResponseFilter.postValue(Event(Resource.Loading()))
        try {
            if (Utils.hasInternetConnection(getApplication<MyApplication>())) {
                val response = appRepository.getDashboardData(body)
                _getDashboardResponseFilter.postValue(response?.let { handleLanguageLabelResponse(it) })
            } else {
                _getDashboardResponseFilter.postValue(
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
                    _getDashboardResponseFilter.postValue(
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
                    _getDashboardResponseFilter.postValue(
                        Event(
                            Resource.Error(t.localizedMessage)
                        )
                    )
                }
            }
        }
    }

    private fun handleLanguageLabelResponse(response: retrofit2.Response<DashboardResponse>): Event<Resource<DashboardResponse>>? {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Event(Resource.Success(resultResponse))
            }
        }
        return Event(Resource.Error(response.message()))
    }
}