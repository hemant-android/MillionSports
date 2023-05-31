package com.forthpro.millionsport.ui.notification.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.forthpro.millionsport.R
import com.forthpro.millionsport.app.MyApplication
import com.forthpro.millionsport.model.RequestBodies
import com.forthpro.millionsport.model.response.CommonResponse
import com.forthpro.millionsport.model.response.NotificationResponse
import com.forthpro.millionsport.repository.AppRepository
import com.forthpro.millionsport.util.Event
import com.forthpro.millionsport.util.Resource
import com.forthpro.millionsport.util.Utils
import kotlinx.coroutines.launch
import java.io.IOException

class NotificationViewModel(app: Application, private val appRepository: AppRepository) :
    AndroidViewModel(app) {

    private val _getNotificationResponse = MutableLiveData<Event<Resource<NotificationResponse>>>()
    val getNotificationResponse: LiveData<Event<Resource<NotificationResponse>>> =
        _getNotificationResponse

    private val _updateNotificationResponse = MutableLiveData<Event<Resource<CommonResponse>>>()
    val updateNotificationResponse: LiveData<Event<Resource<CommonResponse>>> =
        _updateNotificationResponse

    fun getNotificationItem(body: RequestBodies.GetNotificationBody) = viewModelScope.launch {
        getNotificationData(body)
    }

    fun updateNotificationItem(body: RequestBodies.UpdatedNotificationBody) =
        viewModelScope.launch {
            updateNotificationData(body)
        }

    private suspend fun getNotificationData(body: RequestBodies.GetNotificationBody) {
        _getNotificationResponse.postValue(Event(Resource.Loading()))
        try {
            if (Utils.hasInternetConnection(getApplication<MyApplication>())) {
                val response = appRepository.getNotificationData(body)
                _getNotificationResponse.postValue(response?.let { handleResponse(it) })
            } else {
                _getNotificationResponse.postValue(
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
                    _getNotificationResponse.postValue(
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
                    _getNotificationResponse.postValue(
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


    private fun handleResponse(response: retrofit2.Response<NotificationResponse>): Event<Resource<NotificationResponse>>? {
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