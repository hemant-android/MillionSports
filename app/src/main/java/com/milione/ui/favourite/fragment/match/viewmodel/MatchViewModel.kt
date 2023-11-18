package com.milione.ui.favourite.fragment.match.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import app.milionesports.de.R
import com.milione.app.MyApplication
import com.milione.model.RequestBodies
import com.milione.model.response.MatchResponse
import com.milione.repository.AppRepository
import com.milione.util.Event
import com.milione.util.Resource
import com.milione.util.Utils
import kotlinx.coroutines.launch
import java.io.IOException

class MatchViewModel(app: Application, private val appRepository: AppRepository) :
    AndroidViewModel(app) {

    private val _getFavResponse = MutableLiveData<Event<Resource<MatchResponse>>>()
    val getFavResponse: LiveData<Event<Resource<MatchResponse>>> = _getFavResponse

    private val _getFavResponseFilter = MutableLiveData<Event<Resource<MatchResponse>>>()
    val getFavResponseFilter: LiveData<Event<Resource<MatchResponse>>> = _getFavResponseFilter

    fun getFav(body: RequestBodies.FavBody) = viewModelScope.launch {
        getFavData(body)
    }

    fun getFavFilter(body: RequestBodies.FavBody) = viewModelScope.launch {
        getFavDataFilter(body)
    }

    private suspend fun getFavData(body: RequestBodies.FavBody) {
        _getFavResponse.postValue(Event(Resource.Loading()))
        try {
            if (Utils.hasInternetConnection(getApplication<MyApplication>())) {
                val response = appRepository.getMatchData(body)
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

    private suspend fun getFavDataFilter(body: RequestBodies.FavBody) {
        _getFavResponseFilter.postValue(Event(Resource.Loading()))
        try {
            if (Utils.hasInternetConnection(getApplication<MyApplication>())) {
                val response = appRepository.getMatchData(body)
                _getFavResponseFilter.postValue(response?.let { handleResponse(it) })
            } else {
                _getFavResponseFilter.postValue(
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
                    _getFavResponseFilter.postValue(
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
                    _getFavResponseFilter.postValue(
                        Event(
                            Resource.Error(t.localizedMessage)
                        )
                    )
                }
            }
        }
    }

    private fun handleResponse(response: retrofit2.Response<MatchResponse>): Event<Resource<MatchResponse>>? {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Event(Resource.Success(resultResponse))
            }
        }
        return Event(Resource.Error(response.message()))
    }
}