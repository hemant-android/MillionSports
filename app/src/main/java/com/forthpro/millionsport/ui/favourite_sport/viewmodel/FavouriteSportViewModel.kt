package com.forthpro.millionsport.ui.favourite_sport.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.forthpro.millionsport.R
import com.forthpro.millionsport.app.MyApplication
import com.forthpro.millionsport.model.RequestBodies
import com.forthpro.millionsport.model.response.CommonResponse
import com.forthpro.millionsport.model.response.FavouriteSportResponse
import com.forthpro.millionsport.model.response.NotificationResponse
import com.forthpro.millionsport.repository.AppRepository
import com.forthpro.millionsport.util.Event
import com.forthpro.millionsport.util.Resource
import com.forthpro.millionsport.util.Utils
import kotlinx.coroutines.launch
import java.io.IOException

class FavouriteSportViewModel(app: Application, private val appRepository: AppRepository) :
    AndroidViewModel(app) {

    private val _getFavouriteResponse = MutableLiveData<Event<Resource<FavouriteSportResponse>>>()
    val getFavouriteResponse: LiveData<Event<Resource<FavouriteSportResponse>>> =_getFavouriteResponse

    private val _updateNotificationResponse = MutableLiveData<Event<Resource<CommonResponse>>>()
    val updateNotificationResponse: LiveData<Event<Resource<CommonResponse>>> = _updateNotificationResponse

    fun getFavouriteSportItem(body: RequestBodies.GetNotificationBody) = viewModelScope.launch {
        getFavouriteData(body)
    }

    fun updateNotificationItem(body: RequestBodies.UpdatedNotificationBody) =
        viewModelScope.launch {
            updateNotificationData(body)
        }

    private suspend fun getFavouriteData(body: RequestBodies.GetNotificationBody) {
        _getFavouriteResponse.postValue(Event(Resource.Loading()))
        try {
            if (Utils.hasInternetConnection(getApplication<MyApplication>())) {
                val response = appRepository.getFavouriteSportData(body)
                _getFavouriteResponse.postValue(response?.let { handleResponse(it) })
            } else {
                _getFavouriteResponse.postValue(
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
                    _getFavouriteResponse.postValue(
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
                    _getFavouriteResponse.postValue(
                        Event(
                            Resource.Error(t.localizedMessage)
                        )
                    )
                }
            }
        }
    }

    private suspend fun updateNotificationData(body: RequestBodies.UpdatedNotificationBody) {
        _updateNotificationResponse.postValue(Event(Resource.Loading()))
        try {
            if (Utils.hasInternetConnection(getApplication<MyApplication>())) {
                val response = appRepository.updateNotificationData(body)
                _updateNotificationResponse.postValue(response?.let {
                    handleUpdateNotificationResponse(
                        it
                    )
                })
            } else {
                _updateNotificationResponse.postValue(
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
                    _updateNotificationResponse.postValue(
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
                    _updateNotificationResponse.postValue(
                        Event(
                            Resource.Error(t.localizedMessage)
                        )
                    )
                }
            }
        }
    }


    private fun handleResponse(response: retrofit2.Response<FavouriteSportResponse>): Event<Resource<FavouriteSportResponse>>? {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Event(Resource.Success(resultResponse))
            }
        }
        return Event(Resource.Error(response.message()))
    }

    private fun handleUpdateNotificationResponse(response: retrofit2.Response<CommonResponse>): Event<Resource<CommonResponse>>? {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Event(Resource.Success(resultResponse))
            }
        }
        return Event(Resource.Error(response.message()))
    }
}