package com.forthpro.millionsport.ui.favourite.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.forthpro.millionsport.R
import com.forthpro.millionsport.app.MyApplication
import com.forthpro.millionsport.model.RequestBodies
import com.forthpro.millionsport.model.response.FavouriteCommonResponse
import com.forthpro.millionsport.repository.AppRepository
import com.forthpro.millionsport.util.Event
import com.forthpro.millionsport.util.Resource
import com.forthpro.millionsport.util.Utils
import kotlinx.coroutines.launch
import java.io.IOException

class FavouriteViewModel(app: Application, private val appRepository: AppRepository) :
    AndroidViewModel(app) {

    private val _getFavResponse = MutableLiveData<Event<Resource<FavouriteCommonResponse>>>()
    val getFavResponse: LiveData<Event<Resource<FavouriteCommonResponse>>> = _getFavResponse

    fun getFav(body: RequestBodies.GetNotificationBody) = viewModelScope.launch {
        getFavData(body)
    }

    private suspend fun getFavData(body: RequestBodies.GetNotificationBody) {
        _getFavResponse.postValue(Event(Resource.Loading()))
        try {
            if (Utils.hasInternetConnection(getApplication<MyApplication>())) {
                val response = appRepository.getFavData(body)
                _getFavResponse.postValue(response?.let { handleResponse(it) })
            } else {
                _getFavResponse.postValue(
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
                    _getFavResponse.postValue(
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
                    _getFavResponse.postValue(
                        Event(
                            Resource.Error(t.localizedMessage)
                        )
                    )
                }
            }
        }
    }

    private fun handleResponse(response: retrofit2.Response<FavouriteCommonResponse>): Event<Resource<FavouriteCommonResponse>>? {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Event(Resource.Success(resultResponse))
            }
        }
        return Event(Resource.Error(response.message()))
    }
}